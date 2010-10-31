package no.lau.vdvil.player

import no.bouvet.kpro.renderer.audio.{AudioPlaybackTarget, AudioRenderer}
import no.bouvet.kpro.renderer.Renderer

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
class ScalaCompositionPlayer(var scalaCompositionOption: Option[ScalaComposition]) {
  var rendererOption: Option[Renderer] = None

  def play(startCue: Float, playBpm:Float) {
    scalaCompositionOption.foreach {
      composition =>
        rendererOption = Some(new Renderer(composition.asInstructions) {
          addRenderer(new AudioRenderer(new AudioPlaybackTarget()))
          val startCueInMillis: Float = (startCue * 44100 * 60) / playBpm
          start(startCueInMillis.intValue())
        })
    }
  }

  def stop {rendererOption.foreach(_.stop)}
}