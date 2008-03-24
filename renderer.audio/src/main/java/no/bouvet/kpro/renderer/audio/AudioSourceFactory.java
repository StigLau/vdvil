package no.bouvet.kpro.renderer.audio;

import java.io.File;

/**
 * The AudioSourceFactory class provides a way to create an audio source object,
 * implementing the AudioSource interface, for a particular audio File.
 * 
 * @author Michael Stokes
 */
public abstract class AudioSourceFactory {
	/**
	 * Attempts to create an audio source object, implementing the AudioSource
	 * interface, for the given audio File.
	 * 
	 * @param file
	 *            The audio file to load
	 * @return An object implementing AudioSource that will read the file
	 * @throws Exception
	 *             if no source was available or if the file was unreadable
	 * @author Michael Stokes
	 */
	public static AudioSource load(File file) throws Exception {
		String name = file.toString().toLowerCase();

		if (name.endsWith(".mp3")) {
			return new MP3Source(file);
		}

		throw new Exception("No source available");
	}
}