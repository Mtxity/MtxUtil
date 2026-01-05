package com.edavalos.mtx.util.continuum;

import java.util.Iterator;

/**
 * MtxPasskey is a passkey holder that auto-increments passkeys that require periodic rotating.
 * initialPasskey is the value first used for passkey1, and passkeyIncrement is used to add to
 * the passkey every passkeyLength interval.
 */
public class MtxPasskey implements Iterable<String> {
    private final String initialPasskey;
    private final int passkeyIncrement;
    private final int passkeyLength;

    /**
     * Create new MtxPasskey
     * @param initialPasskey String to use as base / first passkey
     * @param passkeyIncrement Integer to add to passkey every passkeyLength (as a String)
     * @param passkeyLength Number of times to increment passkey before rotating entire result
     */
    public MtxPasskey(String initialPasskey, int passkeyIncrement, int passkeyLength) {
        this.initialPasskey = initialPasskey;
        this.passkeyIncrement = passkeyIncrement;
        this.passkeyLength = passkeyLength;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return null;
    }
}
