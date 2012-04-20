/*
@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
@Grab(group='oauth.signpost', module='signpost-core', version='1.2.1' )
@Grab(group='oauth.signpost', module='signpost-commonshttp4', version='1.2.1' )
*/

import groovyx.net.http.RESTClient
import oauth.signpost.basic.*
import oauth.signpost.*
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import com.sun.org.apache.bcel.internal.generic.PUTFIELD
import groovyx.net.http.Method

//http://groovy.codehaus.org/modules/http-builder/doc/auth.html
//https://www.dropbox.com/developers/reference/api

def consumerKey = 'csua4s11j2d45mf'
def consumerSecret = 'vxrecb3sxbks11o'
/*
def consumer = new DefaultOAuthConsumer(consumerKey , consumerSecret)
def provider = new DefaultOAuthProvider(
        "https://api.dropbox.com/1/oauth/request_token",
        "https://api.dropbox.com/1/oauth/access_token",
        "https://www.dropbox.com/1/oauth/authorize");
println "Gå til " + provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND) + " for å godkjenne tilgangen og generere tokens

println "accessToken " + consumer.token
println "secretToken  " + consumer.tokenSecret
accessToken = consumer.token
secretToken= consumer.tokenSecret
*/
//For hver gang disse lages må sluttbruker godkjenne via GUI
accessToken = 'bo9xh3fuui1lzes'
secretToken = 'k61m613yy7d3scz'


def restClient = new RESTClient( 'https://api.dropbox.com/1/' )
restClient.auth.oauth consumerKey, consumerSecret, accessToken, secretToken

assert restClient.get( path : 'account/info' ).status == 200
/*
restClient.get( path : 'account/info') { resp, reader ->
    println "response status: ${resp.statusLine}"
    println 'Headers: -----------'
    resp.headers.each { h ->
        println " ${h.name} : ${h.value}"
    }
    println 'Response data: -----'
    System.out << reader
    println '\n--------------------'

    println reader.country
    reader.each {
        println it
    }
}

*/
/*

def file = new File("AccessFiles.groovy")
println file.absolutePath
println restClient.put(path: 'files_put/dropbox/hei.txt?param=val', body:file, requestContentType: ContentType.TEXT)
        //[name:'bob', title:'construction worker'],  requestContentType : ContentType.JSON)
*/
restClient.get( path : 'metadata/sandbox') { resp, reader ->
    reader.each {
        println it
    }
}
restClient.post(path: 'fileops/create_folder?root=dropbox&path=%2Ftest')
