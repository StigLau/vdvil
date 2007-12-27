package no.bouvet.kpro.persistence;

import static no.bouvet.topicmap.query.AssociationTypeFactory.createAssociationString;
import no.bouvet.topicmap.query.AssociationTypeFactory;
import no.bouvet.topicmap.query.ITologFragment;
import no.bouvet.topicmap.query.ITopicParameter;

public enum VaudevilleAssociationType implements ITologFragment {
    /**
     * Parameters: MEDIA, PART
     */
    PART_WHOLE {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("vdvil:part-whole", new String[]{"vdvil:part", "vdvil:whole"}, parameters);
    }
    },
    MEDIA_EVENT {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("vdvil:media-event", new String[]{"", ""}, parameters);
    }
    },
    INSTANCE_OF {public String[] tologQuery(ITopicParameter... parameters) {
        return createAssociationString("instance-of", new String[]{"", ""}, parameters); //part-of
    }
    };

    public String asString(ITopicParameter... parameters) {
        return AssociationTypeFactory.asString(this, parameters);
    }
}
