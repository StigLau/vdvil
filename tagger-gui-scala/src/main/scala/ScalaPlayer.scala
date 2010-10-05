package no.lau.vdvil.player

import no.lau.vdvil.swing.VdvilSwingActor
import no.lau.tagger.scala.model.ScalaSong

class ScalaPlayer(var song: ScalaSong) {
  var started: Boolean = false
  var worker: VdvilSwingActor = null


  def playPause(startCue: Float, endCue: Float) {
    worker = started match {
      case false => {
        started = true
        new VdvilSwingActor(song, startCue, endCue) {
          start
        }
      }
      case true => {
        worker.stop
        started = false
        new VdvilSwingActor(song, startCue, endCue)
      }
    }
  }

  def update(changedSimpleSong: ScalaSong) {
    song = changedSimpleSong;
  }
}