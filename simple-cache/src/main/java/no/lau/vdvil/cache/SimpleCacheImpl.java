package no.lau.vdvil.cache;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class SimpleCacheImpl extends CacheFacade {
    private Map<String, UsernameAndPassword> hostAccessList = new HashMap<String, UsernameAndPassword>();

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
     * Functionality for accessing protected URLS
     * @param host host address
     * @param username user name
     * @param password password
     */
    public void addHostUsernameAndPassword(String host, String username, String password) {
        hostAccessList.put(host, new UsernameAndPassword(username, password));
    }

    /**
     * A shorthand for fetching files if they have been downloaded to disk
     * Used by testing purposes
     *
     * @param url      to the file
     * @param checksum may be null if no checksum
     * @return the file or null if empty
     * @Deprecated //TODO Refactor and discontinue use. Should have only one responsibility - downloading!
     */
    @Override
    public File fetchFromInternetOrRepository(URL url, String checksum) throws IOException {
        File fileLocation = fileLocation(url);

        if (refreshCache || !existsInRepository(fileLocation, checksum)) {
            InputStream inputStream = addHostAuthentication(url).getInputStream();
            FileOutputStream outputStream = FileUtils.openOutputStream(fileLocation);
            try {
                IOUtils.copy(inputStream, outputStream);
            } finally {
                IOUtils.closeQuietly(outputStream);
                inputStream.close();
            }
            if (fileLocation.length() == 0) {
                fileLocation.delete();
                fileLocation = null;
            }
        }
        return fileLocation;
    }

    private URLConnection addHostAuthentication(URL url) throws IOException {
        URLConnection uc = url.openConnection();
        if (hostAccessList.containsKey(url.getHost())) {
            UsernameAndPassword usernameAndPassword = hostAccessList.get(url.getHost());
            uc.setRequestProperty("Authorization", "Basic " + usernameAndPassword.b64Encoded());
        }
        return uc;
    }
}

/**
 * Simple class for holding auth information for accessing a URL with username and password
 */
class UsernameAndPassword {
    final String username;
    final String password;

    UsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String b64Encoded() {
        String userAndPassword = username + ":" + password;
        return new BASE64Encoder().encode (userAndPassword.getBytes());
    }


}