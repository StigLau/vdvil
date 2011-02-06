package no.lau.vdvil.downloading

import actors.Actor
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.tagger.scala.model.ScalaSong
import no.lau.vdvil.gui. {NeatStuff}
import no.lau.vdvil.domain.player. {Dvl, MasterMix}
import org.slf4j.LoggerFactory

class DownloadingCoordinator(masterMix: MasterMix, callBack:DVLCallBack) extends Actor {
  val log = LoggerFactory.getLogger(classOf[DownloadingCoordinator])
  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def isStillDownloading = songSet.size < masterMix.dvls.size

  def act {
    loop {
      react {
        case Start => {
          callBack.visible
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
          callBack.finished
          exit
        }
        case error: Error => {
          log.error("Error downloading: " + error.message)
          log.info("Will now stop downloading procedure")
          callBack.visible
          exit
        }
      }
    }
  }
}

class DownloadActor(dvl:Dvl, coordinator: Actor) extends Actor {
  def act {
    coordinator ! DownloadingDvl(dvl)
    val unconvertedSong: ScalaSong = ScalaCacheHandler.fetchSimpleSongAndCacheDvlAndMp3(dvl.url, None)
    coordinator ! ConvertingAndAddingMissingIds(dvl)
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    coordinator ! DownloadingMp3(dvl)
    ScalaCacheHandler.retrieveInputStream(mf.fileName, Some(mf.checksum))
    coordinator ! FinishedDownloading(dvl, unconvertedSong) //TODO Big oversimplification
  }
}

case object Start
case object Success
case class Error(message: String)
case class DownloadingDvl(dvl: Dvl)
case class DownloadingMp3(dvl: Dvl)
case class ConvertingAndAddingMissingIds(dvl: Dvl)
case class FinishedDownloading(dvl:Dvl, song: ScalaSong)

trait DVLCallBack {
  def setLabel(dvl: Dvl, text: String)
  def visible
  def finished
}