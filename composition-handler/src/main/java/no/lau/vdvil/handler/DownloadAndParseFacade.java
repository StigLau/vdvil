package no.lau.vdvil.handler;

import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A frontend for multiple Caches and Parsers
 */
public class DownloadAndParseFacade implements MultimediaParser, DownloaderFacade {
    Logger log = LoggerFactory.getLogger(getClass());
    List<SimpleVdvilCache> caches;
    List<MultimediaParser> parsers;

    public static final DownloadAndParseFacade NULL = new DownloadAndParseFacade();

    public DownloadAndParseFacade() {
        this.caches = new ArrayList<SimpleVdvilCache>();
        this.parsers = new ArrayList<MultimediaParser>();
    }

    /**
     * Tries to download a DVL and see if a Parser will create a MultimediaPart from it.
     * @param instruction with the relevant data
     * @return a parsed MultimediaPart. MultimediaPart.NULL if unsuccessful
     */
    public MultimediaPart parse(CompositionInstruction instruction) throws IOException{
        for (MultimediaParser parser : parsers) {
            try {
                return parser.parse(instruction);
            } catch (Exception e) {
                log.warn("{} could not parse {}", new Object[]{parser, instruction.dvl().url()}, e);
            }
        }
        throw new IOException("Not able to parse " + instruction.dvl().url() + ", check debug log!");
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

    public void setRefreshCaches(boolean value) throws IOException {
        for (SimpleVdvilCache cache : caches) {
            cache.setRefreshCache(value);
        }
    }
}
