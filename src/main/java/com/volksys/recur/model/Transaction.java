package com.volksys.recur.model;

import java.time.LocalDate;

/**
 * Represents a single financial transaction.
 */
public class Transaction {
    private final int amount;
    private final String category;
    private final LocalDate date;

    /**
     * Constructor.
     *
     * @param amount the amount of the transaction in hundredths of a whole currency unit.
     * @param category the name of the budget category to which this transaction belongs
     * @param date the date of the transaction
     */
    public Transaction(int amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
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
     * The date of this transaction.
     *
     * @return the date of the transaction
     */
    public LocalDate getDate() {
        return date;
    }

}
