package com.edavalos.mtx.util.grouping;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * This data structure holds exactly one field of any type. It also holds a list of every value this field has ever had
 * assigned to it. It is used to keep a history of changes made to a variable.
 * @param <T>
 */
public class MtxVersionedVar<T> {
    protected static final String ERROR_NULL = "New value cannot be null!";

    private final Stack<T> varStack;

    // ----------------------- Constructors ------------------------

    public MtxVersionedVar(T firstValue) {
        this.varStack = new Stack<>();
        this.varStack.push(firstValue);
    }

    public MtxVersionedVar() {
        this.varStack = new Stack<>();
    }

    // ---------------------- Public Methods -----------------------

    public T getValue() {
        if (this.varStack.isEmpty()) {
            return null;
        }

        return this.varStack.peek();
    }

    public T getValueAtVersion(int numberOfVersionsAgo) {
        if (numberOfVersionsAgo < 0) {
            throw new IndexOutOfBoundsException(numberOfVersionsAgo);
        }

        int idx = this.varStack.size() - numberOfVersionsAgo - 1;
        if (idx < 0) {
            return null;
        }

        return this.varStack.get(idx);
    }

    public T setValue(T newValue) {
        if (newValue == null) {
            throw new NullPointerException(ERROR_NULL);
        }

        T mostRecentValue = this.getValue();
        this.varStack.push(newValue);
        return mostRecentValue;
    }

    public int totalVersions() {
        return this.varStack.size();
    }

    public Collection<T> getAllVersions() {
        return List.copyOf(this.varStack);
    }

    @Override
    public String toString() {
        String varStackString = this.varStack.toString();
        if (!this.varStack.isEmpty()) {
            varStackString += " -> " + this.varStack.peek().toString();
        }
        return varStackString;
    }
}
