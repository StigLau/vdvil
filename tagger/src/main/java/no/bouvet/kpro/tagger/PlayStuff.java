package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.tagger.model.*;

import java.io.File;

public class PlayStuff {

    private Instructions instructions = new Instructions();
    private Renderer renderer = null;
    private Float bpm;
    private MasterSong masterSong;

    public void init() throws Exception {

        for (Part part : masterSong.getParts()) {
            AudioSource audioSource = new MP3Source(new File(part.getSimpleSong().fileName));
            Float cue = part.getRow().cue;
            int start = part.getStartCue().intValue();
            int end = part.getEndCue().intValue();

            float bpm = part.getBpm();
            SimpleAudioInstruction audioInstruction = new SimpleAudioInstruction(start, end, bpm, cue, part.getSimpleSong().startingOffset, audioSource);
            /*
            for (Effect effect : masterSong.getEffects()) {
                if (effect instanceof Volume) {
                    Volume volume = (Volume) effect;
                    audioInstruction.setInterpolatedVolume(volume.getStartValue(), volume.getEndValue());
                }
                else if (effect instanceof Rate) {
                    Rate rate = (Rate) effect;
                    audioInstruction.setInterpolatedRate(rate.getStartValue(), rate.getEndValue());
                }
            }
            */
            instructions.append(audioInstruction);
        }
    }

    public void play(Float startCue) throws Exception {
        Float startCueInMillis = (startCue * 44100 * 60)/ bpm;
        renderer = new Renderer(instructions);
        AudioTarget target = new AudioPlaybackTarget();
        renderer.addRenderer(new AudioRenderer(target));

        renderer.start(startCueInMillis.intValue());

        while(renderer.isRendering()) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void stop() {
        renderer.stop();
    }

    public void setMasterSong(MasterSong masterSong) {
        this.masterSong = masterSong;
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
}
