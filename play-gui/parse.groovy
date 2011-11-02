import org.cyberneko.html.parsers.SAXParser

def base ="http://www.flickr.com"
def photosClickflashwhirr = "/photos/clickflashwhirr"
def setId = "72157626172514856"
def size = "m"
def clickFlashWhirr = "$base$photosClickflashwhirr/sets/$setId/?page=3"
def parser = new XmlParser(new SAXParser())

println "Fetching $clickFlashWhirr"

def listPics = parser.parse("$clickFlashWhirr")
def links = listPics.depthFirst().A['@href'].findAll{url -> url && url.contains("$photosClickflashwhirr") && url.contains("set-") }

println links.size

links.each{ 
    def singlePicId = stripId(it)
    def singleImageUrl = "$base$photosClickflashwhirr/$singlePicId/sizes/$size/in/set-$setId/"
    def links2 = parser.parse(singleImageUrl).depthFirst().A['@href'].findAll{ url -> url && url.contains(".jpg")}
    println links2[0]
}

def stripId(String url) {
 (url =~ /[0-9]+/)[0]
}
