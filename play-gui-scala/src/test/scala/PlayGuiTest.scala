package no.lau.vdvil.player

import org.junit.Test

class PlayGuiTest {
  val testComposition = new ScalaComposition(150F, CompositionExample.parts)

  @Test def setTextField {
    val panel = new PlayPanel(testComposition)
    //Check that the corresponding field also is correct
    assert(panel.bpmField.text == "150.0")
    // Changing bpm
    panel.bpmField.text_=("0.0")
    //Verify that GUI has been changed
    assert(panel.bpmField.text == "0.0")
    //Check that MasterMix has been updated by trigger
    assert(panel.composition.masterBpm == 150F)

  }
}