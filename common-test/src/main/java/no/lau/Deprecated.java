package no.lau;

/**
 * '@Category' Marker, used for instructing surefire/maven not to run as test on build server.
 * This specific marker os because test is old/not maintained and likely contains erronous code.
 */
public interface Deprecated {
}
