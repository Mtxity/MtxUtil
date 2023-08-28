package com.edavalos.mtx.util.grouping;

public class MtxMutablePair<K, V> extends MtxPair<K, V> {
    private boolean hasChanged;

    public MtxMutablePair(K key, V value) {
        super(key, value);

        this.hasChanged = false;
    }

    @Override
    public void setKey(K key) {
        super.key = key;

        this.hasChanged = true;
    }

    @Override
    public void setValue(V value) {
        super.value = value;

        this.hasChanged = true;
    }

    public boolean hasChanged() {
        return this.hasChanged;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MtxMutablePair<?, ?> otherPair) {
            if (otherPair.hasChanged == this.hasChanged) {
                return super.equals(o);
            }
        }
        return false;
    }
}
