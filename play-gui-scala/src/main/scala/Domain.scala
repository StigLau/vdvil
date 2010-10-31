package no.lau.vdvil.player

import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.bouvet.kpro.renderer.Instructions
import no.lau.vdvil.downloading.Dvl

class ScalaComposition(var masterBpm: Float, val parts: List[ScalaAudioPart]) {
  def asInstructions = new Instructions { parts.foreach(part => append(part.translateToInstruction(masterBpm.floatValue))) }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

class ScalaAudioPart(val song: ScalaSong, val startCue: Float, val endCue: Float, val segment: ScalaSegment) {
  val bpm = song.bpm
}

/**
 * A MasterMix contains the mix which can be played to anyone. It will reference one or more MasterParts, which can contain .dvl's
 */
case class MasterMix(name:String, var masterBpm:Float, parts:List[MasterPart]) {
 def asComposition:ScalaComposition = {
   val scalaParts = for(part <- parts) yield new ScalaAudioPart(part.dvl.song, part.start, part.end, part.dvl.getSegment(part.id).getOrElse(null))
   return new ScalaComposition(masterBpm, scalaParts)
 }
}

case class MasterPart(dvl:Dvl, start:Float, end:Float, id:String)