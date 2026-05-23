package com.edavalos.mtx.util.math.collection;

import com.edavalos.mtx.util.db.var.MtxOptionalVar;

import java.util.Collection;
import java.util.List;
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
        if (item1 == null || item2 == null) {
            return MtxOptionalVar.empty();
        }

        int index = nextRandomInt(2);
        return index == 1 ? MtxOptionalVar.ofNullable(item2) : MtxOptionalVar.ofNullable(item1);
    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3, T item4) {
        if (item1 == null || item2 == null || item3 == null || item4 == null) {
            return MtxOptionalVar.empty();
        }

        MtxOptionalVar<T> result1 = pickRandom(item1, item2);
        MtxOptionalVar<T> result2 = pickRandom(item3, item4);
        return pickRandom(result1.getValue(), result2.getValue());
    }

    public static <T> MtxOptionalVar<T> pickRandom(T item1, T item2, T item3, T item4, T item5, T item6) {
        if (item1 == null || item2 == null || item3 == null || item4 == null || item5 == null || item6 == null) {
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
