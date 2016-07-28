package com.volksys.recur.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a budget for a given period of time.
 */
public class BudgetPeriod {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Map<String, Integer> lineItems;

    /**
     * Constructor.
     *
     * @param startDate the beginning of the budget period, inclusive
     * @param endDate the end of the budget period, exclusive
     */
    public BudgetPeriod(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lineItems = new HashMap<>();
    }

    /**
     * The inclusive beginning of the budget period.
     *
     * @return budget period starting date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * The exclusive end of the budget period.
     *
     * @return budget period ending date
     */
    public LocalDate getEndDate() {
        return endDate;
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
