package no.lau.vdvil

import org.junit.Test
import actors.Actor
import no.lau.tagger.scala.model._

class DownloadFilesActorTest {
  @Test def downloadFilesActor {
    val urls = "holden-nothing-93_returning_mix.dvl" ::
            //"unfinished_sympathy.dvl" ::
            "olive-youre_not_alone.dvl" ::
            "christian_cambas-it_scares_me.dvl" ::
            Nil


    val coordinator = new DownloadCoordinatorActor
    coordinator.start

    val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
    urls.foreach(url => new DownloadActor(baseUrl + url, coordinator).start)
  }


}

class DownloadActor(url: String, coordinator: Actor) extends Actor {
  val cacheHandler = new no.lau.vdvil.cache.ScalaCacheHandler()

  def act() {
    coordinator ! DownloadingDvl(Dvl(url))
    val unconvertedSong:ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(url, null)
    coordinator ! ConvertingAndAddingMissingIds(Dvl(url))
    val song = no.lau.vdvil.gui.NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    val pathToMp3Option: Option[String] = cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    val finSong = new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)
    coordinator ! FinishedDownloading(finSong)
  }
}

class DownloadCoordinatorActor extends Actor {
  def act() {
    loop {
      react {
        case downloading: DownloadingDvl => println("Downloading: " + downloading.dvl.url)
        case converting: ConvertingAndAddingMissingIds => println("Converting: " + converting.dvl.url)
        case finished: FinishedDownloading => println("Finished downloading: " + finished.song.mediaFile.fileName)
        case error: ErrorDownloading => println("Error downloading: " + error.message)
      }
    }
  }
}
case class Dvl(url:String)
case class DownloadingDvl(dvl:Dvl)
case class ConvertingAndAddingMissingIds(dvl:Dvl)
case class FinishedDownloading(song:ScalaSong)
case class ErrorDownloading(message:String)




