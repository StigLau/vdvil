package no.lau.vdvil.gui

import scala.swing._
import event.ButtonClicked
import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
import java.io.File
import no.bouvet.kpro.tagger.PlayerBase

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  var dvlFilePath = System.getProperty("user.home") + "/kpro"
  var playerBase = PlayerBase.NULL

  def top = new MainFrame {
    size = new Dimension(500, 300)
    title = "Tagging GUI"


    contents = new BorderPanel {
      val save = new Button("Save")
      val load = new Button("Load File")

      add(load, BorderPanel.Position.South)

      listenTo(save)
      listenTo(load)

      reactions += {
        case ButtonClicked(`save`) => {
          println("Save button pressed")
          def parser = new XStreamParser[SimpleSong]()
          parser.save(playerBase.getSimpleSong(), dvlFilePath)
          println("Saved: $dvlFilePath")
          println(parser.toXml(playerBase.getSimpleSong))
        }
        case ButtonClicked(`load`) => {
          val fileChooser = new FileChooser(new File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            add(new DynamicScalaTable(loadSongFromFile(dvlFilePath)), BorderPanel.Position.Center)
            add(save, BorderPanel.Position.North)
            //TODO Frame Repack
          }
        }
      }
    }
  }

  def loadSongFromFile(dvlFile: String): SimpleSong = new XStreamParser().load(dvlFile)
}

object GUIStarter {
  def main(args: Array[String]) = TagGUI.main(null)
}


class DynamicScalaTable(song: SimpleSong) extends scala.swing.Component {
  override lazy val peer = new PlayerBase(song).getDynamicTimeTable()
}



