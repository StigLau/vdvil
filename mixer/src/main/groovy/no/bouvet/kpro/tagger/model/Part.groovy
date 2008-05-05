package no.bouvet.kpro.tagger.model

class Part {
    SimpleSong simpleSong
    Row row

    Float bpm
    Float startCue
    Float endCue

    Float beginAtCue
    
    //TreeStructure is propably not for use here. Maybe is another view
    //List<Part> subParts;
	//Part parentPart;
}