package vdvil.server

class Song {
  String reference
  MediaFile mediaFile
  static hasMany = [segments: Segment]
  float bpm

  static constraints = {
  }
}
