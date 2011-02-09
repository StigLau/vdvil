package no.bouvet.kpro.tagger.gui;

import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.tagger.AudioPlayer;
import java.util.List;
import no.lau.tagger.model.SimpleSong;
import org.apache.log4j.Logger;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.jdesktop.swingworker.SwingWorker;

public class Worker extends SwingWorker<Object, Object> {


    private AudioSource audioSource;
    private SimpleSong simpleSong;
    private int startCue;
    private int endCue;
    private AudioPlayer player;
    static Logger log = Logger.getLogger(Worker.class);

    public Worker(SimpleSong simpleSong, int startCue, int endCue) {
        this.simpleSong = simpleSong;
        this.startCue = startCue;
        this.endCue = endCue;
        try {
            audioSource = new MP3Source(VdvilCacheStuff.fileLocation(simpleSong.mediaFile.fileName));
        } catch (Exception e) {
            log.error("Problem fetching mp3 file", e);
        }
    }

    protected Object doInBackground() throws Exception {
        Instructions instructions = new Instructions();
        log.debug("startCue playing = " + startCue);
        int playLength = endCue - startCue;
        instructions.append(new SimpleAudioInstruction(0, playLength, simpleSong.bpm, startCue, simpleSong.mediaFile.startingOffset, audioSource, 1F));
        player = new AudioPlayer();
        player.playMusic(instructions);
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
