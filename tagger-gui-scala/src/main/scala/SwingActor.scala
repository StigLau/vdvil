package no.lau.vdvil.swing

import no.bouvet.kpro.tagger.AudioPlayer
import java.io.File
import no.bouvet.kpro.renderer.Instructions
import no.bouvet.kpro.renderer.audio.{SimpleAudioInstruction, MP3Source, AudioSource}
import org.slf4j.LoggerFactory
import swing.SwingWorker
import no.lau.tagger.scala.model.ScalaSong

class VdvilSwingActor(song: ScalaSong, startCue: Float, endCue: Float) extends SwingWorker //extends Actor
{
  var audioSource: AudioSource = new MP3Source(new File(song.mediaFile.fileName))
  var player = new AudioPlayer
  val log = LoggerFactory.getLogger(classOf[VdvilSwingActor])

  def act() {
    log.debug("startCue playing = " + startCue)
    var instructions = new Instructions
    var playLength: Float = endCue - startCue
    instructions.append(new SimpleAudioInstruction(0F, playLength, song.bpm.floatValue, startCue, song.mediaFile.startingOffset.floatValue, audioSource, 1F))
    player.playMusic(instructions)
  }

  def stop {
    player.stop
    audioSource.close
  }
}
