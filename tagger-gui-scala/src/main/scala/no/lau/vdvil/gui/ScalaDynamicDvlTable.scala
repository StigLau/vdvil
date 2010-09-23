package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import no.lau.vdvil.cache.VdvilCacheHandler
import no.bouvet.kpro.tagger.PlayerBase
import no.lau.tagger.scala.model.{ScalaSong, MediaFile, Segment, SimpleSong}

class ScalaDynamicDvlTable(returningDvlUrl:String, var simpleSong:SimpleSong) {
  var player:PlayerBase = null

  lazy val ui = new BorderPanel {
    add(new TextField(returningDvlUrl, 80) {
      reactions += {
        case _ => {
          //TODO This URL will probably pose a problem!!!
          simpleSong = new SimpleSong(simpleSong.reference, new MediaFile(text, simpleSong.mediaFile.checksum, simpleSong.mediaFile.startingOffset), simpleSong.segments, simpleSong.bpm)
        }
      }
    }, BorderPanel.Position.North)

    add(new BorderPanel {
      add(new FlowPanel {
        contents += new Label("BPM")
        contents += new TextField(simpleSong.bpm.toString, 5) {
          reactions += {
            case _ => simpleSong = new SimpleSong(simpleSong.reference, simpleSong.mediaFile, simpleSong.segments, text.toFloat)
          }
        }

        contents += new Label("Starting offset")
        contents += new TextField(simpleSong.mediaFile.startingOffset.toString, 5) {
          reactions += {
            case _ => simpleSong = new SimpleSong(simpleSong.reference, new MediaFile(simpleSong.mediaFile.fileName, simpleSong.mediaFile.checksum, text.toFloat), simpleSong.segments, simpleSong.bpm)
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
              case _ =>
                simpleSong =
                  NeatStuff.updateSegmentInSimpleSong(
                    new Segment(segment.id, text.toFloat, segment.end, segment.text), simpleSong)
            }
          }
          contents += new TextField(segment.end.toString(), 3) {
            reactions += {
              case _ =>
                simpleSong =
                  NeatStuff.updateSegmentInSimpleSong(
                    new Segment(segment.id, segment.start, text.toFloat, segment.text), simpleSong)
            }
          }
          contents += new TextField(segment.text, 40) {
            reactions += {
              case _ =>
                simpleSong =
                  NeatStuff.updateSegmentInSimpleSong(
                    new Segment(segment.id, segment.start, segment.end, text), simpleSong)
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
  def playSegment(segmentId:String, song:SimpleSong) {
    if(player != null)
      player.playPause(-1F, -1F)//Call to stop the player
    player = new PlayerBase(simpleSong.toJava)
    val segment = segmentWith(segmentId)
    player.playPause(segment.start, segment.end)
  }

  def segmentWith (id: String): Segment = {
    for (segment <- simpleSong.segments) {
      if (id == segment.id) {
        return segment
      }
    }
    return null // TODO FIX THIS SHIT
  }


  object ScalaTimeAppTest extends SimpleSwingApplication {
    val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
    val returningDvlChecksum = "2f0bd28098bce29f555c713cc03ab625"

    def top = new MainFrame {
      title = "Dvl Time Table"
      size = new Dimension(800, 600)
      val javaSong = new VdvilCacheHandler().fetchSimpleSongAndCacheDvlAndMp3(returningDvlUrl, returningDvlChecksum)
      val simpleSong = NeatStuff.setAllNullIdsRandom(ScalaSong.fromJava(javaSong))

      contents = new ScalaDynamicDvlTable(returningDvlUrl, simpleSong).ui
    }
  }
}