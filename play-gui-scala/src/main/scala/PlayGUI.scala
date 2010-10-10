package vdvil

import scala.swing._
import no.lau.tagger.scala.model.ScalaSong
import no.lau.vdvil.gui.TagGUI

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  //These are just test variables
  val returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"
  val testSong: ScalaSong = TagGUI.fetchDvlAndMp3FromWeb(returningDvlUrl).get

  val bpmField = new TextField(testSong.bpm.toString, 3) {
    reactions += {case _ => if (!text.isEmpty) {testSong.bpm = text.toFloat; println("BPM Changed to " + testSong.bpm)}}
  }


  def top = new MainFrame {
    title = "Play GUI"
    size = new Dimension(800, 600) //Need a frame repack

    contents = new FlowPanel {
      contents += new Button("Play")
      contents += new Button("Pause")
      contents += bpmField
    }
  }
}