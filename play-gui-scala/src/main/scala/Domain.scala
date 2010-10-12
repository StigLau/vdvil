package no.lau.vdvil.player

import java.io.File
import no.bouvet.kpro.renderer.audio.{MP3Source, SimpleAudioInstruction}
import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.bouvet.kpro.renderer.Instruction


class ScalaComposition(val masterBpm: Float, val parts: List[ScalaAudioPart])


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