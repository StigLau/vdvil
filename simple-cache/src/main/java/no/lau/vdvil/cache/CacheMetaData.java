package no.lau.vdvil.cache;

import java.io.Serializable;
import java.net.URL;

public class CacheMetaData implements FileRepresentation, Serializable {
    String id;
    URL remoteAddress;
    URL localStorage;
    String md5CheckSum;

    /**
     * Only allows editing inside package
     */

    public String cacheId() {
        return id;
    }

    public URL localStorage() {
        return localStorage;
    }

    public URL remoteAddress() {
        return remoteAddress;
    }

    public String md5CheckSum() {
        return md5CheckSum;
    }

    public static FileRepresentation byURL(final URL raddress) {
        return new CacheMetaData(){{
            this.remoteAddress = raddress;
        }};
    }
}