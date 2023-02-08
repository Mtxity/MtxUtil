package com.edavalos.mtx.util.list;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class MtxLinkedList<T> {
    private class MtxNode {
        T content;
        MtxNode next;

        MtxNode(T content) {
            this.content = content;
            this.next = null;
        }
    }

    private MtxNode head;
    private int size;

    public MtxLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public MtxLinkedList(T[] initialContents) {
        this();
        for (T element : initialContents) {
            this.add(element);
        }
    }

    public void add(T element) {
        MtxNode newNode = new MtxNode(element);

        if (this.head == null) {
            this.head = newNode;
            this.size ++;
            return;
        }

        MtxNode last = this.head;
        while (last.next != null) {
            last = last.next;
        }

        last.next = newNode;
        this.size ++;
    }

    public boolean remove(T element) {
        if (this.head == null) {
            return false;
        }

        if (this.head.content.equals(element)) {
            this.head = this.head.next;
            this.size --;
            return true;
        }

        MtxNode currentNode = this.head;
        MtxNode previous = null;
        while (currentNode != null && !currentNode.content.equals(element)) {
            previous = currentNode;
            currentNode = currentNode.next;
        }

        if (currentNode != null) {
            previous.next = currentNode.next;
            this.size --;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        MtxNode next = this.head;
        while (next != null) {
            string.append(next.content.toString()).append(", ");
            next = next.next;
        }

        string.append("]");
        return string.toString().replace(", ]", "]");
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public boolean contains(T element) {
        MtxNode next = this.head;
        while (next != null) {
            if (next.content.equals(element)) {
                return true;
            }
            next = next.next;
        }
        return false;
    }

    public int countOccurrences(T element) {
        int count = 0;
        MtxNode next = this.head;
        while (next != null) {
            if (next.content.equals(element)) {
                count ++;
            }
            next = next.next;
        }
        return count;
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index);
        }

        MtxNode next = this.head;
        while (index > 0) {
            next = next.next;
            index --;
        }
        return next.content;
    }

    // TODO: Move this method to MtxList and make it default
    @Override
    public boolean equals(Object o) {
        // TODO: Replace 'MtxLinkedList<?>' with 'MtxList<?>' once interface is done being implemented
        if (o instanceof MtxLinkedList<?> otherList) {
            if (otherList.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!otherList.get(i).equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        if (o instanceof List<?> otherList) {
            if (otherList.size() != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!otherList.get(i).equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        if (o instanceof Object[] array) {
            if (array.length != this.size()) {
                return false;
            }

            for (int i = 0; i < this.size(); i++) {
                if (!array[i].equals(this.get(i))) {
                    return false;
                }
            }

            return true;
        }

        return o.equals(this);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        MtxNode next = this.head;
        while (next != null) {
            int elementHashCode = next.content == null ? 0 : next.content.hashCode();
            hashCode = (31 * hashCode) + elementHashCode;

            next = next.next;
        }
        return hashCode;
    }

    public int indexOf(T element) {
        int idx = 0;
        MtxNode next = this.head;
        while (next != null) {
            if (element.equals(next.content)) {
                return idx;
            }

            next = next.next;
            idx ++;
        }
        return -1;
    }

    // TODO: Move this method to MtxList and make it default
    public boolean containsAll(Collection<T> elements) {
        for (T element : elements) {
            if (!this.contains(element)) {
                return false;
            }
        }
        return true;
    }

    // TODO: Move this method to MtxList and make it default
    public boolean containsAll(T[] elements) {
        return this.containsAll(Arrays.asList(elements));
    }

    // TODO: Move this method to MtxList and make it default
    public void addAll(Collection<T> elements) {
        for (T element : elements) {
            this.add(element);
        }
    }

    // TODO: Move this method to MtxList and make it default
    public void addAll(T[] elements) {
        this.addAll(Arrays.asList(elements));
    }

    // TODO: Move this method to MtxList and make it default
    public boolean removeAll(Collection<T> elements) {
        for (T element : elements) {
            if (!this.remove(element)) {
                return false;
            }
        }
        return true;
    }

    // TODO: Move this method to MtxList and make it default
    public boolean removeAll(T[] elements) {
        return this.removeAll(Arrays.asList(elements));
    }

    public void clear() {
        this.head = null;
        this.size = 0;
    }
}
