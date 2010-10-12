package vdvil

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.gui.TagGUI
import no.lau.tagger.scala.model.{ScalaMediaFile, ScalaSong}
import no.lau.vdvil.player.{ScalaComposition, ScalaCompositionPlayer, CompositionExample, ScalaPlayer}

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val testSong: ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl).get


  def top = new MainFrame {
    title = "Play GUI"
    contents = new FlowPanel {
      contents += new Label("Start from")
      contents += startField
      contents += new Label("Stop")
      contents += stopField
      contents += playButton
      contents += playCompositionButton
      contents += new Label("BPM")
      contents += bpmField
    }
  }

  val bpmField = new TextField(testSong.bpm.toString, 3) {
    reactions += {case _ => if (!text.isEmpty) {testSong.bpm = text.toFloat; println("BPM Changed to " + testSong.bpm)}}
  }
  val startField = new TextField("0.0", 4) 
  val stopField = new TextField("64.0", 4)
  val playButton = new Button("Play Segment") {
    reactions += {case ButtonClicked(_) => playSegment(startField.text.toFloat, stopField.text.toFloat, testSong)}
  }
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => playComposition(startField.text.toFloat)}
  }


  var songPlayer: ScalaPlayer = null

  /**
   * Plays the segment of your choice
   */
  def playSegment(startFrom: Float, stopAt:Float, song: ScalaSong) {
    if (songPlayer != null)
      songPlayer.playPause(-1F, -1F) //Call to stop the player
    val mf = song.mediaFile
    val pathToMp3Option = TagGUI.cacheHandler.retrievePathToFileFromCache(mf.fileName, mf.checksum)
    if (pathToMp3Option.isDefined) {
      val copyOfSong = new ScalaSong(song.reference, new ScalaMediaFile(pathToMp3Option.get, mf.checksum, mf.startingOffset), song.segments, song.bpm)
      songPlayer = new ScalaPlayer(copyOfSong)
      songPlayer.playPause(startFrom, stopAt)
    }
  }

  def playComposition(startFrom: Float) {
    val composition = new ScalaComposition(bpmField.text.toFloat, CompositionExample.parts)
    val player = new ScalaCompositionPlayer(composition)
    player.play(startFrom)
  }

  /*
 override def startup(args: Array[String]) {
   val t = top
   t.size_=(new Dimension(800, 600))
   t.visible = true
 } */
}