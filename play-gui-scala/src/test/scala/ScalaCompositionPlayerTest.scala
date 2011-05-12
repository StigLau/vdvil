package no.lau.vdvil.player

import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.domain.player.ScalaComposition
import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.slf4j.LoggerFactory
import org.junit.{Before, Test}
import org.junit.Assert._


class ScalaCompositionPlayerTest {
  var composition:ScalaComposition = null
  val log = LoggerFactory.getLogger(classOf[ScalaCompositionPlayerTest])
  val cache = VdvilHttpCache.create()

  @Before
  def cacheMp3Files() {
    val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
    cacheFile(baseUrl + "holden-nothing-93_returning_mix.dvl")
    cacheFile(baseUrl + "olive-youre_not_alone.dvl")
    cacheFile(baseUrl + "christian_cambas-it_scares_me.dvl")
    composition = JavaZoneDemoComposition.masterMix.asComposition
  }

  def cacheFile(url:String) {
    val song:SimpleSong = new XStreamParser().load(cache.fetchAsStream(url))
    cache.fetchAsStream(song.mediaFile.fileName)
  }

  @Test def createInstructionsFromParts {
    assertEquals(20, composition.parts.size)
    val instructions = composition.asInstructions
    assert(instructions.getDuration == 4445280)
    assert(instructions.getDuration == 252*44100*60/150)
  }

  @Test def durationCalculator {
    log.info("Duration " +     composition.durationAsBeats)
    assert(composition.durationAsBeats == 252F)
  }
}