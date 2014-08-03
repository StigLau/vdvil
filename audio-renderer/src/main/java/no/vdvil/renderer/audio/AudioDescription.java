package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.OldRenderer;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.MutableInstruction;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioDescription implements MultimediaPart, MutableInstruction {

    Logger log = LoggerFactory.getLogger(getClass());
    private Segment segment;
    public final CompositionInstruction compositionInstruction;
    final Track track;
    FileRepresentation fileRepresentation;
    private int bpmCueDifference = 0;

    public AudioDescription(Segment segment, CompositionInstruction compositionInstruction, Track track) {
        this.segment = segment;
        this.compositionInstruction = compositionInstruction;
        this.track = track;
        this.fileRepresentation = track.fileRepresentation;
    }

    /**
     * Used to move the start of a track. Used when adding an additional composition that is to be played later.
     * @param bpmCueDifference in BPMs
     */
    public void moveStart(int bpmCueDifference) {
        this.bpmCueDifference = bpmCueDifference;
    }

    @Deprecated
    public AudioInstruction asInstruction(Float masterBpm) {
        Float speedFactor = OldRenderer.RATE * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = calculate(compositionInstruction.start() + bpmCueDifference, speedFactor, differenceBetweenMasterSongAndPart);
        int _duration = calculate(compositionInstruction.duration() + bpmCueDifference, speedFactor, differenceBetweenMasterSongAndPart);
        int _end = _start + _duration;
        //The cue is where to start inside the mp3 sample
        int _cue = new Float((segment.start + compositionInstruction.cueDifference()) * speedFactor + track.mediaFile.startingOffset * OldRenderer.RATE).intValue();

        AudioInstruction audioInstruction = new AudioInstruction(_start, _end, _cue, _duration, fileRepresentation);
        //Set start and duration for logging purposes
        audioInstruction.startAsBpm = compositionInstruction.start() + bpmCueDifference;
        audioInstruction.durationAsBpm = compositionInstruction.duration() + bpmCueDifference;

        //Note that Playback speed is a different equation!!
        audioInstruction.setConstantRate(masterBpm / track.bpm);
        return audioInstruction;
    }

    int calculate(int bpm, Float speedFactor, Float differenceBetweenMasterSongAndPart) {
        return new Float(bpm * speedFactor * differenceBetweenMasterSongAndPart).intValue();
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
