package no.lau.vdvil.player

import no.bouvet.kpro.renderer.audio.{AudioPlaybackTarget, AudioRenderer}
import no.bouvet.kpro.renderer.OldRenderer
import no.lau.vdvil.domain.player.ScalaComposition
import no.vdvil.renderer.lyric.LyricRenderer
import no.vdvil.renderer.image.ImageRenderer
import no.lau.vdvil.cache.SimpleVdvilCache

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
class ScalaCompositionPlayer(var scalaCompositionOption: Option[ScalaComposition], cache:SimpleVdvilCache) {
  var rendererOption: Option[Renderer] = None

  //"Constructor"
  scalaCompositionOption.map {
      composition =>
        rendererOption = Some(new Renderer(composition.asInstructions) {
          addRenderer(new AudioRenderer(new AudioPlaybackTarget()))
          addRenderer(new LyricRenderer(1000, 100))
          addRenderer(new ImageRenderer(800, 600, cache))
        })
    }


  def play(startCue: Int, playBpm:Float) {
    val startCueInMillis: Float = (startCue * 44100 * 60) / playBpm
    rendererOption.foreach(_.start(startCueInMillis.intValue()))
  }


  def stop {
    rendererOption.foreach(_.stop)
  }
}
