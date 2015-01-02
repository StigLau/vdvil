package no.vdvil.web.controller;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilAudioConfig;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @author Stig@Lau.no - 02/01/15.
 */
public class PlayPerViewController {

    private final Logger logger = LoggerFactory.getLogger(PlayPerViewController.class);

    public PlayPerViewController() {
        get("/vdvil/play", (req, res) -> new ModelAndView("a simple model", "godmode.mustache"), new MustacheTemplateEngine());


        get("/vdvil/kompose", (request, response) -> {
            logger.info("Komposing");

            try {
                ParseFacade parser = new VdvilAudioConfig().getParseFacade();
                FileRepresentation fileRepresentation = TestMp3s.javaZoneComposition_WithoutImages;
                Composition composition = (Composition) parser.parse(PartXML.create(fileRepresentation));
                new BackStage().prepare(composition, new MasterBeatPattern(0, 16, 150F)).playUntilEnd();
            } catch (IOException e) {
                e.printStackTrace();
            }

            response.redirect("/vdvil/");
            return "";
        });

    }
}
