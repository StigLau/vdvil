package no.lau.vdvil.player

import no.bouvet.kpro.tagger.AudioPlayer
import no.bouvet.kpro.renderer.Instructions
import no.bouvet.kpro.renderer.audio.{SimpleAudioInstruction, MP3Source, AudioSource}
import org.slf4j.LoggerFactory
import no.lau.tagger.scala.model.ScalaSong
import actors.Actor
import org.codehaus.httpcache4j.cache.VdvilCacheStuff

class SegmentPlayer(var song: ScalaSong, startCue: Int, endCue: Int) extends Actor {
  val mp3File = VdvilCacheStuff.fileLocation(song.mediaFile.fileName)
  val audioSource: AudioSource = new MP3Source(mp3File)
  val player = new AudioPlayer
  val log = LoggerFactory.getLogger(classOf[SegmentPlayer])

  def act {
    log.debug("startCue playing = " + startCue)
    var instructions = new Instructions
    var playLength: Int = endCue - startCue
    instructions.append(new SimpleAudioInstruction(0, playLength, song.bpm.floatValue, startCue, song.mediaFile.startingOffset.floatValue, audioSource, 1F))
    player.playMusic(instructions)
  }

  def stop {
    player.stop
    audioSource.close
  }
}
