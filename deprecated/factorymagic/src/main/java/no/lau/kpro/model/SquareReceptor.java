package no.lau.kpro.model;

import no.lau.kpro.domain.Receptor;
import no.lau.kpro.domain.TopicIdentifyable;
import net.sf.oval.constraint.*;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 3, 2008
 */
@net.sf.oval.guard.Guarded
public class SquareReceptor implements Receptor {


    public void put(@NotNull @InstanceOfAny(value = Square.class) TopicIdentifyable shape) {
        System.out.println("Sqaure receptor invoked " + shape);
    }

    public Class getAcceptsClass() {
        return Square.class;
    }
}
