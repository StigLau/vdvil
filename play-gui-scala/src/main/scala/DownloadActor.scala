package no.lau.vdvil

import actors.Actor
import cache.ScalaCacheHandler
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import no.lau.vdvil.gui.NeatStuff


class DownloadActor(url: String, coordinator: Actor) extends Actor {
  val cacheHandler = new ScalaCacheHandler()

  def act() {
    coordinator ! DownloadingDvl(Dvl(url))
    val unconvertedSong: ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(url, null)
    coordinator ! ConvertingAndAddingMissingIds(Dvl(url))
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).foreach {
      pathToMp3 => coordinator ! FinishedDownloading(new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm))
    }
  }
}

class DownloadCoordinatorActor(numberOfFilesToDownload: Int) extends Actor {
  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def act() {
    loop {
      react {
        case downloading: DownloadingDvl => println("Downloading: " + downloading.dvl.url)
        case converting: ConvertingAndAddingMissingIds => println("Converting: " + converting.dvl.url)
        case finished: FinishedDownloading => {
          println("Finished downloading: " + finished.song.mediaFile.fileName)
          songSet += finished.song
          if (songSet.size >= numberOfFilesToDownload) {
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

case class Dvl(url: String)
case class DownloadingDvl(dvl: Dvl)
case class ConvertingAndAddingMissingIds(dvl: Dvl)
case class FinishedDownloading(song: ScalaSong)
case class ErrorDownloading(message: String)
