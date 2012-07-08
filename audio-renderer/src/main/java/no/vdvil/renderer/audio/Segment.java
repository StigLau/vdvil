package no.vdvil.renderer.audio;

public class Segment {
    public static final Segment NULL = new Segment("null", -1, -1, "nullText");
    public final String id;
    public final String text;
    public final int start;
    public final int length;
    public final int end;

    public Segment(String id, int start, String text, int length) {
        this.id = id;
        this.text = text;
        this.start = start;
        this.length = length;
        this.end = start+length;
    }

    @Deprecated
    /**
     * Old API which uses end in stead of length
     */
    public Segment(String id, int start, int end, String text) {
        this(id, start, text, end-start);
    }
}
