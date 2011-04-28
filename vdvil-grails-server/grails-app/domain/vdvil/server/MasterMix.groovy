package vdvil.server
//Should be renamed to Composition and merged with CompositionController
class MasterMix {
  String name
  float masterBpm
  static hasMany = [parts: MixPart]

  String toString() { name }
}