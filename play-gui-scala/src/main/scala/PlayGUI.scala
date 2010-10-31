package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.downloading._
import collection.immutable.HashMap

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
  val song = Song(
    Dvl(baseUrl + "holden-nothing-93_returning_mix.dvl", "returning") ::
    Dvl(baseUrl + "unfinished_sympathy.dvl", "unfinished_sympathy") ::
    Dvl(baseUrl + "olive-youre_not_alone.dvl", "You're not alone") ::
    Dvl(baseUrl + "christian_cambas-it_scares_me.dvl", "It scares me") ::
    Nil
)

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("Static") {
          val callback = new DVLCallBackGUI(song) {
            lazy val downloadingPanel = new Frame {
              contents = new GridPanel(dvlLabels.size, 1) {
                dvlLabels.foreach(contents += _._2)
              }
            }
          }
          val downloadingCoordinator = new DownloadingCoordinator(song, callback) {
            start
          } ! Start
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
 }
}

class PlayPanel(val composition: ScalaComposition) {
  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += playCompositionButton
    contents += new Label("BPM")
    contents += bpmField
  }

  val bpmField = new TextField(composition.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(composition.durationAsBeats.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toFloat)}
  }
  val compositionPlayer = new ScalaCompositionPlayer(None) {
    def pauseAndplay(startFrom: Float) {
      stop
      composition.masterBpm = bpmField.text.toFloat
      scalaCompositionOption = Some(composition)
      play(startFrom, bpmField.text.toFloat)
    }
  }
}

abstract class DVLCallBackGUI (song:Song) extends DVLCallBack {
  val downloadingPanel:UIElement
  val dvlLabels: Map[Dvl, Label] = Map.empty ++ song.dvls.map(dvl => dvl -> new Label(dvl.url))
  def setLabel(dvl: Dvl, text: String) {dvlLabels(dvl).text_=(text)}
  def visible(value:Boolean) { downloadingPanel.visible_=(value) }
}