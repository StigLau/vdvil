package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import no.bouvet.kpro.tagger.PlayerBase
import org.slf4j.LoggerFactory
import no.lau.tagger.scala.model.{ScalaSegment, TranslateTo, ScalaSong}

class ScalaDynamicDvlTable(dvlUrl: String, var song: ScalaSong) {

  var player: PlayerBase = null
  val log = LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])

  var editingGrid: GridPanel = buildEditingGrid

  /**
   * Main GUI Panel
   */
  lazy val ui = new BorderPanel {
    add(new TextField(dvlUrl, 80) {
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
    contents += new Label("Start")
    contents += new Label("End")
    contents += new Label("Text")
    contents += new Label("")
    contents += new Label("")

    song.segments.foreach {
      segment =>
        contents += new TextField(segment.start.toString(), 3) {
          reactions += {case _ => song.segmentWithId(segment.id).get.start = text.toFloat}
        }
        contents += new TextField(segment.end.toString(), 3) {
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
      reactions += {case ButtonClicked(_) => log.info(TagGUI.cacheHandler.printableXml(song))}
    }
    contents += new Button("Save as .dvl file") {
      reactions += {
        case ButtonClicked(_) => {
          val pathToSaveTo = Dialog.showInput(this, "", "Save to", Dialog.Message.Plain, Swing.EmptyIcon, Nil, dvlUrl)
          if (pathToSaveTo.isDefined) {
            log.info("Saving to {}", pathToSaveTo.get)
            try {TagGUI.cacheHandler.save(song, pathToSaveTo.get)}
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
    import no.lau.tagger.scala.model.ScalaMediaFile
    if (player != null)
      player.playPause(-1F, -1F) //Call to stop the player
    val mf = song.mediaFile
    val pathToMp3Option = TagGUI.cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    if (pathToMp3Option.isDefined) {
      val copyOfSong = new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)

      player = new PlayerBase(TranslateTo.from(copyOfSong))
      val segment = song.segmentWithId(segmentId).get
      player.playPause(segment.start, segment.end)
    }
  }
}