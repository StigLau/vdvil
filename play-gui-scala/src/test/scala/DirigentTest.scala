package no.lau.vdvil.dirigent

import java.net.URL
import org.junit.{Ignore, Test}
import org.slf4j.LoggerFactory
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.handler.persistence.{CompositionXMLParser, SimpleFileCache}
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser
import no.vdvil.renderer.audio.AudioXMLParser
import no.lau.vdvil.handler.{Composition, DownloadAndParseFacade}
import no.lau.vdvil.cache.testresources.TestMp3s

/**
 * A Simple structuring of the Dirigent
 * The Dirigent is a simple generic way of handling the domain of multimedia playback
 *
 */
@Deprecated //Has been severely outdated
class DirigentTest {
  var compositionUrl = TestMp3s.javaZoneComposition

  val player = new no.lau.vdvil.player.ScalaCompositionPlayer(None, VdvilHttpCache.create())
  val log = LoggerFactory.getLogger(classOf[DirigentTest])

  @Test
  @Ignore def testSetupAndPlaying {

    val dirigent = new Dirigent(ScalaHttpCache :: LocalFileCache :: Nil, new CompositionHandler :: Nil, player) {
      playbackBpm = 150F
      prepare(compositionUrl)
    }
    dirigent.play(32)
    Thread.sleep(5000)
    dirigent.stop
  }

  @Test
  def advancedPreparing {
    val dirigent = new Dirigent(ScalaHttpCache :: LocalFileCache :: Nil, new CompositionHandler :: Nil, player)
    //dirigent.preparingMagic(compositionUrl)
  }
}

class ImprovedDirigentTest {
  @Test
  def runThis {
    val compositionURL = TestMp3s.javaZoneComposition
    val downloadAndParseFacade = new DownloadAndParseFacade {
      addCache(VdvilHttpCache.create())
      addParser(new CompositionXMLParser(this))
      addParser(new ImageDescriptionXMLParser(this))
      addParser(new AudioXMLParser(this))
    }
    val composition:Composition = downloadAndParseFacade.parse("", compositionURL).asInstanceOf[Composition]
    println("My compositiion; " + composition)
    /*
    val player = new ScalaCompositionPlayer(Some(composition), VdvilHttpCache.create)
    player.play(0, 150F)
    Thread.sleep((16*60/150)*1000)
    player.stop
    */
  }
}
