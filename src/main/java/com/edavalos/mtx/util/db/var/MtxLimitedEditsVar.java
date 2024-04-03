package com.edavalos.mtx.util.db.var;

/**
 * This data structure is identical to MtxVersionedVar, except it only allows its value to be changed n times, after
 * which it becomes immutable. n is specified in the constructor.
 * @param <T>
 */
public class MtxLimitedEditsVar<T> extends MtxVersionedVar<T> {
    protected static final String ERROR_NO_EDITS_LEFT = "This value can only be set %s times, and there are none left!";

    private final int editsAllowed;
    private int editsLeft;

    // ----------------------- Constructors ------------------------

    public MtxLimitedEditsVar(int totalEditsAllowed) {
        this.editsAllowed = totalEditsAllowed;
        this.editsLeft = totalEditsAllowed;
    }

    // ---------------------- Public Methods -----------------------

    @Override
    public T setValue(T newValue) {
        if (newValue == null) {
            throw new NullPointerException(ERROR_NULL);
        }

        if (this.editsLeft == 0) {
            throw new UnsupportedOperationException(String.format(ERROR_NO_EDITS_LEFT, this.editsAllowed));
        }

        this.editsLeft --;
        return super.setValue(newValue);
    }

    public int totalEditsAllowed() {
        return this.editsAllowed;
    }

    public int editsLeft() {
        return this.editsLeft;
    }
}
