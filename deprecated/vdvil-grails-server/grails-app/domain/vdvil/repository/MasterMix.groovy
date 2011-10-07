package vdvil.repository

class MasterMix {
  String name
  float masterBpm
  static hasMany = [parts: MixPart]

  String toString() { name }
}