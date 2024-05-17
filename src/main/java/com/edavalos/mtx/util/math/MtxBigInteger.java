package com.edavalos.mtx.util.math;

import java.util.Stack;

public class MtxBigInteger {
    private final Stack<Integer> intChain;

    public MtxBigInteger(int value) {
        this.intChain = new Stack<>();
        this.intChain.push(value);
    }

    public MtxBigInteger() {
        this(0);
    }
}
