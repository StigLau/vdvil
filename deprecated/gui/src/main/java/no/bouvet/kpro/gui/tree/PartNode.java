package no.bouvet.kpro.gui.tree;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemChange;

import no.bouvet.kpro.model.old.Part;
import no.bouvet.kpro.persistence.Storage;

/**
 * A view for showing nodes. Uses Part as its model.
 * 
 * @see no.bouvet.kpro.model.old.Part
 * @author Jostein Gogstad Date: 2007-10-02
 * @author Remy Jensen Date: 2007-10-15
 */
public class PartNode extends AbstractNode {

	private List<Edge> parentEdgeList;
	private MediaNode mediaNode;

	/**
	 * Constructs a Node object that represents a Part object in a graphic
	 * manner to be placed in a TreeWidget.
	 * 
	 * @param parent -
	 *            The Node's parent Node. <code>null</code> if the Node is a
	 *            root Node.
	 * @param model -
	 *            A Part object defining the model for the Node.
	 * @param color -
	 *            A QColor object defining the color for the Node.
	 */
	public PartNode(AbstractNode parent, MediaNode mediaNode, TreeNodeModel model) {
		super(parent, model);
		this.mediaNode = mediaNode;
		this.model = model;
		parent.addChild(this);
		
		parentEdgeList = new ArrayList<Edge>();
	}
	
	/**
	 * Expands the node.
	 */
	public void expand() {
		expanded = true;
		PartNode lastChild = null;
		for (Part p : Storage.getInstance().getChildren(model.getId())) {
			PartNode n = new PartNode(this,mediaNode,new TreeNodeModelImpl(p));
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
		mediaNode.getTreeWidget().treeExpansionChanged.emit(mediaNode);
	}

	/**
	 * Adds an incomming edge to the Node's edge list. Each node needs to signal
	 * incoming edges when it changes, this is to ensure that the edges are
	 * redrawn. Outgoing edges is not a problem since every edge is owned by
	 * that node.
	 * 
	 */
	void addParentEdge(Edge e) {
		parentEdgeList.add(e);
	}
	
	@Override
	public Object itemChange(GraphicsItemChange change, Object value) {
		switch(change) {
		case ItemPositionChange:
			for (Edge e : parentEdgeList) {
				e.adjust();
			}
			break;
		}
		return super.itemChange(change, value);
	}

}
