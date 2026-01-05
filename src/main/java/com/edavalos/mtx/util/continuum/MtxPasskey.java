package com.edavalos.mtx.util.continuum;

import java.util.Iterator;

/**
 * MtxPasskey is a passkey holder that auto-increments passkeys that require periodic rotating.
 * initialPasskey is the value first used for passkey1, and passkeyIncrement is used to add to
 * the passkey every passkeyLength interval.
 */
public class MtxPasskey implements Iterable<String> {
    private static final String PASSKEY_VARIABLE_EXT = "_%V%_";

    private final String initialPasskey;

    private String currentPasskey;
    private int currentPasskeyKey;

    /**
     * Create new MtxPasskey
     * @param initialPasskey String to use as base / first passkey
     */
    public MtxPasskey(String initialPasskey) {
        this.initialPasskey = initialPasskey + PASSKEY_VARIABLE_EXT;

        this.currentPasskey = this.initialPasskey;
        this.currentPasskeyKey = 0;
    }

    public String getNextPasskey() {
        String nextPasskey = this.currentPasskey.replaceFirst(PASSKEY_VARIABLE_EXT, String.valueOf(this.currentPasskeyKey));
        this.currentPasskeyKey++;
        return nextPasskey;
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
