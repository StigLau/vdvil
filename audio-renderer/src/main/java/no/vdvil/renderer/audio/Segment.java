package no.vdvil.renderer.audio;

public class Segment {
    public static final Segment NULL = new Segment("null", -1, "nullText", -1);
    public final String id;
    public final String text;
    public final int start;
    public final int duration;
    public final int end;

    public Segment(String id, int start, String text, int duration) {
        this.id = id;
        this.text = text;
        this.start = start;
        this.duration = duration;
        this.end = start+duration;
    }
}
