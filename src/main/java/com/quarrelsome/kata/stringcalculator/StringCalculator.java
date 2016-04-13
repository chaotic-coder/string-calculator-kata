package com.quarrelsome.kata.stringcalculator;

import java.util.stream.Collectors;


public class StringCalculator {

    public int add(String text) throws NegativeValueException {
        InputText inputText = InputText.parseText(text);

        int result=0;
        if (!inputText.isEmptyNumbersText()) {
            checkForNegatives(inputText);

            result = inputText.getNumbersStream().map(x->Integer.parseInt(x)).
                reduce(0, (a, b) -> a + (b<1001 ? b: 0));
        }

        return result;
    }

    private void checkForNegatives(InputText inputText) throws NegativeValueException {
        String negatives = inputText.getNumbersStream().
              filter(x -> Integer.parseInt(x) < 0).
              collect(Collectors.joining(", "));

        if (!negatives.isEmpty()) {
            throw new NegativeValueException("Negatives not allowed : " + negatives);
        }
    }

}
