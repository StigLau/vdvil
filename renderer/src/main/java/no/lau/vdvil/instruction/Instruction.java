package no.lau.vdvil.instruction;

/**
 * V2 interface for instructions
 * @author Stig Lau
 * @since June 2012
 */
public interface Instruction extends Comparable{

    /**
     * The fundamental time unit (resolution. There are RATE units per second.
     */
    int RESOLUTION = 44100;
    long start();

    long length();

    /**
     * Convenience method for calculating end
     * @return start + length
     */
    long end();
}
