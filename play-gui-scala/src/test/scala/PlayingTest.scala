package no.lau.vdvil.player.test

import no.lau.vdvil.player.{ScalaCompositionPlayer, JavaZoneDemoComposition}
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.domain.player.{ImagePart, ScalaComposition}
import org.junit.{Ignore, Test}

class PlayingTest {

  @Test
  @Ignore // Problem caching the files on disk that are violating ET tag...
  def cacheFiles{
    val imagePart:List[ImagePart] = JavaZoneDemoComposition.masterMix.parts.collect{case x:ImagePart => x}
    val cache = VdvilHttpCache.create()
    imagePart.foreach{image => cache.fetchFromInternetOrRepository(image.url.toString, "Woot")}
  }
  //TODO Why does it download so many files!?!
  @Test def playMix {
    val composition:ScalaComposition = JavaZoneDemoComposition.masterMix.asComposition
    val player = new ScalaCompositionPlayer(Some(composition))
    player.play(0, 150F)
    Thread.sleep((16*60/150)*1000)
    player.stop
  }
}