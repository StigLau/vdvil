package no.lau.vdvil.domain.player

import no.lau.tagger.scala.model.{ScalaSong, ScalaSegment}
import no.bouvet.kpro.renderer.audio. {MP3Source, SimpleAudioInstruction}
import no.bouvet.kpro.renderer. {Instruction, Instructions}
import no.lau.vdvil.mix.Repo
import org.slf4j.LoggerFactory
import org.codehaus.httpcache4j.cache.VdvilHttpCache
import no.vdvil.renderer.lyric.LyricInstruction

class ScalaComposition(var masterBpm: Float, val parts: List[AnyRef]) {
  def asInstructions:Instructions = new Instructions {
    for(part <- parts) part match {
      case audio:ScalaAudioPart => append(audio.translateToInstruction(masterBpm.floatValue))
      case lyric:LyricPart => append(new LyricInstruction(lyric.start, lyric.end, masterBpm.floatValue, lyric.text))
    }
  }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

class ScalaAudioPart(val song: ScalaSong, val startCue: Int, val endCue: Int, val segment: ScalaSegment) {
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
case class MasterMix(name:String, var masterBpm:Float, parts:List[AnyRef]) {
 def asComposition:ScalaComposition = {
   val scalaParts:List[AnyRef] = for(part <- parts) yield part match {
     case audio:MasterPart => {
       new ScalaAudioPart(Repo.findSong(audio.dvl), audio.start, audio.end, Repo.findSegment(audio.id, audio.dvl)
         .getOrElse(throw new Exception("Segment with id " + audio.id + " Not found in " + audio.dvl.name)))
     }

     case lyric:LyricPart => lyric
   }
   return new ScalaComposition(masterBpm, scalaParts)
 }
  //TODO FIX Back
  //def dvls = parts.groupBy[Dvl](part => part.dvl).keySet
  def dvls:Set[Dvl] = null
  //TODO FIX Back
  //def durationAsBeats:Float = parts.foldLeft(0F)((max,part) => if(part.end > max) part.end else max)
  def durationAsBeats = null
}

case class MasterPart(dvl:Dvl, start:Int, end:Int, id:String)
case class LyricPart(text:String, start:Int, end:Int)
case class Dvl(url: String, name:String)

object MasterMix {
  def fromXML(node:xml.Node) = MasterMix(
    (node \ "name").text,
    (node \ "masterBpm").text.toFloat,
    (for( part <- (node \ "parts" \ "part")) yield MasterPart.fromXML(part)).toList
  )
  //TODO FIX Back
  def toXML(composition:MasterMix) = <composition/>
  /*
<composition>
  <name>{composition.name}</name>
  <masterBpm>{composition.masterBpm}</masterBpm>
  <parts>
    {composition.parts.map(MasterPart.toXML)}
  </parts>
</composition>
*/
}

object MasterPart {
  def fromXML(node:xml.Node) = MasterPart(
    Dvl.fromXML((node \ "dvl").head),
    (node \ "start").text.toInt,
    (node \ "end").text.toInt,
    (node \ "id").text
  )
  //TODO FIX Back
  def toXML(part: MasterPart) = <part/>
  /*
  <part>
    <id>{part.id}</id>
    {Dvl.toXML(part.dvl)}
    <start>{part.start}</start>
    <end>{part.end}</end>
  </part>
  */

}

object Dvl {
  def fromXML(node:xml.Node) = Dvl(
    (node \ "url").text,
    (node \ "name").text
  )
  //TODO FIX Back
  def toXML(dvl:Dvl) = <dvl/>
  /*
    <dvl>
      <url>{dvl.url}</url>
      <name>{dvl.name}</name>
    </dvl>
    */
}