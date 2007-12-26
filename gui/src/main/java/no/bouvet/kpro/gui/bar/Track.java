package no.bouvet.kpro.gui.bar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.bouvet.kpro.gui.exception.InvalidItemException;
import no.bouvet.kpro.gui.tree.AbstractNode;
import no.bouvet.kpro.gui.tree.MediaNode;
import no.bouvet.kpro.gui.tree.PartNode;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QGraphicsRectItem;

public class Track extends QGraphicsRectItem implements Iterable<TrackItem> {
	
	private Map<AbstractNode,TrackItem> items;
	
	private MediaNode mediaNode;
	
	private BarWidget widget;
	
	public Track(BarWidget widget, MediaNode mediaNode) {
		this.widget = widget;
		this.mediaNode = mediaNode;
		items = new HashMap<AbstractNode,TrackItem>();
		pen().setStyle(Qt.PenStyle.SolidLine);
		pen().setWidth(2);
	}
	
	public Track(BarWidget widget, MediaNode node,double height, double width) {
		this(widget,node);
		setRect(0,0,width,height);
	}
	
	public void addItem(TrackItem item) {
		if (item.parentItem() != this) {
			throw new InvalidItemException(item + " does not belong in this track.");
		}
		items.put(item.getNode(),item);
	}
	
	public void removeItem(TrackItem item) {
		items.remove(item.getNode());
	}
	
	public void removeItem(AbstractNode node) {
		TrackItem item = items.remove(node);
		scene().removeItem(item);
	}
	
	public void trackitemChanged(TrackItem trackItem) {
		widget.updateTrackItems();
	}
	
	public Collection<TrackItem> getItems() {
		return items.values();
	}
	
	public Set<AbstractNode> getCoveredNodes() {
		return items.keySet();
	}
	
	public boolean containsNode(AbstractNode node) {
		for (TrackItem ti: items.values()) {
			if (ti.getNode() == node) {
				return true;
			}
		}
		return false;
	}
	
	public MediaNode getMediaNode() {
		return mediaNode;
	}

	public Iterator<TrackItem> iterator() {
		return items.values().iterator();
	}

}
