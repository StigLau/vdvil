package no.lau.vdvil.player

import org.junit.Test
import org.junit.Assert._
import no.lau.vdvil.domain.player. {MasterPart, MasterMix, Dvl}
import org.slf4j.LoggerFactory

class DomainPersistenceTest {

  val dvl = Dvl("http://dvl.lau.no/dvls/yey.dvl", "yey")
  val example = MasterMix("A simple example", 150F,
    MasterPart(dvl, 0, 32, "1") :: MasterPart(dvl, 32, 64, "2") :: Nil)
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

   val mixTape = MasterPart(not_alone, 0, 32, "4479230163500364845") ::
      MasterPart(scares_me, 16, 48, "5403996530329584526") ::
      MasterPart(not_alone, 32, 70, "8313187524105777940") ::
      MasterPart(scares_me, 48, 64, "5403996530329584526") ::
      MasterPart(scares_me, 64, 112, "1826025806904317462") ::
      MasterPart(returning, 96, 140, "6401936245564505757") ::
      MasterPart(returning, 96, 140, "6401936245564505757") ::
      MasterPart(returning, 128, 174, "6182122145512625145") ::
      MasterPart(returning, 144, 174, "3378726703924324403") ::
      MasterPart(returning, 174, 175, "4823965795648964701") ::
      MasterPart(returning, 175, 176, "5560598317419002938") ::
      MasterPart(returning, 176, 240, "9040781467677187716") ::
      MasterPart(scares_me, 208, 224, "8301899110835906945") ::
      MasterPart(scares_me, 224, 252, "5555459205073513470") :: Nil
    val masterMix = MasterMix("JavaZone Demo", 150, mixTape)

}