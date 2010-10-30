package no.lau.vdvil.downloading

import org.junit.Test
import no.lau.vdvil.player.Song

/**
 * Functional test which downloads the .dvl and .mp3 songs and shows the downloading GUI
 */
class DownloadFilesActorTest {
  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
  val song = Song(
    Dvl(baseUrl + "holden-nothing-93_returning_mix.dvl", "returning") ::
    Dvl(baseUrl + "unfinished_sympathy.dvl", "unfinished_sympathy") ::
    Dvl(baseUrl + "olive-youre_not_alone.dvl", "You're not alone") ::
    Dvl(baseUrl + "christian_cambas-it_scares_me.dvl", "It scares me") ::
    Nil
)

  def createDvls(base: String, urlList: List[String]): List[Dvl] = for {url <- urlList} yield Dvl(base + url, url)

  @Test def downloadFilesActor {
    val downloadingCoordinator = new DownloadingCoordinator(song, new DVLCallBack) {
      start
    } ! Start
  }
}