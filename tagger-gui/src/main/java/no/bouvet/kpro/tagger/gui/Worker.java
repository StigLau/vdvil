package no.bouvet.kpro.tagger.gui;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import java.io.File;
import java.util.List;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.jdesktop.swingworker.SwingWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker extends SwingWorker<Object, Object> {
    VdvilCache cache = VdvilHttpCache.create();


    private AudioSource audioSource;
    private SimpleSong simpleSong;
    private int startCue;
    private int endCue;
    private VdvilPlayer player;
    Logger log = LoggerFactory.getLogger(getClass());

    public Worker(SimpleSong simpleSong, int startCue, int endCue) {
        this.simpleSong = simpleSong;
        this.startCue = startCue;
        this.endCue = endCue;
        try {
            File fileInCache = cache.fetchFromInternetOrRepository(simpleSong.mediaFile.fileName, simpleSong.mediaFile.checksum);
            audioSource = new MP3Source(fileInCache);
        } catch (Exception e) {
            log.error("Problem fetching mp3 file", e);
        }
    }

    protected Object doInBackground() throws Exception {
        Instructions instructions = new Instructions();
        log.debug("startCue playing = " + startCue);
        int playLength = endCue - startCue;
        instructions.append(new SimpleAudioInstruction(0, playLength, simpleSong.bpm, startCue, simpleSong.mediaFile.startingOffset, audioSource, 1F));
        player = new InstructionPlayer(simpleSong.bpm, instructions, new AudioRenderer(new AudioPlaybackTarget()));
        player.play(0);
        return "worker finished";
    }

    protected void process(List<Object> objects) {
        super.process(objects);    //To change body of overridden methods use File | Settings | File Templates.
        log.debug("Here is the intermediate result = " + objects);
    }

    protected void done() {
        log.debug("Player instance done");
        super.done();
    }

    public void stop() {
        player.stop();
        audioSource.close();
    }
}
