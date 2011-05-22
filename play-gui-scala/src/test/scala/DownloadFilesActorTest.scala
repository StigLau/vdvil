package no.lau.vdvil.downloading

import org.junit.Test
import no.lau.vdvil.domain.player. {Dvl, MasterMix}
import no.lau.vdvil.player.DVLCallBackGUI
import no.lau.vdvil.cache.testresources.TestMp3s

/**
 * Functional test which downloads the .dvl and .mp3 songs and shows the downloading GUI
 */
class DownloadFilesActorTest {
  val song = MasterMix("JavaZone", 150F, Nil)
  val someList =
    Dvl(TestMp3s.returningDvl, "returning") ::
    Dvl(TestMp3s.unfinishedSympathyDvl, "unfinished_sympathy") ::
    Dvl(TestMp3s.not_aloneDvl, "You're not alone") ::
    Dvl(TestMp3s.scares_meDvl, "It scares me") ::
    Nil

  @Test def downloadFilesActor {
    val downloadingCoordinator = new DownloadingCoordinator(song, new DVLCallBackGUI(song)) {
      start
    } ! Start
  }
}