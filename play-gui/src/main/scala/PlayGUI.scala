package no.lau.vdvil.player

import scala.swing._
import swing.TabbedPane.Page
import org.slf4j.LoggerFactory
import java.net.URL
import no.vdvil.renderer.audio.TestMp3s

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val log = LoggerFactory.getLogger(this.getClass)
  val demoCompositionUrl = TestMp3s.javaZoneComposition_WithoutImages

  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new MenuItem(Action("Load") {showDownloadCompositionInput(menuBar)})
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
  showDownloadCompositionInput(t.menuBar)
 }

  def showDownloadCompositionInput(parent:Component) {
    Dialog.showInput(parent, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, demoCompositionUrl.toString)
      .map(chosenPath => startDownload(new URL(chosenPath)))
  }

  def startDownload(url:URL){
    println("Composition: " + url)
    val playPanel = new PlayPanel(url)
    tabs.pages.append(new Page(playPanel.name, playPanel.ui))
  }
}