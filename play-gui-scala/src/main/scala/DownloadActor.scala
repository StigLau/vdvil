package no.lau.vdvil.downloading

import actors.Actor
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import no.lau.vdvil.gui.NeatStuff
import no.lau.vdvil.cache.ScalaCacheHandler

class DownloadActor(dvl:Dvl, coordinator: Actor) extends Actor {
  val cacheHandler = new ScalaCacheHandler()

  def act() {
    coordinator ! DownloadingDvl(dvl)
    val unconvertedSong: ScalaSong = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(dvl.url, null)
    coordinator ! ConvertingAndAddingMissingIds(dvl)
    val song = NeatStuff.convertAllNullIDsToRandom(unconvertedSong)
    val mf = song.mediaFile
    coordinator ! DownloadingMp3(dvl)
    cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum).foreach {
      pathToMp3 => coordinator ! FinishedDownloading(dvl, new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3, mf.checksum, mf.startingOffset), song.segments, song.bpm))
    }
  }
}
