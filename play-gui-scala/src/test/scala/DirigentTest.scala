package no.lau.vdvil.dirigent

import no.lau.vdvil.cache.VdvilCache
import no.lau.vdvil.domain.player.{ScalaComposition, MasterMix}
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import xml.XML
import no.lau.vdvil.persistence.MasterMixXML

/**
 * A Simple structuring of the Dirigent
 * The Dirigent is a simple generic way of handling the domain of multimedia playback
 *
 */
class DirigentTest {
  var compositionUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml"

  @Test def testSetupAndPlaying {
    val player = new no.lau.vdvil.player.ScalaCompositionPlayer(None)
    val dirigent = new Dirigent(VdvilHttpCache.create :: Nil, new CompositionHandler :: Nil, player) {
      playbackBpm = 150F
      prepare(compositionUrl)
    }
    dirigent.play(32)
    Thread.sleep(5000)
    dirigent.stop
  }
}

class Dirigent(downloaders: List[VdvilCache], handlers:List[VdvilHandler], var player:no.lau.vdvil.player.ScalaCompositionPlayer) {
  val log: Logger = LoggerFactory.getLogger(classOf[Dirigent])
  var playbackBpm: Float = 0F

  def prepare(compositionUrl: String) {
    for (downloader <- downloaders) { //TODO Perhaps use for-comprehension for matching correct downloader, and avoiding nagging error logging!
      if (downloader.accepts(compositionUrl)) {
        log.info("Trying to download {} with ", compositionUrl, downloader)
        var inputStream: InputStream = downloader.fetchAsStream(compositionUrl)
        var mimeType: String = downloader.mimeType(compositionUrl)
        mimeType = "composition/xml" //TODO Set the mimetype staticly until server is finished.
        for (handler <- handlers) {
          if (handler.accepts(mimeType)) {
            player.scalaCompositionOption = handler.load(inputStream, mimeType)
          }
        }
      }
      else log.warn("Downloader {} did not accept {}", downloader, compositionUrl)
    }
  }


  def play(startAtCue: Int) {
    log.info("Play ")
    player.play(startAtCue, playbackBpm)
  }

  def stop {
    log.info("Stop ")
    player.stop
  }
}

trait VdvilHandler {
  def accepts(mimeType: String): Boolean

  def load(inputStream: InputStream, mimeType: String): Option[ScalaComposition]
}

class CompositionHandler extends VdvilHandler {
  val log: Logger = LoggerFactory.getLogger(classOf[VdvilHandler])

  def accepts(mimeType: String): Boolean = "composition/xml".equals(mimeType)

  def load(inputStream: InputStream, mimeType: String): Option[ScalaComposition] = {
    if (accepts(mimeType))
      Some(MasterMixXML.fromXML(XML.load(inputStream)).asComposition)
    else
      None
  }
}