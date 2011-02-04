package vdvil.server

class DvlController {
  def xml = {
    def songs = Song.get(params.id)
    if (!songs) songs = Song.list()

    render(contentType: "text/xml") {
      for(song in songs) {
        track {
          reference(song.reference)
          bpm(song.bpm)
          mediaFile {
            def mf = song.mediaFile
            fileName(mf.fileName)
            startingOffset(mf.startingOffset)
            checksum(mf.checksum)
          }
          segments {
            for(aSegment in song.segments) {
              segment {
                id(aSegment.segmentId)
                text(aSegment.text)
                start(aSegment.startCue)
                end(aSegment.endCue)
              }
            }
          }
        }
      }
    }
  }
}
