package com.edavalos.mtx.util.list;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public final class MtxIntrusiveLinkedList<T> implements MtxList<T>, Iterable<T> {
    private class MtxItrNode {
        MtxItrNode next;
        MtxItrNode prev;

        MtxItrNode() {
            this.next = null;
            this.prev = null;
        }
    }

    private class MtxItrData<D> extends MtxItrNode {
        D data;

        MtxItrData(D data) {
            this.data = data;
        }
    }

    private MtxItrNode head;
    private int size;

    public MtxIntrusiveLinkedList() {
        this.head = new MtxItrNode();
        this.head.next = this.head;
        this.head.prev = this.head;
        this.size = 0;
    }

    public MtxIntrusiveLinkedList(T[] initialContents) {
        this();
        for (T element : initialContents) {
            this.add(element);
        }
    }

    @Override
    public void add(T element) {
        MtxItrData<T> newNode = new MtxItrData<>(element);
        newNode.next = this.head.next;
        newNode.prev = this.head;
        this.head.next.prev = newNode;
        this.head.next = newNode;
        this.size ++;
    }

    @Override
    public boolean remove(T element) {
        MtxItrNode node = this.findNode(element);
        if (node == null) {
            return false;
        }

        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        this.size --;
        return true;
    }

    private MtxItrData<T> findNode(T contents) {
        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                if (dataNode.data.equals(contents)) {
                    return dataNode;
                }
            }
            current = current.prev;
        }
        return null;
    }

    @Override
    public String toString() {
        String[] contentsStrArr = new String[this.size()];

        MtxItrNode current = this.head.prev;
        int i = 0;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                contentsStrArr[i++] = dataNode.data.toString();
            }
            current = current.prev;
        }

        return "[" + String.join(", ", contentsStrArr) + "]";
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == this.head && this.head.prev == this.head;
    }

    @Override
    public boolean contains(T element) {
        return this.findNode(element) != null;
    }

    @Override
    public int countOccurrences(T element) {
        int count = 0;
        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                if (dataNode.data.equals(element)) {
                    count ++;
                }
            }
            current = current.prev;
        }
        return count;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index);
        }

        return ((MtxItrData<T>) this.getNodeAt(index)).data;
    }

    private MtxItrNode getNodeAt(int index) {
        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                if (index == 0) {
                    return dataNode;
                }
                index --;
            }
            current = current.prev;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                int elementHashCode = dataNode.data == null ? 0 : dataNode.data.hashCode();
                hashCode = (31 * hashCode) + elementHashCode;
            }
            current = current.prev;
        }
        return hashCode;
    }

    @Override
    public int indexOf(T element) {
        int idx = 0;
        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                if (dataNode.data.equals(element)) {
                    return idx;
                }
                idx ++;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public void clear() {
        this.head = new MtxItrNode();
        this.head.next = this.head;
        this.head.prev = this.head;
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private MtxItrNode current = head.next;

            @Override
            public boolean hasNext() {
                return current != head;
            }

            @Override
            public T next() {
                T element = ((MtxItrData<T>) this.current).data;
                this.current = this.current.next;
                return element;
            }
        };
    }

    @Override
    public MtxList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > this.size()) {
            throw new IndexOutOfBoundsException(toIndex);
        }

        MtxIntrusiveLinkedList<T> subList = new MtxIntrusiveLinkedList<>();
        MtxItrNode current = this.getNodeAt(fromIndex);
        MtxItrNode end = this.getNodeAt(toIndex);

        while (current != end && current != this.head) {
            subList.add(((MtxItrData<T>) current).data);
            current = current.prev;
        }
        return subList;
    }

    @Override
    public T[] toArray() {
        Object[] array = new Object[this.size()];

        MtxItrNode current = this.head.prev;
        int i = 0;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                array[i++] = dataNode.data;
            }
            current = current.prev;
        }

        return (T[]) array;
    }

    @Override
    public T removeAt(int index) {
        MtxItrNode node = this.getNodeAt(index);
        if (node == null) {
            throw new IndexOutOfBoundsException(index);
        }

        T data = ((MtxItrData<T>) node).data;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
        this.size --;
        return data;
    }

    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size() || this.getNodeAt(index) == null) {
            throw new IndexOutOfBoundsException(index);
        }

        MtxItrData<T> node = ((MtxItrData<T>) this.getNodeAt(index));
        T previousData = node.data;
        node.data = element;
        return previousData;
    }

    @Override
    public boolean removeDuplicates() {
        Set<T> seen = new HashSet<>();
        boolean foundDuplicate = false;

        MtxItrNode current = this.head.prev;
        while (current != this.head) {
            if (current instanceof MtxItrData dataNode) {
                if (seen.contains(dataNode.data)) {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                    this.size --;
                    foundDuplicate = true;
                } else {
                    seen.add((T) dataNode.data);
                }
            }
            current = current.prev;
        }
        return foundDuplicate;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        this.head.next = this.mergeSort(this.head.next, comparator);

        MtxItrNode tail = this.head;
        while (tail.next != this.head) {
            tail.next.prev = tail;
            tail = tail.next;
        }
        tail.next = this.head;
        this.head.prev = tail;
    }

    @Override
    public void rotateLeft(int times) {
        if (size <= 1) {
            return;
        }

        for (int i = 0; i < times; i++) {
            MtxItrNode last = head.prev;
            MtxItrNode beforeLast = last.prev;
            MtxItrNode first = head.next;

            beforeLast.next = head;
            head.prev = beforeLast;

            last.next = first;
            last.prev = head;
            first.prev = last;
            head.next = last;
        }
    }

    @Override
    public void rotateRight(int times) {
        if (size <= 1) {
            return;
        }

        for (int i = 0; i < times; i++) {
            MtxItrNode first = head.next;
            MtxItrNode second = first.next;
            MtxItrNode last = head.prev;

            head.next = second;
            second.prev = head;

            last.next = first;
            first.prev = last;
            first.next = head;
            head.prev = first;
        }
    }

    private MtxItrNode mergeSort(MtxItrNode start, Comparator<T> comparator) {
        if (start == null || start.next == this.head || start == this.head) {
            return start;
        }

        MtxItrNode middle = this.getMiddle(start);
        MtxItrNode nextOfMiddle = middle.next;

        middle.next = this.head;
        nextOfMiddle.prev = this.head;

        MtxItrNode left = this.mergeSort(start, comparator);
        MtxItrNode right = this.mergeSort(nextOfMiddle, comparator);

        return this.merge(left, right, comparator);
    }

    private MtxItrNode merge(MtxItrNode left, MtxItrNode right, Comparator<T> comparator) {
        if (left == this.head) {
            return right;
        }
        if (right == this.head) {
            return left;
        }
        if (comparator.compare(((MtxItrData<T>) left).data, ((MtxItrData<T>) right).data) > 0) {
            left.next = merge(left.next, right, comparator);
            left.next.prev = left;
            left.prev = this.head;
            return left;
        } else {
            right.next = merge(left, right.next, comparator);
            right.next.prev = right;
            right.prev = this.head;
            return right;
        }
    }

    private MtxItrNode getMiddle(MtxItrNode start) {
        if (start == head) return start;
        MtxItrNode fast = start.next;
        MtxItrNode slow = start;

        while (fast != head) {
            fast = fast.next;
            if (fast != head) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        return slow;
    }

    @Override
    public void reverse() {
        if (this.size <= 1) {
            return;
        }

        MtxItrNode current = this.head;
        do {
            MtxItrNode tmp = current.next;
            current.next = current.prev;
            current.prev = tmp;

            current = current.prev;
        } while (current != this.head);
    }

    public boolean isPalindrome() {
        if (this.size() <= 1) {
            return true;
        }

        MtxItrNode left = this.head.next;
        MtxItrNode right = this.head.prev;

        while (left != right && left.prev != right) {
            T leftData = ((MtxItrData<T>) left).data;
            T rightData = ((MtxItrData<T>) right).data;

            if (!Objects.equals(leftData, rightData)) {
                return false;
            }

            left = left.next;
            right = right.prev;
        }

        return true;
    }
}
