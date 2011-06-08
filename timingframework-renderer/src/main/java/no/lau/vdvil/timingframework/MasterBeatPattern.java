package no.lau.vdvil.timingframework;

public class MasterBeatPattern {
    public final Integer fromBeat;
    public final Integer toBeat;
    final Float masterBpm;

    public MasterBeatPattern(int fromBeat, int toBeat, Float masterBpm) {
        this.fromBeat = fromBeat;
        this.toBeat = toBeat;
        this.masterBpm = masterBpm;
    }


    public Float getBpm(int beat) {
        return masterBpm;
    }

    public Float durationCalculation() {
        int durationBeats = toBeat - fromBeat;
        return durationBeats * 60 * 1000 / masterBpm;
    }

    public MasterBeatPattern duration(int fromBeat, int toBeat) {
        return new MasterBeatPattern(fromBeat, toBeat, masterBpm);
    }

    public double percentage(Integer startBeat) {
        return startBeat.doubleValue() / toBeat;
    }
}
