package no.lau.vdvil.player

import no.lau.vdvil.gui.TagGUI
import no.lau.vdvil.cache.ScalaCacheHandler
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}


object CompositionExample {
  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"

  lazy val returning: ScalaSong = getSong("holden-nothing-93_returning_mix.dvl")
  lazy val unfinished_sympathy: ScalaSong = getSong("unfinished_sympathy.dvl")
  lazy val not_alone: ScalaSong = getSong("olive-youre_not_alone.dvl")
  lazy val scares_me: ScalaSong = getSong("christian_cambas-it_scares_me.dvl")

  def getSong(url: String): ScalaSong = {
    val song = TagGUI.fetchDvlAndMp3FromWeb(baseUrl + url).get
    val mf = song.mediaFile
    val pathToMp3Option: Option[String] = ScalaCacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    return new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)
  }

  lazy val parts: List[ScalaAudioPart] = {
    new ScalaAudioPart(not_alone, 0F, 32F, not_alone.segments(0)) ::
            new ScalaAudioPart(scares_me, 16F, 48F, scares_me.segments(2)) ::
            new ScalaAudioPart(not_alone, 32F, 70F, not_alone.segments(1)) ::
            new ScalaAudioPart(scares_me, 48F, 64F, scares_me.segments(2)) ::
            new ScalaAudioPart(scares_me, 64F, 112F, scares_me.segments(4)) ::
            new ScalaAudioPart(returning, 96F, 140F, returning.segments(4)) ::
            new ScalaAudioPart(returning, 96F, 140F, returning.segments(4)) ::
            new ScalaAudioPart(returning, 128F, 174F, returning.segments(6)) ::
            new ScalaAudioPart(returning, 144F, 174.5F, returning.segments(9)) ::
            new ScalaAudioPart(returning, 174F, 175.5F, returning.segments(10)) ::
            new ScalaAudioPart(returning, 175F, 176.5F, returning.segments(11)) ::
            new ScalaAudioPart(returning, 176F, 240F, returning.segments(12)) ::
            new ScalaAudioPart(scares_me, 208F, 224F, scares_me.segments(12)) ::
            new ScalaAudioPart(scares_me, 224F, 252F, scares_me.segments(13)) :: Nil
  }

}