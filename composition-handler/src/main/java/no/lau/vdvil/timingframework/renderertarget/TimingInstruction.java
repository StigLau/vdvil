package no.lau.vdvil.timingframework.renderertarget;

import no.lau.vdvil.timingframework.MasterBeatPattern;

public class TimingInstruction {
    public final MasterBeatPattern beatPattern;

    public TimingInstruction(MasterBeatPattern beatPattern) {
        this.beatPattern = beatPattern;
    }
}
