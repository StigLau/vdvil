package no.lau.vdvil.gui

import scala.swing._
import TabbedPane._
import java.io.FileInputStream
import no.lau.tagger.scala.model.{ToScalaSong, ScalaSegment, ScalaSong}
import no.bouvet.kpro.tagger.persistence.{XStreamParser}
import no.lau.tagger.model.SimpleSong
import org.codehaus.httpcache4j.cache.VdvilCacheStuff
import org.slf4j.LoggerFactory

/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
object TagGUI extends SimpleSwingApplication {
  val log =  LoggerFactory.getLogger(TagGUI.getClass)
  var dvlFilePath = System.getProperty("user.home") + "/kpro"

  var dvlTable: ScalaDynamicDvlTable = null
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  val tabs = new TabbedPane

  def top = new MainFrame {
    title = "Tagging GUI"

    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("From File") {

          val fileChooser = new FileChooser(new java.io.File(dvlFilePath))
          val returnVal = fileChooser.showOpenDialog(this);
          if (returnVal == FileChooser.Result.Approve) {
            dvlFilePath = fileChooser.selectedFile.getAbsolutePath
            val dvlAsStream = new FileInputStream(dvlFilePath)
            try {
              val loadedSong = ToScalaSong.fromJava(new XStreamParser[SimpleSong].load(dvlAsStream))
              addEditingPanel(dvlFilePath, NeatStuff.convertAllNullIDsToRandom(loadedSong))
            } catch {case _ => log.error("Could not parse file {}", dvlFilePath)} 
          }
        })
        contents += new MenuItem(Action("From Web") {
          Dialog.showInput(menuBar, "", "Load from", Dialog.Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl).map{
            url => fetchDvlAndMp3FromWeb(url).map(song => addEditingPanel(url, song))
          }
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

  def fetchDvlAndMp3FromWeb(url: String): Option[ScalaSong] = {
    try {
      val stream = VdvilCacheStuff.fetchAsStream(url)
      val xml = new XStreamParser[SimpleSong].load(stream)
      val song = ToScalaSong.fromJava(xml)
      NeatStuff.cacheMp3(song.mediaFile.fileName, song.mediaFile.checksum)
      Some(NeatStuff.convertAllNullIDsToRandom(song))
    } catch {
      case e: Exception => log.error("Some problem", e); None
      case _ => log.error("Could not download or parse {}", url); None
    }
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

  def cacheMp3(url:String, checksum:String) = VdvilCacheStuff.fetchAsFile(url, checksum)
}



