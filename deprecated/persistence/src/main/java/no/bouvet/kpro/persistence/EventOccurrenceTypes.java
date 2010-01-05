package no.bouvet.kpro.persistence;

import static no.bouvet.topicmap.query.AssociationTypeFactory.createAssociationString;
import no.bouvet.topicmap.query.AssociationTypeFactory;
import no.bouvet.topicmap.query.ITologFragment;
import no.bouvet.topicmap.query.ITopicParameter;

public enum EventOccurrenceTypes implements ITologFragment {

    START {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("vdvil:start", new String[]{"", ""}, parameters);
    }
    },
    LENGTH {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("vdvil:length", new String[]{"", ""}, parameters);
    }
    };

    public String asString(ITopicParameter... parameters) {
        return AssociationTypeFactory.asString(this, parameters);
    }
}