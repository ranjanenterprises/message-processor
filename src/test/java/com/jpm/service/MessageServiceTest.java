package com.jpm.service;

import com.jpm.representation.Sales;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static com.jpm.representation.Sales.Action.ADD;
import static com.jpm.representation.Sales.Action.SUBTRACT;
import static com.jpm.representation.Sales.Action.MULTIPLY;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageService messageService;

    @Test
    public void testRecordSaleDetailsSuccess() {
        messageService.recordSaleDetails(salesData(null));
    }

    @Test(expected = Exception.class)
    public void testRecordSalesFailure() {
        doThrow(new Exception()).when(messageService).recordSaleDetails(any(Sales.class));
        try {
            messageService.recordSaleDetails(salesData(null));
        } finally {
            verify(messageService).recordSaleDetails(any(Sales.class));
        }
    }

    @Test
    public void testAdjustRateWithAdditionSuccess() {
        messageService.adjustRate(salesData(ADD));
    }

    @Test
    public void testAdjustRateWithSubtractionSuccess() {
        messageService.adjustRate(salesData(SUBTRACT));
    }

    @Test
    public void testAdjustRateWithMultiplicationSuccess() {
        messageService.adjustRate(salesData(MULTIPLY));
    }

    @Test(expected = Exception.class)
    public void testAdjustRateWithMultiplicationFailure() {
        doThrow(new Exception()).when(messageService).adjustRate(any(Sales.class));
        try {
            messageService.adjustRate(salesData(MULTIPLY));
        } finally {
            verify(messageService).adjustRate(any(Sales.class));
        }
    }

    private Sales salesData(Sales.Action action) {
        return new Sales.Builder()
                        .withProductType("Apple")
                        .withValue(new BigDecimal("1"))
                        .withAction(action)
                        .withCount(5L)
                        .build();
    }

}