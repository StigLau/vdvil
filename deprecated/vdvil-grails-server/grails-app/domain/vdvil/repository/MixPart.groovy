package vdvil.repository

/**
 * A mixPart is a reference to a Segment which is played as part of a masterMix
 * it also tells when it is played in context of a masterMix
 */
class MixPart {
  Segment segment
  int startCue
  int endCue

    static constraints = {
        startCue()
        endCue()
        segment()
    }
    String toString() { "Part: $startCue-$endCue $segment" }

}
