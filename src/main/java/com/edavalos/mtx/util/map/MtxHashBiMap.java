package com.edavalos.mtx.util.map;

import java.util.*;

public class MtxHashBiMap<K, V> implements MtxBiMap<K, V> {
    private static final String DUPLICATE_VALUES_ERROR_MSG = "MtxBiMap cannot have duplicate values.";

    private final HashMap<K, V> internalMap;

    public MtxHashBiMap() {
        this.internalMap = new HashMap<>();
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
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }

        if (this.internalMap.containsValue(value)) {
            throw new IllegalArgumentException(DUPLICATE_VALUES_ERROR_MSG);
        }

        return this.internalMap.put(key, value);
    }

    @Override
    public V remove(K key) {
        return this.internalMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (m.values().size() != new HashSet<V>(m.values()).size()) {
            throw new IllegalArgumentException(DUPLICATE_VALUES_ERROR_MSG);
        }

        this.internalMap.putAll(m);
    }

    @Override
    public void clear() {
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
}
