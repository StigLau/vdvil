package no.lau.vdvil.dirigent

import org.codehaus.httpcache4j.cache.VdvilHttpCache
import org.junit.Test
import java.net.URL

/**
 * A Simple structuring of the Dirigent
 * The Dirigent is a simple generic way of handling the domain of multimedia playback
 *
 */
class DirigentTest {
  var compositionUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml"

  @Test def testSetupAndPlaying {
    val player = new no.lau.vdvil.player.ScalaCompositionPlayer(None)
    val dirigent = new Dirigent(ScalaHttpCache :: LocalFileCache :: Nil, new CompositionHandler :: Nil, player) {
      playbackBpm = 150F
      prepare(new URL(compositionUrl))
    }
    dirigent.play(32)
    Thread.sleep(5000)
    dirigent.stop
  }
}
