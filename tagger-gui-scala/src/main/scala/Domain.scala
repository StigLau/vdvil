package no.lau.tagger.scala.model

import java.net.URL

/**
 * The domain model works as the bridge between the Java and Scala world
 */

class ScalaSegment(val id: String, var start: Int, var end: Int, var text: String)

class ScalaSong(val reference: String, val mediaFile: ScalaMediaFile, var segments: List[ScalaSegment], var bpm: Float) {
  def segmentWithId(id:String):Option[ScalaSegment] = segments.filter(segment=> segment.id == id).headOption
}

class ScalaMediaFile(var url: URL, var checksum: String, var startingOffset: Float)