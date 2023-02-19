package com.edavalos.mtx.util.network;

// From: https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
public enum RequestMethod {
    // Commonly used methods
    GET,
    POST,
    PUT,
    DELETE,

    // Lesser known methods
    PATCH,
    HEAD,
    CONNECT,
    OPTIONS,
    TRACE,

    // When none is specified
    UNKNOWN
}
