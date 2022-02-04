package no.lau;

import java.net.URL;

public class NullChecker {
    public static URL nullChecked(URL url) {
        if(url == null)
            throw new RuntimeException("Target URL was null");
        else
            return url;
    }
}
