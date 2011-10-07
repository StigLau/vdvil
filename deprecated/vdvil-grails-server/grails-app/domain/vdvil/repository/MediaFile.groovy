package vdvil.repository

class MediaFile {
  String fileName
  String checksum
  float startingOffset

  String toString() { fileName }

  static constraints = {
  }
}
