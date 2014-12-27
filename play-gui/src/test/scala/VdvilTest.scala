package no.lau.vdvil.player

import no.lau.vdvil.playback.{BackStage, VdvilAudioConfig}
import no.lau.vdvil.handler.Composition
import no.lau.vdvil.handler.persistence.PartXML
import no.lau.vdvil.cache.Store
import no.lau.vdvil.timing.MasterBeatPattern
import org.junit.Test

class VdvilTest {
  val url = getClass.getResource("/surrender.composition.xml")

  @Test def play() {
    val composition = new VdvilAudioConfig().getParseFacade.parse(PartXML.create(Store.get().cache(url))).asInstanceOf[Composition]
    val vdvilPlayer = new BackStage()prepare composition.withBeatPattern(new MasterBeatPattern(0, 16, 150F))
    vdvilPlayer.play()
    while (vdvilPlayer.isPlaying)
      Thread.sleep(200)
    vdvilPlayer.stop()
  }
}