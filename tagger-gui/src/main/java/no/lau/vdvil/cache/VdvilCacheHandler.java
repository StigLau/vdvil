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
        return buildSimpleSongWithCorrectReferenceToMp3(cachedDvlFile);
    }

    public String retrievePathToFileFromCache(String urlOfFile, String checksum) throws FileNotFoundException {
        File cachedFile;
        if (checksum == null) {
            cachedFile = httpCache.fetchAsFile(urlOfFile);
        } else {
            cachedFile = httpCache.fetchAsFile(urlOfFile, checksum);
        }
        log.debug("{} is located on disk: {}", cachedFile.getName(), cachedFile.getAbsolutePath());
        return cachedFile.getAbsolutePath();
    }
    /*
    public SimpleSong readDvlFromDiskAndDownloadMp3(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return parser.fromXML(reader);
    } */

    /**
     * This function is called to set the MediaFile to the placement where the mp3 is located after downloading to disc
     *
     * @param cachedFile the location of the file on disc
     * @return resulting SimpleSong
     * @throws FileNotFoundException in case the file was not found on disc
     */
    public SimpleSong buildSimpleSongWithCorrectReferenceToMp3(String cachedFile) throws FileNotFoundException {
        SimpleSong originalSong =  parser.load(cachedFile);
        String pathToMp3 = retrievePathToFileFromCache(originalSong.mediaFile.fileName, originalSong.mediaFile.checksum);
        MediaFile mediaFile = new MediaFile(pathToMp3, originalSong.mediaFile.checksum, originalSong.mediaFile.startingOffset);
        return new SimpleSong(originalSong.reference, mediaFile, originalSong.segments, originalSong.bpm);
    }
}
