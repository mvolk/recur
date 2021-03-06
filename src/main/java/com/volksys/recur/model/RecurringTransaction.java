package com.volksys.recur.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;

/**
 * Represents a financial transaction that occurs on a regular basis.
 */
public class RecurringTransaction implements Iterable<Transaction> {
    private final int amount;
    private final String category;
    private final LocalDate initialOccurrence;
    private final LocalDate finalOccurrence;
    private final Period period;
    private final String description;

    /**
     * Constructor.
     *
     * @param description A description of this transaction
     * @param amount The amount of the transaction in hundredths of a whole currency unit.
     * @param category The name of the budget category to which this transaction belongs
     * @param initialOccurrence The first occurrence of this transaction
     * @param finalOccurrence (nullable) The last occurrence of this transaction
     * @param period The amount of time that passes between occurrences of this transaction
     */
    public RecurringTransaction(String description, int amount, String category,
                                LocalDate initialOccurrence, LocalDate finalOccurrence, Period period) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.initialOccurrence = initialOccurrence;
        this.finalOccurrence = finalOccurrence;
        this.period = period;
    }

    /**
     * A description of this transaction.
     *
     * @return A description of this transaction.
     */
    public String getDescription() {
        return description;
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
    public LocalDate getInitialOccurrence() {
        return initialOccurrence;
    }

    /**
     * The date of the final occurrence of this transaction.
     *
     * @return the date past which there are no further occurrences of this transaction
     */
    public LocalDate getFinalOccurrence() {
        return finalOccurrence;
    }

    /**
     * The amount of time that passes between each occurrence of this transaction.
     *
     * @return the period of this transaction
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Iterator over all occurrences of this transaction in chronological order.
     *
     * @return all occurrences of this transaction in natural (ascending) chronological order
     */
    @Override
    public Iterator<Transaction> iterator() {
        return new Iterator<Transaction>() {
            private LocalDate date = initialOccurrence;

            /**
             * Indicates whether there is another transaction.
             *
             * @return {@code true} if there is another transaction
             */
            @Override
            public boolean hasNext() {
                return finalOccurrence == null || !date.isAfter(finalOccurrence);
            }

            /**
             * The next transaction chronologically.
             *
             * @return the transaction that occurs soonest after the transaction returned by the last invocation of
             *         this method, or the first transaction if this method has not previously been invoked.
             */
            @Override
            public Transaction next() {
                Transaction transaction = new Transaction(description, amount, category, date);
                date = date.plus(period);
                return transaction;
            }
        };
    }

}
