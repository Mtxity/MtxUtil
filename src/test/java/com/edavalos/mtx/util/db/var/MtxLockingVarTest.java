package com.edavalos.mtx.util.db.var;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxLockingVarTest {
    private final String SAMPLE_STR = "sampleString";

    private MtxLockingVar<String> mtxLVar;

    @Nested
    class TestConstructor {

        @Test
        public void testConstructor_default() {
            mtxLVar = new MtxLockingVar<>();

            assertNull(mtxLVar.get());
            assertFalse(mtxLVar.isLocked());
        }

        @Test
        public void testConstructor_valueParam() {
            mtxLVar = new MtxLockingVar<>(SAMPLE_STR);

            assertEquals(SAMPLE_STR, mtxLVar.get());
            assertFalse(mtxLVar.isLocked());
        }

        @Test
        public void testConstructor_valueParam_lockParam() {
            mtxLVar = new MtxLockingVar<>(SAMPLE_STR, true);

            assertEquals(SAMPLE_STR, mtxLVar.get());
            assertTrue(mtxLVar.isLocked());
        }
    }

    @Nested
    class TestSet {
        private final String NEW_STR = "newString";

        @BeforeEach
        public void setUp() {
            mtxLVar = new MtxLockingVar<>(SAMPLE_STR);
        }

        @Test
        public void testSet_unlocked() {
            assertEquals(SAMPLE_STR, mtxLVar.get());

            assertTrue(mtxLVar.set(NEW_STR));

            assertEquals(NEW_STR, mtxLVar.get());
        }

        @Test
        public void testSet_locked() {
            mtxLVar.lock();

            assertEquals(SAMPLE_STR, mtxLVar.get());

            assertFalse(mtxLVar.set(NEW_STR));

            assertEquals(SAMPLE_STR, mtxLVar.get());
        }
    }

    @Test
    public void testGet() {
        mtxLVar = new MtxLockingVar<>(SAMPLE_STR);
        assertEquals(SAMPLE_STR, mtxLVar.get());

        mtxLVar.set(null);
        assertNull(mtxLVar.get());
    }

    @Nested
    public class TestLockUnlock {

        @BeforeEach
        public void setUp() {
            mtxLVar = new MtxLockingVar<>(SAMPLE_STR);
        }

        @Nested
        public class TestLock {

            @Test
            public void testLock_lockWhenUnlocked() {
                assertFalse(mtxLVar.isLocked());
                assertTrue(mtxLVar.lock());
                assertTrue(mtxLVar.isLocked());
                assertFalse(mtxLVar.lock());
            }

            @Test
            public void testLock_lockWhenLocked() {
                mtxLVar.lock();

                assertTrue(mtxLVar.isLocked());
                assertFalse(mtxLVar.lock());
                assertTrue(mtxLVar.isLocked());
                assertFalse(mtxLVar.lock());
            }
        }

        @Nested
        public class TestUnlock {

            @Test
            public void testUnlock_unlockWhenLocked() {
                mtxLVar.lock();

                assertTrue(mtxLVar.isLocked());
                assertTrue(mtxLVar.unlock());
                assertFalse(mtxLVar.isLocked());
                assertFalse(mtxLVar.unlock());
            }

            @Test
            public void testUnlock_unlockWhenUnlocked() {
                assertFalse(mtxLVar.isLocked());
                assertFalse(mtxLVar.unlock());
                assertFalse(mtxLVar.isLocked());
                assertFalse(mtxLVar.unlock());
            }
        }
    }

    @Test
    public void testIsLocked() {
        mtxLVar = new MtxLockingVar<>(SAMPLE_STR, true);
        assertTrue(mtxLVar.isLocked());

        mtxLVar.unlock();
        assertFalse(mtxLVar.isLocked());

        mtxLVar.lock();
        assertTrue(mtxLVar.isLocked());
    }
}
