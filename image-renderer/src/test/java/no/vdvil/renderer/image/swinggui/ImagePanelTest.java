package no.vdvil.renderer.image.swinggui;

import org.junit.jupiter.api.Test;
import java.awt.Dimension;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Stig@Lau.no - 26/12/14.
 */
public class ImagePanelTest {
    @Test
    public void smallHeight() {
        Dimension result = new ImagePanel().recalculateImageSize(100, 200, 100, 100);
        assertEquals(50, result.width);
        assertEquals(100, result.height);
    }

    @Test
    public void smallWidth() {
        Dimension result = new ImagePanel().recalculateImageSize(200, 100, 100, 100);
        assertEquals(100, result.width);
        assertEquals(50, result.height);
    }
}
