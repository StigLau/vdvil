package no.vdvil.renderer.simple;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;
import no.lau.tagger.model.SimpleSong;

import java.util.ArrayList;
import java.util.List;

public class SoundTest {
    public static void main(String[] args) throws Exception {
        PlayStuff player = new PlayStuff(new Composition(135F, SoundTest.parts()));
        try {
            player.play(0F);
            Thread.sleep(120000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            player.stop();
            Thread.sleep(200);
        }
        System.exit(0);
    }

    public static List<Part> parts() {
        SimpleSong returning = new XStreamParser().load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl");
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(returning, 0F, 16F, returning.segments.get(3)));
        parts.add(new Part(returning, 12F, 32F, returning.segments.get(6)));
        parts.add(new Part(returning, 32F, 62.5F, returning.segments.get(9)));
        parts.add(new Part(returning, 62F, 63.5F, returning.segments.get(10)));
        parts.add(new Part(returning, 63F, 64.5F, returning.segments.get(11)));
        parts.add(new Part(returning, 64F, 128F, returning.segments.get(12)));
        parts.add(new Part(returning, 128F, 256F, returning.segments.get(14)));
        return parts;
    }
}