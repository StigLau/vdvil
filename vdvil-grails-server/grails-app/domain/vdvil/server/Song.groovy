package vdvil.server

class Song {
  String reference
  MediaFile mediaFile
  static hasMany = [segments: Segment]
  float bpm

  String toString() { "$id:$reference" }
  static constraints = {
  }
}
