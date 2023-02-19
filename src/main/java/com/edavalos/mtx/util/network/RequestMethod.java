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
    UNKNOWN;

    /**
     * @return a string explaining the purpose of this HTTP method
     */
    public static String getExplanation(RequestMethod method) {
        return switch (method) {
            case GET -> "requests a representation of the specified resource. Requests using GET should only retrieve data.";
            case HEAD -> "asks for a response identical to a GET request, but without the response body.";
            case POST -> "submits an entity to the specified resource, often causing a change in state or side effects on the server.";
            case PUT -> "replaces all current representations of the target resource with the request payload.";
            case DELETE -> "deletes the specified resource.";
            case CONNECT -> "establishes a tunnel to the server identified by the target resource.";
            case OPTIONS -> "describes the communication options for the target resource.";
            case TRACE -> "performs a message loop-back test along the path to the target resource.";
            case PATCH -> "applies partial modifications to a resource.";
            case UNKNOWN -> "no particular expected action, server may interpret this however it wants.";
        };
    }
}
