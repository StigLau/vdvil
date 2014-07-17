package no.lau.vdvil.player

import no.lau.vdvil.playback.PreconfiguredVdvilPlayer
import no.lau.vdvil.handler.Composition
import no.lau.vdvil.handler.persistence.PartXML
import no.lau.vdvil.cache.Store
import no.lau.vdvil.timing.MasterBeatPattern
import org.junit.Test

class VdvilTest {
  val url = getClass.getResource("/surrender.composition.xml")
  val vdvilPlayer = new PreconfiguredVdvilPlayer

  @Test def play {
    val composition = PreconfiguredVdvilPlayer.PARSE_FACADE.parse(PartXML.create(Store.get().cache(url))).asInstanceOf[Composition]
    vdvilPlayer.init(composition.withBeatPattern(new MasterBeatPattern(0, 16, 150F)))
    vdvilPlayer.play()
    while (vdvilPlayer.isPlaying)
      Thread.sleep(200)
    vdvilPlayer.stop()
  }
}