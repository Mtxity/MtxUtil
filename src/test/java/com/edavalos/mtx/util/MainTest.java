package com.edavalos.mtx.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void givenSystemOutRedirection_whenInvokePrintln_thenOutputCaptorSuccess() {
        // https://www.baeldung.com/java-testing-system-out-println
        String stringToPrint = "Test print";
        System.out.print(stringToPrint);

        assertEquals(stringToPrint, outputStreamCaptor.toString());
    }

    @Test
    public void testMain() {
        String expected = "Hello world!";
        Main.main(null);
        String actual = outputStreamCaptor.toString().trim();

        assertEquals(expected, actual);
    }
}
