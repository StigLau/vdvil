package vdvil.repository

class Segment {
  String segmentId
  int startCue
  int endCue
  String text

    Song song
    static belongsTo = Song

    String toString() { "Segment: $startCue-$endCue $text" }
    static constraints = {
        segmentId()
        startCue()
        endCue()
        text()
        song(nullable: true)
    }
}
