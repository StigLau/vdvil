package no.lau.vdvil.playback;

/**
 * @author Stig@Lau.no - 02/01/15.
 */
public class LameEnkoderWrapping {
    public String enkode(String rawFile) throws Exception {
    Process p = Runtime.getRuntime().exec("lame " + rawFile);
        p.waitFor();
        return rawFile.replaceAll(".wav", ".mp3");
    }
}
