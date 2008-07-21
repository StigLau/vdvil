package no.lau.kpro;

import org.testng.annotations.Test;
import no.lau.kpro.model.*;
import no.lau.kpro.domain.Topic;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 12, 2008
 */
public class ReceptorTest {

    @Test
    public void testPuttingSquareIntoSquareReceptorSucceeds() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(new PinkSquare(new Topic("5")));
    }

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testPuttingCircleIntoSquareReceptorFails() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(new YellowCircle(new Topic("4")));
    }

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testPuttingNullIntoSquareReceptorFails() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(null);
    }
}
