package no.bouvet.kpro.gui.tree;

public interface TreeNodeModel {
	
	/**
	 * @return The id identifying the node in the topic map
	 */
	public String getId();
	
	/**
	 * @return The text which the node should display
	 */
	public String getText();
	
	/**
	 * @return When the node starts to play.
	 */
	public int getStartTime();
	
	/**
	 * @return When the node stops to play.
	 */
	public int getStopTime();
	
	/**
	 * @return Any data contained by the node.
	 */
	public Object data();

}
