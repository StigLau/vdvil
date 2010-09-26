package no.lau.vdvil.cache;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.MediaFile;
import no.lau.tagger.model.SimpleSong;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;

public class VdvilCacheHandler {
    final Logger log = LoggerFactory.getLogger(VdvilCacheHandler.class);
    final String tempFolder = System.getProperty("java.io.tmpdir"); //Not yet in use
    final VdvilCacheStuff httpCache = new VdvilCacheStuff(new File("/tmp/vdvil"));
    XStreamParser parser = new XStreamParser();

    /**
     * @param dvlUrl      Where to locate the file
     * @param dvlChecksum if it is null, checksum is disregarded
     * @return simpleSong
     * @throws FileNotFoundException if the files werent found
     */
    public SimpleSong fetchSimpleSongAndCacheDvlAndMp3(String dvlUrl, String dvlChecksum) throws FileNotFoundException {
        log.debug("Downloading dvl files download mp3's with httpCache");
        String cachedDvlFile = retrievePathToFileFromCache(dvlUrl, dvlChecksum);
        if(cachedDvlFile != null)
            return loadSimpleSongFromDvlOnDisk(cachedDvlFile);
        else
            throw new FileNotFoundException("Could not download .dvl file " + dvlUrl);
    }

    public String retrievePathToFileFromCache(String urlOfFile, String checksum) throws FileNotFoundException {
        File cachedFile;
        if (checksum == null) {
            cachedFile = httpCache.fetchAsFile(urlOfFile);
        } else {
            cachedFile = httpCache.fetchAsFile(urlOfFile, checksum);
        }
        if(cachedFile != null) {
            log.debug("{} is located on disk: {}", cachedFile.getName(), cachedFile.getAbsolutePath());
            return cachedFile.getAbsolutePath();
        } else
            return null;
    }

    public SimpleSong loadSimpleSongFromDvlOnDisk(String cachedFile) throws FileNotFoundException {
         return parser.load(cachedFile);
     }

    public void save(SimpleSong song, String path) {
        parser.save(song, path);
    }

    public String printableXml(SimpleSong ss) {
        return parser.toXml(ss);
    }
}
