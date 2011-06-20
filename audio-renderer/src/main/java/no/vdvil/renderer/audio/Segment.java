package no.vdvil.renderer.audio;

public class Segment {
    public static final Segment NULL = new Segment("null", -1, -1, "nullText");
    public final String id;
    public final String text;
    public final int start;
    public final int end;

    public Segment(String id, int start, int end, String text) {
        this.id = id;
        this.text = text;
        this.start = start;
        this.end = end;
    }
}
