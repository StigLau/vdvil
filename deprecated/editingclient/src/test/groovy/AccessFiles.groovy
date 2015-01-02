import groovyx.net.http.*

def http = new HTTPBuilder('http://www.google.com')

http.get( path : '/search',
        contentType : ContentType.JSON,
        query : [q:'Groovy'] ) { resp, reader ->

    println "response status: ${resp.statusLine}"
    println 'Headers: -----------'
    resp.headers.each { h ->
        println " ${h.name} : ${h.value}"
    }
    println 'Response data: -----'
    System.out << reader
    println '\n--------------------'
}