package no.lau.vdvil.persistence

import no.lau.vdvil.domain.player.{Dvl, AudioPart, MasterMix}

object MasterMixXML {
  def fromXML(node:xml.Node) = MasterMix(
    (node \ "name").text,
    (node \ "masterBpm").text.toFloat,
    (for( part <- (node \ "parts" \ "part")) yield AudioPartXML.fromXML(part)).toList
  )

  def toXML(composition:MasterMix) = {
<composition>
  <name>{composition.name}</name>
  <masterBpm>{composition.masterBpm}</masterBpm>
  <parts>
    {composition.audioParts.map(AudioPartXML.toXML)}
  </parts>
</composition>
  }
}

object AudioPartXML {
  def fromXML(node:xml.Node) = AudioPart(
    DvlXML.fromXML((node \ "dvl").head),
    (node \ "start").text.toInt,
    (node \ "end").text.toInt,
    (node \ "id").text
  )
  def toXML(audioPart: AudioPart) =
  <part>
    <id>{audioPart.id}</id>
    {DvlXML.toXML(audioPart.dvl)}
    <start>{audioPart.startCue}</start>
    <end>{audioPart.endCue}</end>
  </part>
}

object DvlXML {
  def fromXML(node:xml.Node) = Dvl(
    (node \ "url").text,
    (node \ "name").text
  )

  def toXML(dvl:Dvl) =
    <dvl>
      <url>{dvl.url}</url>
      <name>{dvl.name}</name>
    </dvl>
}