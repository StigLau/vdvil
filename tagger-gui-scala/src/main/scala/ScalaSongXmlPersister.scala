package no.lau.vdvil.gui


import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.SimpleSong
import org.slf4j.LoggerFactory
import no.lau.tagger.scala.model._
import java.net.URL

object ScalaSongXmlPersister {
  val log = LoggerFactory.getLogger(ScalaSongXmlPersister.getClass)
  var parser = new XStreamParser[SimpleSong]

  def printableXml(scalaSong: ScalaSong): String = parser.toXml(TranslateTo.from(scalaSong))

  def save(song: ScalaSong, path: URL): Unit = parser.save(TranslateTo.from(song), path)
  }