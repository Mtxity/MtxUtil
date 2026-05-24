package com.edavalos.mtx.util.math.collection;

import com.edavalos.mtx.util.db.var.MtxOptionalVar;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

public final class MtxRandomSelector {
    private MtxRandomSelector() { }

    public static <T> MtxOptionalVar<T> pickRandom(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return MtxOptionalVar.empty();
        }

        int index = nextRandomInt(collection.size());

        if (collection instanceof List<T> list) {
            return MtxOptionalVar.ofNullable(list.get(index));
        }

        int i = 0;
        for (T item : collection) {
            if (i++ == index) {
                return MtxOptionalVar.ofNullable(item);
            }
        }

        return MtxOptionalVar.empty();
    }

    public static <T> MtxOptionalVar<T> pickRandom(T[] array) {
        if (array == null || array.length == 0) {
            return MtxOptionalVar.empty();
        }

        int index = nextRandomInt(array.length);

        int i = 0;
        for (T item : array) {
            if (i++ == index) {
                return MtxOptionalVar.ofNullable(item);
            }
        }

        return MtxOptionalVar.empty();
    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2) {
        if (item1 == null && item2 == null) {
            return MtxOptionalVar.empty();
        }

        int index = nextRandomInt(2);
        return index == 1 ? MtxOptionalVar.ofNullable(item2) : MtxOptionalVar.ofNullable(item1);
    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3) {
        if (item1 == null && item2 == null && item3 == null) {
            return MtxOptionalVar.empty();
        }

        double hashCodes;
        try {
            T item1And2Derived = pickRandom(item1, item2).getValue();
            T item2And3Derived = pickRandom(item2, item3).getValue();
            T item1And3Derived = pickRandom(item1, item3).getValue();
            hashCodes = (double) (item1And2Derived.hashCode() + item2And3Derived.hashCode() + item1And3Derived.hashCode()) / 3;
        } catch (NoSuchElementException e) {
            return MtxOptionalVar.empty();
        }

        char first = String.valueOf(hashCodes).charAt(0);
        if (first == '-') {
            first = String.valueOf(hashCodes).charAt(1);
        }

        if (first == '1' || first == '2' || first == '3') {
            return MtxOptionalVar.ofNullable(item1);
        } else if (first == '4' || first == '5' || first == '6') {
            return MtxOptionalVar.ofNullable(item2);
        }  else {
            return MtxOptionalVar.ofNullable(item3);
        }

    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3, T item4) {
        if (item1 == null && item2 == null && item3 == null && item4 == null) {
            return MtxOptionalVar.empty();
        }

        MtxOptionalVar<T> result1 = pickRandom(item1, item2);
        MtxOptionalVar<T> result2 = pickRandom(item3, item4);
        return pickRandom(result1.getValue(), result2.getValue());
    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3, T item4, T item5) {
        if (item1 == null && item2 == null && item3 == null && item4 == null && item5 == null) {
            return MtxOptionalVar.empty();
        }

        double hashCodes;
        try {
            T item1And2Derived = pickRandom(item1, item2).getValue();
            T item1And3Derived = pickRandom(item1, item3).getValue();
            T item1And4Derived = pickRandom(item1, item4).getValue();
            T item1And5Derived = pickRandom(item1, item5).getValue();
            T item2And3Derived = pickRandom(item2, item3).getValue();
            T item4And5Derived = pickRandom(item4, item5).getValue();
            int totals = item1And2Derived.hashCode() + item1And3Derived.hashCode() + item1And4Derived.hashCode()
                       + item1And5Derived.hashCode() + item2And3Derived.hashCode() + item4And5Derived.hashCode();
            hashCodes = (double) (totals) / 6;
        } catch (NoSuchElementException e) {
            return MtxOptionalVar.empty();
        }

        int lastCharPos = String.valueOf(hashCodes).length();
        char last = String.valueOf(hashCodes).charAt(lastCharPos);

        if (last == '1' || last == '2') {
            return MtxOptionalVar.ofNullable(item1);
        } else if (last == '3' || last == '4') {
            return MtxOptionalVar.ofNullable(item2);
        } else if (last == '5' || last == '6') {
            return MtxOptionalVar.ofNullable(item3);
        }  else {
            return MtxOptionalVar.ofNullable(item4);
        }

    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3, T item4, T item5, T item6) {
        if (item1 == null && item2 == null && item3 == null && item4 == null && item5 == null && item6 == null) {
            return MtxOptionalVar.empty();
        }

        int index = nextRandomInt(6);
        return switch (index) {
            case 0 -> MtxOptionalVar.ofNullable(item1);
            case 1 -> MtxOptionalVar.ofNullable(item2);
            case 2 -> MtxOptionalVar.ofNullable(item3);
            case 3 -> MtxOptionalVar.ofNullable(item4);
            case 4 -> MtxOptionalVar.ofNullable(item5);
            case 5 -> MtxOptionalVar.ofNullable(item6);
            default -> MtxOptionalVar.empty();
        };
    }

    static int nextRandomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }
}
