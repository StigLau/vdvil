package no.lau.vdvil.player

import org.junit.Test
import no.lau.vdvil.downloading.Dvl
import org.junit.Assert._


class DomainTest {
  @Test def calculateDuration {
    val testThing = testData
    println("Res " + testThing.durationAsBeats)
    assertEquals(64, testThing.durationAsBeats.intValue)
  }

  def testData:MasterMix = {
    MasterMix("name", 140F,
      MasterPart(Dvl("url", "name"),0, 16, "0") ::MasterPart(Dvl("url", "name1"),32, 64, "0") ::MasterPart(Dvl("url", "name"),16, 32, "0") :: Nil)
  }
}