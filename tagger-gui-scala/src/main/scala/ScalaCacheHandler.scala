package no.lau.vdvil.cache


import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.codehaus.httpcache4j.cache.VdvilCacheStuff
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import no.lau.tagger.scala.model._

object ScalaCacheHandler {
  //val tempFolder = System.getProperty("java.io.tmpdir")
  val httpCache = new VdvilCacheStuff(new File("/tmp/vdvil"))
  val log = LoggerFactory.getLogger(ScalaCacheHandler.getClass)
  var parser = new XStreamParser[SimpleSong]

  def printableXml(scalaSong: ScalaSong): String = parser.toXml(TranslateTo.from(scalaSong))

  /**
   * @param dvlUrl Where to locate the file
   * @param dvlChecksum if it is null, checksum is disregarded
   * @return song
   * @throws FileNotFoundException if the files werent found
   */
  def fetchSimpleSongAndCacheDvlAndMp3(dvlUrl: String, dvlChecksum: String): ScalaSong = {
    log.debug("Downloading dvl files download mp3's with httpCache")
    retrievePathToFileFromCache(dvlUrl, dvlChecksum) match {
      case Some(dvlPath: String) => loadSimpleSongFromDvlOnDisk(dvlPath)
      case _ => throw new FileNotFoundException("Could not download .dvl file " + dvlUrl)
    }
  }

  //TODO Use Option instead of null!!!!!!
  def retrievePathToFileFromCache(urlOfFile: String, checksum: String): Option[String] = {
    val cachedFile = checksum match {
      case null => httpCache.fetchAsFile(urlOfFile)
      case _ => httpCache.fetchAsFile(urlOfFile, checksum)
    }
    cachedFile match {
      case null => None
      case _ => {
        log.debug("{} is located on disk: {}", cachedFile.getName, cachedFile.getAbsolutePath)
        Some(cachedFile.getAbsolutePath)
      }
    }
  }

  def save(song: ScalaSong, path: String): Unit = parser.save(TranslateTo.from(song), path)


  def loadSimpleSongFromDvlOnDisk(cachedFile: String): ScalaSong = ToScalaSong.fromJava(parser.load(cachedFile))
}