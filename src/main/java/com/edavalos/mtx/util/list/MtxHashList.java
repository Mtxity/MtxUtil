package com.edavalos.mtx.util.list;

import java.math.BigInteger;
import java.util.HashMap;

public final class MtxHashList<T> {
    private static String NULL_SPOT_MARKER = ";___-___-___;";

    private HashMap<BigInteger, T> content;
    private BigInteger nextSpot;

    public MtxHashList() {
        this.content = new HashMap<>();
        this.nextSpot = BigInteger.valueOf(0);
    }

    public MtxHashList(T[] initialContents) {
        this.content = new HashMap<>();
        int idx = 0;
        for (T element : initialContents) {
            this.content.put(BigInteger.valueOf(idx), element);
            idx ++;
        }
        this.nextSpot = BigInteger.valueOf(idx);
    }

    public void add(T element) {
        this.content.put(this.nextSpot, element);
        this.nextSpot = this.nextSpot.add(BigInteger.valueOf(1));
    }

    public boolean remove(T element) {
        if (!this.content.containsValue(element)) {
            return false;
        }

        for (BigInteger key : this.content.keySet()) {
            if (this.content.get(key).equals(element)) {
                this.content.replace(key, (T) NULL_SPOT_MARKER);
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        for (BigInteger key : this.content.keySet()) {
            T element = this.content.get(key);
            if (this.isNullSpot(element)) {
                continue;
            }
            string.append(element.toString()).append(", ");
        }

        string.append("]");
        return string.toString().replace(", ]", "]");
    }

    public int size() {
        return this.content.size();
    }

    public boolean isEmpty() {
        return this.content.isEmpty();
    }

    private boolean isNullSpot(T possiblyNullSpotElement) {
        if (possiblyNullSpotElement instanceof String nullSpotString) {
            return nullSpotString.equals(NULL_SPOT_MARKER);
        }
        return false;
    }
}
