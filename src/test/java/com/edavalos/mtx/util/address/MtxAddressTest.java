package com.edavalos.mtx.util.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public final class MtxAddressTest {
    private MtxAddress mtxAddress;

    @Test
    public void testConstructor_validInput() {
        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    null,
                    "San Carlos",
                    "CA",
                    "93210"
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testConstructor_invalidZipcode() {
        assertThrows(IllegalArgumentException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                "CA",
                "932100"
        ));
    }

    @Test
    public void testConstructor_nullStreetName() {
        assertThrows(NullPointerException.class, () -> mtxAddress = new MtxAddress(
                124,
                null,
                null,
                "San Carlos",
                "CA",
                "932100"
        ));
    }

    @Test
    public void testConstructor_nullCityName() {
        assertThrows(NullPointerException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                null,
                "CA",
                "932100"
        ));
    }

    @Test
    public void testConstructor_nullState() {
        assertThrows(NullPointerException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                null,
                "932100"
        ));
    }

    @Test
    public void testConstructor_nullZipCode() {
        assertThrows(NullPointerException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                "CA",
                null
        ));
    }

    @Test
    public void testConstructor_validIntZipcode() {
        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    null,
                    "San Carlos",
                    "CA",
                    93210
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testConstructor_invalidIntZipcode() {
        assertThrows(IllegalArgumentException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                "CA",
                932100
        ));
    }

    @Test
    public void testConstructor_validStateName() {
        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    null,
                    "San Carlos",
                    "Arizona",
                    93210
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }

        assertEquals("AZ", mtxAddress.state());
    }

    @Test
    public void testConstructor_invalidStateName() {
        assertThrows(IllegalArgumentException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                "Westlandia",
                932100
        ));
    }

    @Test
    public void testConstructor_validStateCode() {
        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    null,
                    "San Carlos",
                    "Arizona",
                    93210
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }

        assertEquals("AZ", mtxAddress.state());
    }

    @Test
    public void testConstructor_invalidStateCode() {
        assertThrows(IllegalArgumentException.class, () -> mtxAddress = new MtxAddress(
                124,
                "Valley Dr.",
                null,
                "San Carlos",
                "XX",
                932100
        ));
    }

    @Test
    public void testToString() {
        String address = "124 Valley Dr. Apt 24\n" +
                         "San Carlos, CA 93210";

        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    "Apt 24",
                    "San Carlos",
                    "CA",
                    "93210"
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }

        assertEquals(address, mtxAddress.toString());
    }

    @Test
    public void testToString_noUnitNumber() {
        String address = "124 Valley Dr.\n" +
                         "San Carlos, CA 93210";

        try {
            mtxAddress = new MtxAddress(
                    124,
                    "Valley Dr.",
                    null,
                    "San Carlos",
                    "CA",
                    "93210"
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }

        assertEquals(address, mtxAddress.toString());
    }

    @Test
    public void testGetters() {
        int number = 124;
        String street = "Valley Dr.";
        String unitAbbrev = null;
        String city = "San Carlos";
        String state = "California";
        int zipCode = 93210;

        try {
            mtxAddress = new MtxAddress(
                    number,
                    street,
                    unitAbbrev,
                    city,
                    state,
                    zipCode
            );
        } catch (NullPointerException | IllegalArgumentException e) {
            fail();
        }

        assertEquals(number, mtxAddress.buildingNumber());
        assertEquals(street, mtxAddress.streetName());
        assertNull(mtxAddress.optionalUnitAbbrevAndNumber());
        assertEquals(city, mtxAddress.cityName());
        assertEquals("CA", mtxAddress.state());
        assertEquals(String.valueOf(zipCode), mtxAddress.zipCode());
    }
}
