package no.lau.vdvil.cache;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.net.URL;

/**
 * Wrapper class around HttpCache4J to make it more usable for VDvil usage
 */
public class SimpleCacheImpl extends CacheFacade {

    @Override
    protected String storeLocation() {
        return "/tmp/vdvil";
    }

    enum accepts { HTTP, HTTPS, FILE }

    public boolean accepts(URL url) {
        try {
            accepts.valueOf(url.getProtocol().toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Deprecated
    public String mimeType(URL url) {
        return null;
    }


    /**
     * A shorthand for fetching files if they have been downloaded to disk
     * Used by testing purposes
     *
     * @param url      to the file
     * @param checksum may be null if no checksum
     * @return the file or null if empty
     */
    @Override
    public File fetchFromInternetOrRepository(URL url, String checksum) throws IOException {
        File fileLocation = fileLocation(url);

        if (refreshCache || !existsInRepository(fileLocation, checksum)) {
            FileOutputStream outputStream = FileUtils.openOutputStream(fileLocation);
            try {
                IOUtils.copy(url.openStream(), outputStream);
            } finally {
                IOUtils.closeQuietly(outputStream);
            }
            if (fileLocation.length() == 0) {
                fileLocation.delete();
                fileLocation = null;
            }
        }
        return fileLocation;
    }
}
