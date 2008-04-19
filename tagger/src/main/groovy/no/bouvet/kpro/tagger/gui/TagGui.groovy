package no.bouvet.kpro.tagger.gui

import java.awt.BorderLayout
import javax.swing.JTextField
import javax.swing.JFrame
import javax.swing.JFileChooser
import groovy.swing.SwingBuilder
import no.bouvet.kpro.tagger.PlayerBase
import no.bouvet.kpro.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.bouvet.kpro.tagger.model.Row

class TagGui {
    def swing = new SwingBuilder()
    JTextField bpmText
    JFrame frame
    PlayerBase playerBase
    String dvlFilePath = System.getProperty("user.home") + "/kpro"

    public void createGui() {
        frame = swing.frame(title: 'Frame', size: [500, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
            borderLayout()

            button(text: 'Load File', constraints: BorderLayout.SOUTH,
                    actionPerformed: {
                        JFileChooser fileChooser = swing.fileChooser(currentDirectory: new File(dvlFilePath))
                        int returnVal = fileChooser.showOpenDialog(frame);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            dvlFilePath = fileChooser.getSelectedFile().getAbsolutePath()
                            loadSong (loadSongFromFile())
                        }
                    }
            )
            button(text: 'New Song', constraints: BorderLayout.EAST,
                    actionPerformed: {
                        JFileChooser fileChooser = swing.fileChooser(currentDirectory: new File(dvlFilePath))
                        int returnVal = fileChooser.showOpenDialog(frame);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            dvlFilePath = fileChooser.getSelectedFile().getAbsolutePath()
                            loadSong(createNewSong())
                        }
                    }
            )
            button(text: 'Save', constraints: BorderLayout.WEST,
                    actionPerformed: {
                        new XStreamParser().save(playerBase.simpleSong, dvlFilePath)
                    }
            )
        }
        frame.pack()
        frame.show()
    }

    def loadSong(SimpleSong song) {
        playerBase = new PlayerBase(song)
        frame.add(playerBase.getDynamicTimeTable(), BorderLayout.CENTER)
        frame.pack()
        frame.show()
    }


    SimpleSong loadSongFromFile() {
        XStreamParser parser = new XStreamParser()
        return parser.load(dvlFilePath)

    }

    SimpleSong createNewSong() {
        SimpleSong song = new SimpleSong()
        [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15].each { song.rows.add(new Row(0F, 0F, ""))}
        return song
    }

    public static void main(String[] args) {
        def tagGui = new TagGui()
        tagGui.createGui()
    }
}


