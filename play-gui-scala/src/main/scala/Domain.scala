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

class ScalaComposition(var masterBpm: Float, val parts: List[MultimediaPartTrait]) {
  def asInstructions:Instructions = new Instructions {
    for(part <- parts) part match {
      case audio:ScalaAudioPart => append(audio.translateToInstruction(masterBpm.floatValue))
      case lyric:LyricPart => append(new LyricInstruction(lyric.startCue, lyric.endCue, masterBpm.floatValue, lyric.text))
      case image:ImagePart => append(new ImageInstruction(image.startCue, image.endCue, masterBpm.floatValue, Repo.findFile(image.imageUrl)))
    }
  }
  def durationAsBeats:Float = asInstructions.getDuration * masterBpm / (44100 * 60)
}

case class ScalaAudioPart(song: ScalaSong, startCue:Int, endCue:Int, segment: ScalaSegment) extends MultimediaPartTrait {
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
case class MasterMix(name:String, var masterBpm:Float, parts:List[MultimediaPartTrait]) {
 def asComposition:ScalaComposition = {
   val scalaParts:List[MultimediaPartTrait] = for(part <- parts) yield part match {
     case audio:AudioPart => {
       new ScalaAudioPart(Repo.findSong(audio.dvl), audio.startCue, audio.endCue, Repo.findSegment(audio.id, audio.dvl)
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
  def durationAsBeats:Float = parts.foldLeft(0F)((max,audioPart) => if(audioPart.endCue > max) audioPart.endCue else max)

  //Filter out only audioParts
  val audioParts:List[AudioPart] =  parts.collect{case x:AudioPart => x}
}
trait MultimediaPartTrait {
  val startCue:Int
  val endCue:Int
}
case class AudioPart(dvl:Dvl, startCue:Int, endCue:Int, id:String) extends MultimediaPartTrait
case class LyricPart(text:String, startCue:Int, endCue:Int) extends MultimediaPartTrait
case class ImagePart(imageUrl:URL, startCue:Int, endCue:Int) extends MultimediaPartTrait
case class Dvl(url: String, name:String)