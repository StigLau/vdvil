package no.lau.vdvil.player

import org.junit.Test
import org.junit.Assert._
import org.slf4j.LoggerFactory
import no.lau.vdvil.domain.player.{LyricPart, ImagePart, AudioPart, MasterMix, Dvl}
import java.net.URL

class DomainPersistenceTest {

  val dvl = Dvl("http://dvl.lau.no/dvls/yey.dvl", "yey")
  val example = MasterMix("A simple example", 150F,
    AudioPart(dvl, 0, 32, "1") :: AudioPart(dvl, 32, 64, "2") :: Nil)
  val log = LoggerFactory.getLogger(classOf[DomainPersistenceTest])

  @Test def testXmlPersistence {
    val asXml = MasterMix.toXML(example)
    log.debug(asXml.toString)
    val composition = MasterMix.fromXML(asXml)
    assertEquals(150, composition.masterBpm.intValue)
    assertEquals(1, composition.dvls.size)
    assertEquals(2, composition.parts.size)
    val part1 = composition.parts.head
    //assertEquals(0, part1.start.intValue)
    //assertEquals(32, part1.end.intValue)
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

  val mixTape = AudioPart(not_alone, 0, 32, "4479230163500364845") ::
    LyricPart("Vegen til mitt hjarte går gjennom skivane mine", 2, 8) ::
    ImagePart(ClassLoader.getSystemResource("images/pink_teddy.jpg"), 2, 8) ::
    AudioPart(scares_me, 16, 48, "5403996530329584526") ::
    ImagePart(ClassLoader.getSystemResource("images/dj-teddy.jpg"), 8, 16) ::
    LyricPart("Scares Me", 16, 18) ::
    AudioPart(not_alone, 32, 70, "8313187524105777940") ::
    ImagePart(new URL("http://farm1.static.flickr.com/40/87264190_d673a87545_o_d.jpg"), 32, 34) ::
    LyricPart("Not Alone 2", 32, 33) ::
    AudioPart(scares_me, 48, 64, "5403996530329584526") ::
    AudioPart(scares_me, 64, 112, "1826025806904317462") ::
    ImagePart(new URL("http://farm3.static.flickr.com/2187/2054210711_8034ee7d6b_o_d.jpg"), 64, 65) ::
    AudioPart(returning, 96, 140, "6401936245564505757") ::
    AudioPart(returning, 96, 140, "6401936245564505757") ::
    AudioPart(returning, 128, 174, "6182122145512625145") ::
    AudioPart(returning, 144, 174, "3378726703924324403") ::
    AudioPart(returning, 174, 175, "4823965795648964701") ::
    AudioPart(returning, 175, 176, "5560598317419002938") ::
    AudioPart(returning, 176, 240, "9040781467677187716") ::
    AudioPart(scares_me, 208, 224, "8301899110835906945") ::
    AudioPart(scares_me, 224, 252, "5555459205073513470") :: Nil
  val masterMix = MasterMix("JavaZone Demo", 150, mixTape)
}