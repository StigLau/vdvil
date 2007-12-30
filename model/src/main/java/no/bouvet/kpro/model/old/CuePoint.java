package no.bouvet.kpro.model.old;

/**
 * The cue points specify when key points in the mixing of performances occur
 * and what mixing parameters that are associated with them. The mixing
 * parameters describe how the playback of the song is performed.
 * 
 * @author karlespe
 */
public class CuePoint {

	private int time;
	private int volume;
	private double sampleRate;

	/**
	 * Instantiates a new CuePoint object.
	 * 
	 * @param time
	 *            the point in time within the timescale of the compostition the
	 *            cue-point is placed
	 */
	public CuePoint(int time) {
		this.time = time;
	}

	/**
	 * Getter for the time the cue-point is placed within the timescale of the
	 * compostition.
	 * 
	 * @return the point in time where the cue-point is placed
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Getter for the time the cue-point is placed within the timescale of the
	 * compostition.
	 * 
	 * @param time
	 *            the point in time where the cue-point is placed
	 */
	public void setTime(int time) {
		this.time = time;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

}
