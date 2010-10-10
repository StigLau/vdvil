package no.lau.vdvil

import _root_.vdvil.PlayGUI
import org.junit.Test
import no.lau.vdvil.gui.TagGUI
import org.scalatest.Assertions._
import no.lau.tagger.scala.model.{TranslateTo, ScalaSegment, ScalaMediaFile, ScalaSong}

class GuiTest {
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  @Test def loadingFileWithSwingGUI {
    val scalaSongOption = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl)

    
    assert(scalaSongOption.isDefined)
    assert(scalaSongOption.get.bpm == 130F)

  }

  @Test def startGUI {
    //Check that test-song exists
    assert(PlayGUI.testSong != Nil)
    //Check that correct value is set
    assert(PlayGUI.testSong.bpm == 130F)
    //Check that the corresponding field also is correct
    assert(PlayGUI.bpmField.text == "130.0")

    // Changing bpm
    PlayGUI.bpmField.text_=("0.0")
    //Verify that GUI has been changed
    assert(PlayGUI.bpmField.text == "0.0")
    //Check that Song has been updated by trigger
    assert(PlayGUI.testSong.bpm == 0F)    
  }
}