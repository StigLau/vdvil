package no.bouvet.kpro.renderer.audio;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.SuperInstruction;
import java.io.IOException;

/**
 * The AudioInstruction class is a specialization of the AbstractInstruction class. It
 * represents an instruction to the AudioRenderer.
 * 
 * An AudioInstruction instructs the AudioRenderer to mix a section of audio
 * from an AudioSource into its output. It may also contain rate or volume
 * adjustments, with either or both of these being linearly interpolated across
 * the audio section.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class AudioInstruction extends SuperInstruction {
	protected final MP3Source source;
	protected int _cue;
	protected int _duration;

	protected int _rate1;
	protected long _rated;
	protected int _volume1;
	protected long _volumed;

	protected int _cacheExternal = -1;
	protected int _cacheInternal = -1;

	/**
	 * Construct a new AudioInstruction.
	 *
     * @param start
     *            the start time of the instruction in samples (super)
     * @param end
     *            the end time of the instruction in samples (super)
     * @param cue
*            the cue point (start point) within source in samples
     * @param duration
     *      * @throws RuntimeException if unable to access mp3 file
     */
	public AudioInstruction(int start, int end, int cue, int duration, FileRepresentation fileRepresentation) {
        super(start, end - start, fileRepresentation);
        try {
            source = new MP3Source(fileRepresentation);
        } catch (IOException e) {
            throw new RuntimeException("Problem accessing MP3 file " + fileRepresentation, e);
        }
		_cue = cue;
		_duration = duration;

		_rate1 = Instruction.RESOLUTION;
		_rated = 0;
		_volume1 = 127;
		_volumed = 0;
	}

    /**
	 * Get the AudioSource.
	 *
	 * @return the AudioSource
     * TODO Possibly called WAY too many times!!
	 */
	public AudioSource getSource() {
        return source;
	}

	/**
	 * Get the cuepoint within source in samples.
	 *
	 * @return the cuepoint within source in samples
	 */
	public int getCue() {
		return _cue;
	}

	/**
	 * Get the duration within source in samples.
	 * 
	 * @return the duration within source in samples
	 */
	public int getSourceDuration() {
		return _duration;
	}

	/**
	 * Set a constant playback rate, where 1.0 is the original rate.
	 * 
	 * @param rate
	 *            the constant rate
	 */
	public void setConstantRate(float rate) {
		_rate1 = (int) (rate * Instruction.RESOLUTION);
		_rated = 0;
	}

	/**
	 * Set a linearly interpolated playback rate, where 1.0 is the original
	 * rate.
	 * 
	 * @param rate1
	 *            the rate at the start of the section
	 * @param rate2
	 *            the rate at the end of the section
	 */
	public void setInterpolatedRate(float rate1, float rate2) {
		_rate1 = (int) (rate1 * Instruction.RESOLUTION);
		_rated = (long) (rate2 * Instruction.RESOLUTION) - _rate1;
	}

	/**
	 * Get the rate at a given point within the section.
	 * 
	 * @param time
	 *            the time in samples within the section, where 0 is the start
	 *            of the section
	 * @return the rate in integer form relative to Instruction.RESOLUTION
	 */
	public int getInterpolatedRate(int time) {
		return _rate1 + (int) (_rated * time / _duration);
	}

	/**
	 * Set a constant playback volume, where 1.0 is the original volume.
	 * 
	 * @param volume
	 *            the constant volume
	 */
	public void setConstantVolume(float volume) {
		_volume1 = (int) (volume * 127);
		_volumed = 0;
	}

	/**
	 * Set a linearly interpolated playback volume, where 1.0 is the original
	 * volume.
	 * 
	 * @param volume1
	 *            the volume at the start of the section
	 * @param volume2
	 *            the volume at the end of the section
	 */
	public void setInterpolatedVolume(float volume1, float volume2) {
		_volume1 = (int) (volume1 * 127);
		_volumed = (long) (volume2 * 127) - _volume1;
	}

	/**
	 * Get the volume at a given point within the section.
	 * 
	 * @param time
	 *            the time in samples within the section, where 0 is the start
	 *            of the section
	 * @return the volume in integer form where 127 is natural volume
	 */
	public int getInterpolatedVolume(int time) {
		return _volume1 + (int) (_volumed * time / _duration);
	}

	public int getCacheExternal() {
		return _cacheExternal;
	}

	public int getCacheInternal() {
		return _cacheInternal;
	}

	public void setCacheExternal(int cacheExternal) {
		_cacheExternal = cacheExternal;
	}

	public void setCacheInternal(int cacheInternal) {
		_cacheInternal = cacheInternal;
	}

	public void advanceCache(int external, int internal) {
		_cacheExternal += external;
		_cacheInternal += internal;
	}
}
