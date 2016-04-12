package com.quarrelsome.kata.stringcalculator;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringCalculator {

    private static final String USER_DEFINED_DELIMITER_REGEX = "//\\[(.+)\\]\n(.+)";
    private static final String DEFAULT_DELIMITERS_REGEX = "[,\n]";

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

    public int add(String text) throws NegativeValueException {

        CalculatorInput calculatorInput = parseText(text);
        String numbers = calculatorInput.getNumbers();
        String delimiter = calculatorInput.getDelimiter();

        int result=0;
        if (!numbers.isEmpty()) {
            checkForNegatives(calculatorInput);

            Collection<String> valueList = Arrays.asList(numbers.split(delimiter));
            result = valueList.stream().map(x->Integer.parseInt(x)).
                reduce(0, (a, b) -> a + (b<1001 ? b: 0));
        }

        return result;
    }

    private CalculatorInput parseText(String text) {
        String delimiter = DEFAULT_DELIMITERS_REGEX;
        String numbers = text;

        Matcher matcher  = Pattern.compile(USER_DEFINED_DELIMITER_REGEX).
            matcher(text);

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
            if (Integer.parseInt(number) < 0) {
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
