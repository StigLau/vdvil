package no;

import net.sf.oval.Validator;
import net.sf.oval.ConstraintViolation;

import java.util.List;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import no.lau.kpro.MyShape;
import no.lau.kpro.model.Square;

public class BusinessObjectOvalTest {
    /*
    @Test
    public void testMyShapeNotNullValidation() {
        Validator validator = new Validator();

        MyShape shape = new MyShape();
        shape.setRound(null);
        // collect the constraint violations List<ConstraintViolation> violations =
        List<ConstraintViolation> exs = validator.validate(shape);
        assertEquals(1, exs.size());
        for (ConstraintViolation ex : exs) {
            System.out.println("ex = " + ex);
        }
    }

    @Test
    public void testMyShapeGroovyValidation() {
        Validator validator = new Validator();

        Square square = new Square();

        MyShape shape = new MyShape();
        shape.setRound(square);
        // collect the constraint violations List<ConstraintViolation> violations =
        List<ConstraintViolation> exs = validator.validate(shape);
        assertEquals(1, exs.size());
        for (ConstraintViolation ex : exs) {
            System.out.println("ex = " + ex);
        }
    }
    */
}
