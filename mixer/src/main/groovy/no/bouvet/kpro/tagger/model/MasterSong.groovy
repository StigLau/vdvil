package no.bouvet.kpro.tagger.model

class MasterSong {
    Collection<Part> parts
    Collection<Effect> effects

    Float masterBpm

    def MasterSong() {
        parts = new ArrayList()
        effects = new ArrayList()
    }

    void addPart(Part part) {
        parts.add(part)
    }

    void addEffect(Effect effect) {
        effects.add(effect)
    }
}