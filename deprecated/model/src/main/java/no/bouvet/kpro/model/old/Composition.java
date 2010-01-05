package no.bouvet.kpro.model.old;

import java.util.ArrayList;
import java.util.List;

/**
 * This contains all the in the performances in the composition. This is the
 * top-level container which represtents the mix.
 * 
 * @author gogstad, karlespe
 * 
 */
public class Composition {

	private String id;

	private String name;

	private List<String> performances;

	/**
	 * Instantiates a new Composition object.
	 * 
	 * @param id
	 *            id of the new object
	 */
	public Composition(String id) {
		this.id = id;
		performances = new ArrayList<String>();
	}

	/**
	 * Adds a new performance to the composition.
	 * 
	 * @param e
	 *            id of the added performance
	 */
	public void addPerformance(String e) {
		performances.add(e);
	}

	/**
	 * Removes a performance from the composition.
	 * 
	 * @param e
	 *            id of the performace to be removed
	 * @return if performace list has changed as a result of the call
	 */
	public boolean removePerformance(String e) {
		return performances.remove(e);
	}

	/**
	 * Returns a list ids for the performances in the compostition.
	 * 
	 * @return the list of performance ids
	 */
	public List<String> getPerformances() {
		return performances;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
