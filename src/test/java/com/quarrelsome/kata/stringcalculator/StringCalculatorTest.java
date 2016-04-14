package com.quarrelsome.kata.stringcalculator;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;


public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test()
    public void shouldReturnZeroForEmptyString() throws NegativeValueException {
        assertThat(stringCalculator.add(""), is(equalTo(0)));
    }

    @Test
    public void shouldParseNumberOne() throws NegativeValueException {
        assertThat(stringCalculator.add("1"), is(equalTo(1)));
    }

    @Test
    public void shouldParseNumberNinetyNine() throws NegativeValueException {
        assertThat(stringCalculator.add("99"), is(equalTo(99)));
    }

    @Test
    public void shouldAddTwoNumbers() throws NegativeValueException {
        assertThat(stringCalculator.add("5,32"), is(equalTo(37)));
    }

    @Test
    public void shouldAddUnknownAmountOfNumbers() throws NegativeValueException {
        assertThat(stringCalculator.add("3,12,0,10"), is(equalTo(25)));
    }

    @Test
    public void shouldAcceptCommaOrNewLineAsDelimiter() throws NegativeValueException {
        assertThat(stringCalculator.add("3\n12,5,10"), is(equalTo(30)));
    }

    @Test
    public void shouldAcceptNewLineAsDelimiter() throws NegativeValueException {
        assertThat(stringCalculator.add("3\n12\n5\n10"), is(equalTo(30)));
    }

    @Test
    public void shouldAcceptColonAsUserDefinedDelimiter() throws NegativeValueException {
        assertThat(stringCalculator.add("//[:]\n13:12:5:10"), is(equalTo(40)));
    }

    @Test
    public void shouldAcceptStringAsUserDefinedDelimiter() throws NegativeValueException {
        assertThat(stringCalculator.add("//[delim]\n13delim12delim5delim10"), is(equalTo(40)));
    }

    @Test
    public void throwsExceptionForSingleNegativeValue() throws NegativeValueException {
        try {
            stringCalculator.add("-1");
            fail("Expected NegativeValueException");
        } catch (NegativeValueException ex) {
            assertThat(ex.getMessage(), containsString("Negatives not allowed : -1"));
        }
    }

    @Test
    public void throwsExceptionForMultipleNegativeValues() throws NegativeValueException {
        try {
            stringCalculator.add("19,-1,12,-8,15");
            fail("Expected NegativeValueException");
        } catch (NegativeValueException ex) {
            assertThat(ex.getMessage(), containsString("Negatives not allowed : -1, -8"));
        }
    }

    @Test
    public void shouldIgnoreNumbersGreaterThan1000() throws NegativeValueException {
        assertThat(stringCalculator.add("3,12,1001,0,10"), is(equalTo(25)));
    }

    @Test
    public void shouldNotIgnore1000() throws NegativeValueException {
        assertThat(stringCalculator.add("3,12,1000,0,10"), is(equalTo(1025)));
    }

    @Test
    public void shouldIgnoreSingle1001() throws NegativeValueException {
        assertThat(stringCalculator.add("1001"), is(equalTo(0)));
    }

    @Test
    public void shouldIgnoreNumbersGreaterThan1000WhenUsingUserDefinedDelimiter() throws NegativeValueException {
        assertThat(stringCalculator.add("//[:]\n13:12:1001:5:10"), is(equalTo(40)));
    }

    @Test
    public void shouldAcceptMultipleUserDefinedDelimiters() throws NegativeValueException {
        assertThat(stringCalculator.add("//[:][;]\n13:12;5:10"), is(equalTo(40)));
    }
}