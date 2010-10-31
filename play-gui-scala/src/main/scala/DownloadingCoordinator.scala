package no.lau.vdvil.downloading

import actors.Actor
import swing.TabbedPane.Page
import no.lau.vdvil.player._
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import no.lau.vdvil.gui.NeatStuff
import no.lau.vdvil.cache.ScalaCacheHandler

class DownloadingCoordinator(song: Song, callBack:DVLCallBack) extends Actor {

  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def isStillDownloading = songSet.size < song.dvls.size

  def act {
    loop {
      react {
        case Start => {
          callBack.visible(true)
          song.dvls.foreach(dvl => new DownloadActor(dvl, this).start)
        }
        case downloading: DownloadingDvl => callBack.setLabel(downloading.dvl, "Downloading Dvl" + downloading.dvl.name)
        case downloading: DownloadingMp3 => callBack.setLabel(downloading.dvl, "Downloading Mp3 " + downloading.dvl.name)
        case converting: ConvertingAndAddingMissingIds => callBack.setLabel(converting.dvl, "Converting " + converting.dvl.name)
        case finished: FinishedDownloading => {
          callBack.setLabel(finished.dvl, finished.dvl.name + " Finished")
          songSet += finished.song
          if (!isStillDownloading) {
            this ! Success
          }
        }
        case Success => {
          val composition = new ScalaComposition(150F, CompositionExample.parts)
          PlayGUI.tabs.pages.append(new Page("PLAYPANEL", new PlayPanel(composition).ui))
          callBack.visible(false)
          exit
        }
        case error: Error => {
          println("Error downloading: " + error.message)
          println("Will now stop downloading procedure")
          callBack.visible(false)
          exit
        }
      }
    }
  }
}

class DownloadActor(dvl:Dvl, coordinator: Actor) extends Actor {
  val cacheHandler = new ScalaCacheHandler()

  def act() {
    coordinator ! DownloadingDvl(dvl)
    val unconvertedSong: ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(dvl.url, null)
    coordinator ! ConvertingAndAddingMissingIds(dvl)
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    coordinator ! DownloadingMp3(dvl)
    cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).map {
      pathToMp3 => coordinator ! FinishedDownloading(dvl, new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm))
    }
  }
}

case class Start
case class Success
case class Error(message: String)
case class Dvl(url: String, name:String)
case class DownloadingDvl(dvl: Dvl)
case class DownloadingMp3(dvl: Dvl)
case class ConvertingAndAddingMissingIds(dvl: Dvl)
case class FinishedDownloading(dvl:Dvl, song: ScalaSong)

trait DVLCallBack {
  def setLabel(dvl: Dvl, text: String)
  def visible(value:Boolean)
}