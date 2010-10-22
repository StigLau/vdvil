package no.lau.vdvil.downloading

import actors.Actor
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import no.lau.vdvil.gui.NeatStuff
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.vdvil.player.Song

class DownloadActor(dvl:Dvl, coordinator: Actor) extends Actor {
  val cacheHandler = new ScalaCacheHandler()

  def act() {
    coordinator ! DownloadingDvl(dvl)
    val unconvertedSong: ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(dvl.url, null)
    coordinator ! ConvertingAndAddingMissingIds(dvl)
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    coordinator ! DownloadingMp3(dvl)
    cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).foreach {
      pathToMp3 => coordinator ! FinishedDownloading(dvl, new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm))
    }
  }
}

class DownloadCoordinatorActor(song:Song, label:DvlLabel) extends Actor {
  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def isStillDownloading = songSet.size < song.dvls.size

  def act() {
    loop {
      react {
        case downloading: DownloadingDvl => label.setLabel(downloading.dvl, "Downloading Dvl" + downloading.dvl.name)
        case downloading: DownloadingMp3 => label.setLabel(downloading.dvl, "Downloading Mp3 " + downloading.dvl.name)
        case converting: ConvertingAndAddingMissingIds => label.setLabel(converting.dvl, "Converting " + converting.dvl.name)
        case finished: FinishedDownloading => {
          label.setLabel(finished.dvl, finished.dvl.name + " Finished")
          songSet += finished.song
          if (!isStillDownloading) {
            println("Stopping Coordinator")
            exit()
          }
        }
        case error: ErrorDownloading => {
          println("Error downloading: " + error.message)
          println("Will now stop downloading procedure")
          exit()
        }
      }
    }
  }
}

class DownloaderWorkerPane(song:Song, labelHolder:DvlLabel) {
  def start {
    val coordinator = new DownloadCoordinatorActor(song, labelHolder)
    coordinator.start
    song.dvls.foreach(dvl => new DownloadActor(dvl, coordinator).start)
    /*
    while(coordinator.isStillDownloading) {
      Thread.sleep(500)
    }
    frame.visible_=(false)
    */
  }
}

case class Dvl(url: String, name:String)
case class DownloadingDvl(dvl: Dvl)
case class DownloadingMp3(dvl: Dvl)
case class ConvertingAndAddingMissingIds(dvl: Dvl)
case class FinishedDownloading(dvl:Dvl, song: ScalaSong)
case class ErrorDownloading(message: String)
