package com.edavalos.mtx.util.continuum;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxPasskeyTest {
    private MtxPasskey mtxPasskey;

    @Nested
    class GetNextPasskeyTests {

        @Test
        public void testGetNextPasskey_incrementsCorrectly() {
            mtxPasskey = new MtxPasskey("key", 30);

            assertEquals("key0", mtxPasskey.getNextPasskey());
            assertEquals("key1", mtxPasskey.getNextPasskey());
            assertEquals("key2", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesEmptyInitialPasskey() {
            mtxPasskey = new MtxPasskey("", 40);

            assertEquals("0", mtxPasskey.getNextPasskey());
            assertEquals("1", mtxPasskey.getNextPasskey());
        }

        @Test
        public void testGetNextPasskey_handlesNullInitialPasskey() {
            mtxPasskey = new MtxPasskey(null, 50);

            assertEquals("null0", mtxPasskey.getNextPasskey());
        }
    }

    @Nested
    class IteratorTests {

        @Test
        public void testIterator_hasNext_returnsTrueWhenWithinBounds() {
            mtxPasskey = new MtxPasskey("test", 10);
            Iterator<String> it = mtxPasskey.iterator();

            assertTrue(it.hasNext(), "Iterator should have next if increment max is within bounds");
            assertNotNull(it.next());
        }

        @Test
        public void testIterator_integrationWithForEach() {
            mtxPasskey = new MtxPasskey("key", 0);

            int count = 0;
            for (String passkey : mtxPasskey) {
                assertTrue(passkey.startsWith("key"));
                count++;
                if (count >= 3) break;
            }

            assertEquals(3, count);
        }
    }
}
