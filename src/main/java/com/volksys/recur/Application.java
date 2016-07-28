package com.volksys.recur;

import com.volksys.recur.model.Budget;
import com.volksys.recur.model.LocalDateRange;
import com.volksys.recur.model.RecurringTransaction;
import com.volksys.recur.model.Transaction;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Main application.
 */
@SuppressWarnings("PMD.SystemPrintln")
public class Application {

    private final LocalDateRange budgetYear;

    protected Application(int year) {
        budgetYear = new LocalDateRange(LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year + 1, Month.JANUARY, 1));
    }

    /**
     * Performs the work.
     */
    private void run() {
        List<Budget> budgets = getBudgets();
        List<RecurringTransaction> recurringTransactions = getRecurringTransactions();
        populateBudget(budgets, recurringTransactions);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        NumberFormat nf = new DecimalFormat("$#.00");
        for (Budget period : budgets) {
            System.out.println(dtf.format(period.getDateRange().getStartInclusive()) + " - "
                    + dtf.format(period.getDateRange().getEndExclusive().minus(1, ChronoUnit.DAYS)));
            System.out.println("=======================");
            List<String> categories = new ArrayList<>(period.getLineItems().keySet()); //NOPMD
            Collections.sort(categories);
            for (String category : categories) {
                System.out.println("  " + category + ": " + nf.format(toCurrency(period.getLineItems().get(category))));
            }
            System.out.println();
            System.out.println();
        }
    }

    private double toCurrency(int cents) {
        return (double) cents / 100;
    }

    /**
     * Generates an ordered list of {@link Budget}s covering the entire year.
     *
     * @return a list of budget periods ready to be populated
     */
    protected List<Budget> getBudgets() {
        List<Budget> budgets = new ArrayList<>(12);
        int year = budgetYear.getStartInclusive().getYear();
        for (Month month : Month.values()) {
            budgets.add(createBudget(year, month));
        }
        return budgets;
    }

    private Budget createBudget(int year, Month month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1);
        return new Budget(new LocalDateRange(start, end));
    }

    /**
     * Stub that returns mock data.
     *
     * @return list of recurring transactions
     */
    protected List<RecurringTransaction> getRecurringTransactions() {
        List<RecurringTransaction> list = new ArrayList<>();
        list.add(new RecurringTransaction(75000, "Quarterly", LocalDate.of(2016, Month.MARCH, 31), Period.ofMonths(3)));
        list.add(new RecurringTransaction(500, "Monthly", LocalDate.of(2016, Month.JANUARY, 5), Period.ofMonths(1)));
        list.add(new RecurringTransaction(999, "Monthly", LocalDate.of(2016, Month.JANUARY, 15), Period.ofMonths(1)));
        return list;
    }

    /**
     * Updates a budget to include a given list of recurring transactions. Note that if these transactions have already
     * been included in the budget, they will be added again and will not replace or update the previously included
     * transactions.
     *
     * @param budgets the budget to update
     * @param recurringTransactions the transactions to include in the budget
     */
    protected void populateBudget(List<Budget> budgets, List<RecurringTransaction> recurringTransactions) {
        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            populateBudget(budgets, recurringTransaction);
        }
    }

    /**
     * Updates a budget to include a given recurring transaction. Note that if this transaction has already
     * been included in the budget, it will be added again and will not replace or update the previously included
     * transaction(s).
     *
     * @param budgets the budget to update
     * @param recurringTransaction the transaction to include in the budget
     */
    protected void populateBudget(List<Budget> budgets, RecurringTransaction recurringTransaction) {
        for (Transaction transaction : recurringTransaction) {
            if (transaction.getDate().isBefore(budgetYear.getStartInclusive())) {
                continue;
            }
            if (!transaction.getDate().isBefore(budgetYear.getEndExclusive())) {
                break;
            }
            for (Budget budget : budgets) {
                addTransaction(budget, transaction);
            }
        }
    }

    private void addTransaction(Budget budget, Transaction transaction) {
        if (budget.getDateRange().contains(transaction.getDate())) {
            String category = transaction.getCategory();
            int runningTotal = getCategoryTotal(budget, category) + transaction.getAmount();
            budget.getLineItems().put(category, runningTotal);
        }
    }

    private int getCategoryTotal(Budget budget, String category) {
        return budget.getLineItems().containsKey(category) ? budget.getLineItems().get(category) : 0;
    }

    /**
     * Main entry point.
     *
     * @param args ignored... for now.
     */
    public static void main(String... args) {
        new Application(2016).run();
    }

}
