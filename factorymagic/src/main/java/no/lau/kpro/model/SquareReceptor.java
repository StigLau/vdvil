package no.lau.kpro.model;

import no.lau.kpro.domain.Receptor;
import no.lau.kpro.domain.TopicIdentifyable;
import net.sf.oval.constraint.*;
import net.sf.oval.guard.Pre;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 3, 2008
 */
@net.sf.oval.guard.Guarded
public class SquareReceptor implements Receptor {


    @Pre(expr = "return _args[0] instanceof no.lau.kpro.model.Square", lang = "groovy")
    public void put(@NotNull TopicIdentifyable shape) {
        System.out.println("Sqaure receptor invoked " + shape);
    }

    public Class getAcceptsClass() {
        return Square.class;
    }
}
