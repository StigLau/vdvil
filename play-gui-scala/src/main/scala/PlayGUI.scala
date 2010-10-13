package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val testSong: ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl).get
  var compositionOption: Option[ScalaComposition] = None


  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += loadMenuItem
      }
    }
    contents = new FlowPanel {
      contents += new Label("Start from")
      contents += startField
      contents += new Label("Stop")
      contents += stopField
      contents += playCompositionButton
      contents += new Label("BPM")
      contents += bpmField
    }
  }

  val bpmField = new TextField(4) {
    reactions += {case _ => if (!text.isEmpty) {testSong.bpm = text.toFloat; println("BPM Changed to " + testSong.bpm)}}
  }
  val startField = new TextField("0", 4)
  val stopField = new TextField(4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toFloat)}
  }

  val loadMenuItem = new MenuItem(Action("Static") {
    val composition = new ScalaComposition(150F, CompositionExample.parts)
    compositionOption = Some(composition)
    stopField.text_=(composition.durationAsBeats.toString)
    bpmField.text_=(composition.masterBpm.toString)
  })

  val compositionPlayer = new ScalaCompositionPlayer(None) {
    def pauseAndplay(startFrom: Float) {
      stop
      scalaCompositionOption = compositionOption
      play(startFrom)
    }
  }

  /*
 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
 } */
}