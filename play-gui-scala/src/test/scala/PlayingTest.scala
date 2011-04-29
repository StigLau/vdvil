package no.lau.vdvil.player.test

import org.junit.Test
import no.lau.vdvil.player.{ScalaCompositionPlayer, JavaZoneDemoComposition}
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.domain.player.{ImagePart, ScalaComposition}

class PlayingTest {
  /*
  @Test
  def cacheFiles{
    val imagePart:List[ImagePart] = JavaZoneDemoComposition.masterMix.collect{case x:ImagePart => x}
    val cache = new VdvilHttpCache
    imagePart.foreach(cache.fetchFromInternetOrRepository(_.imageUrl))
  } */
  //TODO Why does it download so many files!?!
  @Test def playMix {
    val composition:ScalaComposition = JavaZoneDemoComposition.masterMix.asComposition
    val player = new ScalaCompositionPlayer(Some(composition))
    player.play(0, 150F)
    Thread.sleep((16*60/150)*1000)
    player.stop
  }
}