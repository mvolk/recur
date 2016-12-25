package com.volksys.recur.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models a budget for a given period of time.
 */
public class Budget {
    private final LocalDateRange dateRange;
    private final Map<String, List<Transaction>> categorizedTransactions;

    /**
     * Constructor.
     *
     * @param dateRange the period of time covered by this budget
     */
    public Budget(LocalDateRange dateRange) {
        this.dateRange = dateRange;
        this.categorizedTransactions = new HashMap<>();
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
     * The budget line items (categories) in this budget.
     *
     * @return the categories for this budget
     */
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>(categorizedTransactions.keySet());
        Collections.sort(categories);
        return categories;
    }

    /**
     * The budget line items (categories) and amounts to budget for each.
     *
     * @return the budget for this period (in cents)
     */
    public int getBudgetFor(String category) {
        int accumulator = 0;
        for (Transaction transaction : getTransactionsFor(category)) {
            accumulator += transaction.getAmount();
        }
        return accumulator;
    }

    /**
     * The transactions budgeted for a given category.
     *
     * @return transactions accommodated by this budget for the given category
     */
    public List<Transaction> getTransactionsFor(String category) {
        List<Transaction> transactions = categorizedTransactions.get(category);
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    /**
     * Add a new transaction.
     *
     * @param transaction (not null) the transaction to add to the budget
     */
    public void addTransaction(Transaction transaction) {
        if (!getDateRange().contains(transaction.getDate())) {
            return;
        }
        List<Transaction> transactions = categorizedTransactions.get(transaction.getCategory());
        if (transactions == null) {
            transactions = new ArrayList<>();
            categorizedTransactions.put(transaction.getCategory(), transactions);
        }
        transactions.add(transaction);
    }
}
