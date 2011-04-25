package no.bouvet.kpro.renderer.audio;

/**
 * The AudioTarget interface defines an audio target to which the AudioRenderer
 * can send mixed audio data.
 * 
 * @author Michael Stokes
 */
public interface AudioTarget {
	/**
	 * Close the audio target, releasing any resources such as files or audio
	 * hardware. Any other method calls that are blocking in other threads shall
	 * return immediately.
	 * 
	 * @author Michael Stokes
	 */
	void close();

	/**
	 * Flush the audio target. Any samples that have been queued for output
	 * shall be discarded, and output shall stop. This method shall complete
	 * immediately, and shall reset the output position to 0.
	 * 
	 * @author Michael Stokes
	 */
	void flush();

	/**
	 * Drain the audio target. This method shall not return until all queued
	 * samples have been output, or the close() method is called from another
	 * thread.
	 * 
	 * @author Michael Stokes
	 */
	void drain();

	/**
	 * Gets the size of the audio target's sample queue, in samples. This is the
	 * maximum number of samples that can ever be written to the audio target
	 * without blocking. A sample is a pair of 16-bit values (left and right).
	 * 
	 * @return The number of samples that fit in the target's queue
	 * @author Michael Stokes
	 */
	int getBufferDuration();

	/**
	 * Gets the number of samples that can currently be written to the audio
	 * target without blocking. This may be less than the value returned by
	 * getBufferDuration(), if there are existing samples in the sample queue. A
	 * sample is a pair of 16-bit values (left and right).
	 * 
	 * @return The number of samples that can be written without blocking
	 * @author Michael Stokes
	 */
	int getWritableDuration();

	/**
	 * Gets the current output position of the audio target, in samples. For
	 * some audio targets this will simply equal the number of samples that have
	 * been output. For others, such as real-time audio targets, this value
	 * represents the exact sample that is currently being rendered by the
	 * computer's audio hardware. The first sample to be output is sample 0, and
	 * the output position is reset to 0 whenever flush() is called.
	 * 
	 * @return The sample currently being output
	 * @author Michael Stokes
	 */
	int getOutputPosition();

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
	int write(byte[] buffer, int offset, int duration);
}
