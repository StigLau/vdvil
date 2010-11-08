package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import org.slf4j.LoggerFactory
import no.lau.vdvil.player.ScalaPlayer
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSegment, ScalaSong}
import java.io.FileNotFoundException

class ScalaDynamicDvlTable(dvlUrl: String, song: ScalaSong) {

  var player: ScalaPlayer = new ScalaPlayer(asPlayableCopy(song))
  val log = LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])

  var editingGrid: GridPanel = buildEditingGrid

  /**
   * Main GUI Panel
   */
  lazy val ui = new BorderPanel {
    add(new TextField(song.mediaFile.fileName, 80) {
      reactions += {case _ => song.mediaFile.fileName = text}
    }, BorderPanel.Position.North)

    add(new BorderPanel {
      add(buildBpmAndOffsetPanel, BorderPanel.Position.North)
      add(editingGrid, BorderPanel.Position.Center)
      add(buildSavingPanel, BorderPanel.Position.South)
    }, BorderPanel.Position.Center)
  }

  def buildBpmAndOffsetPanel = new FlowPanel {
    contents += new Label("BPM")
    contents += new TextField(song.bpm.toString, 5) {
      reactions += {case _ => song.bpm = text.toFloat}
    }
    contents += new Label("Starting offset")
    contents += new TextField(song.mediaFile.startingOffset.toString, 5) {
      reactions += {case _ => song.mediaFile.startingOffset = text.toFloat}
    }
  }

  def buildEditingGrid = new GridPanel(song.segments.size + 1, 4) {
    contents += new Label("ID")
    contents += new Label("Start")
    contents += new Label("End")
    contents += new Label("Text")
    contents += new Label("")
    contents += new Label("")

    song.segments.foreach {
      segment =>
        contents += new Label(segment.id)
        contents += new TextField(segment.start.toString, 3) {
          reactions += {case _ => song.segmentWithId(segment.id).get.start = text.toFloat}
        }
        contents += new TextField(segment.end.toString, 3) {
          reactions += {case _ => song.segmentWithId(segment.id).get.end = text.toFloat}
        }
        contents += new TextField(segment.text, 40) {
          reactions += {case _ => song.segmentWithId(segment.id).get.text = text}
        }
        contents += new Button("Play") {
          reactions += {case ButtonClicked(_) => playSegment(segment.id, song)}
        }
        contents += new Button("-") {
          reactions += {case ButtonClicked(_) => {song.segments = removeSegmentFromList(segment.id)}}
        }
    }
  }

  def buildSavingPanel = new FlowPanel {
    contents += new Button("Add row") {
      reactions += {case ButtonClicked(_) => song.segments = addEmptySegmentToList}
    }    
    contents += new Button("View .dvl XML") {
      reactions += {case ButtonClicked(_) => log.info(ScalaCacheHandler.printableXml(song))}
    }
    contents += new Button("Save as .dvl file") {
      reactions += {
        case ButtonClicked(_) => {
          Dialog.showInput(this, "", "Save to", Dialog.Message.Plain, Swing.EmptyIcon, Nil, dvlUrl).map{ pathToSaveTo =>
            log.info("Saving to {}", pathToSaveTo)
            try {ScalaCacheHandler.save(song, pathToSaveTo)}
            catch {case ioE: java.io.IOException => log.error("Could not save file")}
          }
        }
      }
    }
  }

  def addEmptySegmentToList: List[ScalaSegment] = new ScalaSegment(NeatStuff.generateRandomId, 0F, 0F, "") :: song.segments

  def removeSegmentFromList(segmentId: String): List[ScalaSegment] = for{segment <- song.segments if (segment.id != segmentId)} yield segment


  /**
   * Plays the segment of your choice
   */
  def playSegment(segmentId: String, song: ScalaSong) {
    if(player.started)
      player.stop
    song.segmentWithId(segmentId).map(segment =>  player.play(segment.start, segment.end))
  }

  def asPlayableCopy(song:ScalaSong):ScalaSong = {
    val mf = song.mediaFile
    ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).map { pathToMp3 =>
        return new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm)
    }
    throw new FileNotFoundException("File not found " + mf.fileName)
  }
}