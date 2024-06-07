package com.edavalos.mtx.util.generator.data;

import com.edavalos.mtx.util.string.MtxStringUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MtxLoremIpsumTest {
    private MtxLoremIpsum mtxLoremIpsum;

    @Test
    public void testGetFullLoremIpsum() {
        int length = 0;
        try {
            for (String[] row : MtxLoremIpsum.getFullLoremIpsum()) {
//                // Will spam console with lorem ipsum
//                System.out.println(Arrays.toString(row));

                if (row.length >= 1 && !MtxStringUtil.isEmpty(row[0])) {
                    length ++;
                }
            }
            assertEquals(MtxLoremIpsum.MAX_LOREMS, length);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetFullLoremIpsumInOneLine() {
        try {
            assertEquals(8382, MtxLoremIpsum.getFullLoremIpsumInOneLine().length);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
