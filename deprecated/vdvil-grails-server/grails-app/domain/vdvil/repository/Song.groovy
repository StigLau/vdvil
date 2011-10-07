package vdvil.repository

class Song {
  String reference
  MediaFile mediaFile
  static hasMany = [segments: Segment]
  float bpm

  String toString() { "$reference" }
  static constraints = {
      reference()
      bpm()
      mediaFile()
  }
}
