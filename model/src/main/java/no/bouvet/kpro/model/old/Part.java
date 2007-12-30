package no.bouvet.kpro.model.old;

import java.util.ArrayList;
import java.util.List;

/**
 * An part describes a time segment of a media file. Its main function is to
 * identify a given time segment and give it a textual description.
 * 
 * @author karlespe
 * 
 */
public class Part {

	private String id;

	private int startTime;

	private int stopTime;

	private String name;

	private String description;

	protected List<String> subParts;

	private String parentPart;

	/**
	 * Instantiates a new Media object.
	 * 
	 * @param id
	 *            id of the new object
	 */
	public Part(String id) {
		this.id = id;
		this.subParts = new ArrayList<String>();
	}

	/**
	 * Getter for the id to the parent of this part.
	 * 
	 * @return the id to the parent of this part
	 */
	public String getParentPart() {
		return parentPart;
	}

	/**
	 * Setter for the id to the parent of this part.
	 * 
	 * @param parentPart
	 *            id to the parent of this part
	 */
	public void setParentPart(String parentPart) {
		this.parentPart = parentPart;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the list of ids for sub part of this part.
	 * 
	 * @return the list of ids for sub part of this part
	 */
	public List<String> getSubParts() {
		return subParts;
	}

	/**
	 * Setter for the list of ids for sub-parts of this part.
	 * 
	 * @param subParts
	 *            the list of ids for sub-parts of this part
	 */
	public void setSubParts(List<String> subParts) {
		this.subParts = subParts;
	}

	/**
	 * Adds a new part to the list of sub-part ids.
	 * 
	 * @param subPart
	 *            id of the added sub-part
	 */
	public void addSubPart(String subPart) {
		this.subParts.add(subPart);
	}

	/**
	 * Removes a part from list of sub-part ids.
	 * 
	 * @param subPart
	 *            id of the sub-part to be removed
	 * @return if sub-part list has changed as a result of the call
	 */
	public boolean removeSubPart(String subPart) {
		return this.subParts.remove(subPart);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter for the point in time the within the timescale of the media this
	 * part begins.
	 * 
	 * @return the point in time this part begins
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Setter for the point in time the within the timescale of the media this
	 * part begins.
	 * 
	 * @param startTime
	 *            the point in time this part begins
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	/**
	 * Getter for the point in time the within the timescale of the media this
	 * part ends.
	 * 
	 * @return the point in time this part ends
	 */
	public int getStopTime() {
		return stopTime;
	}

	/**
	 * Setter for the point in time the within the timescale of the media this
	 * part ends.
	 * 
	 * @param stopTime
	 *            the point in time this part ends
	 */
	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}

	public boolean equals(Part other) {
		boolean desc = false;
		if (description == other.description)
			desc = true;
		else if (description != null) {
			if (description.equals(other.description))
				desc = false;
		} else
			return false;
		if (desc && id.equals(other.id) && name.equals(other.name)
				&& startTime == other.startTime && stopTime == other.stopTime)
			return true;
		else
			return false;

	}
}
