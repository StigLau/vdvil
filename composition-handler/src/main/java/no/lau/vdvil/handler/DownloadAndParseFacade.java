package no.lau.vdvil.handler;

import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.cache.VdvilCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadAndParseFacade implements MultimediaParser, DownloaderFacade {
    Logger log = LoggerFactory.getLogger(getClass());
    List<SimpleVdvilCache> caches;
    List<MultimediaParser> parsers;

    public DownloadAndParseFacade() {
        this.caches = new ArrayList<SimpleVdvilCache>();
        this.parsers = new ArrayList<MultimediaParser>();
    }

    public DownloadAndParseFacade(List<SimpleVdvilCache> caches, List<MultimediaParser> parsers) {
        this.caches = caches;
        this.parsers = parsers;
    }

    /**
     * Tries to download a DVL and see if a Parser will create a MultimediaPart from it.
     * @param url to parse
     * @return a parsed MultimediaPart. MultimediaPart.NULL if unsuccessful
     */
    public MultimediaPart parse(String id, URL url) throws IOException{
        for (MultimediaParser parser : parsers) {
            try {
                return parser.parse(id, url);
            } catch (Exception e) {
                log.error("{} could not parse {}", new Object[]{parser, url}, e);
            }
        }
        throw new IOException("Not able to parse " + url + ", check debug log!");
    }


    public InputStream fetchAsStream(URL url) throws IOException {
        for (SimpleVdvilCache cache : caches) {
            if(cache.accepts(url)) {
                try {
                    return cache.fetchAsStream(url);
                }catch (IOException ioE) {
                    log.warn("DownloaderFacade {} could not fetch {}", new Object[]{cache, url}, ioE);
                }
            }
        }
        log.error("Not able to dowload or fetch {}, check debug log!", url);
        throw new IOException("Unable to fetch " + url + " with any of the caches");
    }

    public String toString(){
        return getClass().getName();
    }

    public void addCache(SimpleVdvilCache cache) {
        this.caches.add(cache);
    }

    public void addParser(MultimediaParser parser) {
        this.parsers.add(parser);
    }

    public URL fetchFromCache(URL url, String checksum) throws IOException {
        for (SimpleVdvilCache cache : caches) {
            if (cache instanceof VdvilCache) {
                VdvilCache vdvilCache = (VdvilCache) cache;
                return vdvilCache.fetchFromInternetOrRepository(url, checksum).toURI().toURL();
            }
        }
        throw new IOException("No VdvilCache configured for " + getClass().getName());
    }
}
