package no.bouvet.kpro.tagger.model

class Effect {
    Float startValue
    Float endValue

    Float startCue
    Float endCue

    Collection<Part> partsAffected = new ArrayList<Part>()
    /*
    def Effect(Float startCue, Float endCue, Float startValue, Float endValue, Part... parts) {
        this.startCue = startCue
        this.endCue = endCue
        this.startValue = startValue
        this.endValue = endValue
        parts.each {Part part ->
            partsAffected << part }
    } */

    void addAffectedPart(Part part) {
        partsAffected.add(part)
    }
}