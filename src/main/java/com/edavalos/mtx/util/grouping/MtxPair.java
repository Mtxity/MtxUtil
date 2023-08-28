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

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof MtxPair<?,?> otherPair) {
            return otherPair.key.equals(this.key) &&
                   otherPair.value.equals(this.value);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "<" + this.key.toString() + " : " + this.value.toString() + ">";
    }
}
