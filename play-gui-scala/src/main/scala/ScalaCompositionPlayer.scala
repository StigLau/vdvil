package no.lau.vdvil.player

import no.bouvet.kpro.renderer.audio.{AudioPlaybackTarget, AudioRenderer}
import no.bouvet.kpro.renderer.Renderer
import no.lau.vdvil.domain.player.ScalaComposition
import no.vdvil.renderer.lyric.LyricRenderer
import no.vdvil.renderer.lyric.LyricGUI
import no.vdvil.renderer.image.ImageRenderer
import no.vdvil.renderer.image.swinggui.ImageGUI

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
class ScalaCompositionPlayer(var scalaCompositionOption: Option[ScalaComposition]) {
  var rendererOption: Option[Renderer] = None

  def play(startCue: Int, playBpm:Float) {
    scalaCompositionOption.map {
      composition =>
        rendererOption = Some(new Renderer(composition.asInstructions) {
          addRenderer(new AudioRenderer(new AudioPlaybackTarget()))
          val lyricGUI = new LyricGUI() {
            create
            show
          }
          addRenderer(new LyricRenderer(lyricGUI))
          val imageGUI = new ImageGUI(800, 600)
          addRenderer(new ImageRenderer(imageGUI.getImageListener))
          val startCueInMillis: Float = (startCue * 44100 * 60) / playBpm
          start(startCueInMillis.intValue())
        })
    }
  }

  def stop {rendererOption.foreach(_.stop)}
}