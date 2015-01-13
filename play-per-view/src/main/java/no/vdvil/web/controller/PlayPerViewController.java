package no.vdvil.web.controller;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.LameEnkoderWrapping;
import no.lau.vdvil.playback.VdvilAudioConfig;
import no.lau.vdvil.playback.VdvilWavConfig;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * @author Stig@Lau.no - 02/01/15.
 */
public class PlayPerViewController {

    private final Logger logger = LoggerFactory.getLogger(PlayPerViewController.class);
    ParseFacade parser = new VdvilAudioConfig().getParseFacade();
    FileRepresentation fileRepresentation = TestMp3s.javaZoneComposition_WithoutImages;

    public PlayPerViewController() {
        get("/vdvil/play", (req, res) -> new ModelAndView("a simple model", "godmode.mustache"), new MustacheTemplateEngine());

        get("/vdvil/kompose", (request, response) -> {
            logger.info("Komposing");
            try {
                new BackStage().prepare(kompost(fileRepresentation), new MasterBeatPattern(0, 16, 150F)).playUntilEnd();
            } catch (IOException e) {
                logger.error("Shitz", e);
            }
            response.redirect("/vdvil/");
            return "";
        });

        get("/vdvil/kompost", (req, res) -> new ModelAndView(status(), "komposted.mustache"), new MustacheTemplateEngine());
    }

    Map<String, String> status() {
        Map<String, String> status = new HashMap<>();
        try {
            VdvilWavConfig config = new VdvilWavConfig(this);
            new BackStage(config)
                    .prepare(kompost(fileRepresentation), new MasterBeatPattern(0, 256, 150F))
                    .playUntilEnd();
            logger.info("File is located at {}", config.resultingFile);
            String mp3File = new LameEnkoderWrapping().enkode(config.resultingFile.getAbsolutePath());
            status.put("theFile", new File(mp3File).getName());
            logger.info("Resulting file {}", mp3File);
        } catch (Exception e) {
            logger.error("Shitz in pantz", e);
        }
        return status;
    }

    Composition kompost(FileRepresentation fileRepresentation) throws IOException {
        return (Composition) parser.parse(PartXML.create(fileRepresentation));
    }


}
