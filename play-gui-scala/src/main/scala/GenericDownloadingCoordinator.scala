package no.lau.vdvil.mix

import actors.Actor
import no.lau.vdvil.downloading.DVLCallBack
import org.slf4j.LoggerFactory
import org.codehaus.httpcache4j.cache.VdvilHttpCache;

class GenericDownloadingCoordinator(downloadGUICallback:DVLCallBack) extends Actor {

  val log = LoggerFactory.getLogger(classOf[GenericDownloadingCoordinator])
  var numberOfDownloads = 0

  def act {
    loop {
      react {
        case download:Download => {
          if(numberOfDownloads == 0)
            downloadGUICallback.visible
          numberOfDownloads += 1
          new DownloadActor(download, this).start
        }
        case finished: FinishedDownloading => {
          numberOfDownloads -= 1
          if (numberOfDownloads <= 0) {
            downloadGUICallback.finished
            exit
          }
        }
      }
    }
  }
}

class DownloadActor(download:Download, coordinator: Actor) extends Actor {
  val cache = VdvilHttpCache.create()

  def act {
    val inputStream = cache.fetchAsStream(download.url)
    download.callBack.finished(inputStream)
    coordinator ! FinishedDownloading(download)
  }
}


case class Download(url:String, callBack:DownloadableFileCallback)
case class FinishedDownloading(download:Download)
