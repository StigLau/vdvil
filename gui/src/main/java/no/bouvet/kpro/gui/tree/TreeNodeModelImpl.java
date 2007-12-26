package no.bouvet.kpro.gui.tree;

import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.model.Part;

public class TreeNodeModelImpl implements TreeNodeModel {

	private Object model;
	
	public TreeNodeModelImpl(Part model) {
		this.model = model;
	}
	
	public TreeNodeModelImpl(Media model) {
		this.model = model;
	}
	
	public int getStartTime() {
		if (model instanceof Part) {
			return ((Part)model).getStartTime();
		} else {
			return ((Media)model).getStartTime();
		}
	}

	public int getStopTime() {
		if (model instanceof Part) {
			return ((Part)model).getStopTime();
		} else {
			return ((Media)model).getStopTime();
		}
	}

	public String getText() {
		if (model instanceof Part) {
			return ((Part)model).getName();
		} else {
			return ((Media)model).getName();
		}
	}

	public String getId() {
		if (model instanceof Part) {
			return ((Part)model).getId();
		} else {
			return ((Media)model).getId();
		}
	}
	
	public void setModel(Part model) {
		this.model = model;
	}
	
	public void setModel(Media model) {
		this.model = model;
	}
	
	public Object data() {
		return model;
	}

}
