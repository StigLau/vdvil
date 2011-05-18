package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.SimpleVdvilCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SimpleFileCache implements SimpleVdvilCache {
    enum accepts {
        HTTP, FILE
    }

    public InputStream fetchAsStream(URL url) throws IOException {
        if(url == null || !accepts(url))
            throw new IOException("Erronous URL");
        else
            return url.openStream();
    }

    public boolean accepts(URL url) {
        try{ accepts.valueOf(url.getProtocol().toUpperCase());
            return true;
        } catch(Exception e) { return false; }

    }

    public String mimeType(URL url) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
