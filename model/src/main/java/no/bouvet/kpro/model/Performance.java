package no.bouvet.kpro.model;

/**
 * A performance contains information that describes how a media-object is mixed
 * into a composition.
 * 
 * @author karlespe
 * 
 */
public class Performance {

	private String id;

	private CuePoint leadInStart;

	private CuePoint leadInStop;

	private CuePoint leadOutStart;

	private CuePoint leadOutStop;

	private String media;

	/**
	 * Instantiates a new Performance object.
	 * 
	 * @param id
	 *            id of the new object
	 */
	public Performance(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CuePoint getLeadInStart() {
		return leadInStart;
	}

	public void setLeadInStart(CuePoint leadInCue) {
		this.leadInStart = leadInCue;
	}

	public CuePoint getLeadInStop() {
		return leadInStop;
	}

	public void setLeadInStop(CuePoint leadInStop) {
		this.leadInStop = leadInStop;
	}

	public CuePoint getLeadOutStart() {
		return leadOutStart;
	}

	public void setLeadOutStart(CuePoint leadOutStart) {
		this.leadOutStart = leadOutStart;
	}

	public CuePoint getLeadOutStop() {
		return leadOutStop;
	}

	public void setLeadOutStop(CuePoint leadOutStop) {
		this.leadOutStop = leadOutStop;
	}

	/**
	 * Getter for the media object the performance describes.
	 * 
	 * @return id of the media object
	 */
	public String getMedia() {
		return media;
	}

	/**
	 * Setter for the media object the performance describes.
	 * 
	 * @param media
	 *            if of the media object
	 */
	public void setMedia(String media) {
		this.media = media;
	}
}
