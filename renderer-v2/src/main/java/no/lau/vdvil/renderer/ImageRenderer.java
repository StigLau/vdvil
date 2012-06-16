package no.lau.vdvil.renderer;

import java.util.ArrayList;
import java.util.List;

public class ImageRenderer {

    final static String[] acceptHeaders = new String[] {"Image/png", "Image/jpg"};
    private List instructionList = new ArrayList();
    private Timer timer;

    public ImageRenderer(Timer timer) {
        this.timer = timer;
    }

    @Deprecated
    public boolean accepts(String meta) {
        for (String acceptHeader : acceptHeaders) {
            if(acceptHeader.equals(meta))
                return true;
        }
        return false;
    }

    public void addInstruction(Instruction instruction) {
        instructionList.add(instruction);
        timer.addInstruction(instruction.getStartBeat(), instruction);
    }
}
