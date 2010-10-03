package no.lau.vdvil.player

import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.gui.Worker

class ScalaPlayer(var simpleSong: SimpleSong) {
  var started: Boolean = false
  var worker: Worker = null


  def playPause(startCue: Float, endCue: Float) {
    worker = started match {
      case false => {
        started = true
        new Worker(simpleSong, startCue, endCue) {
          execute
        }
      }
      case true => {
        worker.stop()
        started = false
        new Worker(simpleSong, startCue, endCue)
      }
    }
  }

  def update(changedSimpleSong: SimpleSong) {
    simpleSong = changedSimpleSong;
  }
}