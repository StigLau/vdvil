package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.SuperInstruction;

/**
 * V2 Wrapper to create a AudioInstruction
 */
public class AudioInstructionV2 extends SuperInstruction{
    final Track track;
    final long cueDifference;
    final long segmentStart;

    public AudioInstructionV2(long start, long length, FileRepresentation fileRepresentation, Track track, long cueDifference, long segmentStart) {
        super(start, length, fileRepresentation);
        this.track = track;
        this.cueDifference = cueDifference;
        this.segmentStart = segmentStart;
    }

    public AudioInstruction asInstruction(Float masterBpm) {
        if(fileRepresentation.localStorage() == null)
            throw new RuntimeException(track.mediaFile.fileName + " had not been cached before creating instruction! - Downloader not set");

        Float speedFactor = Instruction.RESOLUTION * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = (int) (start() * speedFactor * differenceBetweenMasterSongAndPart);
        int _end = (int) ((start() + length()) * speedFactor * differenceBetweenMasterSongAndPart);
        //The cue is where to start inside the mp3 sample
        Float _cue = (segmentStart + cueDifference) * speedFactor + track.mediaFile.startingOffset * Instruction.RESOLUTION;
        int _duration = _end - _start;

        AudioInstruction audioInstruction = new AudioInstruction(_start, _end, _cue.intValue(), _duration, fileRepresentation);

        //Note that Playback speed is a different equation!!
        audioInstruction.setConstantRate(masterBpm / track.bpm);
        return audioInstruction;
    }
}
