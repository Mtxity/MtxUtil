package com.edavalos.mtx.util.grouping;

public abstract class MtxPair<K, V> {
    protected K key;
    protected V value;

    public MtxPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public abstract void setKey(K key);

    public abstract void setValue(V value);
}
