package no.lau.vdvil.cache;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

public class CacheMetaData implements FileRepresentation, Serializable {
    String id;
    URL remoteAddress;
    File localStorage;
    String md5CheckSum;
    int downloadAttempts = 1; //Increase to allow retries of downloading during the current session

    /**
     * Only allows editing inside package
     */

    public String cacheId() {
        return id;
    }

    public File localStorage() {
        return localStorage;
    }

    public URL remoteAddress() {
        return remoteAddress;
    }

    public String md5CheckSum() {
        return md5CheckSum;
    }

    public int downloadAttemptsLeft() {
        return downloadAttempts;
    }

    public static FileRepresentation byURL(final URL raddress) {
        return new CacheMetaData(){{
            this.remoteAddress = raddress;
        }};
    }
}