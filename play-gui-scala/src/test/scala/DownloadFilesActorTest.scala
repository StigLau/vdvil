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
  def act() {
    println("Downloading " + url)
    val song = getSong(url)
    coordinator ! "woot"

  }

  def getSong(url: String): ScalaSong = {
    val cacheHandler = new no.lau.vdvil.cache.ScalaCacheHandler()
    val unconvertedSong:ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(url, null)
    val song = no.lau.vdvil.gui.NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    val pathToMp3Option: Option[String] = cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    return new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)
  }

}

class DownloadCoordinatorActor extends Actor {
  def act() {
    loop {
      react {
        case message:String => println("downloaded " + message)
        case song: ScalaSong => {
          println("downloaded " + song)
        }
      }
    }
  }
}



