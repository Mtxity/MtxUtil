package com.edavalos.mtx.util.db.var;

/**
 * This data structure is a wrapper for any object that should only be mutable when allowed to be. That is, when
 * unlocked. Locking this MtxLockingVar prevents any assignment or reassignment to its inner value. Unlocking it allows
 * the inner value to be set or reset. The inner value can always be accessed and modified, however. (For example, a
 * list or array will still be editable even when locked. Locking this MtxLockingVar would only prevent a new collection
 * from being assigned to it.)
 * <p> </p>
 * This is useful in concurrency problems such as distributed systems sharing one resource.
 * @param <T> Object type to store
 */
public final class MtxLockingVar<T> {
    private T value;
    private boolean isLocked;

    // ----------------------- Constructors ------------------------

    /**
     * Creates a new MtxLockingVar of type T
     * @param value starting value
     * @param isLocked whether MtxLockingVar should start out locked or unlocked
     */
    public MtxLockingVar(T value, boolean isLocked) {
        this.value = value;
        this.isLocked = isLocked;
    }

    /**
     * Creates a new unlocked MtxLockingVar of type T
     * @param value starting value
     */
    public MtxLockingVar(T value) {
        this(value, false);
    }

    /**
     * Creates a new unlocked MtxLockingVar of type T with a null value
     */
    public MtxLockingVar() {
        this(null, false);
    }

    // ---------------------- Public Methods -----------------------

    /**
     * Sets the value in this MtxLockingVar if it is currently unlocked
     * @param newValue new value to be stored in this MtxLockingVar
     * @return true if new value was set, false if this MtxLockingVar is locked
     */
    public boolean set(T newValue) {
        if (this.isLocked) {
            return false;
        } else {
            this.value = newValue;
            return true;
        }
    }

    /**
     * Gets the value stored in this MtxLockingVar
     * @return value held by MtxLockingVar. May be null if value was set to null or has not been set.
     */
    public T get() {
        return this.value;
    }

    /**
     * Prevents the value in this MtxLockingVar from being set or replaced
     * @return true if this MtxLockingVar was previously unlocked and is now locked, false if it was already locked
     */
    public boolean lock() {
        if (this.isLocked) {
            return false;
        } else {
            this.isLocked = true;
            return true;
        }
    }

    /**
     * Allows the value in this MtxLockingVar to be set or replaced
     * @return true if this MtxLockingVar was previously locked and is now unlocked, false if it was already unlocked
     */
    public boolean unlock() {
        if (this.isLocked) {
            this.isLocked = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if this MtxLockingVar currently allows reassignment of it's value
     * @return true if this MtxLockingVar is locked, false if it is not
     */
    public boolean isLocked() {
        return this.isLocked;
    }
}
