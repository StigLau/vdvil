package no.bouvet.kpro.gui.tree;


import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsLineItem;
import com.trolltech.qt.gui.QLineF;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPen;
import com.trolltech.qt.gui.QPolygonF;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;

public class Edge extends QGraphicsLineItem {

	private AbstractNode source;
	private PartNode destination;

	private double penWidth = 1;

	public Edge(AbstractNode source, PartNode destination) {
		super(source);
		this.destination = destination;
		this.source = source;
		
		setPen(new QPen(QColor.black, penWidth, Qt.PenStyle.SolidLine, Qt.PenCapStyle.RoundCap, Qt.PenJoinStyle.RoundJoin));
		setLine(new QLineF(getLineStartPoint(source),getLineStopPoint(destination)));
		
		setZValue(1.0);		
	}
	
	private QPointF getLineStartPoint(AbstractNode node) {
		return new QPointF(node.rect().width()/2.0,node.rect().height());
	}
	
	private QPointF getLineStopPoint(AbstractNode node) {
		return new QPointF(node.x() + node.rect().width()/2.0,node.y());
	}

	public void adjust() {
		prepareGeometryChange();
		setLine(new QLineF(getLineStartPoint(source),getLineStopPoint(destination)));
	}
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		if (source == null || destination == null) {
			return;
		} else {
			super.paint(painter, option, widget);
		}
	}
}
