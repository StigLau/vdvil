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
    final XStreamParser parser = new XStreamParser();

    public SimpleSong fetchSimpleSongAndCacheDvlAndMp3(String dvlUrl, String dvlChecksum) throws FileNotFoundException {
        log.debug("Downloading dvl files download mp3's with httpCache");
        String cachedDvlFile = retrievePathToFileFromCache(dvlUrl, dvlChecksum);
        return buildSimpleSongWithCorrectReferenceToMp3(cachedDvlFile);
    }

    private String retrievePathToFileFromCache(String returningUrl, String checksum) throws FileNotFoundException {
        File cachedFile = httpCache.fetchAsFile(returningUrl, checksum);
        log.debug("{} is located on disk: {}", cachedFile.getName(), cachedFile.getAbsolutePath());
        return cachedFile.getAbsolutePath();
    }

    private SimpleSong buildSimpleSongWithCorrectReferenceToMp3(String cachedFile) throws FileNotFoundException {
        SimpleSong originalSong = parser.load(cachedFile);
        String pathToMp3 = retrievePathToFileFromCache(originalSong.mediaFile.fileName, originalSong.mediaFile.checksum);
        MediaFile mediaFile = new MediaFile(pathToMp3, originalSong.mediaFile.checksum, originalSong.mediaFile.startingOffset);
        return new SimpleSong(originalSong.reference, mediaFile, originalSong.segments, originalSong.bpm);
    }
}
