package no.lau.vdvil.dirigent

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.InputStream
import xml.XML
import no.lau.vdvil.persistence.MasterMixXML
import no.lau.vdvil.domain.player.ScalaComposition
import java.net.URL
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.lau.vdvil.cache.VdvilCache


class Dirigent(downloaders: List[FileCache], handlers:List[VdvilHandler], var player:no.lau.vdvil.player.ScalaCompositionPlayer) {
  val log: Logger = LoggerFactory.getLogger(classOf[Dirigent])
  var playbackBpm: Float = 0F

  def prepare(compositionUrl: URL) {
    for (downloader <- downloaders) { //TODO Perhaps use for-comprehension for matching correct downloader, and avoiding nagging error logging!
      if (downloader.accepts(compositionUrl)) {
        log.info("Trying to download {} with ", compositionUrl, downloader)
        val inputStream: InputStream = downloader.fetchAsStream(compositionUrl)
        var mimeType: String = downloader.mimeType(compositionUrl)
        mimeType = "composition/xml" //TODO Set the mimetype staticly until server is finished.
        for (handler <- handlers) {
          if (handler.accepts(mimeType)) {
            player.scalaCompositionOption = handler.load(inputStream, mimeType)
          }
        }
      }
      else log.warn("DownloaderFacade {} did not accept {}", downloader, compositionUrl)
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
  def accepts(mimeType: String):Boolean

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

trait FileCache {
  def fetchAsStream(url:URL):InputStream

 /* Checks whether the downloader accepts the URL format */
  def accepts(url:URL):Boolean

  def mimeType(url:URL):String
}

object LocalFileCache extends FileCache {
  def accepts(url:URL):Boolean = true
  def fetchAsStream(url:URL):InputStream = url.openStream
  def mimeType(url:URL):String = "any/something"
}

object ScalaHttpCache extends FileCache{
  val vdvilCache:VdvilCache = VdvilHttpCache.create

  def fetchAsStream(url:URL):InputStream = vdvilCache.fetchAsStream(url.toString)

 /* Checks whether the downloader accepts the URL format */
  def accepts(url:URL):Boolean = vdvilCache.accepts(url.toString)

  def mimeType(url:URL):String = vdvilCache.mimeType(url.toString)

}