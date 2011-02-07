package no.lau.vdvil.player.test

import org.junit.Test
import no.lau.vdvil.player. {JavaZoneDemoComposition, ScalaCompositionPlayer}

class PlayingTest {
  //TODO Why does it download so many files!?!
  @Test def playMix {
    val composition = JavaZoneDemoComposition.masterMix.asComposition
    val player = new ScalaCompositionPlayer(Some(composition))
    player.play(0, 150F)
    Thread.sleep((16*60/150)*1000)
    player.stop
  }
}