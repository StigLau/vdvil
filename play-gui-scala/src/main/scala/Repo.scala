package no.lau.vdvil.mix

import no.lau.tagger.scala.model.ToScalaSong
import no.lau.tagger.scala.model. {ScalaSegment, ScalaSong}
import no.lau.vdvil.domain.player. {MasterMix, Dvl}
import xml.XML
import java.io.InputStream
import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
import org.codehaus.httpcache4j.cache.VdvilCacheStuff

object Repo {
  def findSegment(id:String, dvl:Dvl):Option[ScalaSegment] = findSong(dvl).segments.find(segment => id == segment.id)

  def findSong(dvl:Dvl):ScalaSong = {
    val xml = new XStreamParser[SimpleSong].load(VdvilCacheStuff.fetchAsStream(dvl.url))
    ToScalaSong.fromJava(xml)
  }
}

trait DownloadableFileCallback {
  def finished(fileAsStream:InputStream)
}

trait CompositionCallback {
  def finished(compositionOption:Option[MasterMix])
}

class MyRepo(downloadingCoordinator:GenericDownloadingCoordinator) {
  def fetchComposition(url:String, compositionCallBack:CompositionCallback) {
    downloadingCoordinator ! Download(url, None, new DownloadableFileCallback {
      def finished(mixAsStream:InputStream){
        compositionCallBack.finished(Some(MasterMix.fromXML(XML.load(mixAsStream))))
      }
    })
  }
}