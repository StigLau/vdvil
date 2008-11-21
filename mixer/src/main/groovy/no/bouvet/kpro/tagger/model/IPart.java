package no.bouvet.kpro.tagger.model;

import java.util.List;

public interface IPart {
    List<IPart> children();
}
