package no.bouvet.kpro.gui.tree;

import no.bouvet.kpro.model.old.Media;
import no.bouvet.kpro.model.old.Part;
import no.bouvet.kpro.persistence.Storage;

public class MediaNode extends AbstractNode {
	
	private TreeWidget treeWidget;
	
	private MediaNode(AbstractNode parent, TreeNodeModel model) {
		super(parent,model);
	}
	
	public MediaNode(TreeWidget treeWidget, Media media) {
		this(null,new TreeNodeModelImpl(media));
		this.treeWidget = treeWidget;
		this.model = new TreeNodeModelImpl(media);
	}

	public Media getMedia() {
		return (Media)model.data();
	}

	@Override
	public void expand() {
		expanded = true;
		PartNode lastChild = null;
		for (Part p : Storage.getInstance().getChildren((Media)model.data())) {
			//hack, this needs to be fixed!
			p.setStartTime(p.getStartTime());
			p.setStopTime(p.getStopTime());
			//hack end
			PartNode n = new PartNode(this,this,new TreeNodeModelImpl(p));
			if (lastChild == null) {
				lastChild = n;
				n.setPos(-rect().width() / 2, rect().height() + 20);
			} else {
				n.setPos(lastChild.x() + lastChild.rect().width() + 20,
						lastChild.y());
				lastChild = n;
			}
			Edge e = new Edge(this, n);
			n.addParentEdge(e);
		}
		treeWidget.treeExpansionChanged.emit(this);
	}

	public TreeWidget getTreeWidget() {
		return treeWidget;
	}
}
