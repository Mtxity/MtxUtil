package com.edavalos.mtx.util.continuum;

import java.util.Iterator;

/**
 * MtxPasskey is a passkey holder that auto-increments passkeys that require periodic rotating.
 * initialPasskey is the value first used for passkey1, and passkeyIncrement is used to add to
 * the passkey every passkeyLength interval.
 */
public class MtxPasskey implements Iterable<String> {
    private static final String PASSKEY_VARIABLE_EXT = "_%V%_";
    private static final int MAX_AUTO_PASSKEYS = 100;

    private final String currentPasskey;
    private final int passkeyIncrementMax;

    private int currentPasskeyKey;

    /**
     * Create new MtxPasskey
     * @param initialPasskey String to use as base / first passkey
     */
    public MtxPasskey(String initialPasskey, int passkeyIncrementMax) {
        this.currentPasskey = initialPasskey + PASSKEY_VARIABLE_EXT;
        this.currentPasskeyKey = 0;
        this.passkeyIncrementMax = passkeyIncrementMax;
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
        return new Iterator<>() {
            private final int idx_itr = passkeyIncrementMax;

            @Override
            public boolean hasNext() {
                return this.idx_itr <= MAX_AUTO_PASSKEYS;
            }

            @Override
            public String next() {
                return getNextPasskey();
            }
        };
    }
}
