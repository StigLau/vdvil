package no.lau.vdvil.player

import no.lau.vdvil.timing.MasterBeatPattern
import no.lau.vdvil.handler.persistence.PartXML
import org.junit.Test
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer
import no.lau.vdvil.handler.Composition

class VdvilTest {
  val url = getClass.getResource("/CompositionExample.xml") //TODO Have a more exiting file
  val vdvilPlayer = new PreconfiguredVdvilPlayer

  @Test def play {
    val composition = PreconfiguredVdvilPlayer.downloadAndParseFacade.parse(PartXML.create(url)).asInstanceOf[Composition]
    vdvilPlayer.init(composition.withBeatPattern(new MasterBeatPattern(0, 16, 150F)))
    vdvilPlayer.play()
    while (vdvilPlayer.isPlaying)
      Thread.sleep(200)
  }
}