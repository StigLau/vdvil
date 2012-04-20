@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
@Grab(group='oauth.signpost', module='signpost-core', version='1.2.1' )
@Grab(group='oauth.signpost', module='signpost-commonshttp4', version='1.2.1' )

import groovyx.net.http.RESTClient
import oauth.signpost.basic.*
import oauth.signpost.*

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