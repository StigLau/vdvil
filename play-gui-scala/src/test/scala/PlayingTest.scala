package no.lau.vdvil.player.test

import org.junit.Test
import no.lau.vdvil.player.{ScalaComposition, CompositionExample, ScalaCompositionPlayer}

class PlayingTest {
  @Test def playMix {
    val composition = new ScalaComposition(150F, CompositionExample.parts)
    val player = new ScalaCompositionPlayer(composition)
    player.play(0F)
    Thread.sleep(5000)
    player.stop
  }
}