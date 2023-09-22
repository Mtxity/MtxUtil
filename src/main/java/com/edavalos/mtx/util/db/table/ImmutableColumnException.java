package com.edavalos.mtx.util.db.table;

public class ImmutableColumnException extends UnsupportedOperationException {
    protected static final String ERROR_CANNOT_EDIT_PRIMARY_KEY = "Primary keys cannot be changed! " +
                                                                  "Before: '%s', After: '%s'";

    ImmutableColumnException(Object before, Object after) {
        super(String.format(ERROR_CANNOT_EDIT_PRIMARY_KEY, before.toString(), after.toString()));
    }
}
