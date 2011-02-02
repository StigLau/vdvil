package vdvil.server

import groovy.xml.MarkupBuilder

class DvlController {
  def xml = {
    def mixList = MasterMix.get(params.id)
    def writer = new StringWriter()
    def builder = new MarkupBuilder(writer)

    mixList.each { mix ->
      builder.track {

        reference(mix.name)
        bpm(mix.masterBpm)
        //builder.part(id: mix.id) {
        builder.parts {
          builder.part {
            mix.parts.each { part ->
              id(part.segment.segmentId)
              start(part.startCue)
              end(part.endCue)
              builder.dvl {

                name(part.segment.song.reference)
                url(part.segment.song.mediaFile.fileName)
              }
            }
          }
        }
      }
    }


    render(writer.toString())
  }

}
