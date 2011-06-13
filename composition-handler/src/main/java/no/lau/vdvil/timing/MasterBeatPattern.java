package no.lau.vdvil.timing;

/**
 * This is the ground basis for most of the calculations concerning timing
 * @author Stig@lau.no
 * @since June 2011
 */
public class MasterBeatPattern {
    public final Integer fromBeat;
    public final Integer toBeat;
    public final Float masterBpm;
    /**
     * Indicating that the most fine-grained timing can be performed at this level.
     * In time will be set higher - 44100 or somehting!
     */
    public static final int FRAMERATE_PER_SECOND = 1000;
    /**
     * DUH! But OK to know what these magic numbers are ;)
     */
    public static final int SECONDS_IN_A_MINUTE = 60;

    public MasterBeatPattern(int fromBeat, int toBeat, Float masterBpm) {
        this.fromBeat = fromBeat;
        this.toBeat = toBeat;
        this.masterBpm = masterBpm;
    }

    /**
     * For calulating what the BPM is at a specific
     * @param beat teh beat
     * @return Beats Per Minute
     */
    public Float bpmAt(int beat) {
        return masterBpm;
    }

    public Float durationCalculation() {
        int durationBeats = toBeat - fromBeat;
        return durationBeats * SECONDS_IN_A_MINUTE * FRAMERATE_PER_SECOND / masterBpm;
    }

    public MasterBeatPattern duration(int fromBeat, int toBeat) {
        return new MasterBeatPattern(fromBeat, toBeat, masterBpm);
    }

    public double percentage(Integer startBeat) {
        return startBeat.doubleValue() / toBeat;
    }

    @Override
    public String toString() {
        return masterBpm + " BPM from beat " + fromBeat + "-" + toBeat;
    }
}
