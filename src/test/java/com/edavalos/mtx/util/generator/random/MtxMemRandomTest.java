package com.edavalos.mtx.util.generator.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtxMemRandomTest {
    private static final int TESTS_COUNT = 10;

    private MtxMemRandom mtxMemRandom;

    @BeforeEach
    public void setUp() {
        mtxMemRandom = new MtxMemRandom();
    }

    @Nested
    public class RandomByteTests {
        private List<Byte> randomsList;
        private HashSet<Byte> randomsSet;

        @BeforeEach
        public void setUp() {
            randomsList = new ArrayList<>();
            for (int i = 0; i < TESTS_COUNT; i++) {
                randomsList.add(mtxMemRandom.randomByte());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomByte() {
            assertTrue(
                    randomsSet.size() == randomsList.size() ||
                    randomsSet.size() == randomsList.size() + 1
            );
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
                randomsList.add(mtxMemRandom.randomShort());
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
                randomsList.add(mtxMemRandom.randomInt());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomInt() {
            assertEquals(randomsSet.size(), randomsList.size());
        }

        @Test
        public void testRandomInt_withBounds() {
            for (int i = 0; i < TESTS_COUNT; i++) {
                int smallBound = new Random().nextInt(1, 5);
                int largeBound = new Random().nextInt(10, 15);
                for (int j = 0; j < TESTS_COUNT; j++) {
                    int randomSample = mtxMemRandom.randomInt(smallBound, largeBound);
                    assertTrue(smallBound <= randomSample);
                    assertTrue(largeBound > randomSample);
                }
            }
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
                randomsList.add(mtxMemRandom.randomLong());
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
                randomsList.add(mtxMemRandom.randomFloat());
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
                randomsList.add(mtxMemRandom.randomDouble());
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
                randomsList.add(mtxMemRandom.randomChar());
            }
            randomsSet = new HashSet<>(randomsList);
        }

        @Test
        public void testRandomChar() {
            assertEquals(randomsSet.size(), randomsList.size());
        }
    }
}
