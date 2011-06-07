package no.lau.vdvil.timingframework;

public class MasterBpm {
    final Float masterBpm;

    public MasterBpm(Float masterBpm) {
        this.masterBpm = masterBpm;
    }


    Float getBpm(int beat) {
        return masterBpm;
    }

    Float duration(int fromBeat, int toBeat) {
        int durationBeats = toBeat - fromBeat;
        return durationBeats * 60 * 1000 / masterBpm;
    }
}
