package no.lau.vdvil.handler.persistence.domain;

import no.lau.vdvil.handler.persistence.PartXML;

import java.util.List;

/**
 * Internal classes only used for parsing to/from XML/JSON!
 */

public class CompositionXml {
    public String name;
    public Float masterBpm;
    public List<PartXML> parts;
}
