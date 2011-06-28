package no.lau.vdvil.mix

import no.lau.tagger.scala.model.ToScalaSong
import no.lau.tagger.scala.model. {ScalaSegment, ScalaSong}
import xml.XML
import java.io.InputStream
import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import java.net.URL
import no.lau.vdvil.domain.player.{MasterMix, Dvl}
import no.lau.vdvil.persistence.MasterMixXML

@Deprecated
object Repo {
  val cache = VdvilHttpCache.create();

  def findSegment(id:String, dvl:Dvl):Option[ScalaSegment] = findSong(dvl).segments.find(segment => id == segment.id)

  def findSong(dvl:Dvl):ScalaSong = {
    val xml = new XStreamParser[SimpleSong].load(cache.fetchAsStream(dvl.url))
    ToScalaSong.fromJava(xml)
  }

  def findFile(url:URL):InputStream = cache.fetchAsStream(url)
}

trait DownloadableFileCallback {
  def finished(fileAsStream:InputStream)
}

trait CompositionCallback {
  def finished(compositionOption:Option[MasterMix])
}

class MyRepo(downloadingCoordinator:GenericDownloadingCoordinator) {
  def fetchComposition(url:URL , compositionCallBack:CompositionCallback) {
    downloadingCoordinator ! Download(url, new DownloadableFileCallback {
      def finished(mixAsStream:InputStream){
        compositionCallBack.finished(Some(MasterMixXML.fromXML(XML.load(mixAsStream))))
      }
    })
  }
}