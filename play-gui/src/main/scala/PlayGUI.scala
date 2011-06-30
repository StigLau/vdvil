package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import swing.TabbedPane.Page
import org.slf4j.LoggerFactory
import java.net.URL
import no.vdvil.renderer.audio.TestMp3s
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer
import no.lau.vdvil.handler._
import no.lau.vdvil.handler.persistence._
import no.lau.vdvil.timing.MasterBeatPattern

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val log = LoggerFactory.getLogger(this.getClass)
  //val javaZoneDemoCompositionUrl = TestMp3s.javaZoneComposition_WithoutImages
  val javaZoneDemoCompositionUrl = new URL("http://localhost:8080/vdvil-server/composition/xml")

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("from web") {
          Dialog.showInput(menuBar, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, javaZoneDemoCompositionUrl.toString)
            .map(chosenPath => startDownload(new URL(chosenPath)))
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

  def startDownload(url:URL){
    println(url)
    val playPanel = new PlayPanel(url)
    tabs.pages.append(new Page(playPanel.name, playPanel.ui))
  }
}

class PlayPanel(val url:URL) {
  val cache = PreconfiguredVdvilPlayer.downloadAndParseFacade
  var composition: Composition = fetchComposition(url)
  def name = composition.name

  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += new Label("BPM")
    contents += bpmField
    contents += playCompositionButton
    contents += reloadButton
  }
  val beatPattern = composition.masterBeatPattern
  val bpmField = new TextField(beatPattern.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(beatPattern.toBeat.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(new MasterBeatPattern(startField.text.toInt, stopField.text.toInt, bpmField.text.toFloat))}
  }
  val reloadButton = new Button("Reload") {
    reactions += {case ButtonClicked(_) => {
      cache.setRefreshCaches(true)
      composition = fetchComposition(url)
      cache.setRefreshCaches(false)
      stopField.text_=(composition.masterBeatPattern.toBeat.toString)
    }}
  }
  val compositionPlayer = new PreconfiguredVdvilPlayer() {
    def pauseAndplay(beatPattern:MasterBeatPattern) {
      stop
      init(composition, beatPattern)
      println("Playing " + composition.name + " " + beatPattern)
      play
    }
  }

  def fetchComposition(url:URL) = cache.parse(PartXML.create(url)).asInstanceOf[Composition]
}
