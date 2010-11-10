package no.lau.vdvil.mix

import no.lau.tagger.model.MediaFile
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model. {ScalaSegment, ScalaSong}
import no.lau.vdvil.domain.player. {MasterMix, Dvl}
import xml.XML

object Repo {
  def findSegment(id:String, dvl:Dvl):Option[ScalaSegment] = findSong(dvl).segments.find(segment => id == segment.id)

  def findSong(dvl:Dvl):ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(dvl.url).get

  def pathToMp3Option(mf:MediaFile): Option[String] = ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
}

trait DownloadableFileCallback {
  def finished(localPath:Option[String])
}

trait CompositionCallback {
  def finished(compositionOption:Option[MasterMix])
}

class MyRepo(downloadingCoordinator:GenericDownloadingCoordinator) {
  def fetchComposition(url:String, compositionCallBack:CompositionCallback) {
    downloadingCoordinator ! Download(url, new DownloadableFileCallback {
      def finished(localPathOption:Option[String]){
        if(localPathOption.isDefined)
          compositionCallBack.finished(Some(MasterMix.fromXML(XML.loadFile(localPathOption.get))))
        else
          compositionCallBack.finished(None)
      }
    })
  }
}