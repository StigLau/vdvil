package no.lau.tagger.scala.model

/**
 * The domain model works as the bridge between the Java and Scala world
 */

class ScalaSegment(val id: String, var start: Float, var end: Float, var text: String)

class ScalaSong(val reference: String, val mediaFile: ScalaMediaFile, var segments: List[ScalaSegment], var bpm: Float) {
  def segmentWithId(id:String):Option[ScalaSegment] = segments.filter(segment=> segment.id == id).headOption
}

class ScalaMediaFile(var fileName: String, var checksum: String, var startingOffset: Float)

class ScalaAudioPart(val song:ScalaSong, val startCue:Float, val endCue:Float, val segment:ScalaSegment)

class ScalaComposition(val masterBpm:Float, val parts:List[ScalaAudioPart])