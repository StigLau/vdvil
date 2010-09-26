package no.lau.tagger.scala.model

import collection.mutable.ListBuffer
import collection.JavaConversions
import java.util.ArrayList

/**
 * The domain model works as the bridge between the Java and Scala world
 */

class ScalaSegment(val id: String, var start: Float, var end: Float, var text: String) {
  def toJava():no.lau.tagger.model.Segment = new no.lau.tagger.model.Segment(id, start.floatValue, end.floatValue, text)
}

object ToScalaSegment {
  def fromJavaList(segmentList: java.util.List[no.lau.tagger.model.Segment]): List[ScalaSegment] = {
    var newSegmentList = new ListBuffer[ScalaSegment]()
    for (seg <- JavaConversions.asBuffer(segmentList)) {
      newSegmentList += ToScalaSegment.fromJava(seg)
    }
    return newSegmentList.toList
  }
  def fromJava(segment:no.lau.tagger.model.Segment): ScalaSegment = new ScalaSegment(segment.id, segment.start.floatValue, segment.end.floatValue, segment.text)
}

class ScalaSong(val reference: String, val mediaFile: ScalaMediaFile, var segments: List[ScalaSegment], var bpm: Float) {
  def toJava(): no.lau.tagger.model.SimpleSong = new no.lau.tagger.model.SimpleSong(reference, mediaFile.toJava, segmentListAsJava, bpm)
  def segmentListAsJava(): java.util.List[no.lau.tagger.model.Segment] = {
    val list = new ArrayList[no.lau.tagger.model.Segment]()
    for(seg <- segments) {
      list.add(seg.toJava)
    }
    return list
  }

  def segmentWithId(id:String):Option[ScalaSegment] = segments.filter(segment=> segment.id == id).headOption
}

object ToScalaSong {
  def fromJava(javaSong: no.lau.tagger.model.SimpleSong): ScalaSong = new ScalaSong(javaSong.reference, ToScalaMediaFile.fromJava(javaSong.mediaFile), ToScalaSegment.fromJavaList(javaSong.segments), javaSong.bpm.floatValue)
}

class ScalaMediaFile(var fileName: String, var checksum: String, var startingOffset: Float) {
  def toJava(): no.lau.tagger.model.MediaFile = new no.lau.tagger.model.MediaFile(fileName, checksum, startingOffset.floatValue)
}

object ToScalaMediaFile {
  def fromJava(javaMediaFile:no.lau.tagger.model.MediaFile): ScalaMediaFile = new ScalaMediaFile(javaMediaFile.fileName, javaMediaFile.checksum, javaMediaFile.startingOffset.floatValue)
}
