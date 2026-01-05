package com.edavalos.mtx.util.continuum;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MtxPasskeyTest {
    private MtxPasskey mtxPasskey;

    @Nested
    class GetNextPasskeyTests {

        @Test
        public void testGetNextPasskey_incrementsCorrectly() {
            mtxPasskey = new MtxPasskey("key");

            assertEquals("key0", mtxPasskey.getNextPasskey());
            assertEquals("key1", mtxPasskey.getNextPasskey());
            assertEquals("key2", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesEmptyInitialPasskey() {
            mtxPasskey = new MtxPasskey("");

            assertEquals("0", mtxPasskey.getNextPasskey());
            assertEquals("1", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesNullInitialPasskey() {
            mtxPasskey = new MtxPasskey(null);

            assertEquals("null0", mtxPasskey.getNextPasskey());
        }
    }
}
