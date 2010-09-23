package no.lau.vdvil.gui

import scala.swing._
import no.bouvet.kpro.tagger.persistence.XStreamParser
import TabbedPane._
import no.lau.vdvil.cache.VdvilCacheHandler
import no.lau.tagger.scala.model.{ScalaSong, Segment, SimpleSong}

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"
    size = new Dimension(800, 600) //Need a frame repack

    menuBar = new MenuBar {
      import Dialog._
      contents += new Menu("File") {
        contents += new MenuItem(Action("Load From File") {

          val fileChooser = new FileChooser(new java.io.File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            showEditingPanel(dvlFilePath, NeatStuff.setAllNullIdsRandom(fetchDvlFromFile(dvlFilePath)))
          }
        })
        contents += new MenuItem(Action("Load From Web") {
          val input = showInput(menuBar, "", "Load from", Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
          showEditingPanel(input.get, fetchDvlAndMp3FromWeb(input.get))
        })
        contents += new MenuItem(Action("Save") {
          def parser = new XStreamParser[no.lau.tagger.model.SimpleSong]()
          //parser.save(dvlTable.simpleSong.toJava, dvlFilePath)
          println(parser.toXml(dvlTable.simpleSong.toJava))
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  def loadSongFromFile(dvlFile: String): SimpleSong = new XStreamParser().load(dvlFile)

  def fetchDvlFromFile(path: String): SimpleSong = ScalaSong.fromJava(new VdvilCacheHandler().buildSimpleSongWithCorrectReferenceToMp3(path))

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



