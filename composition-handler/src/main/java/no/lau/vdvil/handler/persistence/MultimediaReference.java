package no.lau.vdvil.handler.persistence;

import java.net.URL;

/**
 * These are Serialized transport objects
 */
public interface MultimediaReference {
    String name();
    URL url();
    String fileChecksum();
}
