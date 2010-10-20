package no.lau.vdvil.downloading

import collection.immutable.HashMap
import swing. {GridPanel, Label, SimpleSwingApplication}

class DownloadingPanel(dvls: List[Dvl]) extends DvlLabel{
  lazy val dvlLabels:Map[Dvl, Label] = asMap

  lazy val ui = new GridPanel(dvlLabels.size, 1) {
    dvlLabels.foreach(contents += _._2)
  }

  def asMap: Map[Dvl, Label] = {
    var map = new HashMap[Dvl, Label]
      dvls.foreach(dvl => map += dvl -> new Label(dvl.url))
    map
    //for{dvl <- dvls} yield (dvl -> new Label(dvl.url))
  }

  def setLabel(dvl:Dvl, text:String) { dvlLabels(dvl).text_=(text) }
}

trait DvlLabel {
  def setLabel(dvl:Dvl, text:String)
}