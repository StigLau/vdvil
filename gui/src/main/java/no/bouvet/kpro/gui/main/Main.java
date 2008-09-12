package no.bouvet.kpro.gui.main;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QApplication;

import no.bouvet.kpro.model.old.Media;
import no.bouvet.kpro.persistence.Storage;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.bouvet.kpro.renderer.audio.AudioTopicMapInstructions;

import java.io.File;

public class Main extends QObject{
	
	private Renderer renderer;
	private AudioTarget audioTarget;
	private MainWindow mainWindow;
	private AudioTopicMapInstructions instructions;
		
	public Main() throws Exception {
		audioTarget = new AudioPlaybackTarget();
		mainWindow = new MainWindow();
    	mainWindow.playButtonPressed.connect(this,"rendererStartAdapter(String)");
    	mainWindow.stopAction.triggered.connect(this,"stopRenderer()");
	}
	
	public static void main(String[] args) throws Exception {
    	QApplication.initialize(args);
    	Main main = new Main();
    	main.mainWindow.show();
    	QApplication.exec();
	}
	
	/**
	 * Method for translating calls to the renderer. Used by jambi's
	 * signal-slot mechanism and provided for the demonstration.
	 */
	@SuppressWarnings("unused")
	private void rendererStartAdapter(String path) throws Exception {

		//Media instructionMedia = Storage.getInstance().getMediaByFileName("snap_vs_corona.mp3");

		//instructions = new AudioTopicMapInstructions( instructionMedia, new File(path));
		renderer = new Renderer(instructions);
		renderer.addRenderer(new AudioRenderer(audioTarget));
		renderer.start(0);
	}

	@SuppressWarnings("unused")
	private void stopRenderer() {
		if (renderer != null) {
			renderer.stop();
			audioTarget.close();
			instructions.close();
		}
	}

}
