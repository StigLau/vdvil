package no.lau.vdvil.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SimpleCacheImpl implements VdvilCache, SimpleVdvilCache {
    private final Map<String, UsernameAndPassword> hostAccessList = new HashMap<>();

    Logger log = LoggerFactory.getLogger(SimpleCacheImpl.class);

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
     */
    public void fetchFromInternet(URL url, File localStorage) throws IOException {
        Path target = Paths.get(localStorage.toURI());
        Path parent = target.getParent();
        try (InputStream inputStream = addHostAuthentication(url).getInputStream()) {
            if(Files.exists(target)) {
                log.warn("File {} already exists and will be overwritten", target);
            } else {
                Files.createDirectories(parent);
            }
            Files.copy(inputStream, target, REPLACE_EXISTING);


        }
    }

    public InputStream fetchAsStream(URL url) throws IOException {
        return url.openStream();
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
        return Base64.getEncoder().encodeToString(userAndPassword.getBytes());
    }
}