package com.jpm.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageProcessorTest {

    private static final String MESSAGE_INPUT_FILE = "messages/messages.json";

    @Mock
    private MessageProcessor messageProcessor;

    @Test
    public void testProcessMessageSuccess() {
        messageProcessor.processSalesMessage(MESSAGE_INPUT_FILE);
    }

    @Test(expected = Exception.class)
    public void testProcessMessageFailure() {
        doThrow(new FileNotFoundException()).when(messageProcessor).processSalesMessage(eq(MESSAGE_INPUT_FILE));
        try {
            messageProcessor.processSalesMessage(MESSAGE_INPUT_FILE);
        } finally {
            verify(messageProcessor).processSalesMessage(eq(MESSAGE_INPUT_FILE));
        }

    }

}