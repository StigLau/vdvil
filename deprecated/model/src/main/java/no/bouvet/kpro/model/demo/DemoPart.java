package no.bouvet.kpro.model.demo;

import no.bouvet.kpro.model.old.Part;

public class DemoPart extends Part {

	public DemoPart(String id) {
		super(id);
		setName(id);
	}

	public DemoPart(String id, String name) {
		super(id);
		setName(name);
	}

	public void addSubPart(DemoPart part) {
		subParts.add(part.getId());
	}

}
