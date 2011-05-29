package no.lau.vdvil.downloading

import org.junit.Test
import org.slf4j.LoggerFactory
import no.lau.vdvil.mix._
import no.lau.vdvil.domain.player. {MasterMix, Dvl}
import no.lau.vdvil.cache.testresources.TestMp3s

class DownloadingRepoTest {

  val log = LoggerFactory.getLogger(classOf[DownloadingRepoTest])


  @Test def downloadingWithRepoRight {

    val guiCallback = new DVLCallBack {
      def setLabel(dvl: Dvl, text: String) {log.info("Set label")}
      def visible {log.info("Visible")}
      def finished {log.info("finished")}
    }
    val coordinator = new GenericDownloadingCoordinator(guiCallback)
    coordinator.start

    new MyRepo(coordinator).fetchComposition(TestMp3s.javaZoneComposition,
      new CompositionCallback {
        def finished(compositionOption:Option[MasterMix]) {
          log.info("Finished downloading composition {}", compositionOption.get.name)
        }
    })
  }
}