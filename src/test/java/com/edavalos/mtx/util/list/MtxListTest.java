package com.edavalos.mtx.util.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxListTest {
    private MtxLinkedList<Object> mtxLinkedList;
    private MtxArrayList<Object> mtxArrayList;
    private MtxHashList<Object> mtxHashList;

    @BeforeEach
    public void setUp() {
        mtxLinkedList = new MtxLinkedList<>();
        mtxArrayList = new MtxArrayList<>();
        mtxHashList = new MtxHashList<>();
    }

    @Test
    public void testCopy() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four", "Five", "Six"};
        String startingContents = "[Zero, One, Two, Three, Four, Five, Six]";
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
            mtxArrayList.add(element);
            mtxHashList.add(element);
        }

        MtxList<Object> mtxLinkedListCopy = mtxLinkedList.copy();
        MtxList<Object> mtxArrayListCopy = mtxArrayList.copy();
        MtxList<Object> mtxHashListCopy = mtxHashList.copy();

        // MtxLinkedList tests
        assertEquals(mtxLinkedList.size(), mtxLinkedListCopy.size());
        assertTrue(mtxLinkedList.equals(mtxLinkedListCopy));
        assertEquals(startingContents, mtxLinkedListCopy.toString());

        // MtxArrayList tests
        assertEquals(mtxArrayList.size(), mtxArrayListCopy.size());
        assertTrue(mtxArrayList.equals(mtxArrayListCopy));
        assertEquals(startingContents, mtxArrayListCopy.toString());

        // MtxHashList tests
        assertEquals(mtxHashList.size(), mtxHashListCopy.size());
        assertTrue(mtxHashList.equals(mtxHashListCopy));
        assertEquals(startingContents, mtxHashListCopy.toString());
    }

    @Nested
    class ContainsAllTests {
        Integer[] fullSet = {4, 5, 6, 9, 2, 3};
        Integer[] partialSet = {5, 9, 2};
        Integer[] nonInclusiveSet = {5, 2, 8};

        @BeforeEach
        public void setUp() {
            mtxLinkedList = new MtxLinkedList<>(fullSet);
            mtxArrayList = new MtxArrayList<>(fullSet);
            mtxHashList = new MtxHashList<>(fullSet);
        }

        @Test
        public void testContainsAll_collection() {
            // Java generics is forcing all these to be individual inline instances
            assertTrue(mtxLinkedList.containsAll(new LinkedList<>(Arrays.stream(partialSet).toList())));
            assertTrue(mtxArrayList.containsAll(new LinkedList<>(Arrays.stream(partialSet).toList())));
            assertTrue(mtxHashList.containsAll(new LinkedList<>(Arrays.stream(partialSet).toList())));

            assertFalse(mtxLinkedList.containsAll(new LinkedList<>(Arrays.stream(nonInclusiveSet).toList())));
            assertFalse(mtxArrayList.containsAll(new LinkedList<>(Arrays.stream(nonInclusiveSet).toList())));
            assertFalse(mtxHashList.containsAll(new LinkedList<>(Arrays.stream(nonInclusiveSet).toList())));
        }

        @Test
        public void testContainsAll_array() {
            assertTrue(mtxLinkedList.containsAll(partialSet));
            assertTrue(mtxArrayList.containsAll(partialSet));
            assertTrue(mtxHashList.containsAll(partialSet));

            assertFalse(mtxLinkedList.containsAll(nonInclusiveSet));
            assertFalse(mtxArrayList.containsAll(nonInclusiveSet));
            assertFalse(mtxHashList.containsAll(nonInclusiveSet));
        }
    }

    @Test
    public void testEquals() {
        String[] sampleElements = {"Zero", "One", "Two", "Three", "Four"};
        String[] otherList1 = {"Zero", "One", "Two", "Three", "Four", "Five"};
        String[] otherList2 = {"Thero", "Won", "Too", "Free", "For"};
        for (String element : sampleElements) {
            mtxLinkedList.add(element);
            mtxArrayList.add(element);
            mtxHashList.add(element);
        }

        // MtxLinkedList tests
        assertTrue(mtxLinkedList.equals(new MtxLinkedList<>(sampleElements)));
        assertTrue(mtxLinkedList.equals(List.of(sampleElements)));
        assertTrue(mtxLinkedList.equals(sampleElements));

        assertFalse(mtxLinkedList.equals(new MtxLinkedList<>(otherList1)));
        assertFalse(mtxLinkedList.equals(List.of(otherList1)));
        assertFalse(mtxLinkedList.equals(otherList1));

        assertFalse(mtxLinkedList.equals(new MtxLinkedList<>(otherList2)));
        assertFalse(mtxLinkedList.equals(List.of(otherList2)));
        assertFalse(mtxLinkedList.equals(otherList2));

        assertFalse(mtxLinkedList.equals(mtxLinkedList.toString()));

        // MtxArrayList tests
        assertTrue(mtxArrayList.equals(new MtxLinkedList<>(sampleElements)));
        assertTrue(mtxArrayList.equals(List.of(sampleElements)));
        assertTrue(mtxArrayList.equals(sampleElements));

        assertFalse(mtxArrayList.equals(new MtxLinkedList<>(otherList1)));
        assertFalse(mtxArrayList.equals(List.of(otherList1)));
        assertFalse(mtxArrayList.equals(otherList1));

        assertFalse(mtxArrayList.equals(new MtxLinkedList<>(otherList2)));
        assertFalse(mtxArrayList.equals(List.of(otherList2)));
        assertFalse(mtxArrayList.equals(otherList2));

        assertFalse(mtxArrayList.equals(mtxArrayList.toString()));

        // MtxHashList tests
        assertTrue(mtxHashList.equals(new MtxLinkedList<>(sampleElements)));
        assertTrue(mtxHashList.equals(List.of(sampleElements)));
        assertTrue(mtxHashList.equals(sampleElements));

        assertFalse(mtxHashList.equals(new MtxLinkedList<>(otherList1)));
        assertFalse(mtxHashList.equals(List.of(otherList1)));
        assertFalse(mtxHashList.equals(otherList1));

        assertFalse(mtxHashList.equals(new MtxLinkedList<>(otherList2)));
        assertFalse(mtxHashList.equals(List.of(otherList2)));
        assertFalse(mtxHashList.equals(otherList2));

        assertFalse(mtxLinkedList.equals(mtxHashList.toString()));
    }

    @Nested
    class AddAllTests {
        Character[] defaultSet = {'a', 'b', 'c', 'd', 'e'};
        Character[] setToAdd = {'f', 'g', 'h'};
        Character[] fullSet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

        @BeforeEach
        public void setUp() {
            mtxLinkedList = new MtxLinkedList<>(defaultSet);
            mtxArrayList = new MtxArrayList<>(defaultSet);
            mtxHashList = new MtxHashList<>(defaultSet);
        }

        @Test
        public void testAddAll_collection() {
            assertTrue(mtxLinkedList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));
            assertTrue(mtxArrayList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));
            assertTrue(mtxHashList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));

            mtxLinkedList.addAll(new LinkedList<>(Arrays.stream(setToAdd).toList()));
            mtxArrayList.addAll(new LinkedList<>(Arrays.stream(setToAdd).toList()));
            mtxHashList.addAll(new LinkedList<>(Arrays.stream(setToAdd).toList()));

            assertTrue(mtxLinkedList.equals(new LinkedList<>(Arrays.stream(fullSet).toList())));
            assertTrue(mtxArrayList.equals(new LinkedList<>(Arrays.stream(fullSet).toList())));
            assertTrue(mtxHashList.equals(new LinkedList<>(Arrays.stream(fullSet).toList())));
        }

        @Test
        public void testAddAll_array() {
            assertTrue(mtxLinkedList.equals(defaultSet));
            assertTrue(mtxArrayList.equals(defaultSet));
            assertTrue(mtxHashList.equals(defaultSet));

            mtxLinkedList.addAll(setToAdd);
            mtxArrayList.addAll(setToAdd);
            mtxHashList.addAll(setToAdd);

            assertTrue(mtxLinkedList.equals(fullSet));
            assertTrue(mtxArrayList.equals(fullSet));
            assertTrue(mtxHashList.equals(fullSet));
        }
    }

    @Nested
    class RemoveAllTests {
        Character[] defaultSet = {'a', 'b', 'c', 'd', 'e'};
        Character[] setToRemove = {'b', 'e'};
        Character[] independentSet = {'f', 'g', 'h'};
        Character[] newSet = {'a', 'c', 'd'};

        @BeforeEach
        public void setUp() {
            mtxLinkedList = new MtxLinkedList<>(defaultSet);
            mtxArrayList = new MtxArrayList<>(defaultSet);
            mtxHashList = new MtxHashList<>(defaultSet);
        }

        @Test
        public void testAddAll_collection() {
            assertTrue(mtxLinkedList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));
            assertTrue(mtxArrayList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));
            assertTrue(mtxHashList.equals(new LinkedList<>(Arrays.stream(defaultSet).toList())));

            assertTrue(mtxLinkedList.removeAll(new LinkedList<>(Arrays.stream(setToRemove).toList())));
            assertFalse(mtxLinkedList.removeAll(new LinkedList<>(Arrays.stream(independentSet).toList())));
            assertTrue(mtxArrayList.removeAll(new LinkedList<>(Arrays.stream(setToRemove).toList())));
            assertFalse(mtxArrayList.removeAll(new LinkedList<>(Arrays.stream(independentSet).toList())));
            assertTrue(mtxHashList.removeAll(new LinkedList<>(Arrays.stream(setToRemove).toList())));
            assertFalse(mtxHashList.removeAll(new LinkedList<>(Arrays.stream(independentSet).toList())));

            assertTrue(mtxLinkedList.equals(new LinkedList<>(Arrays.stream(newSet).toList())));
            assertTrue(mtxArrayList.equals(new LinkedList<>(Arrays.stream(newSet).toList())));
            assertTrue(mtxHashList.equals(new LinkedList<>(Arrays.stream(newSet).toList())));
        }

        @Test
        public void testAddAll_array() {
            assertTrue(mtxLinkedList.equals(defaultSet));
            assertTrue(mtxArrayList.equals(defaultSet));
            assertTrue(mtxHashList.equals(defaultSet));

            assertTrue(mtxLinkedList.removeAll(setToRemove));
            assertFalse(mtxLinkedList.removeAll(independentSet));
            assertTrue(mtxArrayList.removeAll(setToRemove));
            assertFalse(mtxArrayList.removeAll(independentSet));
            assertTrue(mtxHashList.removeAll(setToRemove));
            assertFalse(mtxHashList.removeAll(independentSet));

            assertTrue(mtxLinkedList.equals(newSet));
            assertTrue(mtxArrayList.equals(newSet));
            assertTrue(mtxHashList.equals(newSet));
        }
    }
}
