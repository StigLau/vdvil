import no.bouvet.kpro.renderer.audio.{AudioPlaybackTarget, AudioRenderer}
import no.bouvet.kpro.renderer.{Instructions, Renderer}
import no.lau.tagger.scala.model.ScalaComposition

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
class ScalaCompositionPlayer(scalaComposition: ScalaComposition) {
  lazy val renderer: Renderer = new Renderer(createInstructionsFromParts(scalaComposition)) {
    addRenderer(new AudioRenderer(new AudioPlaybackTarget()))
  }
  //static Logger log = Logger.getLogger(PlayStuff.class)

  def createInstructionsFromParts(composition: ScalaComposition): Instructions = {
    new Instructions() {
      //for (part <- scalaComposition.parts) {
        //append(part.translateToInstruction(scalaComposition.masterBpm.floatValue))
      //}
    }
  }

  def play(startCue: Float) {
    val startCueInMillis:Float = (startCue * 44100 * 60) / scalaComposition.masterBpm
    renderer.start(startCueInMillis.intValue())
  }

  def stop() {
    renderer.stop()
  }
}
