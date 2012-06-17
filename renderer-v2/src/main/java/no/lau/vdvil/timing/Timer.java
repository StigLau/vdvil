package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Renderer;

public interface Timer {
    void play();
    void setMasterRenderer(Renderer renderer);
}
