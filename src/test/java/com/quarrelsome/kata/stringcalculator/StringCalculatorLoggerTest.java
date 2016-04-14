package com.quarrelsome.kata.stringcalculator;

import com.quarrelsome.kata.stringcalculator.listeners.IEventListenerService;
import com.quarrelsome.kata.stringcalculator.logger.ILogger;
import com.quarrelsome.kata.stringcalculator.logger.LoggerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StringCalculatorLoggerTest {

    @Mock
    ILogger logger;

    @Mock
    IEventListenerService eventListenerService;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @InjectMocks
    StringCalculator stringCalculator;

    @Test
    public void shouldNotTryToLogResultWhenLoggerNotSet() throws NegativeValueException {
        stringCalculator.setLogger(null);
        stringCalculator.add("2,3");

        verify(logger, never()).write(anyString());
    }

    @Test
    public void shouldLogAddResult() throws NegativeValueException {
        stringCalculator.add("2,3");

        String expectedMessage = "StringCalculator::add result=5";
        verify(logger).write(argumentCaptor.capture());
        assertThat(expectedMessage, is(equalTo(argumentCaptor.getValue())));
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

    @Test
    public void shouldNotNotifyEventListenerWhenListetenerIsNotSet() throws NegativeValueException {
        stringCalculator.setLogListenerService(null);
        doThrow(new LoggerException()).when(logger).write(anyString());

        try {
            stringCalculator.add("4,5");
        } catch (LoggerException ex) {

        }

        verify(eventListenerService, never()).registerException(any(LoggerException.class));
    }
}
