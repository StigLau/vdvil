package no.lau.vdvil.gui

import scala.swing._
import scala.swing.event._
import no.lau.vdvil.cache.VdvilCacheHandler
import no.lau.tagger.model.{Segment, SimpleSong}
import collection.JavaConversions
import no.bouvet.kpro.tagger.PlayerBase
import scala.util.Random
import java.util.ArrayList

object ScalaTimeApp extends SimpleSwingApplication {
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val returningDvlChecksum = "2f0bd28098bce29f555c713cc03ab625"

  def top = new MainFrame {
    val cacheHandler = new VdvilCacheHandler()
    val simpleSong: SimpleSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(returningDvlUrl, returningDvlChecksum)

    val player: PlayerBase = new PlayerBase(simpleSong)



    title = "Time Table"
    size = new Dimension(800, 600)
    contents = new BorderPanel {
      add(new TextField(simpleSong.mediaFile.fileName, 80), BorderPanel.Position.North)
      // simpleSongCallBack.update(new SimpleSong(simpleSong.reference, new MediaFile(fileNameField.getText, simpleSong.mediaFile.checksum, simpleSong.mediaFile.startingOffset), simpleSong.segments, simpleSong.bpm))

      add(new BorderPanel {
        add(new FlowPanel {
          contents += new Label("BPM")
          contents += new TextField(simpleSong.bpm.toString, 5)
          //simpleSongCallBack.update(new SimpleSong(simpleSong.reference, simpleSong.mediaFile, simpleSong.segments, bpmField.getText.toFloat))

          contents += new Label("Starting offset")
          contents += new TextField(simpleSong.mediaFile.startingOffset.toString, 5)
          //simpleSongCallBack.update(new SimpleSong(simpleSong.reference, new MediaFile(simpleSong.mediaFile.fileName, simpleSong.mediaFile.checksum, startingOffsetField.getText.toFloat), simpleSong.segments, simpleSong.bpm))
        }, BorderPanel.Position.North)


        val nrOfRows = simpleSong.segments.size + 1
        add(new GridPanel(nrOfRows, 4) {
          hGap = 3
          vGap = 3

          contents += new Label("start")
          contents += new Label("end")
          contents += new Label("text")
          contents += new Label("Play/Pause")

          val scalaSeq = JavaConversions.asBuffer(simpleSong.segments)

          for (segment <- scalaSeq) {
            contents += new TextField(segment.start.toString(), 3) {
              reactions += {
                case _ => player.update(updateSegmentInSimpleSong(new Segment(segment.id, text.toFloat, segment.end, segment.text), simpleSong))
              }
            }
            contents += new TextField(segment.end.toString(), 3) {
              reactions += {
                case _ => player.update(updateSegmentInSimpleSong(new Segment(segment.id, segment.start, text.toFloat, segment.text), simpleSong))
              }
            }
            contents += new TextField(segment.text, 40) {
              reactions += {
                case _ => player.update(updateSegmentInSimpleSong(new Segment(segment.id, segment.start, segment.end, text), simpleSong))
              }
            }
            contents += new Button("play/pause") {
              reactions += {
                case ButtonClicked(_) => player.playPause(segment.start, segment.end)
              }
            }
          }
        }, BorderPanel.Position.Center)
      }, BorderPanel.Position.Center)
    }
  }


  private def updateSegmentInSimpleSong(editedSegment: Segment, simpleSong: SimpleSong): SimpleSong = {
    var newSegmentList: java.util.List[Segment] = new ArrayList[Segment]()
    val oldSegmentList = JavaConversions.asBuffer(simpleSong.segments)

    println("Size of oldlist: " + oldSegmentList.size)
    for (thisSegment <- oldSegmentList) {
      println(" This " + thisSegment.id + " edited: " + editedSegment.id)
      if (thisSegment.id == editedSegment.id) {
        println("Match")
        newSegmentList.add(editedSegment)
      }
      else {
        newSegmentList.add(cloneSegmentAndAddId(thisSegment))
        println("No Match")
      }
    }
    println("Size of newlist: " + newSegmentList.size)
    return new SimpleSong(simpleSong.reference, simpleSong.mediaFile, newSegmentList, simpleSong.bpm)
  }

  private def cloneSegmentAndAddId(thisSegment: Segment): Segment = {
    val id: String = String.valueOf(Math.abs(Random.nextLong))
    return new Segment(id, thisSegment.start, thisSegment.end, thisSegment.text)
  }

}