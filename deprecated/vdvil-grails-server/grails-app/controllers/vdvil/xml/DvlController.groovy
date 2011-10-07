package vdvil.xml

import vdvil.repository.Song

class DvlController {
  def xml = {
    cache shared: true, validUntil: new Date()+7

    def song = Song.get(params.id)
    render(contentType: "text/xml") {
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
