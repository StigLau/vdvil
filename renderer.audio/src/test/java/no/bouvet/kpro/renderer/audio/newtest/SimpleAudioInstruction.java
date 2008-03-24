package no.bouvet.kpro.renderer.audio.newtest;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.AudioInstruction;

public class SimpleAudioInstruction extends AudioInstruction {
    private float bpm;

    public SimpleAudioInstruction(int start, int end, float bpm, float cue, AudioSource source) {
        this(start, end, bpm, cue, 0f, source);
    }

    public SimpleAudioInstruction(int start, int end, float bpm, float cue, float initialOffset, AudioSource source) {
        this.bpm = bpm;
        float speedFactor = 44100 * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();


        _cue = new Float((cue*speedFactor) + initialOffset).intValue();
        _duration = _end - _start;

        _source = source;
        _rate1 = Renderer.RATE;
		_rated = 0;
		_volume1 = 127;
		_volumed = 0;
    }
}
