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
            mtxPasskey = new MtxPasskey("key", 10, 5);

            assertEquals("key0", mtxPasskey.getNextPasskey());
            assertEquals("key1", mtxPasskey.getNextPasskey());
            assertEquals("key2", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesEmptyInitialPasskey() {
            mtxPasskey = new MtxPasskey("", 1, 1);

            assertEquals("0", mtxPasskey.getNextPasskey());
            assertEquals("1", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesNullInitialPasskey() {
            mtxPasskey = new MtxPasskey(null, 1, 1);

            assertEquals("null0", mtxPasskey.getNextPasskey());
        }
    }
}
