package no.lau.vdvil.mix

import no.lau.tagger.model.MediaFile
import no.lau.vdvil.cache.ScalaCacheHandler

object Repo {

  def findMasterMix(id:String) {}

  def findSegment(id:String) {}

  def pathToMp3Option(mf:MediaFile): Option[String] = ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
}