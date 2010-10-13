package no.lau.vdvil.player

import org.junit.Test


class ScalaCompositionPlayerTest {
  val composition = new ScalaComposition(150F, CompositionExample.parts)

  @Test def createInstructionsFromParts {
    assert(composition.parts.size == 14)
    val instructions = composition.asInstructions
    assert(instructions.getDuration == 4445280)
    assert(instructions.getDuration == 252*44100*60/150)
  }

  @Test def durationCalculator {
    println("Duration " +     composition.durationAsBeats)
    assert(composition.durationAsBeats == 252F)
  }
}