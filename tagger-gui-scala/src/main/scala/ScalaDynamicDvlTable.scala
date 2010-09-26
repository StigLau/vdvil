package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import no.bouvet.kpro.tagger.PlayerBase
import no.lau.tagger.scala.model.ScalaSong
import org.slf4j.LoggerFactory
import no.lau.vdvil.cache.TranslateTo

class ScalaDynamicDvlTable(returningDvlUrl: String, var simpleSong: ScalaSong) {
  var player: PlayerBase = null
  val log = LoggerFactory.getLogger(classOf[ScalaDynamicDvlTable])

  lazy val ui = new BorderPanel {
    add(new TextField(returningDvlUrl, 80) {
      reactions += {
        case _ => simpleSong.mediaFile.fileName = text
      }
    }, BorderPanel.Position.North)

    add(new BorderPanel {
      add(new FlowPanel {
        contents += new Label("BPM")
        contents += new TextField(simpleSong.bpm.toString, 5) {
          reactions += {
            case _ => simpleSong.bpm = text.toFloat
          }
        }

        contents += new Label("Starting offset")
        contents += new TextField(simpleSong.mediaFile.startingOffset.toString, 5) {
          reactions += {case _ => simpleSong.mediaFile.startingOffset = text.toFloat}
        }
      }, BorderPanel.Position.North)

      add(new FlowPanel {
        contents += new Button("View .dvl XML") {
          reactions += {case ButtonClicked(_) => log.info(TagGUI.cacheHandler.printableXml(simpleSong))}
        }
        contents += new Button("Save as .dvl file") {
          reactions += {
            case ButtonClicked(_) => {
              val pathToSaveTo = Dialog.showInput(this, "", "Save to", Dialog.Message.Plain, Swing.EmptyIcon, Nil, returningDvlUrl)
              if (pathToSaveTo.isDefined) {
                log.info("Saving to {}", pathToSaveTo.get)
                try { TagGUI.cacheHandler.save(simpleSong, pathToSaveTo.get)}
                catch { case ioE:java.io.IOException => log.error("Could not save file")}
              }
            }
          }
        }
      }, BorderPanel.Position.South)

      val nrOfRows = simpleSong.segments.size + 1
      add(new GridPanel(nrOfRows, 4) {
        hGap = 3
        vGap = 3

        contents += new Label("Start")
        contents += new Label("End")
        contents += new Label("Text")
        contents += new Label("")

        for (segment <- simpleSong.segments) {
          contents += new TextField(segment.start.toString(), 3) {
            reactions += {
              case _ => simpleSong.segmentWithId(segment.id).get.start = text.toFloat
            }
          }
          contents += new TextField(segment.end.toString(), 3) {
            reactions += {
              case _ => simpleSong.segmentWithId(segment.id).get.end = text.toFloat
            }
          }
          contents += new TextField(segment.text, 40) {
            reactions += {
              case _ => simpleSong.segmentWithId(segment.id).get.text = text
            }
          }
          contents += new Button("Play") {
            reactions += {
              case ButtonClicked(_) => playSegment(segment.id, simpleSong)
            }
          }
        }
      }, BorderPanel.Position.Center)
    }, BorderPanel.Position.Center)
  }

  /**
   * Plays the segment of your choice
   */
  def playSegment(segmentId: String, song: ScalaSong) {
    import no.lau.tagger.scala.model.ScalaMediaFile
    if (player != null)
      player.playPause(-1F, -1F) //Call to stop the player
    val mf = song.mediaFile
    val pathToMp3 = new no.lau.vdvil.cache.VdvilCacheHandler().retrievePathToFileFromCache(song.mediaFile.fileName, song.mediaFile.checksum)
    val copyOfSong = new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm)

    player = new PlayerBase(TranslateTo.from(copyOfSong))
    val segment = simpleSong.segmentWithId(segmentId).get
    player.playPause(segment.start, segment.end)
  }
}