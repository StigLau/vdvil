package no.lau.vdvil.domain.player

import org.junit.Assert._
import org.junit.{Ignore, Test}
import java.net.URL


class DomainTest {
  @Test def calculateDuration {
    val testThing = testData
    assertEquals(64, testThing.durationAsBeats.intValue)
  }

  def testData:MasterMix = {
    MasterMix("name", 140F,
      AudioPart(Dvl(new URL("http://test.com"), "name"), 0, 16, "0") ::
      AudioPart(Dvl(new URL("http://test.com"), "name"),32, 64, "0") ::
      AudioPart(Dvl(new URL("http://test.com"), "name"),16, 32, "0") :: Nil)
  }

  @Ignore
  @Test def xmlParsing {
    fail("Need to write this test!! Load xml example files")
  }
}