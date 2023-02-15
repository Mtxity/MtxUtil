package com.edavalos.mtx.util.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Disclaimer: This implementation of MtxList is purposefully stupid
 * */
public final class MtxStringList<T> implements MtxList<T>, Iterable<T> {
    public interface MtxStringDecoder<E> {
        E fromString(String stringRepresentation);

        default String convertToString(E element) {
            return element.toString();
        }
    }

    private static final String DELIMITER = ", ";
    private static final String EMPTY_STRING = "";

    private final MtxStringDecoder<T> mtxStringDecoder;
    private final Class<T> classType;
    private String content;
    private int size;

    public MtxStringList(MtxStringDecoder<T> stringDecoder, Class<T> classType) {
        this.mtxStringDecoder = stringDecoder;
        this.classType = classType;
        this.content = EMPTY_STRING;
        this.size = 0;
    }

    public MtxStringList(MtxStringDecoder<T> stringDecoder, Class<T> classType, T[] initialContents) {
        this.mtxStringDecoder = stringDecoder;
        this.classType = classType;
        this.content = EMPTY_STRING;
        this.size = 0;
        this.addAll(initialContents);
    }

    public void add(T element) {
        if (this.size != 0) {
            this.content += DELIMITER;
        }

        String elementAsString;
        try {
            elementAsString = this.mtxStringDecoder.convertToString(element);
        } catch (Exception e) {
            throw new IllegalArgumentException(this.classType.getName() + " is not serializable!");
        }

        if (elementAsString.contains(DELIMITER)) {
            throw new IllegalArgumentException(
                    "A comma followed by a space (', ') is MtxStringList's delimiter and therefore is not allowed " +
                            "to be in an element of this MtxList"
            );
        }

        this.content += elementAsString;
        this.size ++;
    }

    public void addAll(T[] elements) {
        for (T element : elements) {
            String elementAsString;
            try {
                elementAsString = this.mtxStringDecoder.convertToString(element);
            } catch (Exception e) {
                throw new IllegalArgumentException(this.classType.getName() + " is not serializable!");
            }

            if (elementAsString.contains(DELIMITER)) {
                throw new IllegalArgumentException(
                        "A comma followed by a space (', ') is MtxStringList's delimiter and therefore is not allowed " +
                                "to be in an element of this MtxList"
                );
            }
        }

        if (this.size != 0) {
            this.content += DELIMITER;
        }

        StringBuilder newElements = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                newElements.append(DELIMITER);
            }
            newElements.append(this.mtxStringDecoder.convertToString(elements[i]));
        }

        this.content += newElements.toString();
        this.size += elements.length;
    }

    @Override
    public boolean remove(T element) {
        String toRemove = this.mtxStringDecoder.convertToString(element);
        if (!this.content.contains(toRemove)) {
            return false;
        }

        if (this.size == 1) {
            this.content = this.content.replace(toRemove, "");
        } else if (this.content.startsWith(toRemove)) {
            this.content = this.content.replace(toRemove + DELIMITER, "");
        } else {
            this.content = this.content.replace(DELIMITER + toRemove, "");
        }
        this.size --;
        return true;
    }

    @Override
    public boolean removeDuplicates() {
        String[] elementsAsStrings = this.content.split(DELIMITER);
        List<String> newContents = new ArrayList<>();
        boolean foundDuplicate = false;

        for (int i = 0; i < this.size(); i++) {
            if (newContents.contains(elementsAsStrings[i])) {
                foundDuplicate = true;
            } else {
                newContents.add(elementsAsStrings[i]);
            }
        }

        if (foundDuplicate) {
            StringBuilder newElements = new StringBuilder();
            for (int i = 0; i < newContents.size(); i++) {
                if (i != 0) {
                    newElements.append(DELIMITER);
                }
                newElements.append(newContents.get(i));
            }
            this.content = newElements.toString();
            this.size = newContents.size();
            return true;
        }
        return false;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        MtxArrayList<T> mtxArrayList = new MtxArrayList<>(this.toArray());
        mtxArrayList.sort(comparator);

        this.content = "";
        this.size = 0;
        this.addAll(mtxArrayList.toArray());
        this.size = mtxArrayList.size();
    }

    @Override
    public void clear() {
        this.content = EMPTY_STRING;
        this.size = 0;
    }

    @Override
    public String toString() {
        return "[" + this.content + "]";
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        return this.mtxStringDecoder.fromString(this.content.split(DELIMITER)[index]);
    }

    @Override
    public T set(int index, T element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        T foundElement = this.get(index);
        String[] newStrings = this.content.split(this.mtxStringDecoder.convertToString(foundElement));
        this.content = newStrings[0] + this.mtxStringDecoder.convertToString(element);
        if (newStrings.length > 1) {
            this.content += newStrings[1];
        }
        return foundElement;
    }

    @Override
    public T removeAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size()) {
            throw new IndexOutOfBoundsException(index);
        }

        T foundElement = this.get(index);
        if (this.size() == 1) {
            this.content = "";
            this.size = 0;
            return foundElement;
        }
        String foundElementString = this.mtxStringDecoder.convertToString(foundElement);

        if (index == 0) {
            this.content = this.content.substring(foundElementString.length() + 2);
            this.size --;
            return foundElement;
        }

        if (index == this.size() - 1) {
            String[] strings = this.content.split(
                    DELIMITER + foundElementString
            );
            this.content = strings[0];
            this.size --;
            return foundElement;
        }

        String[] strings = this.content.split(
                DELIMITER + foundElementString
        );
        this.content = strings[0] + strings[1];
        this.size --;
        return foundElement;
    }

    @Override
    public int indexOf(T element) {
        String[] elementAsStrings = this.content.split(DELIMITER);
        for (int i = 0; i < elementAsStrings.length; i++) {
            if (elementAsStrings[i].equals(this.mtxStringDecoder.convertToString(element))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int countOccurrences(T element) {
        int count = 0;
        for (String elementString : this.content.split(DELIMITER)) {
            if (element.toString().equals(elementString)) {
                count ++;
            }
        }
        return count;
    }

    @Override
    public MtxList<T> subList(int fromIndex, int toIndex) throws IndexOutOfBoundsException {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException(fromIndex);
        }
        if (toIndex > this.size()) {
            throw new IndexOutOfBoundsException(toIndex);
        }

        List<String> newContents = new ArrayList<>();
        for (T element : this.toArray()) {
            newContents.add(this.mtxStringDecoder.convertToString(element));
        }
        MtxList<T> newContents2 = new MtxStringList<>(this.mtxStringDecoder, this.classType);
        for (int j = fromIndex; j < toIndex; j++) {
            newContents2.add(this.mtxStringDecoder.fromString(newContents.get(j)));
        }
        return newContents2;
    }

    public T[] toArray() {
        List<T> elements = new ArrayList<>();
        for (String piece : this.content.split(Pattern.quote(DELIMITER))) {
            if (piece.length() < 1) {
                continue;
            }
            T element = this.mtxStringDecoder.fromString(piece);
            elements.add(element);
        }
        return elements.toArray(((T[]) Array.newInstance(classType, this.size)));
    }

    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(T element) {
        return this.content.contains(element.toString());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final T[] array = toArray();
            private int idx = 0;

            @Override
            public boolean hasNext() {
                return this.idx < size();
            }

            @Override
            public T next() {
                return array[this.idx++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        return this.equalsTo(o);
    }
}
