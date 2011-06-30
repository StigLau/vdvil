package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class VdvilServerTest extends SuperPlayingSetup{
    URL url;
    @Test
    public void play() throws MalformedURLException {
        url = new URL("http://localhost:8080/vdvil-server/composition/xml");
        super.play(new MasterBeatPattern(0, 16, 150F));
    }

    @Override
    protected Composition compose(MasterBeatPattern beatPattern) throws IOException {
        return ((Composition) super.downloader.parse(PartXML.create(url))).withBeatPattern(beatPattern);
    }
}