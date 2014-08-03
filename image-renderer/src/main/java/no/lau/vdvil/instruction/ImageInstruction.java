package no.lau.vdvil.instruction;

import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.timing.MasterBeatPattern;

/**
 * ImageInstruction v2
 * @author Stig Lau
 * @since June 2012
 */
public class ImageInstruction extends SuperInstruction {

    public ImageInstruction(long start, long length, FileRepresentation fileRepresentation) {
        super(start, length, fileRepresentation);
    }

    public static ImageInstruction create(MasterBeatPattern mbp, FileRepresentation fileRepresentation) {
        float speedFactor = OldRenderer.RATE * 60 / mbp.bpmAt(mbp.fromBeat);
        int _start = new Float(mbp.fromBeat * speedFactor).intValue();
        int _end = new Float(mbp.toBeat * speedFactor).intValue();
        int lenght = _end - _start;
        return new ImageInstruction(_start, lenght, fileRepresentation);
    }
}