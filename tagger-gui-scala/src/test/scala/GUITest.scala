package no.lau.vdvil

import org.junit.Test
import org.junit.Assert._
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model.{TranslateTo, ScalaSegment, ScalaMediaFile, ScalaSong}

class GUITest {
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"

  @Test
  def testLoadingFileWithSwingGUI {
    val scalaSongOption = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl)

    assert(scalaSongOption.isDefined)
    assertEquals(130F.intValue, scalaSongOption.get.bpm.intValue)
  }
}

class ConvertingTest {
  import VdvilDomainDataCreator._
  @Test def convertScalaSongToJava {
    val javaSong = TranslateTo.from(song)
    assertEquals("http://some.reference.com/my/test.dvl", javaSong.reference)
    assertEquals(4, javaSong.segments.size)
    assertEquals(130, javaSong.bpm.intValue)
  }
}

class NeatStuffTest {
  import no.lau.vdvil.gui.NeatStuff._
  @Test def nullIdConverter {
    val songWithNullSegmentIds = new ScalaSong("", null, List(new ScalaSegment(null, 0F, 0F, ""), new ScalaSegment("", 0F, 0F, "")), 0F)
    val convertedSong = convertAllNullIDsToRandom(songWithNullSegmentIds)
    assertNotNull(convertedSong.segments(0))
    assertNotNull(convertedSong.segments(1))
  }

  @Test def testChangingASegment {
    val editedSegment = VdvilDomainDataCreator.song.segments(0)
    editedSegment.start = 123F
    val updatedSong = updateSegmentInSimpleSong(editedSegment, VdvilDomainDataCreator.song)
    assertEquals(123, updatedSong.segments(0).start.intValue)
  }
}

object VdvilDomainDataCreator {
  val song = new ScalaSong("http://some.reference.com/my/test.dvl", new ScalaMediaFile("/tmp/test.dvl", "123", 0F),
    List(new ScalaSegment("Id1", 0F, 16F, "Intro"), new ScalaSegment("Id2", 16F, 32F, "Verse"), new ScalaSegment("Id3", 32F, 48F, "Refrain"), new ScalaSegment("Id4", 48F, 64F, "Outro"))
    , 130F)
}