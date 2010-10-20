package no.lau.vdvil.downloading

import org.junit.Test
import javax.swing.{PopupFactory, Popup}

/**
 * Functional test which downloads the .dvl and .mp3 songs and shows the downloading GUI
 */
class DownloadFilesActorTest {
  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
  val urls = "holden-nothing-93_returning_mix.dvl" ::
    //"unfinished_sympathy.dvl" ::
    "olive-youre_not_alone.dvl" ::
    "christian_cambas-it_scares_me.dvl" ::
    Nil

  lazy val dvls = createDvls(baseUrl, urls)
  def createDvls(base: String, urlList: List[String]): List[Dvl] = for {url <- urlList} yield Dvl(base + url, url)

  @Test def downloadFilesActor {
    val downloadingPanel = new DownloadingPanel(dvls)
    val popup: Popup = PopupFactory.getSharedInstance().getPopup(null, downloadingPanel.ui.peer, 50, 50)
    popup.show()

    val coordinator = new DownloadCoordinatorActor(dvls, downloadingPanel)
    coordinator.start
    dvls.foreach(dvl => new DownloadActor(dvl, coordinator).start)

    while(coordinator.isStillDownloading) {
      Thread.sleep(500)
    }
    popup.hide()
    downloadingPanel.quit
  }
}
