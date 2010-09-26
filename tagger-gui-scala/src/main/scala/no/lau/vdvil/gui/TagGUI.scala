package no.lau.vdvil.gui

import scala.swing._
import TabbedPane._
import no.lau.vdvil.cache.VdvilCacheHandler
import no.lau.tagger.scala.model.{ScalaSong, Segment, SimpleSong}
import org.slf4j.{LoggerFactory, Logger}

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  val log:Logger =  LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])//TODO THIS IS SOOOOO WRONG!!!!
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  val cacheHandler = new VdvilCacheHandler
  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"
    //size = new Dimension(800, 600) //Need a frame repack

    menuBar = new MenuBar {
      import Dialog._
      contents += new Menu("File") {
        contents += new MenuItem(Action("Load From File") {

          val fileChooser = new FileChooser(new java.io.File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            val loadedSong = ScalaSong.fromJava(cacheHandler.loadSimpleSongFromDvlOnDisk(dvlFilePath))
            showEditingPanel(dvlFilePath, NeatStuff.setAllNullIdsRandom(loadedSong))
          }
        })
        contents += new MenuItem(Action("Load From Web") {
          val input = showInput(menuBar, "", "Load from", Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
          if(input != None)
            showEditingPanel(input.get, fetchDvlAndMp3FromWeb(input.get))
        })
        contents += new MenuItem(Action("View .dvl XML") {
          if(dvlTable != null)
            log.info(cacheHandler.printableXml(dvlTable.simpleSong.toJava))
        })
        contents += new MenuItem(Action("Save as .dvl file") {
          val input = showInput(menuBar, "", "Load from", Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
          if(input != None) {
            log.info("Saving to {}", input.get)
            cacheHandler.save(dvlTable.simpleSong.toJava, input.get)
          }
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  def fetchDvlAndMp3FromWeb(url: String): SimpleSong = {
    val javaSong = new VdvilCacheHandler().fetchSimpleSongAndCacheDvlAndMp3(url, null)
    return NeatStuff.setAllNullIdsRandom(ScalaSong.fromJava(javaSong))
  }

  def showEditingPanel(input: String, song: SimpleSong) = {
    dvlTable = new ScalaDynamicDvlTable(input, song)
    //TODO Frame Repack
    tabs.pages += new Page(song.reference, dvlTable.ui)
  }
}

object NeatStuff {
  import scala.util.Random
  import scala.collection.mutable.ListBuffer

  def setAllNullIdsRandom(original: SimpleSong): SimpleSong = {
    var newSegmentList = new ListBuffer[Segment]()

    for (thisSegment <- original.segments) {
      if (thisSegment.id == null) {
        val id = String.valueOf(Math.abs(Random.nextLong))
        newSegmentList += new Segment(id, thisSegment.start, thisSegment.end, thisSegment.text)
      }
      else
        newSegmentList += thisSegment
    }
    return new SimpleSong(original.reference, original.mediaFile, newSegmentList.toList, original.bpm)
  }

  def updateSegmentInSimpleSong(editedSegment: Segment, oldSong: SimpleSong): SimpleSong = {
    var newSegmentList = new ListBuffer[Segment]()

    for (thisSegment <- oldSong.segments) {
      if (thisSegment.id == editedSegment.id) {
        newSegmentList += editedSegment
      } else {
        newSegmentList += thisSegment
      }
    }
    return new SimpleSong(oldSong.reference, oldSong.mediaFile, newSegmentList.toList, oldSong.bpm)
  }
}



