package com.jpm.datasource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rahulranjan on 27/08/2018.
 * 
 * This file has been created to store the processed data and history in the memory. 
 * It will behave as database for this application.
 * 
 */
public class SalesInventory {

	private static Map<Map<Object, BigDecimal>, Long> salesList;
	private static List<String> salesHistory;

	private SalesInventory() {
	}

	public static Map<Map<Object, BigDecimal>, Long> getSalesInventory() {
		return salesList;
	}

	public static List<String> getSalesHistory() {
		return salesHistory;
	}

	static {
		try {
			salesList = new ConcurrentHashMap<>();
			salesHistory = new ArrayList<>();
		} catch (Exception e) {
			throw new RuntimeException("Exception occurred while instantiating sales inventory");
		}

	}

}
