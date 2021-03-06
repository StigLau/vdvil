package no.lau.vdvil.player

import org.junit.Test

class PlayGuiTest {
  @Test def setTextField {

    val panel = new PlayPanel(JavaZoneDemoComposition.masterMix)
    //Check that the corresponding field also is correct
    assert(panel.bpmField.text == "150.0")
    // Changing bpm
    panel.bpmField.text_=("0.0")
    //Verify that GUI has been changed
    assert(panel.bpmField.text == "0.0")
    //Check that MasterMix has been updated by trigger
    assert(panel.masterMix.masterBpm == 150F)
  }
}