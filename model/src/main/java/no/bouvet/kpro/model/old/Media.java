package no.bouvet.kpro.model.old;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A media object describes a media file with information that is independent
 * the context the media.
 * 
 * @author karlespe
 * 
 */
public class Media {

	private String id;

	private int startTime;

	private int stopTime;

	private String name;

	private URL mediaFile;

	private List<String> parts;

	/**
	 * Instantiates a new Media object.
	 * 
	 * @param id
	 *            id of the new object
	 */
	public Media(String id) {
		this.id = id;
		this.parts = new ArrayList<String>();
		this.name = new String();
	}

	/**
	 * Adds a new part to the list of part ids.
	 * 
	 * @param e
	 *            id of the added part
	 */
	public void addPart(String e) {
		parts.add(e);
	}

	/**
	 * Removes a part from list of part ids.
	 * 
	 * @param e
	 *            id of the part to be removed
	 * @return if part list has changed as a result of the call
	 */
	public boolean removePart(String e) {
		return parts.remove(e);
	}

	/**
	 * Getter for the list of part ids.
	 * 
	 * @return the list of part ids
	 */
	public List<String> getParts() {
		return parts;
	}

	/**
	 * Getter for the URL to the media file.
	 * 
	 * @return the URL to the media file
	 */
	public URL getMediaFile() {
		return mediaFile;
	}

	/**
	 * Setter for the URL to the media file.
	 * 
	 * @param mediaFile
	 *            the URL to the media file
	 */
	public void setMediaFile(URL mediaFile) {
		this.mediaFile = mediaFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Indicates whether some other media object is "equal to" this one. Two
	 * media objects are equal if all their attributes are equal.
	 * 
	 * @param other
	 *            the reference media object with which to compare.
	 * @return true if this object is the same as the obj argument; false
	 *         otherwise.
	 */
	public boolean equals(Media other) {
		if (other == null)
			return false;
		if (id.equals(other.id) && name.equals(other.name)) {
			if (mediaFile == null) {
				return mediaFile == other.mediaFile;
			} else if (mediaFile.equals(other.mediaFile)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getStopTime() {
		return stopTime;
	}

	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}
}
