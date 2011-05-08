import vdvil.server.Song
import vdvil.server.MediaFile
import vdvil.server.Segment
import vdvil.server.MasterMix
import vdvil.server.MixPart
import vdvil.server.ImageDescription

class BootStrap {

  def init = { servletContext ->
    def nothing = new Song(
            reference: "Holden an Thompsen - Nothing",
            bpm: 130F,
            mediaFile: new MediaFile(fileName: "http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3", startingOffset: 5.945F, checksum: "3e3477a6ccba67aa9f3196390f48b67d").save(),
            segments: [
                    new Segment(startCue: 0, endCue: 64, segmentId: "4336519975847252321", text: "low"),
                    new Segment(startCue: 64, endCue: 128, segmentId: "4638184666682848978", text: ""),
                    new Segment(startCue: 128, endCue: 256, segmentId: "2754708889643705332", text: "Up"),
                    new Segment(startCue: 256, endCue: 320, segmentId: "4533227407229953527", text: "Down"),
                    new Segment(startCue: 320, endCue: 416, segmentId: "6401936245564505757", text: "Setting up"),
                    new Segment(startCue: 416, endCue: 448, segmentId: "30189981949854134", text: "Want nothing 1. time"),
                    new Segment(startCue: 448, endCue: 480, segmentId: "6182122145512625145", text: ""),
                    new Segment(startCue: 480, endCue: 512, segmentId: "6978423701190173373", text: "Action satisfaction"),
                    new Segment(startCue: 512, endCue: 544, segmentId: "3657904262668647219", text: "Calming synth"),
                    new Segment(startCue: 544, endCue: 576, segmentId: "3378726703924324403", text: "Lyrics - 1. part"),
                    new Segment(startCue: 576, endCue: 608, segmentId: "4823965795648964701", text: "Want nothing - 2. part"),
                    new Segment(startCue: 608, endCue: 640, segmentId: "5560598317419002938", text: "want nothing - 2. time"),
                    new Segment(startCue: 640, endCue: 704, segmentId: "9040781467677187716", text: "want nothing - 3. time"),
                    new Segment(startCue: 704, endCue: 768, segmentId: "5762690949488488062", text: "Synth"),
                    new Segment(startCue: 768, endCue: 1024, segmentId: "651352148519104110", text: "End")
            ])

    def notAlone = new Song(
            reference: "Olive - You're not alone",
            bpm: 134.2F,
            mediaFile: new MediaFile(fileName: "http://kpro09.googlecode.com/svn/test-files/olive-youre_not_alone.mp3", startingOffset: 14.75F, checksum: "cf9e64d28bff496cd4ac3d51201795b7").save(),
            segments: [
                    new Segment(startCue: 0, endCue: 64, segmentId: "4479230163500364845", text: "Alone singing"),
                    new Segment(startCue: 64, endCue: 128, segmentId: "8313187524105777940", text: ""),
                    new Segment(startCue: 64, endCue: 256, segmentId: "6708181734132071585", text: "Youre not alone + strings"),
                    new Segment(startCue: 152, endCue: 320, segmentId: "775308953279502385", text: "It is the distance that makes life a little hard"),
                    new Segment(startCue: 216, endCue: 416, segmentId: "7000374134360427377", text: "Youre not alone + strings + d'b")
            ])
    def scaresMe = new Song(
            reference: "Christian Cambas - Scares me",
            bpm: 131.3F,
            mediaFile: new MediaFile(fileName: "http://kpro09.googlecode.com/svn/test-files/Christian_Cambas-It_Scares_Me-Original_Mix.mp3", startingOffset: 1.002F, checksum: "2fe47d1571a7cb085895749de25a3b2c").save(),
            segments: [
                    new Segment(startCue: 30, endCue: 350, segmentId: "1454140698385911843", text: ""),
                    new Segment(startCue: 350, endCue: 478, segmentId: "7324762330771573681", text: ""),
                    new Segment(startCue: 478, endCue: 542, segmentId: "5403996530329584526", text: "Cool base"),
                    new Segment(startCue: 542, endCue: 606, segmentId: "8625548638869601505", text: "It scares me"),
                    new Segment(startCue: 606, endCue: 670, segmentId: "1826025806904317462", text: "Scares me uten bass")
            ])

      new ImageDescription(text:"Hello", src:"http://i2.blogs.indiewire.com/images/blogs/jamesisrael/archives/the-green-lantern-comic-image.jpg").save()

    def songs = [nothing, notAlone, scaresMe]
    songs.each{song ->
      song.segments.each{segment -> segment.song = song}
      song.save()
    }

    def mixParts = [
            new MixPart(startCue: 0, endCue: 32, segment: Segment.findBySegmentId("4479230163500364845")).save(),
            new MixPart(startCue: 16, endCue: 48, segment: Segment.findBySegmentId("5403996530329584526")).save(),
            new MixPart(startCue: 32, endCue: 70, segment: Segment.findBySegmentId("8313187524105777940")).save()
    ]
    def JavaZoneDemoMix = new MasterMix(name: "JavaZone Demo", masterBpm: 150F, parts: mixParts).save()

  }
  def destroy = {
  }
}
