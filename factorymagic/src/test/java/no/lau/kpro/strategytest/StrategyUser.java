package no.lau.kpro.strategytest;

import org.testng.annotations.Test;

import java.util.List;
import java.util.ArrayList;

import no.lau.kpro.domain.Receptor;
import no.lau.kpro.model.Square;
import no.lau.kpro.model.SquareReceptor;
import no.lau.kpro.model.Shape;
import no.lau.kpro.model.Circle;
import net.sf.oval.Validator;
import net.sf.oval.ConstraintViolation;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 3, 2008
 */
public class StrategyUser {

    @Test
    public void testUsingStrategyForBuildingFactory() {
        //Setup
        List<Shape> colouredObjects = new ArrayList<Shape>();
        colouredObjects.add(new Square());
        colouredObjects.add(new Square());
        colouredObjects.add(new Circle());
        colouredObjects.add(null);

        List<Receptor> receptors = new ArrayList<Receptor>();
        receptors.add(new SquareReceptor());

        //Run
        for (Receptor receptor : receptors) {
            for (Shape colouredObject : colouredObjects) {
                receptor.put(colouredObject);
            }
        }
    }
}
