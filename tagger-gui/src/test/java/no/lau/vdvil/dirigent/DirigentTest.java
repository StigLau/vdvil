package no.lau.vdvil.dirigent;

import no.lau.vdvil.cache.VdvilCache;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A Simple structuring of the dirigent
 */
public class DirigentTest {
    Logger log =  LoggerFactory.getLogger(DirigentTest.class);

    @Test
    public void testSetupAndPlaying() throws InterruptedException {
        Dirigent dirigent = new Dirigent("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        dirigent.addDownloader(VdvilHttpCache.create());
        dirigent.addHandler(new VdvilHandler() {
            public void load(InputStream inputStream, String mimeType) {
                log.info("Loading");
            }
        });
        dirigent.changePlayBackSpeed(150F);
        dirigent.prepare();
        dirigent.play(32);
        Thread.sleep(5000);
        dirigent.stop();
    }
}

class Dirigent {
    Logger log =  LoggerFactory.getLogger(Dirigent.class);

    private List<VdvilCache> downloaders = new ArrayList<VdvilCache>(); ;
    private List<VdvilHandler> handlers = new ArrayList<VdvilHandler>();
    private Float playbackBpm;
    private String compositionUrl;

    Dirigent(String compositionUrl) {
        this.compositionUrl = compositionUrl;
    }

    public void prepare() {
        for (VdvilCache downloader : downloaders) {
            if(downloader.acceptsUrl(compositionUrl)) {
                log.info("Trying to download {} with ", compositionUrl, downloader);
                InputStream inputStream = downloader.fetchAsStream(compositionUrl);

                String mimeType = downloader.mimeType(compositionUrl);
                log.info("{} has Mime type {}", compositionUrl, mimeType);
                for (VdvilHandler handler : handlers) {
                    handler.load(inputStream, mimeType);
                }

            }
            else {
                log.info("Downloader {} would not try to download {}", downloader,  compositionUrl);
            }
        }

    }

    public void addDownloader(VdvilCache downloader) { downloaders.add(downloader); }

    public void addHandler(VdvilHandler handler) { handlers.add(handler); }

    public void changePlayBackSpeed(Float playbackBpm) { this.playbackBpm = playbackBpm; }

    public void play(int startAtCue) {

    }

    public void stop() {

    }
}

interface VdvilHandler {

    void load(InputStream inputStream, String mimeType);
}