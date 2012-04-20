@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.5.2' )
@Grab(group='oauth.signpost', module='signpost-core', version='1.2.1' )
@Grab(group='oauth.signpost', module='signpost-commonshttp4', version='1.2.1' )

import groovyx.net.http.RESTClient
import oauth.signpost.basic.*
import oauth.signpost.*

//http://groovy.codehaus.org/modules/http-builder/doc/auth.html

def consumerKey = 'Wx0HVvafECBExFgMVCg'
def consumerSecret = 'acpD2DGZFo6ADexPNSqaJ3Cqu5mCJOzn2ImwcxztQ'
def consumer = new DefaultOAuthConsumer(consumerKey , consumerSecret)
def provider = new DefaultOAuthProvider(
        "https://api.twitter.com/oauth/request_token",
        "https://api.twitter.com/oauth/access_token",
        "https://api.twitter.com/oauth/authorize");
provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);

//provider.retrieveAccessToken(consumer, '<PIN NUMBER FROM BROWSER>')

println "key " + consumer.token
println "TokenSecret " + consumer.tokenSecret


def restClient = new RESTClient( 'https://twitter.com/statuses/' )

accessToken = consumer.token
secretToken= consumer.tokenSecret



restClient.auth.oauth consumerKey, consumerSecret, accessToken, secretToken

assert restClient.get( path : 'public_timeline.json' ).status == 200
