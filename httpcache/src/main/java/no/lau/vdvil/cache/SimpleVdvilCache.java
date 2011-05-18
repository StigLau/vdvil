package no.lau.vdvil.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface SimpleVdvilCache {
    InputStream fetchAsStream(URL url) throws IOException;

    boolean accepts(URL url);

    String mimeType(URL url);
}
