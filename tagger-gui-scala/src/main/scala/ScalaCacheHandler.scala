package no.lau.vdvil.cache


import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.codehaus.httpcache4j.cache.VdvilCacheStuff
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import no.lau.tagger.scala.model.{ToScalaSong, ScalaSong}

class ScalaCacheHandler {
  //val tempFolder = System.getProperty("java.io.tmpdir")
  val httpCache = new VdvilCacheStuff(new File("/tmp/vdvil"))
  val log = LoggerFactory.getLogger(classOf[ScalaCacheHandler])
  var parser = new XStreamParser[SimpleSong]

  def printableXml(scalaSong: ScalaSong): String = parser.toXml(scalaSong.toJava)

  /**
   * @param dvlUrl Where to locate the file
   * @param dvlChecksum if it is null, checksum is disregarded
   * @return simpleSong
   * @throws FileNotFoundException if the files werent found
   */
  def fetchSimpleSongAndCacheDvlAndMp3(dvlUrl: String, dvlChecksum: String): ScalaSong = {
    log.debug("Downloading dvl files download mp3's with httpCache")
    var cachedDvlFile: String = retrievePathToFileFromCache(dvlUrl, dvlChecksum)
    if (cachedDvlFile != null) return loadSimpleSongFromDvlOnDisk(cachedDvlFile)
    else throw new FileNotFoundException("Could not download .dvl file " + dvlUrl)
  }

  def retrievePathToFileFromCache(urlOfFile: String, checksum: String): String = {
    var cachedFile: File = null
    if (checksum == null) {
      cachedFile = httpCache.fetchAsFile(urlOfFile)
    }
    else {
      cachedFile = httpCache.fetchAsFile(urlOfFile, checksum)
    }
    if (cachedFile != null) {
      log.debug("{} is located on disk: {}", cachedFile.getName, cachedFile.getAbsolutePath)
      return cachedFile.getAbsolutePath
    }
    else return null
  }

  def save(song: ScalaSong, path: String):Unit = parser.save(song.toJava, path)


  def loadSimpleSongFromDvlOnDisk(cachedFile: String):ScalaSong = ToScalaSong.fromJava(parser.load(cachedFile))

}

