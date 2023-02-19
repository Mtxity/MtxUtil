package com.edavalos.mtx.util.network;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class MtxRequestReaderTest {
    private MtxRequestReader mtxRequestReader;

    @Test
    public void testConstructor_hasMethod_hasFixedParams_hasQueryParams() {
        String testCase = "GET /fixedParam1/fixedParam2?qpk1=qpv1&qpk2=qpv2 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.GET;
        String[] expectedFixedParams = {"fixedParam1", "fixedParam2"};
        Map<String, String> expectedQueryParams = new HashMap<>(){
            {
                put("qpk1", "qpv1");
                put("qpk2", "qpv2");
            }
        };

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertTrue(mtxRequestReader.hasQueryParams());

        for (String key : expectedQueryParams.keySet()) {
            assertEquals(expectedQueryParams.get(key), mtxRequestReader.getQueryParam(key));
        }
    }

    @Test
    public void testConstructor_hasMethod_hasFixedParams_noQueryParams() {
        String testCase = "POST /fixedParam1/fixedParam2/fixedParam3 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.POST;
        String[] expectedFixedParams = {"fixedParam1", "fixedParam2", "fixedParam3"};
        Map<String, String> expectedQueryParams = new HashMap<>();

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertFalse(mtxRequestReader.hasQueryParams());
        assertNull(mtxRequestReader.getQueryParam("anything"));
    }

    @Test
    public void testConstructor_hasMethod_noFixedParams_hasQueryParams() {
        String testCase = "PUT /?qpk1=qpv1&qpk2=qpv2 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.PUT;
        String[] expectedFixedParams = {};
        Map<String, String> expectedQueryParams = new HashMap<>(){
            {
                put("qpk1", "qpv1");
                put("qpk2", "qpv2");
            }
        };

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertTrue(mtxRequestReader.hasQueryParams());

        for (String key : expectedQueryParams.keySet()) {
            assertEquals(expectedQueryParams.get(key), mtxRequestReader.getQueryParam(key));
        }
    }

    @Test
    public void testConstructor_hasMethod_oneFixedParam_hasQueryParams() {
        String testCase = "PUT /fp1?qpk1=qpv1&qpk2=qpv2 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.PUT;
        String[] expectedFixedParams = {"fp1"};
        Map<String, String> expectedQueryParams = new HashMap<>(){
            {
                put("qpk1", "qpv1");
                put("qpk2", "qpv2");
            }
        };

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertTrue(mtxRequestReader.hasQueryParams());

        for (String key : expectedQueryParams.keySet()) {
            assertEquals(expectedQueryParams.get(key), mtxRequestReader.getQueryParam(key));
        }
    }

    @Test
    public void testConstructor_hasMethod_hasFixedParams_hasQueryParamsWithNoValues() {
        String testCase = "GET /fixedParam1/fixedParam2?qpk1&qpk2 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.GET;
        String[] expectedFixedParams = {"fixedParam1", "fixedParam2"};
        Map<String, String> expectedQueryParams = new HashMap<>(){
            {
                put("qpk1", null);
                put("qpk2", null);
            }
        };

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertTrue(mtxRequestReader.hasQueryParams());

        for (String key : expectedQueryParams.keySet()) {
            assertEquals(key, mtxRequestReader.getQueryParam(key));
        }
    }

    @Test
    public void testConstructor_hasMethod_hasFixedParams_hasQueryParamsWithNullValues() {
        String testCase = "GET /fixedParam1/fixedParam2?qpk1=&qpk2= HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.GET;
        String[] expectedFixedParams = {"fixedParam1", "fixedParam2"};
        Map<String, String> expectedQueryParams = new HashMap<>(){
            {
                put("qpk1", null);
                put("qpk2", null);
            }
        };

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertTrue(mtxRequestReader.hasQueryParams());

        for (String key : expectedQueryParams.keySet()) {
            assertEquals(key, mtxRequestReader.getQueryParam(key));
        }
    }

    @Test
    public void testConstructor_hasMethod_noFixedParams_noQueryParams() {
        String testCase = "DELETE / HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.DELETE;
        String[] expectedFixedParams = {};
        Map<String, String> expectedQueryParams = new HashMap<>();

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertFalse(mtxRequestReader.hasQueryParams());
    }

    @Test
    public void testConstructor_noMethod_hasFixedParams_noQueryParams() {
        String testCase = "/fixedParam1/fixedParam2/fixedParam3 HTTP/1.1";
        RequestMethod expectedRequestMethod = RequestMethod.UNKNOWN;
        String[] expectedFixedParams = {"fixedParam1", "fixedParam2", "fixedParam3"};
        Map<String, String> expectedQueryParams = new HashMap<>();

        mtxRequestReader = new MtxRequestReader(testCase);

        assertEquals(expectedRequestMethod, mtxRequestReader.getRequestMethod());
        assertEquals("HTTP/1.1", mtxRequestReader.getHttpVersion());
        assertArrayEquals(expectedFixedParams, mtxRequestReader.getFixedParams());
        assertEquals(expectedQueryParams, mtxRequestReader.getAllQueryParams());
        assertFalse(mtxRequestReader.hasQueryParams());
        assertNull(mtxRequestReader.getQueryParam("anything"));
    }
}
