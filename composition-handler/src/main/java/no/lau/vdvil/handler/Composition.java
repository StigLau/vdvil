package no.lau.vdvil.handler;

import java.net.URL;
import java.util.List;

public class Composition implements MultimediaPart {
    public final String name;
    public final float masterBpm;
    public final List<MultimediaPart> multimediaParts;
    public final URL url;

    public Composition(String name, float masterBpm, List<MultimediaPart> multimediaParts, URL url) {
        this.name = name;
        this.masterBpm = masterBpm;
        this.multimediaParts = multimediaParts;
        this.url = url;
    }
}

