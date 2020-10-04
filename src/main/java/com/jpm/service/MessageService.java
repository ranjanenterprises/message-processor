package com.jpm.service;

import com.jpm.representation.Sales;

public interface MessageService {

    void recordSaleDetails(Sales sales);
    void adjustRate(Sales sales);

}
