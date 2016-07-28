package com.volksys.recur.model;

import java.util.Date;

/**
 * Represents a financial transaction that occurs on a regular basis.
 */
public class RecurringTransaction {
    private final int amount;
    private final String category;
    private final Date initialOccurrence;
    private final Periodicity periodicity;

    /**
     * Constructor.
     *
     * @param amount The amount of the transaction in hundredths of a whole currency unit.
     * @param category The name of the budget category to which this transaction belongs
     * @param initialOccurrence The first occurrence of this transaction
     * @param periodicity The amount of time that passes between occurrences of this transaction
     */
    public RecurringTransaction(int amount, String category, Date initialOccurrence, Periodicity periodicity) {
        this.amount = amount;
        this.category = category;
        this.initialOccurrence = initialOccurrence;
        this.periodicity = periodicity;
    }

    /**
     * The amount of this transaction in hundredths of a whole currency unit.
     *
     * @return the magnitude of each occurrence of this transaction as measured in hundredths of a whole currency unit.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * The name of the budget category to which this transaction belongs.
     *
     * @return a budget category name for aggregation purposes
     */
    public String getCategory() {
        return category;
    }

    /**
     * The date of the initial occurrence of this transaction.
     *
     * @return the date of the first transaction
     */
    public Date getInitialOccurrence() {
        return initialOccurrence;
    }

    /**
     * The amount of time that passes between each occurrence of this transaction.
     *
     * @return the periodicity of this transaction
     */
    public Periodicity getPeriodicity() {
        return periodicity;
    }
}
