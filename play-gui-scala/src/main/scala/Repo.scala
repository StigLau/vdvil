package no.lau.vdvil.mix

import no.lau.tagger.model.MediaFile
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model. {ScalaSegment, ScalaSong}
import no.lau.vdvil.downloading.Dvl

object Repo {

  def findMasterMix(id:String) {}

  def findSegment(id:String, dvl:Dvl):Option[ScalaSegment] = findSong(dvl).segments.find(segment => id == segment.id)

  def findSong(dvl:Dvl):ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(dvl.url).get

  def pathToMp3Option(mf:MediaFile): Option[String] = ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
}