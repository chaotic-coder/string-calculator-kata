package com.quarrelsome.kata.stringcalculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringCalculator {

    private static final String USER_DEFINED_DELIMITER_REGEX = "//\\[(.+)\\]\n(.+)";
    private final String DEFAULT_DELIMITERS_REGEX = "[,\n]";

    private class CalculatorInput {
        private String delimiter;
        private String numbers;

        public CalculatorInput(String delimiter, String numbers) {
            this.delimiter = delimiter;
            this.numbers = numbers;
        }

        public String getDelimiter() {
            return delimiter;
        }

        public String getNumbers() {
            return numbers;
        }
    }

    public int add(String value) throws NegativeValueException {

        CalculatorInput calculatorInput = parseInput(value);
        String numbers = calculatorInput.getNumbers();
        String delimiter = calculatorInput.getDelimiter();

        int result=0;
        if (!numbers.isEmpty()) {
            checkForNegatives(calculatorInput);

            for (String number : numbers.split(delimiter)) {
                int intValue = Integer.parseInt(number);
                result += (intValue < 1001 ? intValue : 0);
            }
        }

        return result;
    }

    private CalculatorInput parseInput(String value) {
        String delimiter = DEFAULT_DELIMITERS_REGEX;
        String numbers = value;

        Pattern userDefinedDelimiterPattern = Pattern.compile(USER_DEFINED_DELIMITER_REGEX);
        Matcher matcher  = userDefinedDelimiterPattern.matcher(value);
        if (matcher.matches()) {
            delimiter = matcher.group(1);
            numbers = matcher.group(2);
        }

        return new CalculatorInput(delimiter, numbers);
    }

    private void checkForNegatives(CalculatorInput calculatorInput) throws NegativeValueException {
        StringBuilder negatives = new StringBuilder();

        String[] splitNumbers = calculatorInput.getNumbers().split(calculatorInput.getDelimiter());
        for (String number : splitNumbers) {
            int intValue = Integer.parseInt(number);
            if (intValue < 0) {
                if (negatives.length() != 0) {
                    negatives.append(", ");
                }
                negatives.append(number);
            }
        }

        negatives.trimToSize();
        if (!negatives.toString().isEmpty()) {
            throw new NegativeValueException("Negatives not allowed : " + negatives.toString());
        }

    }
}
