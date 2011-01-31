package vdvil.server

class Segment {
  String segmentId
  int startCue
  int endCue
  String text

  String toString() { "$segmentId: $text" }


  static constraints = {
  }
}
