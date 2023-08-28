package com.edavalos.mtx.util.grouping;

public class MtxMutableConstraintPair<K, V> extends MtxMutablePair<K, V> {
    @FunctionalInterface
    public interface MtxPairConstraint<R, L> {
        boolean isValueAllowed(R key, L value);
    }
    private static final String ERROR_PAIR_CONSTRAINT_VIOLATED = "Cannot set this %s as it violates this pair's MtxPairConstraint";

    private final MtxPairConstraint<K, V> mtxPairConstraint;

    public MtxMutableConstraintPair(MtxPairConstraint<K, V> constraint) {
        super(null, null);

        this.mtxPairConstraint = constraint;
        if (!mtxPairConstraint.isValueAllowed(super.key, super.value)) {
            throw new IllegalArgumentException(String.format(ERROR_PAIR_CONSTRAINT_VIOLATED, "key and value"));
        }
    }

    public MtxMutableConstraintPair(MtxPairConstraint<K, V> constraint, K key, V value) {
        super(key, value);

        this.mtxPairConstraint = constraint;
        if (!mtxPairConstraint.isValueAllowed(super.key, super.value)) {
            throw new IllegalArgumentException(String.format(ERROR_PAIR_CONSTRAINT_VIOLATED, "key and value"));
        }
    }

    @Override
    public void setKey(K key) {
        if (!mtxPairConstraint.isValueAllowed(key, super.value)) {
            throw new IllegalArgumentException(String.format(ERROR_PAIR_CONSTRAINT_VIOLATED, "key"));
        }
        super.setKey(key);
    }

    @Override
    public void setValue(V value) {
        if (!mtxPairConstraint.isValueAllowed(super.key, value)) {
            throw new IllegalArgumentException(String.format(ERROR_PAIR_CONSTRAINT_VIOLATED, "value"));
        }
        super.setValue(value);
    }
}
