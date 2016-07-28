package com.volksys.recur.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a budget for a given period of time.
 */
public class BudgetPeriod {
    private final Date startDate;
    private final Date endDate;
    private final Map<String, Integer> lineItems;

    /**
     * Constructor.
     *
     * @param startDate the beginning of the budget period, inclusive
     * @param endDate the end of the budget period, inclusive
     */
    public BudgetPeriod(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.lineItems = new HashMap<>();
    }

    /**
     * The inclusive beginning of the budget period.
     *
     * @return budget period starting date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * The inclusive end of the budget period.
     *
     * @return budget period ending date
     */
    public Date getEndDate() {
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
