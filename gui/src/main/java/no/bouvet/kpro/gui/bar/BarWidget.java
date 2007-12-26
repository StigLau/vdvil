package no.bouvet.kpro.gui.bar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.bouvet.kpro.gui.tree.AbstractNode;
import no.bouvet.kpro.gui.tree.MediaNode;
import no.bouvet.kpro.gui.tree.TreeNodeModel;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QWidget;

public class BarWidget extends QGraphicsView {
	
	private List<Track> tracks;
	private double height = 90;
	private double width = 600;
	private double interTrackSpacing = 3;
	private double compositionDuration = 100;
	
	private double topMargin = 2;
	private double bottomMargin = 2;
	private double leftMargin = 2;
	private double rightMargin = 2;
	
	public BarWidget() {
		this(null);
	}
	
	public BarWidget(QWidget parent) {
		super(parent);

		tracks = new ArrayList<Track>();
		setScene(new QGraphicsScene(this));
		scene().setSceneRect(0, 0, width, height);
		scene().setItemIndexMethod(QGraphicsScene.ItemIndexMethod.NoIndex);
		setRenderHint(QPainter.RenderHint.Antialiasing);
	}
	
	public void addTrack(MediaNode node) {
		Track track = new Track(this,node);
		tracks.add(track);
		createTrackItem(node, track);
		updateTracks();
		scene().addItem(track);
	}
	
	private void updateTracks() {
		Double lastYPosition = null;
		for (Track t: tracks) {
			lastYPosition = nextPosition(lastYPosition);
			t.setRect(leftMargin,lastYPosition,width-rightMargin,trackHeight());
			for (TrackItem ti: t) {
				ti.rect().setHeight(t.rect().height());
			}
		}
	}
	
	private double nextPosition(Double lastYPosition) {
		if (lastYPosition == null) {
			return topMargin;
		} else {
			return lastYPosition + interTrackSpacing + trackHeight();
		}
	}
	
	private double trackHeight() {
		return (height-topMargin-bottomMargin-interTrackSpacing*tracks.size())/tracks.size();
	}

	/**
	 * Used internally.
	 * 
	 * Slot used when any tree is expanded or contracted. This method let the tracks
	 * know what nodes currently existing on the treeWidget's scene.
	 * 
	 * Not that this method does not add anything to any scene.
	 * @param root
	 */
	public void trackItemsCountChanged(MediaNode root) {
		Track track = getTrack(root);
		Set<AbstractNode> allNodes = track.getMediaNode().getAllSubNodes();
		if (allNodes.size() > tracks.size()) {
			addNewNodes(track,allNodes);
		} else {
			Set<AbstractNode> nodesToRemove = track.getCoveredNodes();
			nodesToRemove.removeAll(allNodes);
			removeTrackItems(nodesToRemove,track);
		}
	}
	
	public Track getTrack(MediaNode root) {
		for (Track t: tracks) {
			if (t.getMediaNode() == root) {
				return t;
			}
		}
		return null;
	}
	
	private void addNewNodes(Track track,Set<AbstractNode> allNodes) {
		Set<AbstractNode> nodesToAdd = new HashSet<AbstractNode>();
		for (AbstractNode n: allNodes) {
			if (!track.containsNode(n)) {
				nodesToAdd.add(n);
			}
		}
		for (AbstractNode n: nodesToAdd) {
			createTrackItem(n,track);
		}
	}
	
	private void createTrackItem(AbstractNode n, Track track) {
		TrackItem ti = new TrackItem(track, n);
		n.nodePositionChanged.connect(ti,"nodePositionChanged()");
		n.nodeSelectionChanged.connect(ti,"nodeSelectionChanged(Boolean)");
		n.shouldBePlayedChanged.connect(ti,"shouldBePlayedChanged(Boolean)");
	}
	
	private void removeTrackItems(Set<AbstractNode> nodes, Track track) {
		Set<AbstractNode> clone = new HashSet<AbstractNode>();
		clone.addAll(nodes);
		for (AbstractNode n: clone) {
			track.removeItem(n);
		}
	}
	
	void updateTrackItems() {
		List<TrackItem> allItems = new ArrayList<TrackItem>();
		for (Track t: tracks) {
			for (TrackItem ti: t.getItems()) {
				if (ti.isVisible()) {
					allItems.add(ti);
				}
			}
		}
		Collections.sort(allItems);
		double lastX = 0;
		double lastWidth = 0;
		for (TrackItem ti: allItems) {
			double position = lastX + lastWidth;
			lastWidth = updateTrackItem(ti,position);
			lastX = position;
		}
	}
	
	private double updateTrackItem(TrackItem ti, double position) {
		TreeNodeModel model = ti.getNode().getModel();
		int starttime = model.getStartTime();
		int stoptime = model.getStopTime();
		int duration = stoptime-starttime;
		double width = duration/compositionDuration*100;

		QRectF trackRectangle = ti.getTrack().rect();
		QPointF sceneCoords = mapToScene((int)trackRectangle.x(),(int)trackRectangle.y());
		ti.setRect(new QRectF(position,mapFromScene(sceneCoords).y(),width,trackHeight()));
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}
	
}
