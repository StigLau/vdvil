package no.bouvet.kpro.gui.tree;

import java.util.ArrayList;
import java.util.List;

import no.bouvet.kpro.model.old.Media;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

public class TreeWidget extends QGraphicsView {

	private List<MediaNode> mediaNodes;

	public Signal1<MediaNode> numberOfTreesChanged;
	public Signal1<MediaNode> treeExpansionChanged;
	
	public TreeWidget() {
		this(null);
	}
	
	public TreeWidget(QWidget parent) {
		super(parent);
		
		mediaNodes = new ArrayList<MediaNode>();
		numberOfTreesChanged = new Signal1<MediaNode>();
		treeExpansionChanged = new Signal1<MediaNode>();

		setScene(new QGraphicsScene(this));
		scene().setSceneRect(0, 0, 700, 350);
        scene().setItemIndexMethod(QGraphicsScene.ItemIndexMethod.NoIndex);

        setRenderHint(QPainter.RenderHint.Antialiasing);
		setTransformationAnchor(QGraphicsView.ViewportAnchor.AnchorUnderMouse);
        setResizeAnchor(QGraphicsView.ViewportAnchor.AnchorViewCenter);
	}
/*	
	public void addRandomRootNode() {
		Node root = Demo.buildDummyTree();
		root.childrenCountChanged.connect(treeExpansionChanged);
		addRootNode(root);
	}
*/	
	public void addMedia(Media media) {
		int padding = 150;
		MediaNode mediaNode = new MediaNode(this,media);
	
		scene().addItem(mediaNode);
		MediaNode lastAddedNode = mediaNodes.size() > 0 ? mediaNodes.get(mediaNodes.size()-1) : null;
		if (lastAddedNode == null) {
			mediaNode.setPos(new QPointF(100,100));
		} else {
			mediaNode.setPos(new QPointF(lastAddedNode.rect().width() + lastAddedNode.x() + padding,
					100));
		}
		numberOfTreesChanged.emit(mediaNode);
		mediaNode.expand();
		mediaNodes.add(mediaNode);
	}
	
	public List<Media> getAllMedia() {
		List<Media> result = new ArrayList<Media>();
		for (MediaNode mn: mediaNodes) {
			result.add(mn.getMedia());
		}
		return result;
	}

}
