package no.bouvet.kpro.tagger.gui

import java.awt.BorderLayout
import javax.swing.JTextField
import javax.swing.JFrame
import javax.swing.JFileChooser
import groovy.swing.SwingBuilder
import no.bouvet.kpro.tagger.PlayerBase
import no.lau.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.XStreamParser
/**
 * Note - to make the TagGUI functional, it can be necessary to make a small change to the file and recompile.
 */
class TagGui {
    def swing = new SwingBuilder()
    JTextField bpmText
    JFrame frame
    PlayerBase playerBase
    String dvlFilePath = System.getProperty("user.home") + "/kpro"

    public void createGui() {
        frame = swing.frame(title: 'Frame', size: [500, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
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
            button(text: 'Save', constraints: BorderLayout.WEST,
                    actionPerformed: {
                      def parser = new XStreamParser()
                        parser.save(playerBase.simpleSong, dvlFilePath)
                        println "Saved:" + dvlFilePath
                        println parser.toXml(playerBase.simpleSong)
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

    public static void main(String[] args) {
        new TagGui().createGui()
        println "Tagger Started "
    }
}


