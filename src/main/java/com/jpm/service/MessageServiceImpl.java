package com.jpm.service;

import com.jpm.representation.Sales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.jpm.datasource.SalesInventory.getSalesHistory;
import static com.jpm.datasource.SalesInventory.getSalesInventory;
import static com.jpm.representation.Sales.Action.ADD;
import static com.jpm.representation.Sales.Action.MULTIPLY;
import static com.jpm.representation.Sales.Action.SUBTRACT;

@Named
public class MessageServiceImpl implements MessageService {

    private static Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    /**
     * Method to store any product coming to message processor from external source
     */
    @Override
    public void recordSaleDetails(Sales sales) {
        LOGGER.debug("Recording sales details: {}", sales);

        Map<Object, BigDecimal> salesKey = buildSalesDetails(sales);

        if (getSalesInventory().containsKey(salesKey)) {
            getSalesInventory().put(salesKey, getSalesInventory().get(salesKey) + sales.getCount());
        } else {
            getSalesInventory().put(buildSalesDetails(sales), sales.getCount());
        }

        getSalesHistory().add(sales.getCount() + " " + sales.getProductType() + " at rate " + sales.getValue() + " have been added");
    }

    /**
     * Method to adjust rate of requested product. As part of adjustment product
     * value can be added, subtracted and multiplied
     */
    @Override
    public void adjustRate(Sales sales) {
        LOGGER.debug("Adjusting sales details: {}", sales);

        BigDecimal value = sales.getValue();
        Sales.Action action = sales.getAction();

        Map<Map<Object, BigDecimal>, Long> newEntry = new ConcurrentHashMap<>();
        for (Map.Entry<Map<Object, BigDecimal>, Long> currentMap : getSalesInventory().entrySet()) {
            Map<Object, BigDecimal> oldSalesKey = buildSalesDetails(sales);
            Map<Object, BigDecimal> newSalesKey = new HashMap<>();

            Object currentKey = currentMap.getKey().keySet().iterator().next();
            BigDecimal currentValue = currentMap.getKey().values().iterator().next();

            if (oldSalesKey.keySet().equals(currentMap.getKey().keySet())) {

                if (ADD.equals(action)) {
                    newSalesKey = buildSalesKey(currentKey, currentValue.add(value));
                } else if (SUBTRACT.equals(action)) {
                    newSalesKey = buildSalesKey(currentKey, currentValue.subtract(value));
                } else if (MULTIPLY.equals(action)) {
                    newSalesKey = buildSalesKey(currentKey, currentValue.multiply(value));
                }
                newEntry.put(newSalesKey, currentMap.getValue());
                getSalesInventory().remove(buildSalesKey(currentKey, currentValue));
            }
        }
        getSalesInventory().putAll(newEntry);
        getSalesHistory().add(action + " " + value + " to " + sales.getProductType());

    }

    private Map<Object, BigDecimal> buildSalesDetails(Sales sales) {
        Map<Object, BigDecimal> salesDetails = new HashMap<>();
        salesDetails.put(sales.getProductType(), sales.getValue());
        return salesDetails;
    }

    private Map<Object, BigDecimal> buildSalesKey(Object productType, BigDecimal value) {
        Map<Object, BigDecimal> salesDetails = new HashMap<>();
        salesDetails.put(productType, value);

        return salesDetails;
    }
}
