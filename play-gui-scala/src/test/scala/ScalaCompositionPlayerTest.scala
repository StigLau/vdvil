package no.lau.vdvil.player

import org.junit.Test
import no.bouvet.kpro.renderer.Instructions


class ScalaCompositionPlayerTest {

  @Test def createInstructionsFromParts {
    val composition = new ScalaComposition(150F, CompositionExample.parts)
    assert(composition.parts.size == 14)
    val instructions:Instructions = ScalaCompositionPlayer.createInstructionsFromParts(composition)
    assert(instructions.getDuration == 4445280)
  }
}