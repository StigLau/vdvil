package no.lau.tagger.scala.model

import collection.JavaConversions
import java.util.ArrayList

/**
 * This file handles translating from a Java to a Scala model and back
 */

object ToScalaSong {
  def fromJava(javaSong: no.lau.tagger.model.SimpleSong): ScalaSong = new ScalaSong(javaSong.reference, ToScalaMediaFile.fromJava(javaSong.mediaFile), ToScalaSegment.fromJavaList(javaSong.segments), javaSong.bpm.floatValue)
}

object ToScalaMediaFile {
  def fromJava(javaMediaFile: no.lau.tagger.model.MediaFile): ScalaMediaFile = new ScalaMediaFile(javaMediaFile.fileName, javaMediaFile.checksum, javaMediaFile.startingOffset.floatValue)
}
object ToScalaSegment {
  def fromJavaList(segmentList: java.util.List[no.lau.tagger.model.Segment]): List[ScalaSegment] =
    for{segment <- JavaConversions.asBuffer(segmentList).toList} yield ToScalaSegment.fromJava(segment)

  def fromJava(segment: no.lau.tagger.model.Segment): ScalaSegment = new ScalaSegment(segment.id, segment.start.floatValue, segment.end.floatValue, segment.text)
}

object TranslateTo {
  def from(song: ScalaSong): no.lau.tagger.model.SimpleSong = new no.lau.tagger.model.SimpleSong(song.reference, from(song.mediaFile), segmentsFrom(song), song.bpm)

  def from(segment: ScalaSegment): no.lau.tagger.model.Segment = new no.lau.tagger.model.Segment(segment.id, segment.start.floatValue, segment.end.floatValue, segment.text)

  def from(mf: ScalaMediaFile): no.lau.tagger.model.MediaFile = new no.lau.tagger.model.MediaFile(mf.fileName, mf.checksum, mf.startingOffset.floatValue)

  private def segmentsFrom(song: ScalaSong): java.util.List[no.lau.tagger.model.Segment] = {
    val list = new ArrayList[no.lau.tagger.model.Segment]()
    for (segment <- song.segments) {
      list.add(from(segment))
    }
    return list
  }
}
