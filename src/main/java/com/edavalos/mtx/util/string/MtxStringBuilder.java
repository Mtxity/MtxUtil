package com.edavalos.mtx.util.string;

import java.util.ArrayList;

public class MtxStringBuilder {
    private final ArrayList<String> parts;


    // ----------------------- Constructors ------------------------

    public MtxStringBuilder() {
        this.parts = new ArrayList<>();
    }

    public MtxStringBuilder(String firstString) {
        this.parts = new ArrayList<>();
        this.parts.add(firstString);
    }


    // ---------------------- Public Methods -----------------------

    public MtxStringBuilder(Object firstValue) {
        this.parts = new ArrayList<>();
        this.parts.add(firstValue.toString());
    }

    public MtxStringBuilder append(String nextString) {
        this.parts.add(nextString);
        return this;
    }


    // ------------------ Private Helper Methods -------------------

    private String temp() {
        return "";
    }
}
