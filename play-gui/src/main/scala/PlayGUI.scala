package no.lau.vdvil.player

import scala.swing._
import swing.TabbedPane.Page
import org.slf4j.LoggerFactory
import java.net.URL

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