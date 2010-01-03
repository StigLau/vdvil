package no.lau.tagger.model;

import java.util.List;

public interface IPart {
    List<? extends IPart> children();
}
