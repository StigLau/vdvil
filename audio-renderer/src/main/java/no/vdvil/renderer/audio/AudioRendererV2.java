package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.Renderer;

/**
 * V2 of AudioRenderer
 * @author Stig Lau
 * @since June 2012
 */
public class AudioRendererV2 implements Renderer {
    final AudioTarget audioTarget;
    final AudioRenderer audioRenderer;
    final float masterBpm;
    boolean isPlaying = false;


    public AudioRendererV2(AudioTarget audioTarget, float masterBpm) {
        this.audioTarget = audioTarget;
        this.masterBpm = masterBpm;
        this.audioRenderer = new AudioRenderer(audioTarget);
    }

    @Override
    public void notify(Instruction instruction, long beat) {
        AudioInstruction audioInstruction = ((AudioInstructionV2)instruction).asInstruction(masterBpm);
        audioRenderer.notify(audioInstruction, beat);
        if(!isPlaying) {
            isPlaying = true;
            audioRenderer.start((int) beat);
        }
        System.out.println("audioInstruction = " + audioInstruction);

    }
}
