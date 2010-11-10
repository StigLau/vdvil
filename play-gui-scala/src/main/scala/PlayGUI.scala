package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.downloading._
import swing.TabbedPane.Page
import no.lau.vdvil.domain.player. {MasterMix, Dvl}
import no.lau.vdvil.mix. {CompositionCallback, MyRepo, GenericDownloadingCoordinator}
import org.slf4j.LoggerFactory

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val log = LoggerFactory.getLogger(PlayGUI.getClass)
  val javaZoneDemoCompositionUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml"

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("from web") {
          Dialog.showInput(menuBar, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, javaZoneDemoCompositionUrl).map(startDownload(_))
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

  def startDownload(url:String) {
    val guiCallback = new DVLCallBack {
        def setLabel(dvl: Dvl, text: String) {log.info("label set") }
        def visible { log.info("Visible") }
        def finished {log.info("finished")}
    }
    val coordinator = new GenericDownloadingCoordinator(guiCallback)
    coordinator.start
    new MyRepo(coordinator).fetchComposition(url, new CompositionCallback {
        def finished(compositionOption:Option[MasterMix]) {
          val masterMix = compositionOption.get
          val downloadingCoordinator = new DownloadingCoordinator(masterMix, new DVLCallBackGUI(masterMix)) {
            start
          } ! Start
        }
    })
  }
}

class PlayPanel(val masterMix: MasterMix) {
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
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toFloat)}
  }
  val compositionPlayer = new ScalaCompositionPlayer(None) {
    def pauseAndplay(startFrom: Float) {
      stop
      masterMix.masterBpm = bpmField.text.toFloat
      scalaCompositionOption = Some(masterMix.asComposition)
      play(startFrom, bpmField.text.toFloat)
    }
  }
}

class DVLCallBackGUI (masterMix:MasterMix) extends DVLCallBack {
  lazy val downloadingPanel = new Frame {
    contents = new GridPanel(dvlLabels.size, 1) {
      dvlLabels.foreach(contents += _._2)
    }
  }
  lazy val dvlLabels: Map[Dvl, Label] = Map.empty ++ masterMix.dvls.map(dvl => dvl -> new Label(dvl.url))
  def setLabel(dvl: Dvl, text: String) {dvlLabels(dvl).text_=(text)}
  def visible { downloadingPanel.visible_=(true) }
  def finished {
    downloadingPanel.visible_=(false)
    PlayGUI.tabs.pages.append(new Page(masterMix.name, new PlayPanel(masterMix).ui))
  }
}