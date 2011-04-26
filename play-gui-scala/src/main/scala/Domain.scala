package no.lau.vdvil.domain.player

import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.bouvet.kpro.renderer.audio. {MP3Source, SimpleAudioInstruction}
import no.bouvet.kpro.renderer. {Instruction, Instructions}
import no.lau.vdvil.mix.Repo
import org.slf4j.LoggerFactory
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.vdvil.renderer.lyric.LyricInstruction
import no.vdvil.renderer.image.ImageInstruction
import java.net.URL

class ScalaComposition(var masterBpm: Float, val parts: List[SuperPart]) {
  def asInstructions:Instructions = new Instructions {
    for(part <- parts) part match {
      case audio:ScalaAudioPart => append(audio.translateToInstruction(masterBpm.floatValue))
      case lyric:LyricPart => append(new LyricInstruction(lyric.start, lyric.end, masterBpm.floatValue, lyric.text))
      case image:ImagePart => append(new ImageInstruction(image.start, image.end, masterBpm.floatValue, Repo.findFile(image.imageUrl)))
    }
  }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

class ScalaAudioPart(val song: ScalaSong, val startCue: Int, val endCue: Int, val segment: ScalaSegment) extends SuperPart {
  val log = LoggerFactory.getLogger(classOf[ScalaAudioPart])
  val bpm = song.bpm

  def translateToInstruction(masterBpm: Float): Instruction = {
    val audioSource = new MP3Source(VdvilHttpCache.create.fileLocation(song.mediaFile.fileName))
    //TODO check why diff neeeds to be opposite
    val partCompositionDiff: Float = bpm / masterBpm
    val compositionPartDiff: Float = masterBpm / bpm

    new SimpleAudioInstruction(
      startCue,
      endCue,
      bpm,
      segment.start,
      song.mediaFile.startingOffset,
      audioSource,
      partCompositionDiff) {
      setInterpolatedRate(compositionPartDiff, compositionPartDiff)
    }
  }
}

/**
 * A MasterMix contains the mix which can be played to anyone. It will reference one or more MasterParts, which can contain .dvl's
 */
case class MasterMix(name:String, var masterBpm:Float, parts:List[SuperPart]) {
 def asComposition:ScalaComposition = {
   val scalaParts:List[SuperPart] = for(part <- parts) yield part match {
     case audio:AudioPart => {
       new ScalaAudioPart(Repo.findSong(audio.dvl), audio.start, audio.end, Repo.findSegment(audio.id, audio.dvl)
         .getOrElse(throw new Exception("Segment with id " + audio.id + " Not found in " + audio.dvl.name)))
     }
     case lyric:LyricPart => lyric
     //Caches the images to repository
     case image:ImagePart => println("Image URL " + image.imageUrl);Repo.findFile(image.imageUrl); image
   }
   return new ScalaComposition(masterBpm, scalaParts)
 }

  def dvls:scala.collection.Set[Dvl] = audioParts.groupBy[Dvl](audioPart => audioPart.dvl).keySet

  //Will now only calculate the length of the audioParts!
  def durationAsBeats:Float = audioParts.foldLeft(0F)((max,audioPart) => if(audioPart.end > max) audioPart.end else max)

  //Filter out only audioParts
  val audioParts:Seq[AudioPart] = parts.map{case x:AudioPart => x }
}
trait SuperPart
case class AudioPart(dvl:Dvl, start:Int, end:Int, id:String) extends SuperPart
case class LyricPart(text:String, start:Int, end:Int) extends SuperPart
case class ImagePart(imageUrl:URL, start:Int, end:Int) extends SuperPart
case class Dvl(url: String, name:String)

object MasterMix {
  def fromXML(node:xml.Node) = MasterMix(
    (node \ "name").text,
    (node \ "masterBpm").text.toFloat,
    (for( part <- (node \ "parts" \ "part")) yield AudioPart.fromXML(part)).toList
  )
  //TODO FIX Back
  def toXML(composition:MasterMix) = {
<composition>
  <name>{composition.name}</name>
  <masterBpm>{composition.masterBpm}</masterBpm>
  <parts>
    {composition.audioParts.map(AudioPart.toXML)}
  </parts>
</composition>
}
}

object AudioPart {
  def fromXML(node:xml.Node) = AudioPart(
    Dvl.fromXML((node \ "dvl").head),
    (node \ "start").text.toInt,
    (node \ "end").text.toInt,
    (node \ "id").text
  )
  def toXML(audioPart: AudioPart) =
  <part>
    <id>{audioPart.id}</id>
    {Dvl.toXML(audioPart.dvl)}
    <start>{audioPart.start}</start>
    <end>{audioPart.end}</end>
  </part>
}

object Dvl {
  def fromXML(node:xml.Node) = Dvl(
    (node \ "url").text,
    (node \ "name").text
  )

  def toXML(dvl:Dvl) =
    <dvl>
      <url>{dvl.url}</url>
      <name>{dvl.name}</name>
    </dvl>
}