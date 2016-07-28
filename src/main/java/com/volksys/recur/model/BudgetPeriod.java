package com.volksys.recur.model;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a budget for a given period of time.
 */
public class BudgetPeriod {
    private final Instant startDate;
    private final Instant endDate;
    private final Map<String, Integer> lineItems;

    /**
     * Constructor.
     *
     * @param startDate the beginning of the budget period, inclusive
     * @param endDate the end of the budget period, exclusive
     */
    public BudgetPeriod(Instant startDate, Instant endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lineItems = new HashMap<>();
    }

    /**
     * The inclusive beginning of the budget period.
     *
     * @return budget period starting moment
     */
    public Instant getStartDate() {
        return startDate;
    }

    /**
     * The exclusive end of the budget period.
     *
     * @return budget period ending moment
     */
    public Instant getEndDate() {
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
