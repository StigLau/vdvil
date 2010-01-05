/********************************************************************************
** Form generated from reading ui file 'MainWindow.jui'
**
** Created: to 8. nov 12:48:17 2007
**      by: Qt User Interface Compiler version 4.3.1
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package no.bouvet.kpro.gui;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_MainWindow
{
    public QAction actionNew_Composition;
    public QAction actionOpen_Composition;
    public QAction actionSave_Composition;
    public QAction actionSave_Composition_As;
    public QAction actionAdd_Song;
    public QAction actionExport_Composition;
    public QAction actionExit;
    public QAction actionZoom_Out;
    public QAction actionJump_to_Begining;
    public QAction actionJump_to_End;
    public QAction actionJump_to_Previous_Transition;
    public QAction actionJump_to_Next_Transition;
    public QAction actionShow_Beats;
    public QAction actionShow_Meatadata;
    public QAction actionPlay_from_Beigining;
    public QAction actionPlay_Current_View;
    public QAction actionStop;
    public QAction actionShow_Spectrum_Analyzer;
    public QAction actionDon_not_Crossfade;
    public QAction actionShow_Log;
    public QAction actionAbout;
    public QWidget designerWidget;
    public QGridLayout gridLayout;
    public QTreeWidget composition1Tree;
    public QTreeWidget composition1Tree_2;
    public QLineEdit lineEdit;
    public QLineEdit lineEdit_3;
    public QLineEdit lineEdit_2;
    public QLineEdit lineEdit_10;
    public QLineEdit lineEdit_11;
    public QLineEdit lineEdit_12;
    public QLineEdit lineEdit_4;
    public QLineEdit lineEdit_15;
    public QLineEdit lineEdit_14;
    public QLineEdit lineEdit_13;
    public QLabel label;
    public QHBoxLayout hboxLayout;
    public QLineEdit lineEdit_17;
    public QLineEdit lineEdit_16;
    public QLineEdit lineEdit_5;
    public QPushButton pushButton_2;
    public QScrollBar newCompositionScrollBar;
    public QPushButton pushButton;
    public QMenuBar menubar;
    public QMenu menuFile;
    public QMenu menuView;
    public QMenu menuPlayer;
    public QMenu menuHelp;
    public QToolBar toolBar;
    public QToolBar toolBar_2;

    public Ui_MainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow)
    {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(788, 509).expandedTo(MainWindow.minimumSizeHint()));
        MainWindow.setWindowIcon(new QIcon(new QPixmap("icons/qt-logo.png")));
        actionNew_Composition = new QAction(MainWindow);
        actionNew_Composition.setObjectName("actionNew_Composition");
        actionNew_Composition.setIcon(new QIcon(new QPixmap("icons/new.png")));
        actionOpen_Composition = new QAction(MainWindow);
        actionOpen_Composition.setObjectName("actionOpen_Composition");
        actionOpen_Composition.setIcon(new QIcon(new QPixmap("icons/linguist-fileopen.png")));
        actionSave_Composition = new QAction(MainWindow);
        actionSave_Composition.setObjectName("actionSave_Composition");
        actionSave_Composition.setIcon(new QIcon(new QPixmap("icons/linguist-filesave.png")));
        actionSave_Composition_As = new QAction(MainWindow);
        actionSave_Composition_As.setObjectName("actionSave_Composition_As");
        actionAdd_Song = new QAction(MainWindow);
        actionAdd_Song.setObjectName("actionAdd_Song");
        actionAdd_Song.setIcon(new QIcon(new QPixmap("icons/linguist-editfind.png")));
        actionExport_Composition = new QAction(MainWindow);
        actionExport_Composition.setObjectName("actionExport_Composition");
        actionExit = new QAction(MainWindow);
        actionExit.setObjectName("actionExit");
        actionZoom_Out = new QAction(MainWindow);
        actionZoom_Out.setObjectName("actionZoom_Out");
        actionJump_to_Begining = new QAction(MainWindow);
        actionJump_to_Begining.setObjectName("actionJump_to_Begining");
        actionJump_to_Begining.setIcon(new QIcon(new QPixmap("icons/linguist-prevunfinished.png")));
        actionJump_to_End = new QAction(MainWindow);
        actionJump_to_End.setObjectName("actionJump_to_End");
        actionJump_to_End.setIcon(new QIcon(new QPixmap("icons/linguist-nextunfinished.png")));
        actionJump_to_Previous_Transition = new QAction(MainWindow);
        actionJump_to_Previous_Transition.setObjectName("actionJump_to_Previous_Transition");
        actionJump_to_Previous_Transition.setIcon(new QIcon(new QPixmap("icons/linguist-prev.png")));
        actionJump_to_Next_Transition = new QAction(MainWindow);
        actionJump_to_Next_Transition.setObjectName("actionJump_to_Next_Transition");
        actionJump_to_Next_Transition.setIcon(new QIcon(new QPixmap("icons/linguist-next.png")));
        actionShow_Beats = new QAction(MainWindow);
        actionShow_Beats.setObjectName("actionShow_Beats");
        actionShow_Meatadata = new QAction(MainWindow);
        actionShow_Meatadata.setObjectName("actionShow_Meatadata");
        actionPlay_from_Beigining = new QAction(MainWindow);
        actionPlay_from_Beigining.setObjectName("actionPlay_from_Beigining");
        actionPlay_Current_View = new QAction(MainWindow);
        actionPlay_Current_View.setObjectName("actionPlay_Current_View");
        actionPlay_Current_View.setIcon(new QIcon(new QPixmap("icons/stylesheet-branch-closed.png")));
        actionStop = new QAction(MainWindow);
        actionStop.setObjectName("actionStop");
        actionStop.setIcon(new QIcon(new QPixmap("icons/cursor-forbidden.png")));
        actionShow_Spectrum_Analyzer = new QAction(MainWindow);
        actionShow_Spectrum_Analyzer.setObjectName("actionShow_Spectrum_Analyzer");
        actionDon_not_Crossfade = new QAction(MainWindow);
        actionDon_not_Crossfade.setObjectName("actionDon_not_Crossfade");
        actionShow_Log = new QAction(MainWindow);
        actionShow_Log.setObjectName("actionShow_Log");
        actionAbout = new QAction(MainWindow);
        actionAbout.setObjectName("actionAbout");
        designerWidget = new QWidget(MainWindow);
        designerWidget.setObjectName("designerWidget");
        gridLayout = new QGridLayout(designerWidget);
        gridLayout.setObjectName("gridLayout");
        composition1Tree = new QTreeWidget(designerWidget);
        composition1Tree.setObjectName("composition1Tree");
        composition1Tree.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        gridLayout.addWidget(composition1Tree, 0, 0, 1, 4);

        composition1Tree_2 = new QTreeWidget(designerWidget);
        composition1Tree_2.setObjectName("composition1Tree_2");
        composition1Tree_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        gridLayout.addWidget(composition1Tree_2, 0, 4, 1, 5);

        lineEdit = new QLineEdit(designerWidget);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit, 1, 0, 1, 4);

        lineEdit_3 = new QLineEdit(designerWidget);
        lineEdit_3.setObjectName("lineEdit_3");
        lineEdit_3.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_3, 1, 4, 1, 5);

        lineEdit_2 = new QLineEdit(designerWidget);
        lineEdit_2.setObjectName("lineEdit_2");
        lineEdit_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_2, 2, 0, 1, 1);

        lineEdit_10 = new QLineEdit(designerWidget);
        lineEdit_10.setObjectName("lineEdit_10");
        lineEdit_10.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_10, 2, 1, 1, 1);

        lineEdit_11 = new QLineEdit(designerWidget);
        lineEdit_11.setObjectName("lineEdit_11");
        lineEdit_11.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_11, 2, 2, 1, 1);

        lineEdit_12 = new QLineEdit(designerWidget);
        lineEdit_12.setObjectName("lineEdit_12");
        lineEdit_12.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_12, 2, 3, 1, 1);

        lineEdit_4 = new QLineEdit(designerWidget);
        lineEdit_4.setObjectName("lineEdit_4");
        lineEdit_4.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_4, 2, 4, 1, 1);

        lineEdit_15 = new QLineEdit(designerWidget);
        lineEdit_15.setObjectName("lineEdit_15");
        lineEdit_15.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_15, 2, 5, 1, 1);

        lineEdit_14 = new QLineEdit(designerWidget);
        lineEdit_14.setObjectName("lineEdit_14");
        lineEdit_14.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_14, 2, 6, 1, 1);

        lineEdit_13 = new QLineEdit(designerWidget);
        lineEdit_13.setObjectName("lineEdit_13");
        lineEdit_13.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(lineEdit_13, 2, 7, 1, 2);

        label = new QLabel(designerWidget);
        label.setObjectName("label");

        gridLayout.addWidget(label, 3, 0, 1, 3);

        hboxLayout = new QHBoxLayout();
        hboxLayout.setObjectName("hboxLayout");
        hboxLayout.setContentsMargins(0, 0, 0, 0);
        lineEdit_17 = new QLineEdit(designerWidget);
        lineEdit_17.setObjectName("lineEdit_17");
        lineEdit_17.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        hboxLayout.addWidget(lineEdit_17);

        lineEdit_16 = new QLineEdit(designerWidget);
        lineEdit_16.setObjectName("lineEdit_16");
        lineEdit_16.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        hboxLayout.addWidget(lineEdit_16);

        lineEdit_5 = new QLineEdit(designerWidget);
        lineEdit_5.setObjectName("lineEdit_5");
        lineEdit_5.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        hboxLayout.addWidget(lineEdit_5);


        gridLayout.addLayout(hboxLayout, 4, 0, 1, 8);

        pushButton_2 = new QPushButton(designerWidget);
        pushButton_2.setObjectName("pushButton_2");
        pushButton_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(pushButton_2, 4, 8, 1, 1);

        newCompositionScrollBar = new QScrollBar(designerWidget);
        newCompositionScrollBar.setObjectName("newCompositionScrollBar");
        newCompositionScrollBar.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        gridLayout.addWidget(newCompositionScrollBar, 5, 0, 1, 8);

        pushButton = new QPushButton(designerWidget);
        pushButton.setObjectName("pushButton");
        pushButton.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(pushButton, 5, 8, 1, 1);

        MainWindow.setCentralWidget(designerWidget);
        menubar = new QMenuBar(MainWindow);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 788, 19));
        menuFile = new QMenu(menubar);
        menuFile.setObjectName("menuFile");
        menuView = new QMenu(menubar);
        menuView.setObjectName("menuView");
        menuPlayer = new QMenu(menubar);
        menuPlayer.setObjectName("menuPlayer");
        menuHelp = new QMenu(menubar);
        menuHelp.setObjectName("menuHelp");
        MainWindow.setMenuBar(menubar);
        toolBar = new QToolBar(MainWindow);
        toolBar.setObjectName("toolBar");
        MainWindow.addToolBar(com.trolltech.qt.core.Qt.ToolBarArea.TopToolBarArea, toolBar);
        toolBar_2 = new QToolBar(MainWindow);
        toolBar_2.setObjectName("toolBar_2");
        MainWindow.addToolBar(com.trolltech.qt.core.Qt.ToolBarArea.BottomToolBarArea, toolBar_2);

        menubar.addAction(menuFile.menuAction());
        menubar.addAction(menuView.menuAction());
        menubar.addAction(menuPlayer.menuAction());
        menubar.addAction(menuHelp.menuAction());
        menuFile.addAction(actionNew_Composition);
        menuFile.addAction(actionOpen_Composition);
        menuFile.addAction(actionSave_Composition);
        menuFile.addAction(actionSave_Composition_As);
        menuFile.addSeparator();
        menuFile.addAction(actionAdd_Song);
        menuFile.addAction(actionExport_Composition);
        menuFile.addSeparator();
        menuFile.addAction(actionExit);
        menuView.addAction(actionZoom_Out);
        menuView.addSeparator();
        menuView.addAction(actionJump_to_Begining);
        menuView.addAction(actionJump_to_End);
        menuView.addSeparator();
        menuView.addAction(actionJump_to_Previous_Transition);
        menuView.addAction(actionJump_to_Next_Transition);
        menuView.addSeparator();
        menuView.addAction(actionShow_Beats);
        menuView.addAction(actionShow_Meatadata);
        menuPlayer.addAction(actionPlay_from_Beigining);
        menuPlayer.addAction(actionPlay_Current_View);
        menuPlayer.addSeparator();
        menuPlayer.addAction(actionStop);
        menuPlayer.addSeparator();
        menuPlayer.addAction(actionShow_Spectrum_Analyzer);
        menuPlayer.addAction(actionDon_not_Crossfade);
        menuHelp.addAction(actionShow_Log);
        menuHelp.addAction(actionAbout);
        toolBar.addAction(actionNew_Composition);
        toolBar.addAction(actionOpen_Composition);
        toolBar.addAction(actionSave_Composition);
        toolBar.addSeparator();
        toolBar.addAction(actionAdd_Song);
        toolBar.addAction(actionJump_to_Next_Transition);
        toolBar_2.addAction(actionPlay_Current_View);
        toolBar_2.addAction(actionStop);
        toolBar_2.addSeparator();
        toolBar_2.addAction(actionJump_to_Begining);
        toolBar_2.addAction(actionJump_to_Previous_Transition);
        toolBar_2.addAction(actionJump_to_Next_Transition);
        toolBar_2.addAction(actionJump_to_End);
        retranslateUi(MainWindow);

        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "MainWindow"));
        MainWindow.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'MS Shell Dlg 2'; font-size:8.25pt; font-weight:400; font-style:normal;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">MainWindow</p></body></html>"));
        actionNew_Composition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "New Composition"));
        actionOpen_Composition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Open Composition..."));
        actionSave_Composition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Save Composition"));
        actionSave_Composition_As.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Save Composition As..."));
        actionAdd_Song.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Add Song..."));
        actionExport_Composition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Export Composition..."));
        actionExit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Exit"));
        actionZoom_Out.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Zoom Out"));
        actionJump_to_Begining.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Jump to Begining"));
        actionJump_to_End.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Jump to End"));
        actionJump_to_Previous_Transition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Jump to Previous Transition"));
        actionJump_to_Next_Transition.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Jump to Next Transition"));
        actionShow_Beats.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Show Beats"));
        actionShow_Meatadata.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Show Meatadata"));
        actionPlay_from_Beigining.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Play from Beiginning"));
        actionPlay_Current_View.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Play Current View"));
        actionStop.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Stop"));
        actionShow_Spectrum_Analyzer.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Show Spectrum Analyzer"));
        actionDon_not_Crossfade.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Don't Crossfade(2CHAN)"));
        actionShow_Log.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Show Log"));
        actionAbout.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About..."));
        composition1Tree.headerItem().setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Media object 1 tree"));
        composition1Tree.clear();

        QTreeWidgetItem __item = new QTreeWidgetItem(composition1Tree);
        __item.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Intro"));

        QTreeWidgetItem __item1 = new QTreeWidgetItem(composition1Tree);
        __item1.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 1"));

        QTreeWidgetItem __item2 = new QTreeWidgetItem(__item1);
        __item2.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 1"));

        QTreeWidgetItem __item3 = new QTreeWidgetItem(__item2);
        __item3.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 1"));

        QTreeWidgetItem __item4 = new QTreeWidgetItem(__item3);
        __item4.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Note 1"));

        QTreeWidgetItem __item5 = new QTreeWidgetItem(__item2);
        __item5.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 2"));

        QTreeWidgetItem __item6 = new QTreeWidgetItem(__item2);
        __item6.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 3"));

        QTreeWidgetItem __item7 = new QTreeWidgetItem(__item2);
        __item7.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 4"));

        QTreeWidgetItem __item8 = new QTreeWidgetItem(__item1);
        __item8.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 2"));

        QTreeWidgetItem __item9 = new QTreeWidgetItem(__item1);
        __item9.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 3"));

        QTreeWidgetItem __item10 = new QTreeWidgetItem(__item1);
        __item10.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 4"));

        QTreeWidgetItem __item11 = new QTreeWidgetItem(composition1Tree);
        __item11.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Corous"));

        QTreeWidgetItem __item12 = new QTreeWidgetItem(composition1Tree);
        __item12.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 2"));

        QTreeWidgetItem __item13 = new QTreeWidgetItem(composition1Tree);
        __item13.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Corous"));

        QTreeWidgetItem __item14 = new QTreeWidgetItem(composition1Tree);
        __item14.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Outro"));
        composition1Tree_2.headerItem().setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Media object 2 tree"));
        composition1Tree_2.clear();

        QTreeWidgetItem __item15 = new QTreeWidgetItem(composition1Tree_2);
        __item15.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Song 1"));

        QTreeWidgetItem __item16 = new QTreeWidgetItem(composition1Tree_2);
        __item16.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Crossover Song 1 to Song 2"));

        QTreeWidgetItem __item17 = new QTreeWidgetItem(composition1Tree_2);
        __item17.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Song 2"));

        QTreeWidgetItem __item18 = new QTreeWidgetItem(__item17);
        __item18.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 1"));

        QTreeWidgetItem __item19 = new QTreeWidgetItem(__item18);
        __item19.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 1"));

        QTreeWidgetItem __item20 = new QTreeWidgetItem(__item19);
        __item20.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 1"));

        QTreeWidgetItem __item21 = new QTreeWidgetItem(__item20);
        __item21.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Note 1"));

        QTreeWidgetItem __item22 = new QTreeWidgetItem(__item19);
        __item22.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 2"));

        QTreeWidgetItem __item23 = new QTreeWidgetItem(__item19);
        __item23.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 3"));

        QTreeWidgetItem __item24 = new QTreeWidgetItem(__item19);
        __item24.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Bar 4"));

        QTreeWidgetItem __item25 = new QTreeWidgetItem(__item18);
        __item25.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 2"));

        QTreeWidgetItem __item26 = new QTreeWidgetItem(__item18);
        __item26.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 3"));

        QTreeWidgetItem __item27 = new QTreeWidgetItem(__item18);
        __item27.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 4"));

        QTreeWidgetItem __item28 = new QTreeWidgetItem(__item17);
        __item28.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Corous"));

        QTreeWidgetItem __item29 = new QTreeWidgetItem(__item17);
        __item29.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 2"));

        QTreeWidgetItem __item30 = new QTreeWidgetItem(__item17);
        __item30.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Corous"));

        QTreeWidgetItem __item31 = new QTreeWidgetItem(__item17);
        __item31.setText(0, com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Outro"));
        lineEdit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 1"));
        lineEdit_3.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Song 2"));
        lineEdit_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 1"));
        lineEdit_10.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 3"));
        lineEdit_11.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 3"));
        lineEdit_12.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 4"));
        lineEdit_4.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 1"));
        lineEdit_15.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "corous"));
        lineEdit_14.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 2"));
        lineEdit_13.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Corous"));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "New composition"));
        lineEdit_17.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 3"));
        lineEdit_16.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Verse 2"));
        lineEdit_5.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Quadra bar 1"));
        pushButton_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Zoom out"));
        pushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Zoom in"));
        menuFile.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "File"));
        menuView.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "View"));
        menuPlayer.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Player"));
        menuHelp.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Help"));
        toolBar.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "toolBar"));
        toolBar_2.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "toolBar_2"));
    } // retranslateUi

}

