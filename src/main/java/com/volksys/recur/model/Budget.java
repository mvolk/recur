package com.volksys.recur.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Models a budget for a given period of time.
 */
public class Budget {
    private final LocalDateRange dateRange;
    private final Map<String, Integer> lineItems;

    /**
     * Constructor.
     *
     * @param dateRange the period of time covered by this budget
     */
    public Budget(LocalDateRange dateRange) {
        this.dateRange = dateRange;
        this.lineItems = new HashMap<>();
    }

    /**
     * The dates covered by this budget.
     *
     * @return the period of time covered by this budget
     */
    public LocalDateRange getDateRange() {
        return dateRange;
    }

    /**
     * The line items and amounts to budget for each.
     *
     * @return the budget for this period
     */
    public Map<String, Integer> getLineItems() {
        return lineItems;
    }
}
