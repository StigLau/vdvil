import java.awt.BorderLayout
import javax.swing.JTextField
import javax.swing.JFrame
import javax.swing.JButton
import no.bouvet.kpro.renderer.audio.MP3Source
import javax.swing.JFileChooser
import no.bouvet.kpro.gui.Worker
import groovy.swing.SwingBuilder

class TagGui {
    def swing = new SwingBuilder()
    Float cue = 0
    JTextField bpmText
    JTextField fileName
    Boolean started = false

    JButton playButton

    Worker worker

    public void createGui() {
        def frame = swing.frame(title: 'Frame', size: [300, 300], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
            borderLayout()
            bpmText = textField(text: "0", constraints: BorderLayout.WEST)

            fileName = textField(text: "/Volumes/McFeasty/Users/Stig/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3", constraints: BorderLayout.NORTH)
            button(text: 'Load File',
                    actionPerformed: {
                        JFileChooser fileChooser = swing.fileChooser(currentDirectory:new File("./src/main/resources"))
                        int returnVal = fileChooser.showOpenDialog(playButton);

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            fileName.text = fileChooser.getSelectedFile().getAbsolutePath();        
                        }
                    }, constraints: BorderLayout.EAST
            )
            playButton = button(text: 'Play',
                    actionPerformed: {
                        if (!started) {
                            audioSource = new MP3Source(new File(fileName.text));
                            cue = bpmText.text.toFloat()
                            worker = new Worker(audioSource:audioSource, cue:cue)
                            worker.execute()
                            playButton.text = "Stop"
                            started = true
                        } else {
                            //stop

                            worker.stop()
                            started = false
                            playButton.text = "Play"
                        }
                        println cue
                    }, constraints: BorderLayout.SOUTH
            )
        }
        frame.show()
    }

    public static void main(String[] args) {
        def tagGui = new TagGui()
        tagGui.createGui()
    }
}


