package no.vdvil.renderer.image;

import java.io.InputStream;

/**
 * An ImageListener shows images that have been cached to a local repository.
 * It does not have to relate to how the inputstream was aqcuired.
 */
public interface ImageListener {
    void show(InputStream imageStream) ;
}
