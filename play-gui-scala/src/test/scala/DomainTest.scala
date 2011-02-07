package no.lau.vdvil.domain.player

import org.junit.Assert._
import org.junit.{Ignore, Test}


class DomainTest {
  @Test def calculateDuration {
    val testThing = testData
    assertEquals(64, testThing.durationAsBeats.intValue)
  }

  def testData:MasterMix = {
    MasterMix("name", 140F,
      MasterPart(Dvl("url", "name"),0, 16, "0") ::MasterPart(Dvl("url", "name1"),32, 64, "0") ::MasterPart(Dvl("url", "name"),16, 32, "0") :: Nil)
  }

  @Ignore
  @Test def xmlParsing {
    fail("Need to write this test!! Load xml example files")
  }
}