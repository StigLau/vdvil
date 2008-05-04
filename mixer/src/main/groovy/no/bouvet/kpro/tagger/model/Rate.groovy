package no.bouvet.kpro.tagger.model

class Rate extends Effect{
    Rate(Float startCue, Float endCue, Float startValue, Float endValue, Part... parts) {
        super(startCue, endCue, startValue, endValue, parts)
    }
}