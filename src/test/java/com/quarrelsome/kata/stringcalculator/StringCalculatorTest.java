package com.quarrelsome.kata.stringcalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test()
    public void shouldReturnZeroForEmptyString() throws NegativeValueException {
        int result = stringCalculator.add("");

        assertEquals(0, result);
    }

    @Test
    public void shouldParseNumberOne() throws NegativeValueException {
        int result = stringCalculator.add("1");

        assertEquals(1, result);
    }

    @Test
    public void shouldParseNumberNinetyNine() throws NegativeValueException {
        int result = stringCalculator.add("99");

        assertEquals(99, result);
    }

    @Test
    public void shouldAddTwoNumbers() throws NegativeValueException {
        int result = stringCalculator.add("5,32");

        assertEquals(37, result);
    }

    @Test
    public void shouldAddUnknownAmountOfNumbers() throws NegativeValueException {
        int result = stringCalculator.add("3,12,0,10");

        assertEquals(25, result);
    }

    @Test
    public void shouldAcceptCommaOrNewLineAsDelimiter() throws NegativeValueException {
        int result = stringCalculator.add("3\n12,5,10");

        assertEquals(30, result);
    }

    @Test
    public void shouldAcceptNewLineAsDelimiter() throws NegativeValueException {
        int result = stringCalculator.add("3\n12\n5\n10");

        assertEquals(30, result);
    }

    @Test
    public void shouldAcceptColonAsUserDefinedDelimiter() throws NegativeValueException {
        int result = stringCalculator.add("//[:]\n13:12:5:10");

        assertEquals(40, result);
    }

    @Test
    public void shouldAcceptStringAsUserDefinedDelimiter() throws NegativeValueException {
        int result = stringCalculator.add("//[delim]\n13delim12delim5delim10");

        assertEquals(40, result);
    }

    @Test
    public void throwsExceptionForSingleNegativeValue() throws NegativeValueException {
        try {
            stringCalculator.add("-1");
            fail("Expected NegativeValueException");
        } catch (NegativeValueException ex) {
            assertEquals("Negatives not allowed : -1", ex.getMessage());
        }
    }

    @Test
    public void throwsExceptionForMultipleNegativeValues() throws NegativeValueException {
        try {
            stringCalculator.add("19,-1,12,-8,15");
            fail("Expected NegativeValueException");
        } catch (NegativeValueException ex) {
            assertEquals("Negatives not allowed : -1, -8", ex.getMessage());
        }
    }

    @Test
    public void shouldIgnoreNumbersGreaterThan1000() throws NegativeValueException {
        int result = stringCalculator.add("3,12,1001,0,10");

        assertEquals(25, result);
    }

    @Test
    public void shouldNotIgnore1000() throws NegativeValueException {
        int result = stringCalculator.add("3,12,1000,0,10");

        assertEquals(1025, result);
    }

    @Test
    public void shouldIgnoreSingle1001() throws NegativeValueException {
        int result = stringCalculator.add("1001");

        assertEquals(0, result);
    }

    @Test
    public void shouldIgnoreNumbersGreaterThan1000WhenUsingUserDefinedDelimiter() throws NegativeValueException {
        int result = stringCalculator.add("//[:]\n13:12:1001:5:10");

        assertEquals(40, result);
    }

    @Test
    public void shouldAcceptMultipleUserDefinedDelimiters() throws NegativeValueException {
        int result = stringCalculator.add("//[:][;]\n13:12;5:10");

        assertEquals(40, result);
    }
}