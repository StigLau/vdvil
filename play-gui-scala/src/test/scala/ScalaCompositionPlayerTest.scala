package no.lau.vdvil.player

import org.junit.Test


class ScalaCompositionPlayerTest {

  @Test def createInstructionsFromParts {
    val composition = new ScalaComposition(150F, CompositionExample.parts)
    assert(composition.parts.size == 14)
    val instructions = composition.asInstructions
    assert(instructions.getDuration == 4445280)
    assert(instructions.getDuration == 252*44100*60/150)
  }
}