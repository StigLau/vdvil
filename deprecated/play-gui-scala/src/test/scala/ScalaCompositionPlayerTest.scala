package no.lau.vdvil.player

import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.domain.player.ScalaComposition
import org.junit.Test
import org.junit.Assert._

class ScalaCompositionPlayerTest {
  var composition:ScalaComposition = JavaZoneDemoComposition.masterMix.asComposition
  val cache = VdvilHttpCache.create()

  @Test def createInstructionsFromParts {
    assertEquals(20, composition.parts.size)
    val instructions = composition.asInstructions
    assertEquals(4445280, instructions.getDuration)
    assertEquals(252*44100*60/150, instructions.getDuration)
  }

  @Test def durationCalculator {
    assertEquals(252, composition.durationAsBeats.toInt)
  }
}