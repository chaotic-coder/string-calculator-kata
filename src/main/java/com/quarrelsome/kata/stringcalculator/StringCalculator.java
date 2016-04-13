package com.quarrelsome.kata.stringcalculator;

import com.quarrelsome.kata.stringcalculator.log.IEventListenerService;
import com.quarrelsome.kata.stringcalculator.log.ILogger;
import com.quarrelsome.kata.stringcalculator.log.LoggerException;

import java.util.stream.Collectors;


public class StringCalculator {
    private ILogger logger;
    private IEventListenerService eventListenerService;

    public int add(String text) throws NegativeValueException {
        InputText inputText = InputText.parseText(text);

        int result=0;
        if (!inputText.isEmptyNumbersText()) {
            checkForNegatives(inputText);

            result = inputText.createNumbersStream().map(x->Integer.parseInt(x)).
                reduce(0, (a, b) -> a + (b<1001 ? b: 0));
        }

        logMessage("StringCalculator::add result=" + Integer.toString(result));

        return result;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public void setLogListenerService(IEventListenerService logListenerService) {
        this.eventListenerService = logListenerService;
    }

    private void checkForNegatives(InputText inputText) throws NegativeValueException {
        String negatives = inputText.createNumbersStream().
              filter(x -> Integer.parseInt(x) < 0).
              collect(Collectors.joining(", "));

        if (!negatives.isEmpty()) {
            throw new NegativeValueException("Negatives not allowed : " + negatives);
        }
    }

    private void logMessage(String msg) {
        if (logger != null) {
            try {
                logger.write(msg);
            } catch (LoggerException ex) {
                registerException(ex);
            }
        }
    }

    private void registerException(Exception ex) {
        if (eventListenerService != null) {
            eventListenerService.registerException(ex);
        }
    }
}
