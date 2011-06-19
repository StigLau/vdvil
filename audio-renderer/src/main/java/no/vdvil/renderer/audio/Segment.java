package no.vdvil.renderer.audio;

public class Segment {
    public static final Segment NULL = new Segment("null", "nullText", -1, -1);
    public final String id;
    public final String text;
    public final int start;
    public final int end;

    public Segment(String id, String text, int start, int end) {
        this.id = id;
        this.text = text;
        this.start = start;
        this.end = end;
    }
}
