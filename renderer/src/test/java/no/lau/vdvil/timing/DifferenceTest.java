package no.lau.vdvil.timing;

import org.junit.Test;

/**
 * Used for calculating the difference in timing between a renderer and the actual output
 */
public class DifferenceTest {
    @Test
    public void differenceInTime() {
        float offset = 0.15F;
        float factor = 1.2F;

        int beat = 32;
        float bpm = 120F;
        new DifferenceComputing(offset, factor).compute(beat, bpm);
    }
}

class DifferenceComputing {

    private float offset;
    private float factor;

    public DifferenceComputing(float offset, float factor) {

        this.offset = offset;
        this.factor = factor;
    }

    public void compute(int beat, float bpm) {
        //To change body of created methods use File | Settings | File Templates.
    }
}
