package com.edavalos.mtx.util.grouping.table;

public class ExistingPrimaryKeyException extends UnsupportedOperationException {
    protected static final String ERROR_PRIMARY_KEY_ALREADY_EXISTS = "A row with this primary key ('%s') " +
                                                                     "already exists in this table!";

    ExistingPrimaryKeyException(Object key) {
        super(String.format(ERROR_PRIMARY_KEY_ALREADY_EXISTS, key.toString()));
    }
}
