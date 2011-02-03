package vdvil.server

class CompositionController {
  def xml = {
    def mixList = MasterMix.get(params.id)
    if (!mixList) mixList = MasterMix.list()

    render(contentType: "text/xml", encoding: "UTF-8") {
      composition {
        for (mix in mixList) {
          name(mix.name)
          masterBpm(mix.masterBpm)
          //builder.part(id: mix.id) {
          parts {
            part {
              for (part in mix.parts) {
                id(part.segment.segmentId)
                dvl {
                  name(part.segment.song.reference)
                  url("http://localhost:8080/vdvil-server/dvl/xml/" + part.segment.song.id)
                }
                start(part.startCue)
                end(part.endCue)
              }
            }
          }
        }
      }
    }
  }
}