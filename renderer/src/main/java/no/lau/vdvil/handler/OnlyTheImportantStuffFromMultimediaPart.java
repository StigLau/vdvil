package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;

/**
 * MultimediaPart extraction for doing stuff which is not needed everywhere except Renderer
 */
public interface OnlyTheImportantStuffFromMultimediaPart {
    Instruction asInstruction(Float masterBpm);
}
