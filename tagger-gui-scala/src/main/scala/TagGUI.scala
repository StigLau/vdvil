package no.lau.vdvil.gui

import scala.swing._
import TabbedPane._
import no.lau.tagger.scala.model.{ScalaSegment, ScalaSong}
import org.slf4j.LoggerFactory
import no.lau.vdvil.cache.ScalaCacheHandler

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  val log =  LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])//TODO THIS IS SOOOOO WRONG!!!!
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  val cacheHandler = new ScalaCacheHandler
  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"

    menuBar = new MenuBar {
      import Dialog._
      contents += new Menu("Load") {
        contents += new MenuItem(Action("From File") {

          val fileChooser = new FileChooser(new java.io.File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            try {
              val loadedSong = cacheHandler.loadSimpleSongFromDvlOnDisk(dvlFilePath)
              addEditingPanel(dvlFilePath, NeatStuff.convertAllNullIDsToRandom(loadedSong))
            } catch {case _ => log.error("Could not parse file {}", dvlFilePath)} 
          }
        })
        contents += new MenuItem(Action("From Web") {
          val urlOption = showInput(menuBar, "", "Load from", Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
          if (urlOption.isDefined) {
            val songOption = fetchDvlAndMp3FromWeb(urlOption.get)
            if (songOption.isDefined)
              addEditingPanel(urlOption.get, songOption.get)
          }
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  def fetchDvlAndMp3FromWeb(url: String): Option[ScalaSong] = {
    try {
      val song:ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(url, null)
      Some(NeatStuff.convertAllNullIDsToRandom(song))
    } catch {case _ => log.error("Could not download or parse {}", url); None} // TODO This catch all is not working -> WHY!? 
  }

  def addEditingPanel(input: String, song: ScalaSong) = {
    dvlTable = new ScalaDynamicDvlTable(input, song)
    //TODO Frame Repack
    tabs.pages += new Page(song.reference, dvlTable.ui)
  }
}

object NeatStuff {
  import scala.util.Random

  def convertAllNullIDsToRandom(original: ScalaSong): ScalaSong = {
    val segmentList = for{segment <- original.segments} yield segment.id match {
      case null => new ScalaSegment(generateRandomId, segment.start, segment.end, segment.text)
      case _ => segment
    }
    new ScalaSong(original.reference, original.mediaFile, segmentList.toList, original.bpm)
  }

  def updateSegmentInSimpleSong(editedSegment: ScalaSegment, oldSong: ScalaSong): ScalaSong = {
    val newSegmentList = for{currentSegment <- oldSong.segments} yield
      currentSegment.id match {
        case editedSegment.id => editedSegment
        case _ => currentSegment
    }
    new ScalaSong(oldSong.reference, oldSong.mediaFile, newSegmentList, oldSong.bpm)
  }

  def generateRandomId:String = String.valueOf(math.abs(Random.nextLong))
}



