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
}
