package no.lau.tagger.scala.model

import collection.mutable.ListBuffer
import collection.JavaConversions
import java.util.ArrayList

/**
 * The domain model works as the bridge between the Java and Scala world
 */

class Segment(val id: String, var start: Float, var end: Float, var text: String) {
  def toJava():no.lau.tagger.model.Segment = new no.lau.tagger.model.Segment(id, start.floatValue, end.floatValue, text)
}

object ScalaSegment {
  def fromJavaList(segmentList: java.util.List[no.lau.tagger.model.Segment]): List[Segment] = {
    var newSegmentList = new ListBuffer[Segment]()
    for (seg <- JavaConversions.asBuffer(segmentList)) {
      newSegmentList += ScalaSegment.fromJava(seg)
    }
    return newSegmentList.toList
  }
  def fromJava(segment:no.lau.tagger.model.Segment): Segment = new Segment(segment.id, segment.start.floatValue, segment.end.floatValue, segment.text)
}

class SimpleSong(val reference: String, val mediaFile: MediaFile, var segments: List[Segment], var bpm: Float) {
  def toJava(): no.lau.tagger.model.SimpleSong = new no.lau.tagger.model.SimpleSong(reference, mediaFile.toJava, segmentListAsJava, bpm)
  def segmentListAsJava(): java.util.List[no.lau.tagger.model.Segment] = {
    val list = new ArrayList[no.lau.tagger.model.Segment]()
    for(seg <- segments) {
      list.add(seg.toJava)
    }
    return list
  }
}

object ScalaSong {
  def fromJava(javaSong: no.lau.tagger.model.SimpleSong): SimpleSong = new SimpleSong(javaSong.reference, ScalaMediaFile.fromJava(javaSong.mediaFile), ScalaSegment.fromJavaList(javaSong.segments), javaSong.bpm.floatValue)
}

class MediaFile(var fileName: String, var checksum: String, var startingOffset: Float) {
  def toJava(): no.lau.tagger.model.MediaFile = new no.lau.tagger.model.MediaFile(fileName, checksum, startingOffset.floatValue) 
}

object ScalaMediaFile {
  def fromJava(javaMediaFile:no.lau.tagger.model.MediaFile): MediaFile = new MediaFile(javaMediaFile.fileName, javaMediaFile.checksum, javaMediaFile.startingOffset.floatValue)
}
