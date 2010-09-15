package no.lau.vdvil.gui

import scala.swing._
import event.ButtonClicked
import scala.swing.Swing._
import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
import java.io.File

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  //val bpmText: JTextField
  //PlayerBase playerBase
  var dvlFilePath: String = System.getProperty("user.home") + "/kpro"

  def top = new MainFrame {
    size = new Dimension(500, 300)
    title = "Tagging GUI"
    contents = new BorderPanel {
      val save = new Button("Save")
      val load = new Button("Load File")

      add(save, BorderPanel.Position.North)
      add(load, BorderPanel.Position.South)

      listenTo(save)
      listenTo(load)

      reactions += {
        case ButtonClicked(`save`) => {
          println("Save button pressed")
          /*
          def parser = new XStreamParser()
              parser.save(playerBase.simpleSong, dvlFilePath)
              println "Saved:" + dvlFilePath
              println parser.toXml(playerBase.simpleSong)
           */
        }
        case ButtonClicked(`load`) => {
          println("Load button pressed omg")
          val fileChooser = new FileChooser(new File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if(returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            println("User chose " + dvlFilePath)
            //loadSong (loadSongFromFile())
          }
          println(returnVal)
        }
      }
    }
  }
  /*
  def loadSongFromFile(): SimpleSong {
    val parser = new XStreamParser ()
    return parser.load (dvlFilePath)
  }
  */
}
/*

def loadSong(SimpleSong song) {
    playerBase = new PlayerBase(song)
    frame.add(playerBase.getDynamicTimeTable(), BorderLayout.CENTER)
    frame.pack()
    frame.show()
}



*/



