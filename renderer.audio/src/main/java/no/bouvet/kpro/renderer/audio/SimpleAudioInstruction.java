package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.renderer.Renderer;

public class SimpleAudioInstruction extends AudioInstruction {

    public SimpleAudioInstruction(int start, int end, float bpm, float cue, AudioSource source) {
        this(start, end, bpm, cue, 0f, source);
    }

    public SimpleAudioInstruction(int start, int end, float bpm, float cue, float initialOffset, AudioSource source) {
        float speedFactor = 44100 * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();

        _cue = new Float((cue*speedFactor) + (initialOffset * 44100)).intValue();
        _duration = _end - _start;

        _source = source;
        _rate1 = Renderer.RATE;
		_rated = 0;
		_volume1 = 127;
		_volumed = 0;
    }
}
