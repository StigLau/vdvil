package no.lau.vdvil.cache


import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.codehaus.httpcache4j.cache.VdvilCacheStuff
import org.slf4j.LoggerFactory
import no.lau.tagger.scala.model._
import java.io.{InputStream, File}

object ScalaCacheHandler {
  //val tempFolder = System.getProperty("java.io.tmpdir")
  val httpCache = new VdvilCacheStuff(new File("/tmp/vdvil"))
  val log = LoggerFactory.getLogger(ScalaCacheHandler.getClass)
  var parser = new XStreamParser[SimpleSong]

  def printableXml(scalaSong: ScalaSong): String = parser.toXml(TranslateTo.from(scalaSong))

  /**
   * @param dvlUrl Where to locate the file
   * @param dvlChecksum if it is null, checksumOption is disregarded
   * @return song
   */
  def fetchSimpleSongAndCacheDvlAndMp3(dvlUrl: String, dvlChecksum: Option[String]): ScalaSong = {
    log.debug("Downloading dvl files download mp3's with httpCache")
    loadSimpleSongFromDvlStream(retrieveInputStream(dvlUrl, dvlChecksum))
  }

  def retrieveInputStream(urlOfFile: String, checksumOption: Option[String]): InputStream = checksumOption match {
    case None => httpCache.fetchAsStream(urlOfFile)
    case Some(checksum) => httpCache.fetchAsStream(urlOfFile, checksum)
  }

  /**
   * Tries to fetch mp3 file from cache
   * If it is not in cache or checksum is wrong - tries to download from the web
   * At any rate, the reference is always to where the file lies in the cache
   */
  def fetchMp3File(urlOfFile:String, checksum:String):File = httpCache.fetchAsFile(urlOfFile, checksum)

  def save(song: ScalaSong, path: String): Unit = parser.save(TranslateTo.from(song), path)

  def loadSimpleSongFromDvlStream(inputStream: InputStream): ScalaSong = ToScalaSong.fromJava(parser.load(inputStream))
}