package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;

public abstract class SuperPlayingSetup {
    protected PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
    protected DownloadAndParseFacade downloader = vdvilPlayer.accessCache();

    protected abstract Composition compose(MasterBeatPattern masterBeatPattern) throws IOException;

    protected void play(MasterBeatPattern masterBeatPattern) {
        try {
            vdvilPlayer.init(compose(masterBeatPattern));
            vdvilPlayer.play(0);
            while (vdvilPlayer.isPlaying())
                Thread.sleep(200);
        } catch (Exception e) {
            throw new RuntimeException("This should not happen", e);
        }
    }
}