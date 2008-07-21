package no.lau.kpro;

import org.testng.annotations.Test;
import no.lau.kpro.model.SquareReceptor;
import no.lau.kpro.model.Square;
import no.lau.kpro.model.Circle;
import net.sf.oval.exception.ConstraintsViolatedException;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 12, 2008
 */
public class ReceptorTest {

    @Test
    public void testPuttingSquareIntoSquareReceptorSucceeds() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(new Square() {});
    }

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testPuttingCircleIntoSquareReceptorFails() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(new Circle(){});
    }

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testPuttingNullIntoSquareReceptorFails() {
        SquareReceptor squareReceptor = new SquareReceptor();
        squareReceptor.put(null);
    }
}
