import java.awt.BorderLayout
import javax.swing.JTextField
import javax.swing.JFrame
import javax.swing.JFileChooser
import groovy.swing.SwingBuilder
import no.bouvet.kpro.tagger.PlayerBase
import no.bouvet.kpro.tagger.model.SimpleSong
import no.bouvet.kpro.tagger.persistence.SimpleSongParser
import no.bouvet.kpro.tagger.model.SimpleSong

class TagGui {
    def swing = new SwingBuilder()
    JTextField bpmText
    JTextField fileDisplay
    JFrame frame
    PlayerBase playerBase

    public void createGui() {
        SimpleSongParser parser = new SimpleSongParser()
        SimpleSong corona = parser.load("/corona.dvl")

        frame = swing.frame(title: 'Frame', size: [500, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
            borderLayout()

            fileDisplay = textField(text: corona.fileName, constraints: BorderLayout.NORTH)
            button(text: 'Load File',
                    actionPerformed: {
                        JFileChooser fileChooser = swing.fileChooser(currentDirectory: new File(fileDisplay.text))
                        int returnVal = fileChooser.showOpenDialog(frame);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            fileDisplay.text = fileChooser.getSelectedFile().getAbsolutePath();
                            playerBase.setFileName(fileDisplay.text)
                        }
                    }, constraints: BorderLayout.EAST
            )

        }
        playerBase = new PlayerBase(corona)
        frame.add(playerBase.getDynamicTimeTable(), BorderLayout.CENTER)
        frame.pack()
        frame.show()
    }

    public static void main(String[] args) {
        def tagGui = new TagGui()
        tagGui.createGui()
    }
}


