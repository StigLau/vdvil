package no.bouvet.kpro.gui.main;

import java.net.MalformedURLException;
import java.net.URL;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

import no.bouvet.kpro.gui.bar.BarWidget;
import no.bouvet.kpro.gui.tree.TreeWidget;
import no.bouvet.kpro.model.old.Media;
import no.bouvet.kpro.persistence.Storage;

public class MainWindow extends QMainWindow
{
    public QAction newCompositionAction;
    public QAction openCompositionAction;
    public QAction saveCompositionAction;
    public QAction saveCompositionAsAction;
    public QAction addSongAction;
    public QAction exportCompositionAction;
    public QAction exitAction;
    public QAction zoomInAction;
    public QAction zoomOutAction;
    public QAction jumbToBeginningAction;
    public QAction jumbToEndAction;
    public QAction showBeatsAction;
    public QAction playAction;
    public QAction stopAction;
    public QAction toggleCrossfadeAction;
    public QAction actionAboutAction;
    
    public QWidget centralWidget;
    public QGridLayout gridLayout;
    public QHBoxLayout hboxLayout;
    public TreeWidget treeWidget;
    public BarWidget barWidget;
    public QLabel compositionLabel;
    public QHBoxLayout hboxLayout1;
    public QToolButton zoomInButton;
    public QToolButton zoomOutButton;
    public QMenuBar menubar;
    public QMenu menuFile;
    public QMenu menuView;
    public QMenu menuPlayer;
    public QMenu menuHelp;
    public QToolBar newOpenSaveToolBar;
    public QToolBar playStopToolBar;
    
    public Signal1<String> playButtonPressed;
    
    public MainWindow() {
    	resize(new QSize(788, 534).expandedTo(minimumSizeHint()));
        setWindowIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/qt-logo.png")));
        setupActions();
        setupWidgetsAndLayout();
        setupMenubars();
        setupText();
        setupSignalAndSlots();
        playButtonPressed = new Signal1<String>();
    }
    
    private void setupWidgetsAndLayout() {
        centralWidget = new QWidget(this);
        gridLayout = new QGridLayout(centralWidget);
        hboxLayout = new QHBoxLayout();
        hboxLayout.setContentsMargins(0, 0, 0, 0);
        treeWidget = new TreeWidget(centralWidget);
        treeWidget.setFocusPolicy(Qt.FocusPolicy.WheelFocus);
        hboxLayout.addWidget(treeWidget);
        gridLayout.addLayout(hboxLayout, 0, 0, 1, 2);
        compositionLabel = new QLabel(centralWidget);
        gridLayout.addWidget(compositionLabel, 1, 0, 1, 1);
        hboxLayout1 = new QHBoxLayout();
        hboxLayout1.setContentsMargins(0, 0, 0, 0);
        barWidget = new BarWidget(centralWidget);
        barWidget.setFocusPolicy(Qt.FocusPolicy.StrongFocus);
        hboxLayout1.addWidget(barWidget);
        gridLayout.addLayout(hboxLayout1, 2, 0, 1, 1);
        
        zoomInButton = new QToolButton(centralWidget);
        zoomInButton.setFocusPolicy(Qt.FocusPolicy.TabFocus);
        zoomInButton.addAction(zoomInAction);
        zoomInButton.setIcon(zoomInAction.icon());        

        gridLayout.addWidget(zoomInButton, 2, 1, 1, 1);

        zoomOutButton = new QToolButton(centralWidget);
        zoomOutButton.setFocusPolicy(Qt.FocusPolicy.TabFocus);
        zoomOutButton.addAction(zoomOutAction);
        zoomOutButton.setIcon(zoomOutAction.icon());

        gridLayout.addWidget(zoomOutButton, 3, 1, 1, 1);

        setCentralWidget(centralWidget);
    }
    
    private void setupSignalAndSlots() {
		treeWidget.numberOfTreesChanged.connect(barWidget,"addTrack(MediaNode)");
		treeWidget.treeExpansionChanged.connect(barWidget,"trackItemsCountChanged(MediaNode)");
		connectSlotsByName();
    }
    
    private void setupActions() {
        newCompositionAction = new QAction(this);
        newCompositionAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/new.png")));
        openCompositionAction = new QAction(this);
        openCompositionAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/linguist-fileopen.png")));
        openCompositionAction.setObjectName("openCompositionAction");
        saveCompositionAction = new QAction(this);
        saveCompositionAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/linguist-filesave.png")));
        saveCompositionAsAction = new QAction(this);
        addSongAction = new QAction(this);
        addSongAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/linguist-editfind.png")));
        exportCompositionAction = new QAction(this);
        exitAction = new QAction(this);
        zoomInAction = new QAction(this);
        zoomInAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/zoomin.png")));
        zoomOutAction = new QAction(this);
        zoomOutAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/zoomout.png")));
        jumbToBeginningAction = new QAction(this);
        jumbToBeginningAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/linguist-prevunfinished.png")));
        jumbToEndAction = new QAction(this);
        jumbToEndAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/linguist-nextunfinished.png")));
        showBeatsAction = new QAction(this);
        playAction = new QAction(this);
        playAction.setObjectName("playAction");
        playAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/stylesheet-branch-closed.png")));
        stopAction = new QAction(this);
        stopAction.setIcon(new QIcon(new QPixmap("src/main/resources/no/bouvet/kpro/gui/icons/cursor-forbidden.png")));
        toggleCrossfadeAction = new QAction(this);
        actionAboutAction = new QAction(this);
    }
    
    private void setupMenubars() {
        menubar = new QMenuBar(this);
        menubar.setGeometry(new QRect(0, 0, 788, 29));
        menuFile = new QMenu(menubar);
        menuView = new QMenu(menubar);
        menuPlayer = new QMenu(menubar);
        menuHelp = new QMenu(menubar);
        setMenuBar(menubar);
        newOpenSaveToolBar = new QToolBar(this);
        addToolBar(Qt.ToolBarArea.TopToolBarArea, newOpenSaveToolBar);
        playStopToolBar = new QToolBar(this);
        addToolBar(Qt.ToolBarArea.TopToolBarArea, playStopToolBar);

        menubar.addAction(menuFile.menuAction());
        menubar.addAction(menuView.menuAction());
        menubar.addAction(menuPlayer.menuAction());
        menubar.addAction(menuHelp.menuAction());
        menuFile.addAction(newCompositionAction);
        menuFile.addAction(openCompositionAction);
        menuFile.addAction(saveCompositionAction);
        menuFile.addAction(saveCompositionAsAction);
        menuFile.addSeparator();
        menuFile.addAction(addSongAction);
        menuFile.addAction(exportCompositionAction);
        menuFile.addSeparator();
        menuFile.addAction(exitAction);
        menuView.addAction(zoomInAction);
        menuView.addAction(zoomOutAction);
        menuView.addSeparator();
        menuView.addAction(jumbToBeginningAction);
        menuView.addAction(jumbToEndAction);
        menuView.addSeparator();
        menuView.addSeparator();
        menuView.addAction(showBeatsAction);
        menuPlayer.addAction(playAction);
        menuPlayer.addAction(stopAction);
        menuPlayer.addSeparator();
        menuPlayer.addAction(toggleCrossfadeAction);
        menuHelp.addAction(actionAboutAction);
        newOpenSaveToolBar.addAction(newCompositionAction);
        newOpenSaveToolBar.addAction(openCompositionAction);
        newOpenSaveToolBar.addAction(saveCompositionAction);
        newOpenSaveToolBar.addSeparator();
        playStopToolBar.addAction(playAction);
        playStopToolBar.addAction(stopAction);
    }

    private void setupText() {
        setWindowTitle("DJ Composer");

        newCompositionAction.setText("New Composition");
        openCompositionAction.setText("Open Composition...");
        saveCompositionAction.setText("Save Composition");
        saveCompositionAsAction.setText("Save Composition As...");
        addSongAction.setText("Add Song...");
        exportCompositionAction.setText("Export Composition...");
        exitAction.setText("Exit");
        zoomInAction.setText("Zoom In");
        zoomOutAction.setText("Zoom Out");
        jumbToBeginningAction.setText("Jump to Begining");
        jumbToEndAction.setText("Jump to End");
        showBeatsAction.setText("Show Beats");
        playAction.setText("Play Current View");
        stopAction.setText("Stop");
        toggleCrossfadeAction.setText("Don't Crossfade(2CHAN)");
        actionAboutAction.setText("About...");
        compositionLabel.setText("New composition");
        menuFile.setTitle("File");
        menuView.setTitle("View");
        menuPlayer.setTitle("Player");
        menuHelp.setTitle("Help");
        newOpenSaveToolBar.setWindowTitle("toolBar");
        playStopToolBar.setWindowTitle("toolBar_2");
    }

    @Override
    protected void closeEvent(QCloseEvent event) {
    	stopAction.trigger();
    	super.closeEvent(event);
    }
    
    /**
     * Called when user push the button which opens a composition.
     * @throws MalformedURLException 
     */
    @SuppressWarnings("unused")
	private void on_openCompositionAction_triggered() throws MalformedURLException {
        String fullFilePath = QFileDialog.getOpenFileName(this, tr("Open File..."), "",
                new QFileDialog.Filter(tr("MP3 Files (*.mp3);;All Files (*)")));
        if (fullFilePath.length() > 0) {
        	String fileName = fullFilePath.substring(fullFilePath.lastIndexOf("/")+1,fullFilePath.length());
        	Media media = Storage.getInstance().getMediaByFileName(fileName);
        	if (media == null) {
        		QMessageBox.information(this, "Info", "Could not find meta-data for the mp3.");
        	} else {
        		String pathWithoutFile = fullFilePath.substring(0,fullFilePath.lastIndexOf("/"));
        		media.setMediaFile(new URL("file://" + pathWithoutFile));
        		treeWidget.addMedia(media);
        	} 
        }
    }
    /**
     * Called when the play button is triggered.
     */
    @SuppressWarnings("unused")
	private void on_playAction_triggered() {
    	if (treeWidget.getAllMedia().size() > 0) {
    		Media m = treeWidget.getAllMedia().get(0);
    		playButtonPressed.emit(m.getMediaFile().getPath());
    	}
    }
}

