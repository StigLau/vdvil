package no.bouvet.kpro.renderer.audio;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * The WaveFileTarget class is an audio target implementation that writes the
 * audio to a Microsoft WAVE file in PCM format.
 * 
 * @author Michael Stokes
 */
public class WaveFileTarget implements AudioTarget {
	protected File _file;
	protected RandomAccessFile _raf;
	protected int _duration = 0;
    static Logger log = Logger.getLogger(WaveFileTarget.class);

	/**
	 * Constructs a new WaveFileTarget. The file to create or overwrite is
	 * specified by the file parameter. If the file could not be written to, an
	 * exception is thrown.
	 * 
	 * @param file
	 * @throws Exception
	 *             if the file cannot be written to
	 * @author Michael Stokes
	 */
	public WaveFileTarget(File file) throws Exception {
		log.debug("Writing to file " + file.toString() );

		// Delete the file if it exists

		if (file.exists())
			file.delete();

		// Open the file

		_file = file;
		_raf = new RandomAccessFile(file, "rw");

		// Write the RIFF header chunk, with WAVE subtype

		_raf.write(new byte[] { 'R', 'I', 'F', 'F' });
		_raf.writeInt(0);
		_raf.write(new byte[] { 'W', 'A', 'V', 'E' });

		// Write the format chunk

		_raf.write(new byte[] { 'f', 'm', 't', ' ' });
		_raf.writeInt(swap32(16));
		_raf.writeShort(swap16(1));
		_raf.writeShort(swap16(2));
		_raf.writeInt(swap32(no.bouvet.kpro.renderer.Renderer.RATE));
		_raf.writeInt(swap32(no.bouvet.kpro.renderer.Renderer.RATE * 4));
		_raf.writeShort(swap16(4));
		_raf.writeShort(swap16(16));

		// Write the data chunk header

		_raf.write(new byte[] { 'd', 'a', 't', 'a' });
		_raf.writeInt(0);
	}

	/**
	 * Close the audio target by filling in the missing parts of the WAVE header
	 * and closing the file.
	 * 
	 * @author Michael Stokes
	 */
	public void close() {
		try {
			// Fill in the size of the data chunk

			_raf.seek(40);
			_raf.writeInt(swap32(_duration * 4));

			// Fill in the size of the main RIFF chunk

			_raf.seek(4);
			_raf.writeInt(swap32(36 + _duration * 4));

			// Close the file

			_raf.close();

			log.debug("Closed " + _file.toString() );
		} catch (Exception e) {
			log.debug("Exception closing: " + e.toString() );
		}
	}

	/**
	 * Flush the audio target. This method does nothing for a file target.
	 * 
	 * @author Michael Stokes
	 */
	public void flush() {
	}

	/**
	 * Drain the audio target. This method does nothing for a file target.
	 * 
	 * @author Michael Stokes
	 */
	public void drain() {
	}

	/**
	 * Gets the size of the audio target's sample queue, in samples. This audio
	 * target can handle any number of samples, but we'll return 8K as a fairly
	 * good working size.
	 * 
	 * @return The number of samples that fit in the target's queue
	 * @author Michael Stokes
	 */
	public int getBufferDuration() {
		return 8192;
	}

	/**
	 * Gets the number of samples that can currently be written to the audio
	 * target without blocking. This audio target does not queue samples, so
	 * we'll return getWritableDuration.
	 * 
	 * @return The number of samples that can be written without blocking
	 * @author Michael Stokes
	 */
	public int getWritableDuration() {
		return getBufferDuration();
	}

	/**
	 * Gets the current output position of the audio target, in samples. This
	 * audio target does not render the audio data, so the current position is
	 * equal to the number of samples written.
	 * 
	 * @return The sample currently being output
	 * @author Michael Stokes
	 */
	public int getOutputPosition() {
		return _duration;
	}

	/**
	 * Writes one or more samples to the audio target.
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
		// Write the samples

		try {
			_raf.write(buffer, offset << 2, duration << 2);
		} catch (Exception e) {
			log.debug( "Exception writing: " + e.toString() );
		}

		// Increment the duration

		_duration += duration;

		return duration;
	}

	protected static short swap16(int in) {
		return (short) (((in << 8) & 0xFF00) | ((in >>> 8) & 0xFF));
	}

	protected static int swap32(int in) {
		return ((in >>> 24) & 0xFF) | ((in >>> 8) & 0xFF00)
				| ((in << 8) & 0xFF0000) | ((in << 24) & 0xFF000000);
	}
}
