package no.lau.kpro;

import no.lau.kpro.domain.Receptor;
import no.lau.kpro.domain.TopicIdentifyable;

import java.util.List;
import java.util.ArrayList;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 13, 2008
 */
public class SortingBox {
    private List<Receptor> receptors = new ArrayList<Receptor>();

    public void addReceptor(Receptor receptor) {
        this.receptors.add(receptor);
    }

    public void sort(Store stuffToSort) {
        for (Receptor receptor : receptors) {
            List<TopicIdentifyable> shapes = (List<TopicIdentifyable>) stuffToSort.findTopicsExtendingClass(receptor.getAcceptsClass());
            for (TopicIdentifyable shape : shapes) {
                receptor.put(shape);
            }
        }
    }
}
