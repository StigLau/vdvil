package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.OldRenderer;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioDescription implements MultimediaPart {

    Logger log = LoggerFactory.getLogger(getClass());
    private Segment segment;
    public final CompositionInstruction compositionInstruction;
    final Track track;
    FileRepresentation fileRepresentation;

    public AudioDescription(Segment segment, CompositionInstruction compositionInstruction, Track track) {
        this.segment = segment;
        this.compositionInstruction = compositionInstruction;
        this.track = track;
        this.fileRepresentation = track.fileRepresentation;
    }

    @Deprecated
    public AudioInstruction asInstruction(Float masterBpm) {
        Float speedFactor = OldRenderer.RATE * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = new Float(compositionInstruction.start() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _end = new Float(compositionInstruction.end() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        //The cue is where to start inside the mp3 sample
        Float _cue = (segment.start + compositionInstruction.cueDifference()) * speedFactor + track.mediaFile.startingOffset * OldRenderer.RATE;
        int _duration = _end - _start;

        AudioInstruction audioInstruction = new AudioInstruction(_start, _end, _cue.intValue(), _duration, fileRepresentation);

        //Note that Playback speed is a different equation!!
        audioInstruction.setConstantRate(masterBpm / track.bpm);
        return audioInstruction;
    }

    public Instruction asV2Instruction() {
        long start = compositionInstruction.start();
        long end = compositionInstruction.end();
        long cueDifference = compositionInstruction.cueDifference();
        long segmentStart = segment.start;
        return new AudioInstructionV2(start, end-start, fileRepresentation, track, cueDifference, segmentStart);
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public FileRepresentation fileRepresentation() {
        return fileRepresentation;
    }

    public void updateFileRepresentation(FileRepresentation fileRepresentation) {
        this.fileRepresentation = fileRepresentation;
    }
}
