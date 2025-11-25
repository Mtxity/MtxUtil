package com.edavalos.mtx.util.list;

import com.edavalos.mtx.util.list.line.MtxStack;

import java.util.Comparator;
import java.util.Iterator;

public final class MtxLinkedList<T> implements MtxList<T>, Iterable<T> {
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

    @Override
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

    @Override
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

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
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

    @Override
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

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private MtxNode next = head;

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public T next() {
                T element = this.next.content;
                this.next = this.next.next;
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

        MtxList<T> newList = new MtxLinkedList<>();
        int idx = 0;
        MtxNode next = this.head;
        while (next != null) {
            if (idx < fromIndex) {
                next = next.next;
                idx ++;
                continue;
            }
            if (idx >= toIndex) {
                break;
            }

            newList.add(next.content);
            next = next.next;
            idx ++;
        }

        return newList;
    }

    @Override
    public T[] toArray() {
        Object[] array = new Object[this.size()];

        int idx = 0;
        MtxNode next = this.head;
        while (next != null) {
            array[idx] = next.content;
            next = next.next;
            idx ++;
        }

        return (T[]) array;
    }

    @Override
    public T removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        if (index == 0) {
            MtxNode head = this.head;
            this.head = head.next;
            this.size --;
            return head.content;
        }

        int idx = 0;
        MtxNode currentNode = this.head;
        MtxNode previous = null;
        while (idx < index) {
            previous = currentNode;
            currentNode = currentNode.next;
            idx ++;
        }

        T element = currentNode.content;
        previous.next = currentNode.next;
        this.size --;

        return element;
    }

    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        T content = null;
        if (index == 0) {
            if (this.head != null) {
                content = this.head.content;
                this.head.content = element;
            }
            return content;
        }

        int idx = 1;
        MtxNode current = this.head.next;
        while (current != null) {
            if (idx == index) {
                content = current.content;
                current.content = element;
                break;
            }
            current = current.next;
            idx ++;
        }

        return content;
    }

    // Based on https://www.javatpoint.com/program-to-sort-the-elements-of-the-singly-linked-list
    @Override
    public void sort(Comparator<T> comparator) {
        MtxNode current = this.head;
        MtxNode index;

        while (current != null) {
            index = current.next;
            while (index != null) {
                if (comparator.compare(current.content, index.content) > 0) {
                    T temp = current.content;
                    current.content = index.content;
                    index.content = temp;
                }
                index = index.next;
            }
            current = current.next;
        }
    }

    // Based on https://www.javatpoint.com/java-program-to-remove-duplicate-elements-from-a-singly-linked-list
    @Override
    public boolean removeDuplicates() {
        MtxNode current = this.head;
        MtxNode index;
        boolean foundDuplicate = false;

        while (current != null) {
            MtxNode temp = current;
            index = current.next;

            while (index != null) {
                if (current.content.equals(index.content)) {
                    temp.next = index.next;
                    foundDuplicate = true;
                    this.size --;
                } else {
                    temp = index;
                }
                index = index.next;
            }
            current = current.next;
        }
        return foundDuplicate;
    }

    @Override
    public void rotateLeft(int times) {
        if (this.size() <= 1) {
            return;
        }

        for (int i = 0; i < times; i++) {
            MtxNode currentHead = this.head;
            while (currentHead.next != null) {
                currentHead = currentHead.next;
            }
            currentHead.next = this.head;
            currentHead = currentHead.next;
            this.head = this.head.next;
            currentHead.next = null;
        }
    }

    @Override
    public void rotateRight(int times) {
        if (this.size() <= 1) {
            return;
        }

        for (int i = 0; i < times; i++) {
            MtxNode currentHead = this.head;
            while (currentHead.next.next != null) {
                currentHead = currentHead.next;
            }
            MtxNode end = currentHead.next;
            currentHead.next = null;
            end.next = this.head;
            this.head = end;
        }
    }

    @Override
    public void reverse() {
        this.head = this.reverseList(this.head);
    }

    private MtxNode reverseList(MtxNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        MtxNode newHead = reverseList(head.next);

        head.next.next = head;
        head.next = null;

        return newHead;
    }

    public boolean isPalindrome() {
        return isPalindrome(this.head);
    }

    private boolean isPalindrome(MtxNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        MtxStack<T> vals = new MtxStack<>();
        MtxNode slow = head;
        MtxNode fast = head;
        while (fast != null && fast.next != null) {
            vals.push(slow.content);
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast != null) {
            slow = slow.next;
        }
        while (slow != null) {
            if (slow.content != vals.pop()) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }
}
