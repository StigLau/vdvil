package no.lau.vdvil.player

import org.junit.Test
import org.junit.Assert._
import no.lau.vdvil.domain.player. {MasterPart, MasterMix, Dvl}
import org.slf4j.LoggerFactory

class DomainPersistenceTest {

  val dvl = Dvl("http://dvl.lau.no/dvls/yey.dvl", "yey")
  val example = MasterMix("A simple example", 150F,
    MasterPart(dvl, 0F, 32F, "1") :: MasterPart(dvl, 32F, 64F, "2") :: Nil)
  val log = LoggerFactory.getLogger(classOf[DomainPersistenceTest])

  @Test def testXmlPersistence {
    val asXml = MasterMix.toXML(example)
    log.debug(asXml.toString)
    val composition = MasterMix.fromXML(asXml)
    assertEquals(150, composition.masterBpm.intValue)
    assertEquals(1, composition.dvls.size)
    assertEquals(2, composition.parts.size)
    val part1 = composition.parts.head
    assertEquals(0, part1.start.intValue)
    assertEquals(32, part1.end.intValue)
  }

  @Test def printCompositionToLog {
    log.debug(MasterMix.toXML(JavaZoneDemoComposition.masterMix).toString)
  }
}

object JavaZoneDemoComposition {
  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
    val returning = Dvl(baseUrl + "holden-nothing-93_returning_mix.dvl", "returning")
    val not_alone = Dvl(baseUrl + "olive-youre_not_alone.dvl", "You're not alone")
    val scares_me = Dvl(baseUrl + "christian_cambas-it_scares_me.dvl", "It scares me")

   val mixTape = MasterPart(not_alone, 0F, 32F, "4479230163500364845") ::
      MasterPart(scares_me, 16F, 48F, "5403996530329584526") ::
      MasterPart(not_alone, 32F, 70F, "8313187524105777940") ::
      MasterPart(scares_me, 48F, 64F, "5403996530329584526") ::
      MasterPart(scares_me, 64F, 112F, "1826025806904317462") ::
      MasterPart(returning, 96F, 140F, "6401936245564505757") ::
      MasterPart(returning, 96F, 140F, "6401936245564505757") ::
      MasterPart(returning, 128F, 174F, "6182122145512625145") ::
      MasterPart(returning, 144F, 174.5F, "3378726703924324403") ::
      MasterPart(returning, 174F, 175.5F, "4823965795648964701") ::
      MasterPart(returning, 175F, 176.5F, "5560598317419002938") ::
      MasterPart(returning, 176F, 240F, "9040781467677187716") ::
      MasterPart(scares_me, 208F, 224F, "8301899110835906945") ::
      MasterPart(scares_me, 224F, 252F, "5555459205073513470") :: Nil
    val masterMix = MasterMix("JavaZone Demo", 150F, mixTape)

}