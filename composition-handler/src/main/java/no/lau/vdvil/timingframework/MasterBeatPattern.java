package no.lau.vdvil.timingframework;

public class MasterBeatPattern {
    private int fromBeat;
    private int toBeat;
    final Float masterBpm;

    public MasterBeatPattern(int fromBeat, int toBeat, Float masterBpm) {
        this.fromBeat = fromBeat;
        this.toBeat = toBeat;
        this.masterBpm = masterBpm;
    }


    Float getBpm(int beat) {
        return masterBpm;
    }

    Float duration() {
        int durationBeats = toBeat - fromBeat;
        return durationBeats * 60 * 1000 / masterBpm;
    }
}
