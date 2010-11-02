package no.lau.vdvil.player

import scala.swing._
import event.ButtonClicked
import no.lau.vdvil.downloading._

/**
 * Play GUI for playing .vdl files
 */
object PlayGUI extends SimpleSwingApplication {
  val tabs = new TabbedPane

  val baseUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/"
  /*
  val song = MasterMix(

    Dvl(baseUrl + "unfinished_sympathy.dvl", "unfinished_sympathy") ::

    Nil
)   */

val returning = Dvl(baseUrl + "holden-nothing-93_returning_mix.dvl", "returning")
  val not_alone = Dvl(baseUrl + "olive-youre_not_alone.dvl", "You're not alone")
  val scares_me = Dvl(baseUrl + "christian_cambas-it_scares_me.dvl", "It scares me")

  val mixTape = MasterPart(returning, 0F, 32F, "4336519975847252321") ::
    MasterPart(not_alone, 0F, 32F, "4479230163500364845") ::
    MasterPart(scares_me, 16F, 48F, "6708181734132071585") :: Nil
  /*
 ::
             ::
            new ScalaAudioPart(not_alone, 32F, 70F, not_alone.segments(1)) ::
            new ScalaAudioPart(scares_me, 48F, 64F, scares_me.segments(2)) ::
            new ScalaAudioPart(scares_me, 64F, 112F, scares_me.segments(4)) ::
            new ScalaAudioPart(returning, 96F, 140F, returning.segments(4)) ::
            new ScalaAudioPart(returning, 96F, 140F, returning.segments(4)) ::
            new ScalaAudioPart(returning, 128F, 174F, returning.segments(6)) ::
            new ScalaAudioPart(returning, 144F, 174.5F, returning.segments(9)) ::
            new ScalaAudioPart(returning, 174F, 175.5F, returning.segments(10)) ::
            new ScalaAudioPart(returning, 175F, 176.5F, returning.segments(11)) ::
            new ScalaAudioPart(returning, 176F, 240F, returning.segments(12)) ::
            new ScalaAudioPart(scares_me, 208F, 224F, scares_me.segments(12)) ::
            new ScalaAudioPart(scares_me, 224F, 252F, scares_me.segments(13)) :: Nil
   */

  val masterMix = MasterMix("JavaZone Demo", 150F, mixTape)





  def top = new MainFrame {
    title = "Play GUI"
    menuBar = new MenuBar {
      contents += new Menu("Load") {
        contents += new MenuItem(Action("Static") {
          val downloadingCoordinator = new DownloadingCoordinator(masterMix, new DVLCallBackGUI(masterMix)) {
            start
          } ! Start
        })
      }
    }

    contents = new BorderPanel {
      add(tabs, BorderPanel.Position.Center)
    }
  }

 override def startup(args: Array[String]) {
  val t = top
  t.size_=(new Dimension(800, 600))
  t.visible = true
 }
}

class PlayPanel(val masterMix: MasterMix) {
  lazy val ui = new FlowPanel {
    contents += new Label("Start from")
    contents += startField
    contents += new Label("Stop")
    contents += stopField
    contents += playCompositionButton
    contents += new Label("BPM")
    contents += bpmField
  }

  val bpmField = new TextField(masterMix.masterBpm.toString, 4)
  val startField = new TextField("0", 4)
  val stopField = new TextField(masterMix.durationAsBeats.toString, 4)
  val playCompositionButton = new Button("Play Composition") {
    reactions += {case ButtonClicked(_) => compositionPlayer.pauseAndplay(startField.text.toFloat)}
  }
  val compositionPlayer = new ScalaCompositionPlayer(None) {
    def pauseAndplay(startFrom: Float) {
      stop
      masterMix.masterBpm = bpmField.text.toFloat
      scalaCompositionOption = Some(masterMix.asComposition)
      play(startFrom, bpmField.text.toFloat)
    }
  }
}

class DVLCallBackGUI (masterMix:MasterMix) extends DVLCallBack {
  lazy val downloadingPanel = new Frame {
    contents = new GridPanel(dvlLabels.size, 1) {
      dvlLabels.foreach(contents += _._2)
    }
  }
  lazy val dvlLabels: Map[Dvl, Label] = Map.empty ++ masterMix.dvls.map(dvl => dvl -> new Label(dvl.url))
  def setLabel(dvl: Dvl, text: String) {dvlLabels(dvl).text_=(text)}
  def visible(value:Boolean) { downloadingPanel.visible_=(value) }
}