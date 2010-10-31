package no.lau.vdvil.mix

import no.bouvet.kpro.renderer.Instruction
import no.bouvet.kpro.renderer.audio. {SimpleAudioInstruction, MP3Source}
import java.io.File
class Repo {

  def findMasterMix(id:String) {}

  def findSegment(id:String) {}

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