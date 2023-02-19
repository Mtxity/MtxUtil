package com.edavalos.mtx.util.network;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class RequestMethodTest {

    @Test
    public void testGetExplanation() {
        for (RequestMethod httpMethod : RequestMethod.values()) {
            assertNotNull(RequestMethod.getExplanation(httpMethod));
            assertNotEquals("", RequestMethod.getExplanation(httpMethod));
        }
    }
}
