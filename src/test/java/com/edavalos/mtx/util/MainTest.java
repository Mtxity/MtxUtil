package com.edavalos.mtx.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public final class MainTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    private Main mainMock;

    @Before
    public void setUp() {
        mainMock = mock(Main.class);

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
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
