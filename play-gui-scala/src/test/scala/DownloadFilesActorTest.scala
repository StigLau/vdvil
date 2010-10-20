package no.lau.vdvil

import org.junit.Test

class DownloadFilesActorTest {
  @Test def downloadFilesActor {
    val urls = "holden-nothing-93_returning_mix.dvl" ::
            //"unfinished_sympathy.dvl" ::
            "olive-youre_not_alone.dvl" ::
            "christian_cambas-it_scares_me.dvl" ::
            Nil


    val coordinator = new DownloadCoordinatorActor(urls.size)
    coordinator.start

    val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
    urls.foreach {
      url =>
        new DownloadActor(baseUrl + url, coordinator).start
        Thread.sleep(100)
    }
  }
}