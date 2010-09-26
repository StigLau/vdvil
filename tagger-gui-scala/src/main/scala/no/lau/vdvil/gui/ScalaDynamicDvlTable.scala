package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import no.bouvet.kpro.tagger.PlayerBase
import no.lau.tagger.scala.model.SimpleSong

class ScalaDynamicDvlTable(returningDvlUrl: String, var simpleSong: SimpleSong) {
  var player: PlayerBase = null

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
          reactions += {
            case _ => simpleSong.mediaFile.startingOffset = text.toFloat
          }
        }
      }, BorderPanel.Position.North)


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
              case _ => simpleSong.segmentWithId(segment.id).start = text.toFloat
            }
          }
          contents += new TextField(segment.end.toString(), 3) {
            reactions += {
              case _ => simpleSong.segmentWithId(segment.id).end = text.toFloat
            }
          }
          contents += new TextField(segment.text, 40) {
            reactions += {
              case _ => simpleSong.segmentWithId(segment.id).text = text
            }
            contents += new Button("Play") {
              reactions += {
                case ButtonClicked(_) => playSegment(segment.id, simpleSong)
              }
            }
          }
        }
      }, BorderPanel.Position.Center)
    }, BorderPanel.Position.Center)
  }

  /**
   * Plays the segment of your choice
   */
  def playSegment(segmentId: String, song: SimpleSong) {
    import no.lau.tagger.scala.model.MediaFile
    if (player != null)
      player.playPause(-1F, -1F) //Call to stop the player
    val mf = song.mediaFile
    val pathToMp3 = new no.lau.vdvil.cache.VdvilCacheHandler().retrievePathToFileFromCache(song.mediaFile.fileName, song.mediaFile.checksum)
    val copyOfSong = new SimpleSong(song.reference, new MediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm)

    player = new PlayerBase(copyOfSong.toJava)
    val segment = simpleSong.segmentWithId(segmentId)
    player.playPause(segment.start, segment.end)
  }
}