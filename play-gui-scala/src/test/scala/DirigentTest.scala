package no.lau.vdvil.dirigent

import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import no.lau.vdvil.cache.VdvilCache
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import no.lau.vdvil.domain.player.{MasterMix, ScalaComposition}
import xml.XML

/**
 * A Simple structuring of the dirigent
 */
class DirigentTest {
  var compositionUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml"

  @Test def testSetupAndPlaying {
    val dirigent = new Dirigent(compositionUrl, VdvilHttpCache.create :: Nil, new CompositionHandler :: Nil) {
      playbackBpm = 150F
      prepare
    }
    dirigent.play(32)
    Thread.sleep(5000)
    dirigent.stop
  }
}

class Dirigent(compositionUrl: String, downloaders: List[VdvilCache], handlers:List[VdvilHandler]) {
  val log: Logger = LoggerFactory.getLogger(classOf[Dirigent])
  var playbackBpm: Float = 0F

  def prepare {
    for (downloader <- downloaders) {
      if (downloader.accepts(compositionUrl))
        log.info("Trying to download {} with ", compositionUrl, downloader)
      var inputStream: InputStream = downloader.fetchAsStream(compositionUrl)
      var mimeType: String = downloader.mimeType(compositionUrl)
      mimeType = "composition/xml"
      log.info("{} has Mime type {}", compositionUrl, mimeType)
      for (handler <- handlers) {
        if (handler.accepts(mimeType)) handler.load(inputStream, mimeType)
      }
    }
  }


  def play(startAtCue: Int) { log.info("Play ")}

  def stop { log.info("Stop ") }
}

trait VdvilHandler {
  def accepts(mimeType: String): Boolean

  def load(inputStream: InputStream, mimeType: String): Option[MasterMix]
}

class CompositionHandler extends VdvilHandler {
  val log: Logger = LoggerFactory.getLogger(classOf[VdvilHandler])

  def accepts(mimeType: String): Boolean = "composition/xml".equals(mimeType)

  def load(inputStream: InputStream, mimeType: String): Option[MasterMix] = {
    if (accepts(mimeType)) {
      Some(MasterMix.fromXML(XML.load(inputStream)))
    } else
      None
  }
}