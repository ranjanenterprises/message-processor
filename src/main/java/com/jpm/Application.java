package com.jpm;

import com.jpm.processor.MessageProcessor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rahulranjan on 27/08/2018.
 * This is the main class from where message processor will start.
 */
@SpringBootApplication
public class Application {

    /**
     * Main method to start the application
     * Input parameter file name is required to pick the message and start processing
     */
    public static void main(String[] args) {
        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.processSalesMessage(args[0]);
    }

}
