package no.bouvet.kpro.gui.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.MouseButton;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFontMetricsF;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsRectItem;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemChange;
import com.trolltech.qt.gui.QGraphicsItem.GraphicsItemFlag;

/**
 * Abstract class which provides helper methods to nodes which
 * should be shown in the tree.
 * 
 * Note that this class assumes that 
 * @author gogstad
 *
 */
public abstract class AbstractNode extends QGraphicsRectItem {

	private QColor defaultColor = QColor.green;
	private QColor markedAsPlayedColor = QColor.red;
	private QColor currentColor = defaultColor;
	private List<PartNode> children;
	protected TreeNodeModel model;
	
	public Signal1<QPointF> nodePositionChanged;
	public Signal1<Boolean> nodeSelectionChanged;
	public Signal1<Boolean> shouldBePlayedChanged;

	protected boolean expanded;
	protected boolean shouldBePlayed;
	
	public abstract void expand();
	
	public AbstractNode(AbstractNode parent, TreeNodeModel model) {
		super(parent);
		shouldBePlayed = false;
		this.model = model;

		children = new ArrayList<PartNode>();
		
		nodePositionChanged = new Signal1<QPointF>();
		nodeSelectionChanged = new Signal1<Boolean>();
		shouldBePlayedChanged = new Signal1<Boolean>();

		setFlags(new QGraphicsItem.GraphicsItemFlags(
				GraphicsItemFlag.ItemIsSelectable,
				GraphicsItemFlag.ItemIsMovable));
		setPen(new QPen(QColor.black, 0));
		setBrush(new QBrush(defaultColor));
		adjustRectangleWithRespectToText();

		// we want the nodes to be "closer" to the user than the edges.
		setZValue(2.0);
	}

	private void adjustRectangleWithRespectToText() {
		QLabel label = new QLabel();
		int padding = 10;
		QFontMetricsF fontMetrics = new QFontMetricsF(label.font());
		setRect(new QRectF(0, 0, fontMetrics.width(model.getText()) + padding,
				fontMetrics.height() + padding));
	}
	
	/**
	 * @returns Returns the nodes below and including this node in the tree.
	 */
	public Set<AbstractNode> getAllSubNodes() {
		Set<AbstractNode> childNodes = new HashSet<AbstractNode>();
		childNodes.add(this);
		if (children.size() == 0) {
			return childNodes;
		} else {
			for (PartNode n: children) {
				childNodes.addAll(n.getAllSubNodes());
			}
			return childNodes;
		}
	}
	
	@Override
	public Object itemChange(GraphicsItemChange change, Object value) {
		switch (change) {
		case ItemSelectedChange:
			Integer intValue = (Integer) value;
			Boolean bool = new Boolean(intValue == 1);
			updateColor(bool);
			nodeSelectionChanged.emit(bool);
			break;
		case ItemPositionChange:
			nodePositionChanged.emit(pos());
			break;
		}
		return super.itemChange(change, value);
	}
	
	@Override
	public void mousePressEvent(QGraphicsSceneMouseEvent event) {
		if (event.button() == MouseButton.RightButton) {
			if (shouldBePlayed) {
				unmarkAsPlayed();
			} else {
				markAsPlayed();
			}
		} else super.mousePressEvent(event);
	}

	@Override
	public void mouseDoubleClickEvent(QGraphicsSceneMouseEvent event) {
		if (event.button() == MouseButton.RightButton) {
			return;
		}
		if (expanded) {
			unexpand();
		} else {
			expand();
		}
	}
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option, QWidget widget) {
		super.paint(painter, option, widget);
		painter.drawText(rect(), Qt.AlignmentFlag.AlignCenter.value(), model.getText());
	}

	private void unmarkAsPlayed() {
		shouldBePlayed = false;
		currentColor = defaultColor;
		setBrush(new QBrush(currentColor));
		shouldBePlayedChanged.emit(shouldBePlayed);
	}
	
	private void markAsPlayed() {
		shouldBePlayed = true;
		currentColor = markedAsPlayedColor;
		setBrush(new QBrush(currentColor));
		shouldBePlayedChanged.emit(shouldBePlayed);	
	}
	
	/**
	 * Unexpands the node.
	 */
	public void unexpand() {
		expanded = false;
		if (childItems().size() < 1) {
			return;
		}
		for (QGraphicsItemInterface i : childItems()) {
			if (i instanceof PartNode) {
				PartNode child = (PartNode) i;
				removeChild(child);
				child.unexpand();
			}
			scene().removeItem(i);
		}
	}
	/**
	 * Changes the color of the Node to a lighter shade if <code>shouldUpdate</code>
	 * is <code>true</code>, otherwise it uses the original color.
	 * 
	 * @param shouldUpdate
	 *            is <code>true</code> if the color should change to lighter
	 *            shade.
	 */
	private void updateColor(boolean shouldUpdate) {
		if (shouldUpdate) {
			setBrush(new QBrush(currentColor.lighter(150)));
		} else {
			setBrush(new QBrush(currentColor));
		}
	}
	
	/**
	 * @return Returns the nodes directly below this node.
	 */
	public List<PartNode> getChildren() {
		return children;
	}

	public void addChild(PartNode child) {
		children.add(child);
	}
	
	public void removeChild(PartNode child) {
		children.remove(child);
	}
	
	public boolean isExpanded() {
		return expanded;
	}

	public TreeNodeModel getModel() {
		return model;
	}

	public void setModel(TreeNodeModel model) {
		this.model = model;
	}

}
