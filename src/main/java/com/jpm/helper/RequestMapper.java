package com.jpm.helper;

import com.jpm.representation.Sales;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahulranjan on 27/08/2018.
 * This file has been created to read input from the external source.
 * In the real time application will be receiving inputs from the external application
 */
public class RequestMapper {

    private static Logger LOGGER = LoggerFactory.getLogger(RequestMapper.class);

    private static final JSONParser parser = new JSONParser();
    private static final ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

    public static List<Sales> getInputFromSource(String inputFile) {
        List<Sales> salesList = new ArrayList<>();

        try {
            Object rawInputMessage = parser.parse(new FileReader(inputFile));
            JSONArray inputMessageList = (JSONArray) rawInputMessage;

            for (Object inputMessage : inputMessageList) {
                Sales sales = jsonStringToObject(inputMessage);
                Sales inputSalesRequest = new Sales.Builder()
                                .withProductType(sales.getProductType())
                                .withValue(sales.getValue())
                                .withAction(sales.getAction())
                                .withCount(sales.getCount() == null ? 1L : sales.getCount())
                                .build();

                salesList.add(inputSalesRequest);
            }

        } catch (Exception e) {
            LOGGER.error("Failed to read message from input source with message: {}", e.getMessage());
        }
        LOGGER.info("Sales list from input source is : {}", salesList);
        return salesList;
    }

    private static Sales jsonStringToObject(Object jsonString) throws IOException {
        return mapper.readValue(String.valueOf(jsonString), Sales.class);
    }

}
