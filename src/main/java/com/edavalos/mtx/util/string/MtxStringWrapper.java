package com.edavalos.mtx.util.string;

public class MtxStringWrapper {
    private static final String EMPTY_STRING = "";
    private String wrappedString;

    public MtxStringWrapper(String wrappedString) {
        this.wrappedString = wrappedString;
    }

    public MtxStringWrapper() {
        this.wrappedString = EMPTY_STRING;
    }

    public String unwrap() {
        return this.wrappedString;
    }

    @Override
    public String toString() {
        return this.unwrap();
    }

    public void set(String newString) {
        this.wrappedString = newString;
    }

    public void add(String toAdd) {
        this.wrappedString += toAdd;
    }

    // @TODO: Add remove()

    public boolean clear() {
        if (this.wrappedString.isEmpty()) {
            return false;
        }
        this.wrappedString = EMPTY_STRING;
        return true;
    }

    public boolean isEmpty() {
        return this.wrappedString.equals(EMPTY_STRING);
    }
}
