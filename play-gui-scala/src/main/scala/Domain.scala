package no.lau.vdvil.player

import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.lau.vdvil.downloading.Dvl
import no.bouvet.kpro.renderer.audio. {MP3Source, SimpleAudioInstruction}
import java.io.File
import no.bouvet.kpro.renderer. {Instruction, Instructions}
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.vdvil.mix.Repo

class ScalaComposition(var masterBpm: Float, val parts: List[ScalaAudioPart]) {
  def asInstructions:Instructions = new Instructions { parts.foreach(part => append(part.translateToInstruction(masterBpm.floatValue))) }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

class ScalaAudioPart(val song: ScalaSong, val startCue: Float, val endCue: Float, val segment: ScalaSegment) {
  val bpm = song.bpm

  def translateToInstruction(masterBpm: Float): Instruction = {
    val audioSource = new MP3Source(new File(ScalaCacheHandler.retrievePathToFileFromCache(song.mediaFile.fileName, song.mediaFile.checksum).get))
    //TODO check why diff neeeds to be opposite
    val partCompositionDiff: Float = bpm / masterBpm
    val compositionPartDiff: Float = masterBpm / bpm

    new SimpleAudioInstruction(
      startCue,
      endCue,
      bpm,
      segment.start,
      song.mediaFile.startingOffset,
      audioSource,
      partCompositionDiff) {
      setInterpolatedRate(compositionPartDiff, compositionPartDiff)
    }
  }
}

/**
 * A MasterMix contains the mix which can be played to anyone. It will reference one or more MasterParts, which can contain .dvl's
 */
case class MasterMix(name:String, var masterBpm:Float, parts:List[MasterPart]) {
 def asComposition:ScalaComposition = {
   val scalaParts = for(part <- parts) yield new ScalaAudioPart(Repo.findSong(part.dvl), part.start, part.end, Repo.findSegment(part.id, part.dvl)
     .getOrElse(throw new Exception("Segment with id " + part.id + " Not found in " + part.dvl.name)))
   return new ScalaComposition(masterBpm, scalaParts)
 }

  def dvls = parts.groupBy[Dvl](part => part.dvl).keySet

  def durationAsBeats:Float = parts.foldLeft(0F)((max,part) => if(part.end > max) part.end else max)
}

case class MasterPart(dvl:Dvl, start:Float, end:Float, id:String)