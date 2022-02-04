package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilAudioConfig;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import java.io.IOException;

public class PlayingMixFromCompositionOnWebTest {
     static final ParseFacade parser = new VdvilAudioConfig().getParseFacade();

    public static void main(String[] args) throws IOException {
        FileRepresentation fileRepresentation = TestMp3s.javaZoneCompositionJson;
        Composition composition = (Composition) parser.parse(PartXML.create(fileRepresentation));
        new BackStage().prepare(composition, new MasterBeatPattern(0, 16, 150F)).playUntilEnd();
    }
}
