package no.lau.vdvil.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

public abstract class CacheFacade implements VdvilCache, SimpleVdvilCache {
    protected Logger log = LoggerFactory.getLogger(getClass());

    public abstract File fetchFromInternetOrRepository(URL url, String checksum) throws IOException ;

    String storeLocation() {
        return "/tmp/vdvil";
    }

    public abstract boolean removeFromCache(URL url);

    public InputStream fetchAsStream(URL url) throws IOException {
        return new FileInputStream(fetchFromInternetOrRepository(url, null));
    }

    /**
     * Return path to file on disk
     *
     * @param url where the file is originally located on the web
     * @return the file itself
     */
    public File fileLocation(URL url) {
        String urlChecksum = DigestUtils.md5Hex(url.toString());
        return new File(storeLocation() + "/files/" + urlChecksum + "/default");
    }

    /**
     * Verify that file is correct on disk. Used when downloading mp3's and such
     *
     * @param pathToFileOnDisk path on disk
     * @param checksum         to verify integrity of file
     * @return Whether the file exists
     */
    protected boolean existsInRepository(File pathToFileOnDisk, String checksum) {
        if (checksum != null && !validateChecksum(pathToFileOnDisk, checksum))
            return false;
        return pathToFileOnDisk.exists() && pathToFileOnDisk.canRead();
    }

    /**
     * Calculates the checksum of the Url to find where it is located in transport, then validates the file on disk with the checksum
     *
     * @param file     location of the file to verify
     * @param checksum to check the file with
     * @return whether the file validates with the checksum
     */
    public boolean validateChecksum(File file, String checksum) {
        try {
            String fileChecksum = DigestUtils.md5Hex(new FileInputStream(file));
            if (fileChecksum.equals(checksum)) {
                log.debug("Checksum of file on disk matched the provided checksum");
                return true;
            } else {
                log.error("Checksums did not match, expected {}, was {}", checksum, fileChecksum);
                log.error("File in transport may be erronous, check {}", file.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }
}