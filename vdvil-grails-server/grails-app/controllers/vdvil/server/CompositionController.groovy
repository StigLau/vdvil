package vdvil.server

class CompositionController {
  def xml = {
    cache shared: true, validUntil: new Date()+7

    def mixList = MasterMix.get(params.id)
    if (!mixList) mixList = MasterMix.list()

    render(contentType: "text/xml", encoding: "UTF-8") {
      composition {
        for (mix in mixList) {
          name(mix.name)
          masterBpm(mix.masterBpm)
          //builder.part(id: mix.id) {
          parts {
            for (aPart in mix.parts) {
              part {
                id(aPart.segment.segmentId)
                dvl {
                  name(aPart.segment.song.reference)
                  url("http://localhost:8080/vdvil-server/dvl/xml/" + aPart.segment.song.id)
                }
                start(aPart.startCue)
                end(aPart.endCue)
              }
            }
          }
        }
      }
    }
  }
}