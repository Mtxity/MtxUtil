package com.edavalos.mtx.util.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtxTimeRandomTest {
    private static final int TESTS_COUNT = 10;

    private MtxTimeRandom mtxTimeRandom;

    @BeforeEach
    public void setUp() {
        mtxTimeRandom = new MtxTimeRandom();
    }

    @Nested
    public class RandomByteTests {
        private List<Byte> randomsList;
        private HashSet<Byte> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomByte());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomByte() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomShortTests {
        private List<Short> randomsList;
        private HashSet<Short> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomShort());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomShort() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomIntTests {
        private List<Integer> randomsList;
        private HashSet<Integer> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomInt());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomInt() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomLongTests {
        private List<Long> randomsList;
        private HashSet<Long> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomLong());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomLong() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomFloatTests {
        private List<Float> randomsList;
        private HashSet<Float> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomFloat());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomFloat() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomDoubleTests {
        private List<Double> randomsList;
        private HashSet<Double> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomDouble());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomDouble() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }

    @Nested
    public class RandomCharTests {
        private List<Character> randomsList;
        private HashSet<Character> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxTimeRandom.randomChar());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomChar() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }
}
