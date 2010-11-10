package no.lau.vdvil.downloading

import org.junit.Test
import org.slf4j.LoggerFactory
import no.lau.vdvil.mix._
import no.lau.vdvil.domain.player. {MasterMix, Dvl}

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

    new MyRepo(coordinator).fetchComposition("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml",
      new CompositionCallback {
        def finished(compositionOption:Option[MasterMix]) {
          log.info("Finished downloading composition {}", compositionOption.get.name)
        }
    })
  }
}