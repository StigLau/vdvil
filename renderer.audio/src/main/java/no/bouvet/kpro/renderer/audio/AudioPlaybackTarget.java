package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.renderer.Renderer;
import org.apache.log4j.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * The AudioPlaybackTarget class is an audio target implementation that sends
 * audio data to the computer's audio output hardware. It uses the
 * javax.sound.sampled API for audio output.
 * 
 * @author Michael Stokes
 */
public class AudioPlaybackTarget implements AudioTarget {
	protected SourceDataLine _line;
	protected int _base;
    static Logger log = Logger.getLogger(AudioPlaybackTarget.class);

	/**
	 * Constructs a new AudioPlaybackTarget. The audio output device is opened
	 * immediately, and an exception is thrown upon failure. If this constructor
	 * succeeds, the close() method must be called to release the audio output
	 * hardware.
	 * 
	 * @throws LineUnavailableException if the audio device could not be opened
	 * @author Michael Stokes
	 */
	public AudioPlaybackTarget() throws LineUnavailableException {
		log.debug("Opening audio device for " + Renderer.RATE + " Hz stereo 16-bit real-time output" );
		AudioFormat format = new AudioFormat(Renderer.RATE, 16, 2, true, false);

        try {
            _line = AudioSystem.getSourceDataLine(format);
            _line.open(format);
			_line.start();
		} catch (LineUnavailableException e) {
			try {
				_line.close();
				_line = null;
			} catch (Exception e2) {
                log.debug("Error closing line");
			}
			throw e;
		}
		flush();
	}

	/**
	 * Close the audio target by stopping and releasing the audio output
	 * hardware.
	 * 
	 * @author Michael Stokes
	 */
	public void close() {
		if (_line != null) {
			_line.flush();
			_line.close();
			_line = null;

			log.debug("Closed audio device" );
		}
	}

	/**
	 * Flush the audio target. Any samples that have been queued for output
	 * shall be discarded, and output shall stop. This method shall complete
	 * immediately, and shall reset the output position to 0.
	 * 
	 * @author Michael Stokes
	 */
	public void flush() {
		_line.flush();
		_base = _line.getFramePosition();
	}

	/**
	 * Drain the audio target. This method shall not return until all queued
	 * samples have been output, or the close() method is called from another
	 * thread.
	 * 
	 * @author Michael Stokes
	 */
	public void drain() {
		_line.drain();
	}

	/**
	 * Gets the size of the audio target's sample queue, in samples. This is the
	 * maximum number of samples that can ever be written to the audio target
	 * without blocking. A sample is a pair of 16-bit values (left and right).
	 * 
	 * @return The number of samples that fit in the target's queue
	 * @author Michael Stokes
	 */
	public int getBufferDuration() {
		return _line.getBufferSize() >>> 2;
	}

	/**
	 * Gets the number of samples that can currently be written to the audio
	 * target without blocking. This may be less than the value returned by
	 * getBufferDuration(), if there are existing samples in the sample queue. A
	 * sample is a pair of 16-bit values (left and right).
	 * 
	 * @return The number of samples that can be written without blocking
	 * @author Michael Stokes
	 */
	public int getWritableDuration() {
		return _line.available() >>> 2;
	}

	/**
	 * Gets the current output position of the audio target, in samples. This
	 * value represents the exact sample that is currently being rendered by the
	 * computer's audio hardware. The first sample to be output is sample 0, and
	 * the output position is reset to 0 whenever flush() is called.
	 * 
	 * @return The sample currently being output
	 * @author Michael Stokes
	 */
	public int getOutputPosition() {
		synchronized (_line) {
			return _line.getFramePosition() - _base;
		}
	}

	/**
	 * Writes one or more samples to the audio target. This method may block if
	 * the target's sample queue is full, and may write fewer than the requested
	 * number of samples.
	 * 
	 * Audio sample data is passed within a byte array, where each sample is
	 * represented by four bytes. The first two bytes are a signed little-endian
	 * 16-bit value for the left channel intensity, and the last two bytes are a
	 * signed little-endian 16-bit value for the right channel intensity.
	 * 
	 * @param buffer
	 *            An array of bytes that contains the sample data to write,
	 *            where each sample is four bytes
	 * @param offset
	 *            The offset into buffer of the first sample to write, measured
	 *            in samples (i.e. units of 4 bytes)
	 * @param duration
	 *            The number of samples to write, measured in samples
	 * @return The number of samples written, which may be less than requested
	 * @author Michael Stokes
	 */
	public int write(byte[] buffer, int offset, int duration) {
		return _line.write(buffer, offset << 2, duration << 2) >>> 2;
	}
}
