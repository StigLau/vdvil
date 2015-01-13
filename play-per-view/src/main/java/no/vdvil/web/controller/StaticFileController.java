package no.vdvil.web.controller;

import static spark.SparkBase.externalStaticFileLocation;
import static spark.SparkBase.staticFileLocation;

/**
 * @author Stig@Lau.no
 * Creates a simple REST service for displaying data in HTML/Mustache or HTML/Angular/JS
 */
public class StaticFileController {
    public StaticFileController() {
        //staticFileLocation("/static");
        //Use this for dev purposes and create symlink to src/main/resources/static
        externalStaticFileLocation("./static");
    }
}
