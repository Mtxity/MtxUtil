package com.edavalos.mtx.util.string;

public enum StringFilteringPolicy {
    EXACT_STRICT, // Exact matches are filtered, no matter where they are ("ass -> ***, passed -> p***ed")
    EXACT_ISOLATED, // Exact matches are filtered only if they are surrounded by non-alphanumeric characters ("ass -> ***, passed -> passed)
    PARTIAL_STRICT, // All non-alphanumeric characters are ignored when checking for matches ("a s.s -> *****, p!as!sed -> p!****ed)
    PARTIAL_ISOLATED // All non-alphanumeric characters are ignored, but only between whitespaces ("pas.sed" -> "pas.sed", "p as.s ed" -> p **** ed")
}
