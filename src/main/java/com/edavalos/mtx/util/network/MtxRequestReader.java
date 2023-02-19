package com.edavalos.mtx.util.network;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class MtxRequestReader {
    private static final int METHOD_IDX = 0;
    private static final int QUERY_IDX = 1;
    private static final int HTTP_VER_IDX = 2;
    private static final String METHOD_TAG = "method:";

    private final RequestMethod requestMethod;
    private final String httpVersion;
    private final String[] fixedParams;
    private final Map<String, String> queryParams;
    private final boolean hasQueryParams;

    //sample: "GET /test/14x%20d/twelve?key1=value1&key2=value2 HTTP/1.1"
    public MtxRequestReader(String requestLine) {
        String[] components = (METHOD_TAG + requestLine).split(" ");
        components[QUERY_IDX] = components[QUERY_IDX].replaceAll(Pattern.quote("%20"), " ");
        this.requestMethod = this.getRequestMethodEnum(components[METHOD_IDX]);
        this.httpVersion = components[HTTP_VER_IDX];

        // Has query params
        if (components[QUERY_IDX].contains("?")) {
            String[] params = components[QUERY_IDX].split(Pattern.quote("?"));
            this.fixedParams = params[0].split(Pattern.quote("/"));
            this.queryParams = this.parseQueryParams(params[1]);
            this.hasQueryParams = true;

        // Only fixed params
        } else {
            this.fixedParams = components[QUERY_IDX].split(Pattern.quote("/"));
            this.queryParams = new HashMap<>();
            this.hasQueryParams = false;
        }
    }

    // ------------ Private Constructor Helper Methods -------------

    private RequestMethod getRequestMethodEnum(String method) {
        method = method.replace(METHOD_TAG, "");
        if (method.equals("")) {
            return RequestMethod.UNKNOWN;
        }

        RequestMethod methodEnum;
        try {
            methodEnum = RequestMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            methodEnum = RequestMethod.UNKNOWN;
        }
        return methodEnum;
    }

    private Map<String, String> parseQueryParams(String queryParams) {
        HashMap<String, String> params = new HashMap<>();
        for (String param : queryParams.split(Pattern.quote("&"))) {
            String[] kv = param.split(Pattern.quote("="));
            if (kv.length < 2) {
                params.put(param, null);
            } else {
                params.put(kv[0], kv[1]);
            }
        }
        return params;
    }

    // ---------------------- Public Methods -----------------------

    public RequestMethod getRequestMethod() {
        return this.requestMethod;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public String[] getFixedParams() {
        return this.fixedParams;
    }

    public String getQueryParam(String key) {
        if (this.hasQueryParams) {
            return this.queryParams.getOrDefault(key, key);
        } else {
            return null;
        }
    }

    public Map<String, String> getAllQueryParams() {
        return this.queryParams;
    }

    public boolean hasQueryParams() {
        return this.hasQueryParams;
    }
}
