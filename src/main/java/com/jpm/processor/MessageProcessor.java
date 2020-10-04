package com.jpm.processor;

import com.jpm.representation.Sales;
import com.jpm.service.MessageService;
import com.jpm.service.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.jpm.helper.RequestMapper.getInputFromSource;
import static com.jpm.report.MessageReport.generateAdjustmentReport;
import static com.jpm.report.MessageReport.generateSalesReport;

@Named
public class MessageProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    @Inject
    private MessageService messageService = new MessageServiceImpl();

    public void processSalesMessage(String inputFile) {
        LOGGER.info("Reading messages to be processed.");

        List<Sales> salesList = getInputFromSource(inputFile);
        int countProcessedMessage = 0;

        for (Sales sales : salesList) {
            if (null != sales.getAction()) {
                messageService.adjustRate(sales);
                LOGGER.info("Product rate for " + sales + " has been adjusted successfully");
            } else {
                messageService.recordSaleDetails(sales);
                LOGGER.info("Product " + sales + " has been recorded successfully");
            }

            countProcessedMessage++;

            if (countProcessedMessage % 10 == 0) {
                generateSalesReport();
            }
            if (countProcessedMessage == 50) {
                LOGGER.info("Message processing paused to generate the adjustment report...");
                generateAdjustmentReport();
                LOGGER.info("Adjustment Report generated successfully");
            }
        }
    }
}
