package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import swing.TabbedPane.Page

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val testSong: ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl).get
  var compositionOption: Option[ScalaComposition] = None


  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += loadMenuItem
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  val loadMenuItem = new MenuItem(Action("Static") {
    val composition = new ScalaComposition(150F, CompositionExample.parts)
    tabs.pages += new Page("Name", new PlayPanel(composition).ui)
  })


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
      scalaCompositionOption = Some(composition)
      play(startFrom, bpmField.text.toFloat)
    }
  }
}