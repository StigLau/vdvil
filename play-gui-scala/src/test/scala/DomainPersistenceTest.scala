package no.lau.vdvil.player

import org.junit.Test
import org.junit.Assert._
import no.lau.vdvil.domain.player. {MasterPart, MasterMix, Dvl}

class DomainPersistenceTest {

  val dvl = Dvl("http://dvl.lau.no/dvls/yey.dvl", "yey")
  val example = MasterMix("A simple example", 150F,
    MasterPart(dvl, 0F, 32F, "1") :: MasterPart(dvl, 32F, 64F, "2") :: Nil)

  @Test def testStuff {
    val asXml = MasterMix.toXML(example)
    println(asXml.toString)
    val composition = MasterMix.fromXML(asXml)
    assertEquals(150, composition.masterBpm.intValue)
    assertEquals(1, composition.dvls.size)
    assertEquals(2, composition.parts.size)
    val part1 = composition.parts.head
    assertEquals(0, part1.start.intValue)
    assertEquals(32, part1.end.intValue)
  }
}