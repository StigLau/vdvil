package vdvil.repository

class Segment {
  String segmentId
  int startCue
  int endCue
  String text

  String toString() { "$segmentId: $text" }
  Song song
  static belongsTo = Song

  static constraints = {
    song(nullable:true)
  }
}
