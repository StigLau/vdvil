# The Vdvil project

This is an infrastructure for a dj-mixing application

[DeveloperNotebook]

## Live demo
Available at http://play.kompo.st with demo composition [Github|https://raw.github.com/StigLau/vdvil/master/play-gui/src/test/resources/surrender.composition.xml]


JSON file upload to kompo.st store
curl -X POST https://stiglau:password@stiglau.cloudant.com/kompost/ -H "Content-Type: application/json" -d @JavazoneExample.composition.dvl.json

## Turning on debug logging
-Dorg.slf4j.simpleLogger.defaultLogLevel=debug