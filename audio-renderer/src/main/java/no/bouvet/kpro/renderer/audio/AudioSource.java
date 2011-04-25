package no.bouvet.kpro.renderer.audio;

/**
 * The AudioSource interface defines an audio source from which the
 * AudioRenderer can acquire audio data.
 * 
 * @author Michael Stokes
 */
public interface AudioSource {
	/**
	 * Close the source, releasing any resources such as files.
	 * 
	 * @author Michael Stokes
	 */
	void close();

	/**
	 * Gets the duration of the source audio, in number of samples.
	 * 
	 * @return The duration of the audio source, in number of samples
	 * @author Michael Stokes
	 */
	int getDuration();

	/**
	 * Asks the audio source to decode or otherwise make available a period of
	 * audio data, starting at time and extending for duration samples. The
	 * source returns 16-bit stereo audio data in a ShortBuffer object. Each
	 * sample consists of two signed shorts. The current position of the
	 * ShortBuffer object should point to the first requested audio sample (the
	 * one identified by time), and may not necessarily fall at the start of the
	 * ShortBuffer. It is recommended that Source implementations exercise an
	 * audio caching scheme to avoid decoding the same data twice.
	 * 
	 * @param time
	 *            The first audio sample to make available, in samples
	 * @param duration
	 *            The number of audio samples to make available, in samples
	 * @return A ShortBuffer object that contains the requested audio data, with
	 *         the current position set to the first requested sample
	 * @author Michael Stokes
	 */
	java.nio.ShortBuffer getBuffer(int time, int duration);
}
