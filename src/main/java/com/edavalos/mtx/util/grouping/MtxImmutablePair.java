package com.edavalos.mtx.util.grouping;

public class MtxImmutablePair<K, V> extends MtxPair<K, V> {
    private static final String ERROR_IMMUTABLE_PAIR = "Cannot modify %s of MtxImmutablePair! Either create a new one or use MtxMutablePair.";

    public MtxImmutablePair(K key, V value) {
        super(key, value);
    }

    @Override
    public void setKey(K key) {
        throw new UnsupportedOperationException(String.format(ERROR_IMMUTABLE_PAIR, "key"));
    }

    @Override
    public void setValue(V value) {
        throw new UnsupportedOperationException(String.format(ERROR_IMMUTABLE_PAIR, "value"));
    }
}
