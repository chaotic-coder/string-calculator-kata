package com.quarrelsome.kata.stringcalculator;

import com.quarrelsome.kata.stringcalculator.log.IEventListenerService;
import com.quarrelsome.kata.stringcalculator.log.ILogger;
import com.quarrelsome.kata.stringcalculator.log.LoggerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StringCalculatorLoggerTest {

    @Mock
    ILogger logger;

    @Mock
    IEventListenerService eventListenerService;

    @InjectMocks
    StringCalculator stringCalculator;

    @Test
    public void shouldLogAddResult() throws NegativeValueException {
        stringCalculator.add("2,3");

        verify(logger).write("StringCalculator::add result=5");
    }

    @Test
    public void shouldNotifyEventListenerWhenAnExceptionIsThrown() throws NegativeValueException {
        doThrow(new LoggerException()).when(logger).write(anyString());

        try {
            stringCalculator.add("4,5");
        } catch (LoggerException ex) {

        }

        verify(eventListenerService).registerException(any(LoggerException.class));
    }
}
