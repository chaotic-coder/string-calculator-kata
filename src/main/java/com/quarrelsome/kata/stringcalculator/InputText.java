package com.quarrelsome.kata.stringcalculator;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class InputText {

    private static final String TEXT_WITH_CUSTOM_DELIMITERS_REGEX = "//\\[(.+)\\]\n(.+)";
    private static final String DEFAULT_DELIMITERS_REGEX = "(,|\n)";
    private static final String CUSTOM_DELIMITERS_REGEX = "(\\[|\\])";

    private String delimiter;
    private String numbersText;

    boolean isEmptyNumbersText() {
        return numbersText.isEmpty();
    }

    Stream<String> getNumbersStream() {
        return Arrays.asList(numbersText.split(delimiter)).stream();
    }

    static InputText parseText(String text) {
        String delimiter = DEFAULT_DELIMITERS_REGEX;
        String numbers = text;

        Matcher matcher  = Pattern.compile(TEXT_WITH_CUSTOM_DELIMITERS_REGEX).
            matcher(text);

        if (matcher.matches()) {
            delimiter = parseDelimiterText(matcher.group(1));
            numbers = matcher.group(2);
        }

        return new InputText(delimiter, numbers);
    }

    private static String parseDelimiterText(String delimiterText) {
        String[] delimiters = delimiterText.split(CUSTOM_DELIMITERS_REGEX);

        return "(" + Arrays.asList(delimiters).stream().
            filter(x->!x.isEmpty()).collect(Collectors.joining("|")) + ")";
    }

    private InputText(String delimiter, String numbersText) {
        this.delimiter = delimiter;
        this.numbersText = numbersText;
    }
}
