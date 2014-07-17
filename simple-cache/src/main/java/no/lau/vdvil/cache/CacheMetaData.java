package no.lau.vdvil.cache;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

public class CacheMetaData implements FileRepresentation, Serializable {
    URL remoteAddress;
    File localStorage;
    String md5CheckSum;
    int downloadAttempts = 1; //Increase to allow retries of downloading during the current session

    public CacheMetaData(URL url) {
        this.remoteAddress = url;
    }

    public CacheMetaData(URL url, String md5CheckSum) {
        this.remoteAddress = url;
        this.md5CheckSum = md5CheckSum;
    }

    /**
     * Only allows editing inside package
     */

    public File localStorage() {
        return localStorage;
    }

    /**
     * Ability to update local storage after caching
     * @param localStorage
     */
    void updateLocalStorage(File localStorage) {
        this.localStorage = localStorage;
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

    public String toString() {
        return remoteAddress != null ? remoteAddress.toString() : super.toString();
    }
}