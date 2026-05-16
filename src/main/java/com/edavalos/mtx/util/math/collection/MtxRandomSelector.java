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

        int index = ThreadLocalRandom.current().nextInt(collection.size());

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

        int index = ThreadLocalRandom.current().nextInt(array.length);

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

        int index = ThreadLocalRandom.current().nextInt(2);
        return index == 1 ? MtxOptionalVar.ofNullable(item2) : MtxOptionalVar.ofNullable(item1);
    }
}
