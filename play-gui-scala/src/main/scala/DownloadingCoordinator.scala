package no.lau.vdvil.downloading

import actors.Actor
import swing.TabbedPane.Page
import no.lau.vdvil.player._
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.tagger.scala.model. {ScalaSegment, ScalaMediaFile, ScalaSong}
import no.lau.vdvil.gui. {TagGUI, NeatStuff}

class DownloadingCoordinator(masterMix: MasterMix, callBack:DVLCallBack) extends Actor {

  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def isStillDownloading = songSet.size < masterMix.parts.size

  def act {
    loop {
      react {
        case Start => {
          callBack.visible(true)
          masterMix.dvls.foreach(dvl => new DownloadActor(dvl, this).start)
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
          PlayGUI.tabs.pages.append(new Page("PLAYPANEL", new PlayPanel(masterMix).ui))
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
  def act() {
    coordinator ! DownloadingDvl(dvl)
    val unconvertedSong: ScalaSong = ScalaCacheHandler.fetchSimpleSongAndCacheDvlAndMp3(dvl.url, null)
    coordinator ! ConvertingAndAddingMissingIds(dvl)
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    coordinator ! DownloadingMp3(dvl)
    ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).map {
      pathToMp3 => coordinator ! FinishedDownloading(dvl, new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm))
    }
  }
}

case class Start
case class Success
case class Error(message: String)
case class Dvl(url: String, name:String) {
  def getSegment(id:String):Option[ScalaSegment] = {
    println("this is " + id)

    println(song.segments.find(segment => id == segment.id))

    song.segments.find(segment => id == segment.id)
  }
  lazy val song = getSong
  private def getSong:ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(url).get
}
case class DownloadingDvl(dvl: Dvl)
case class DownloadingMp3(dvl: Dvl)
case class ConvertingAndAddingMissingIds(dvl: Dvl)
case class FinishedDownloading(dvl:Dvl, song: ScalaSong)

trait DVLCallBack {
  def setLabel(dvl: Dvl, text: String)
  def visible(value:Boolean)
}