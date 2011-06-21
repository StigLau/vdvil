package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.downloading._
import swing.TabbedPane.Page
import org.slf4j.LoggerFactory
import java.net.URL
import no.vdvil.renderer.audio.TestMp3s
import no.lau.vdvil.handler.Composition
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val log = LoggerFactory.getLogger(this.getClass)
  val javaZoneDemoCompositionUrl = TestMp3s.javaZoneComposition_WithoutImages

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("from web") {
          Dialog.showInput(menuBar, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, javaZoneDemoCompositionUrl.toString).map(chosenPath => startDownload(new URL(chosenPath)))
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

class PlayPanel(val masterMix: Composition) {
  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += playCompositionButton
    contents += new Label("BPM")
    contents += bpmField
  }

  val bpmField = new TextField(masterMix.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(masterMix.durationAsBeats.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toInt)}
  }
  val compositionPlayer = new PreconfiguredVdvilPlayer() {
    def pauseAndplay(startFrom: Int) {
      stop
      masterMix.masterBpm = bpmField.text.toFloat
      scalaCompositionOption = Some(masterMix.asComposition)
      play(startFrom, masterMix.masterBpm)
    }
  }
}

