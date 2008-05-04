package no.bouvet.kpro.tagger.model

class Volume extends Effect {
    Volume(Float startCue, Float endCue, Float startValue, Float endValue, Part... parts) {
        super(startCue, endCue, startValue, endValue, parts)
    }
}