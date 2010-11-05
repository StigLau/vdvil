package no.lau.vdvil.player.test

import org.junit.Test
import no.lau.vdvil.player. {PlayGUI, ScalaCompositionPlayer}

class PlayingTest {
  @Test def playMix {
    val composition = PlayGUI.masterMix.asComposition
    val player = new ScalaCompositionPlayer(Some(composition))
    player.play(0F, 150F)
    Thread.sleep((16*60/150)*1000)
    player.stop
  }
}