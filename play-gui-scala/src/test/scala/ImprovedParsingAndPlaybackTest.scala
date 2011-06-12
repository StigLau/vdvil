package no.vdvil.playback

import org.junit.Test
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser
import no.vdvil.renderer.audio.AudioXMLParser
import java.net.URL
import no.lau.vdvil.cache.testresources.TestMp3s
import no.lau.vdvil.handler.{Composition, DownloadAndParseFacade}
import no.lau.vdvil.handler.persistence.{PartXML, CompositionXMLParser}
import no.vdvil.renderer.image.{ImageRenderer, ImageInstruction}
import no.bouvet.kpro.renderer.audio.{AudioPlaybackTarget, AudioRenderer}
import no.lau.vdvil.player.{InstructionPlayer, VdvilPlayer}
import no.bouvet.kpro.renderer.{AbstractRenderer, Instructions}
import collection.JavaConversions._

class ImprovedParsingAndPlaybackTest {

  val downloadAndParseFacade = new DownloadAndParseFacade {
    addCache(VdvilHttpCache.create)
    addParser(new CompositionXMLParser(this))
    addParser(new ImageDescriptionXMLParser(this))
    addParser(new AudioXMLParser(this))
  }

  @Test
  def tryPlayback {
    var compositionURL: URL = TestMp3s.javaZoneComposition
    var masterBpm: Float = 150F

    var composition = downloadAndParseFacade.parse(PartXML.create(compositionURL)).asInstanceOf[Composition]
    var instructions: Instructions = composition.instructions(masterBpm)

    //To tell the renderer to stop after the last instruction
    instructions.endAt(16 * 60 * 4410)

    for (instruction <- instructions.lock.toArray) {
      if (instruction.isInstanceOf[ImageInstruction]) {
        (instruction.asInstanceOf[ImageInstruction]).cache(downloadAndParseFacade)
      }
    }
    instructions.unlock

    val renderers:List[_ <: AbstractRenderer] = new ImageRenderer(800, 600, downloadAndParseFacade) :: new AudioRenderer(new AudioPlaybackTarget) :: Nil
    val player: VdvilPlayer = new InstructionPlayer(masterBpm, instructions, renderers)
    player.play(0)
    while (player.isPlaying) {
      Thread.sleep(500)
    }
  }
}