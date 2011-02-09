package no.lau.vdvil.player

import org.codehaus.httpcache4j.cache.VdvilCacheStuff
import org.junit.{Before, Test}
import no.lau.vdvil.domain.player.ScalaComposition
import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.slf4j.LoggerFactory


class ScalaCompositionPlayerTest {
  var composition:ScalaComposition = null
  val log = LoggerFactory.getLogger(classOf[ScalaCompositionPlayerTest])

  @Before
  def cacheMp3Files() {
    val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
    cacheFile(baseUrl + "holden-nothing-93_returning_mix.dvl")
    cacheFile(baseUrl + "olive-youre_not_alone.dvl")
    cacheFile(baseUrl + "christian_cambas-it_scares_me.dvl")
    composition = JavaZoneDemoComposition.masterMix.asComposition
  }

  def cacheFile(url:String) {
    val song:SimpleSong = new XStreamParser().load(VdvilCacheStuff.fetchAsStream(url))
    VdvilCacheStuff.fetchAsStream(song.mediaFile.fileName)
  }

  @Test def createInstructionsFromParts {
    assert(composition.parts.size == 14)
    val instructions = composition.asInstructions
    assert(instructions.getDuration == 4445280)
    assert(instructions.getDuration == 252*44100*60/150)
  }

  @Test def durationCalculator {
    log.info("Duration " +     composition.durationAsBeats)
    assert(composition.durationAsBeats == 252F)
  }
}