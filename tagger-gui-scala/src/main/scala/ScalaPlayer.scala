package no.lau.vdvil.player

import no.lau.vdvil.swing.VdvilSwingActor
import no.lau.tagger.scala.model.ScalaSong

class ScalaPlayer(song: ScalaSong) {
  var started: Boolean = false
  var worker: VdvilSwingActor = null


  def play(startCue: Float, endCue: Float) {
    started = true
    worker = new VdvilSwingActor(song, startCue, endCue) {
      start
    }
  }

  def stop {
    worker.stop
    started = false
  }
}