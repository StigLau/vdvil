package no.lau.vdvil.player

import java.io.File
import no.bouvet.kpro.renderer.audio.{MP3Source, SimpleAudioInstruction}
import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.bouvet.kpro.renderer.{Instructions, Instruction}
import no.lau.vdvil.downloading.Dvl

class ScalaComposition(var masterBpm: Float, val parts: List[ScalaAudioPart]) {
  def asInstructions:Instructions = new Instructions { parts.foreach(part => append(part.translateToInstruction(masterBpm.floatValue))) }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

class ScalaAudioPart(val song: ScalaSong, val startCue: Float, val endCue: Float, val segment: ScalaSegment) {
  val bpm = song.bpm

  def translateToInstruction(masterBpm: Float): Instruction = {
    val beginAtCue = 0F

    val audioSource = new MP3Source(new File(song.mediaFile.fileName))
    //TODO check why diff neeeds to be opposite
    val partCompositionDiff: Float = bpm / masterBpm
    val compositionPartDiff: Float = masterBpm / bpm

    new SimpleAudioInstruction(
      startCue,
      endCue,
      bpm,
      segment.start + beginAtCue,
      song.mediaFile.startingOffset,
      audioSource,
      partCompositionDiff) {
      setInterpolatedRate(compositionPartDiff, compositionPartDiff)
    }
  }
}

case class Song(dvls:List[Dvl])