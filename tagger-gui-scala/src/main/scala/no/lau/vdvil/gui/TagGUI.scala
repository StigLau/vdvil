package no.lau.vdvil.gui

import scala.swing._
import no.bouvet.kpro.tagger.persistence.XStreamParser
import java.io.File
import no.lau.vdvil.cache.VdvilCacheHandler
import no.lau.tagger.model.{Segment, SimpleSong}
import java.util.ArrayList
import collection.JavaConversions
import scala.util.Random
import TabbedPane._

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val returningDvlChecksum = "2f0bd28098bce29f555c713cc03ab625"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"
    //size = new Dimension(800, 600) //Need a frame repack


    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Load") {
          val fileChooser = new FileChooser(new File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath

            val simpleSong = NeatStuff.setAllNullIdsRandom(new VdvilCacheHandler().fetchSimpleSongAndCacheDvlAndMp3(returningDvlUrl, returningDvlChecksum))
            dvlTable = new ScalaDynamicDvlTable(returningDvlUrl, simpleSong)

            //add(save, BorderPanel.Position.North)
            //TODO Frame Repack
          }
          tabs.pages += new Page("Rolf", dvlTable.ui)
        })
        contents += new MenuItem(Action("Save") {
          def parser = new XStreamParser[SimpleSong]()
          parser.save(dvlTable.simpleSong, dvlFilePath)
          println("Saved: $dvlFilePath")
          println(parser.toXml(dvlTable.simpleSong))
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

  def loadSongFromFile(dvlFile: String): SimpleSong = new XStreamParser().load(dvlFile)
}

object NeatStuff {
  def setAllNullIdsRandom(original: SimpleSong): SimpleSong = {
    val oldSegmentList = JavaConversions.asBuffer(original.segments)
    var newSegmentList = new ArrayList[Segment]()

    for (thisSegment <- oldSegmentList) {
      if (thisSegment.id == null)
        newSegmentList.add(cloneSegmentAndAddId(thisSegment))
      else
        newSegmentList.add(thisSegment)
    }
    return new SimpleSong(original.reference, original.mediaFile, newSegmentList, original.bpm)
  }

  def cloneSegmentAndAddId(thisSegment: Segment): Segment = {
    val id = String.valueOf(Math.abs(Random.nextLong))
    return new Segment(id, thisSegment.start, thisSegment.end, thisSegment.text)
  }

  def updateSegmentInSimpleSong(editedSegment: Segment, editedSong: SimpleSong): SimpleSong = {
    var newSegmentList = new ArrayList[Segment]()
    val oldSegmentList = JavaConversions.asBuffer(editedSong.segments)

    for (thisSegment <- oldSegmentList) {
      if (thisSegment.id == editedSegment.id) {
        newSegmentList.add(editedSegment)
      } else {
        newSegmentList.add(thisSegment)
      }
    }
    return new SimpleSong(editedSong.reference, editedSong.mediaFile, newSegmentList, editedSong.bpm)
  }
}



