package no.lau.vdvil.player

import org.junit.Test
import org.junit.Assert._
import org.slf4j.LoggerFactory
import java.net.URL
import no.lau.vdvil.domain.player._
import no.lau.vdvil.persistence.MasterMixXML
import no.vdvil.renderer.audio.TestMp3s

class DomainPersistenceTest {

  val dvl = Dvl(new URL("http://dvl.lau.no/dvls/yey.dvl"), "yey")
  val example = MasterMix("A simple example", 150F,
    AudioPart(dvl, 0, 32, "1") :: AudioPart(dvl, 32, 64, "2") :: Nil)
  val log = LoggerFactory.getLogger(classOf[DomainPersistenceTest])

  @Test def testXmlPersistence {
    val asXml = MasterMixXML.toXML(example)
    log.debug(asXml.toString)
    val composition = MasterMixXML.fromXML(asXml)
    assertEquals(150, composition.masterBpm.intValue)
    assertEquals(1, composition.dvls.size)
    assertEquals(2, composition.parts.size)
    val part1 = composition.parts.head
    assertEquals(0, part1.startCue)
    assertEquals(32, part1.endCue)
  }

  @Test def printCompositionToLog {
    log.debug(MasterMixXML.toXML(JavaZoneDemoComposition.masterMix).toString)
  }
}

@Deprecated // Remove usage of "baseurl + something
object JavaZoneDemoComposition {
  val baseUrl = "https://s3.amazonaws.com/dvl-test-music/dvl/"
  val returning = Dvl(TestMp3s.returningDvl, "returning")
  val not_alone = Dvl(TestMp3s.not_aloneDvl, "You're not alone")
  val scares_me = Dvl(TestMp3s.scares_meDvl, "It scares me")

  val mixTape:List[MultimediaPartTrait] = AudioPart(not_alone, 0, 32, "4479230163500364845") ::
    LyricPart("Vegen til mitt hjarte går gjennom skivane mine", 2, 8) ::
    ImagePart("1", new URL("https://dvl-test-music.s3.amazonaws.com/test-images/teddy/cute-teddy.jpg"), 2, 8) ::
    AudioPart(scares_me, 16, 48, "5403996530329584526") ::
    ImagePart("2", new URL("https://dvl-test-music.s3.amazonaws.com/test-images/teddy/4186213402_41896e5599_z.jpg"), 8, 16) ::
    LyricPart("Scares Me", 16, 18) ::
    AudioPart(not_alone, 32, 70, "8313187524105777940") ::
    ImagePart("3", new URL("http://farm1.static.flickr.com/40/87264190_d673a87545_o_d.jpg"), 32, 34) ::
    LyricPart("Not Alone 2", 32, 33) ::
    AudioPart(scares_me, 48, 64, "5403996530329584526") ::
    AudioPart(scares_me, 64, 112, "1826025806904317462") ::
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