package no.lau;

import org.testng.annotations.Test;
import net.sf.oval.exception.ConstraintsViolatedException;
import no.lau.kpro.model.Circle;
import no.lau.kpro.model.Square;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 8, 2008
 */
public class BusinessObjectTest {

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testNotNullInMethodCall() {
        BusinessObject bo = new BusinessObject();
        bo.setName(null);
    }

    @Test
    public void testGroovyPreValidation() {
        BusinessObject bo = new BusinessObject();
        bo.runSomething(new Circle());
    }

    @Test(expectedExceptions = ConstraintsViolatedException.class)
    public void testGroovyPreValidationFails() {
        BusinessObject bo = new BusinessObject();
        bo.runSomething(new Square());
    }
}
