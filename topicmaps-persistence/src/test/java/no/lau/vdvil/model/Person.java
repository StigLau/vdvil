package no.lau.vdvil.model;

import de.topicmapslab.aranuka.annotations.Association;
import de.topicmapslab.aranuka.annotations.Id;
import de.topicmapslab.aranuka.annotations.Name;
import de.topicmapslab.aranuka.annotations.Topic;
import de.topicmapslab.aranuka.enummerations.IdType;

/**
 * Example class for Person with Aranuka annotations.
 *
 * @author Hannes Niederhausen
 *
 */
@Topic(subject_identifier="ex:person")
public class Person {

	@Id(type=IdType.SUBJECT_IDENTIFIER)
	private String id;

	@Name(type="ex:firstname")
	private String firstName;

	@Name(type="ex:lastname")
	private String lastName;

	@Association(type="ex:lives",
		played_role="ex:habitant",
		other_role="ex:place",
		persistOnCascade=true)
	private Address address;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
}
