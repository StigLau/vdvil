package no.lau.vdvil.downloading

import collection.immutable.HashMap
import actors.Actor
import swing.TabbedPane.Page
import no.lau.vdvil.player._
import swing.{Frame, GridPanel, Label}
import no.lau.tagger.scala.model.ScalaSong

class DownloadingCoordinator(song: Song) extends Actor with DvlLabel {
  lazy val dvlLabels: Map[Dvl, Label] = asMap

  lazy val downloadingPanel = new Frame {
    contents = ui
  }

  lazy val ui = new GridPanel(dvlLabels.size, 1) {
    dvlLabels.foreach(contents += _._2)
  }

  def asMap: Map[Dvl, Label] = {
    var map = new HashMap[Dvl, Label]
    song.dvls.foreach(dvl => map += dvl -> new Label(dvl.url))
    map
    //for{dvl <- dvls} yield (dvl -> new Label(dvl.url))
  }

  import scala.collection.mutable.Set
  val songSet = Set[ScalaSong]()

  def isStillDownloading = songSet.size < song.dvls.size

  def setLabel(dvl: Dvl, text: String) {dvlLabels(dvl).text_=(text)}
  //TODO This code should be merged with Coordinator!!!
  def act {
    loop {
      react {
        case Start => {
          downloadingPanel.visible_=(true)
          song.dvls.foreach(dvl => new DownloadActor(dvl, this).start)
        }
        case downloading: DownloadingDvl => setLabel(downloading.dvl, "Downloading Dvl" + downloading.dvl.name)
        case downloading: DownloadingMp3 => setLabel(downloading.dvl, "Downloading Mp3 " + downloading.dvl.name)
        case converting: ConvertingAndAddingMissingIds => setLabel(converting.dvl, "Converting " + converting.dvl.name)
        case finished: FinishedDownloading => {
          setLabel(finished.dvl, finished.dvl.name + " Finished")
          songSet += finished.song
          if (!isStillDownloading) {
            this ! Success
          }
        }
        case Success => {
          val composition = new ScalaComposition(150F, CompositionExample.parts)
          PlayGUI.tabs.pages.append(new Page("PLAYPANEL", new PlayPanel(composition).ui))
          downloadingPanel.visible_=(false)
          exit
        }
        case error: Error => {
          println("Error downloading: " + error.message)
          println("Will now stop downloading procedure")
          downloadingPanel.visible_=(false)
          exit
        }
      }
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


trait DvlLabel {
  def setLabel(dvl: Dvl, text: String)
}