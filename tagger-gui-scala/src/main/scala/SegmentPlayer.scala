package no.lau.vdvil.player

import no.bouvet.kpro.renderer.Instructions
import no.lau.tagger.scala.model.ScalaSong
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.bouvet.kpro.renderer.audio._
import java.util.Collections


@Deprecated //Disuse Segment / playing
class SegmentPlayer(var song: ScalaSong, startCue: Int, endCue: Int) extends VdvilPlayer {

  val mp3File = VdvilHttpCache.create().fetchFromInternetOrRepository(song.mediaFile.url, song.mediaFile.checksum)
  val audioSource: AudioSource = new MP3Source(mp3File)

  var player:VdvilPlayer = {

    val instructions = new Instructions
    var playLength: Int = endCue - startCue
    val masterBpm = -1F
    instructions.append(new SimpleAudioInstruction(0, playLength, song.bpm.floatValue, startCue, song.mediaFile.startingOffset.floatValue, audioSource, 1F))
    new InstructionPlayer(masterBpm, instructions, Collections.singletonList(new AudioRenderer(new AudioPlaybackTarget)))
  }


  def play(startAt:Int) {
    player.play(startAt)
  }

  def stop {
    player.stop
    audioSource.close
  }

  def isPlaying = true
}
