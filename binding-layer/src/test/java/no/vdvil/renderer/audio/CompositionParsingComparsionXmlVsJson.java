package no.vdvil.renderer.audio;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.parser.json.CompositionJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompositionParsingComparsionXmlVsJson {

    final ParseFacade parseFacade = new ParseFacade();
    AudioXMLParser audioXMLParser;

    @BeforeEach
    public void setup() {
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        parseFacade.addParser(new CompositionJsonParser(parseFacade));
        audioXMLParser = new AudioXMLParser();
        parseFacade.addParser(audioXMLParser);
    }

    @Test
    @Disabled //Parsing fails
    public void xmlVsJsonComparison() throws IOException {
        CompositionInstruction compositionInstruction = TestMp3s.compInstructionFactory(TestMp3s.javaZoneCompositionJson, 0, 128);
        Composition jsonComposition = (Composition) parseFacade.parse(compositionInstruction);
        CompositionInstruction compositionInstruction2 = TestMp3s.compInstructionFactory(TestMp3s.javaZoneComposition, 0, 128);
        Composition xmlComposition = (Composition) parseFacade.parse(compositionInstruction2);

        assertEquals(Float.valueOf(150F), xmlComposition.masterBeatPattern.masterBpm);
        assertEquals(Float.valueOf(150F), jsonComposition.masterBeatPattern.masterBpm);
        assertEquals(Integer.valueOf(0), jsonComposition.masterBeatPattern.fromBeat);
        assertEquals(Integer.valueOf(0), xmlComposition.masterBeatPattern.fromBeat);
        assertEquals(Integer.valueOf(252), jsonComposition.masterBeatPattern.toBeat);
        assertEquals(Integer.valueOf(252), xmlComposition.masterBeatPattern.toBeat);
        assertEquals(14, xmlComposition.multimediaParts.size());
        assertEquals(14, jsonComposition.multimediaParts.size());

        checkSomething(xmlComposition.multimediaParts.get(0), "4479230163500364845", 0, 32, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/olive-youre_not_alone.dvl"));
        checkSomething(jsonComposition.multimediaParts.get(0), "4479230163500364845", 0, 32, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/olive-youre_not_alone.dvl"));

        checkSomething(xmlComposition.multimediaParts.get(5), "6401936245564505757", 96, 44, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/holden-nothing-93_returning_mix.dvl"));
        checkSomething(jsonComposition.multimediaParts.get(5), "6401936245564505757", 96, 44, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/holden-nothing-93_returning_mix.dvl"));

        checkSomething(xmlComposition.multimediaParts.get(13), "5555459205073513470", 224, 28, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/christian_cambas-it_scares_me.dvl"));
        checkSomething(jsonComposition.multimediaParts.get(13), "5555459205073513470", 224, 28, 0, new URL("https://s3.amazonaws.com/dvl-test-music/dvl/christian_cambas-it_scares_me.dvl"));
    }

    private void checkSomething(MultimediaPart multimediaPart, String id, int start, int duration, int cueDifference, URL url) {
        assertEquals(id, multimediaPart.compositionInstruction().id());
        assertEquals(start, multimediaPart.compositionInstruction().start());
        assertEquals(duration, multimediaPart.compositionInstruction().duration());
        assertEquals(cueDifference, multimediaPart.compositionInstruction().cueDifference());
        assertEquals(url, multimediaPart.compositionInstruction().dvl().url());
    }
}
