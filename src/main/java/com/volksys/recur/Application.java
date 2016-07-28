package com.volksys.recur;

import com.volksys.recur.model.Budget;
import com.volksys.recur.model.LocalDateRange;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application.
 */
@SuppressWarnings("PMD.SystemPrintln")
public class Application {

    private final int year;

    protected Application(int year) {
        this.year = year;
    }

    /**
     * Performs the work.
     */
    private void run() {
        List<Budget> budgets = getBudgets();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        for (Budget period : budgets) {
            System.out.println(dtf.format(period.getDateRange().getStartInclusive()) + " - "
                    + dtf.format(period.getDateRange().getEndExclusive().minus(1, ChronoUnit.DAYS)) + ": $0.00");
        }
    }

    /**
     * Generates an ordered list of {@link Budget}s covering the entire year.
     *
     * @return a list of budget periods ready to be populated
     */
    protected List<Budget> getBudgets() {
        List<Budget> budgets = new ArrayList<>(12);
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
     * Main entry point.
     *
     * @param args ignored... for now.
     */
    public static void main(String... args) {
        new Application(2016).run();
    }

}
