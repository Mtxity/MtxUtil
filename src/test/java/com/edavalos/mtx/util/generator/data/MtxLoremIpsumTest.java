package com.edavalos.mtx.util.generator.data;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

public class MtxLoremIpsumTest {
    private MtxLoremIpsum mtxLoremIpsum;

    @Test
    public void testGetFullLoremIpsum() {
        try {
            for (String[] row : MtxLoremIpsum.getFullLoremIpsum()) {
                System.out.println(Arrays.toString(row));
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
