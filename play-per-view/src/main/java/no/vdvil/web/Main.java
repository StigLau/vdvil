package no.vdvil.web;

import no.vdvil.web.controller.PlayPerViewController;
import no.vdvil.web.controller.StaticFileController;

import static spark.SparkBase.setPort;

/**
 * @author Stig@Lau.no - 02/01/15.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        setPort(8080);

        new StaticFileController();
        new PlayPerViewController();
    }
}
