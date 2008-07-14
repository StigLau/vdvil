package no.lau.kpro.model;

import no.lau.kpro.domain.Receptor;
import net.sf.oval.constraint.*;
import net.sf.oval.guard.Pre;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 3, 2008
 */
@net.sf.oval.guard.Guarded
public class CircleReceptor implements Receptor {


    @Pre(expr = "return _args[0] instanceof no.lau.kpro.model.Circle", lang = "groovy")
    public void put(@NotNull Shape shape) {
        System.out.println("Circle receptor invoked " + shape);
    }

    public Class getAcceptsClass() {
        return Circle.class;
    }
}