package com.edavalos.mtx.util.graph;

import java.util.Objects;

public record MtxVertex(String label) {

    @Override
    public boolean equals(Object o) {
        if (o instanceof MtxVertex v) {
            return this.label.equals(v.label);
        }
        return o.equals(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
