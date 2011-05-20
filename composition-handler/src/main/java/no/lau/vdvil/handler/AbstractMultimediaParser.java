package no.lau.vdvil.handler;

import no.lau.vdvil.cache.Downloader;
import no.lau.vdvil.cache.SimpleVdvilCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public abstract class AbstractMultimediaParser implements MultimediaParser, Downloader {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected final List<? extends SimpleVdvilCache> caches;
    protected final List<? extends MultimediaParser> parsers;

    public AbstractMultimediaParser(List<? extends SimpleVdvilCache> caches, List<? extends MultimediaParser> parsers) {
        this.caches = caches;
        this.parsers = parsers;
    }

    public abstract MultimediaPart parse(URL url) throws IOException;

    public InputStream fetchAsStream(URL url) throws IOException {
        for (SimpleVdvilCache cache : caches) {
            if(cache.accepts(url)) {
                try {
                    return cache.fetchAsStream(url);
                }catch (IOException ioE) {
                    log.warn("Downloader {} could not fetch {}", new Object[]{cache, url}, ioE);
                }
            }
        }
        log.error("Not able to dowload or fetch {}, check debug log!", url);
        throw new IOException("Unable to fetch " + url + " with any of the caches");
    }

    public String toString(){
        return getClass().getName();
    }
}
