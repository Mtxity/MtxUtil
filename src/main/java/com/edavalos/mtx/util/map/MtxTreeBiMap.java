package com.edavalos.mtx.util.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class MtxTreeBiMap<K, V> implements MtxBiMap<K, V> {
    private static final String DUPLICATE_VALUES_ERROR_MSG = "MtxBiMap cannot have duplicate values.";
    private static final String ALREADY_AN_INVERSE_ERROR_MSG = "This MtxBiMap is already an inverse of another one.";
    private static final String CANNOT_EDIT_INVERSE_ERROR_MSG = "You cannot modify an inverse map. Modifications " +
                                                                "must be done to the original";

    private final TreeMap<K, V> internalMap;
    private final MtxTreeBiMap<V, K> inverseMap;
    private final boolean isInverse;

    public MtxTreeBiMap() {
        this.internalMap = new TreeMap<>();
        this.inverseMap = new MtxTreeBiMap<>(true);
        this.isInverse = false;
    }

    private MtxTreeBiMap(boolean isInverse) {
        this.internalMap = new TreeMap<>();
        this.inverseMap = null;
        this.isInverse = isInverse;
    }

    @Override
    public int size() {
        return this.internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }

        return this.internalMap.containsKey(key);
    }

    @Override
    public boolean containsValue(V value) {
        if (value == null) {
            return false;
        }

        return this.internalMap.containsValue(value);
    }

    @Override
    public V get(K key) {
        return this.internalMap.get(key);
    }

    @Override
    public MtxBiMap<V, K> inverse() {
        if (this.isInverse) {
            throw new UnsupportedOperationException(ALREADY_AN_INVERSE_ERROR_MSG);
        }

        return this.inverseMap;
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        if (!this.isInverse) {
            if (this.internalMap.containsValue(value)) {
                throw new IllegalArgumentException(DUPLICATE_VALUES_ERROR_MSG);
            }

            assert this.inverseMap != null;
            this.inverseMap.put(value, key, true);

            return this.internalMap.put(key, value);
        } else {
            throw new UnsupportedOperationException(CANNOT_EDIT_INVERSE_ERROR_MSG);
        }
    }

    private V put(K key, V value, boolean inverseOverride) {
        assert inverseOverride;

        assert this.isInverse;
        assert this.inverseMap == null;

        return this.internalMap.put(key, value);
    }

    @Override
    public V remove(K key) {
        if (!this.isInverse) {
            assert this.inverseMap != null;
            this.inverseMap.remove(this.internalMap.get(key), true);

            return this.internalMap.remove(key);
        } else {
            throw new UnsupportedOperationException(CANNOT_EDIT_INVERSE_ERROR_MSG);
        }
    }

    private V remove(K key, boolean inverseOverride) {
        assert inverseOverride;

        assert this.isInverse;
        assert this.inverseMap == null;

        return this.internalMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m.values().size() != new HashSet<V>(m.values()).size()) {
            throw new IllegalArgumentException(DUPLICATE_VALUES_ERROR_MSG);
        }

        if (!this.isInverse) {
            assert this.inverseMap != null;
            for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
                if (this.inverseMap.containsKey(entry.getValue())) {
                    throw new IllegalArgumentException(DUPLICATE_VALUES_ERROR_MSG);
                }
                this.inverseMap.put(entry.getValue(), entry.getKey(), true);
            }

            this.internalMap.putAll(m);
        } else {
            throw new UnsupportedOperationException(CANNOT_EDIT_INVERSE_ERROR_MSG);
        }
    }

    @Override
    public void clear() {
        if (!this.isInverse) {
            assert this.inverseMap != null;
            this.inverseMap.clear(true);

            this.internalMap.clear();
        } else {
            throw new UnsupportedOperationException(CANNOT_EDIT_INVERSE_ERROR_MSG);
        }
    }

    private void clear(boolean inverseOverride) {
        assert inverseOverride;

        assert this.isInverse;
        assert this.inverseMap == null;

        this.internalMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.internalMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return this.internalMap.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return this.internalMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MtxTreeBiMap otherBiMap)) {
            return false;
        }
        return this.internalMap.equals(otherBiMap.internalMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalMap, isInverse);
    }

    @Override
    public String toString() {
        return this.internalMap.toString();
    }
}
