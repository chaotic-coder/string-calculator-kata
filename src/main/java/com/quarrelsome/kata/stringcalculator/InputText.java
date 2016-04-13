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

    private String delimiterRegex;
    private String numbersText;

    boolean isEmptyNumbersText() {
        return numbersText.isEmpty();
    }

    Stream<String> createNumbersStream() {
        return Arrays.asList(numbersText.split(delimiterRegex)).stream();
    }

    static InputText parseText(String text) {
        String delimiterRegex = DEFAULT_DELIMITERS_REGEX;
        String numbers = text;

        Matcher matcher  = Pattern.compile(TEXT_WITH_CUSTOM_DELIMITERS_REGEX).
            matcher(text);

        if (matcher.matches()) {
            String[] delimiters = matcher.group(1).split(CUSTOM_DELIMITERS_REGEX);
            delimiterRegex = "(" + Arrays.asList(delimiters).stream().
                filter(x->!x.isEmpty()).collect(Collectors.joining("|")) + ")";

            numbers = matcher.group(2);
        }

        return new InputText(delimiterRegex, numbers);
    }

    private InputText(String delimiterRegex, String numbersText) {
        this.delimiterRegex = delimiterRegex;
        this.numbersText = numbersText;
    }
}
