package com.volksys.recur;

import com.volksys.recur.model.Budget;
import com.volksys.recur.model.LocalDateRange;
import com.volksys.recur.model.RecurringTransaction;
import com.volksys.recur.model.Transaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application.
 */
@SuppressWarnings({"PMD.SystemPrintln", "PMD.AvoidPrintStackTrace", "PMD.AvoidCatchingGenericException"})
public class Application {

    private static final String FIELD_DESCRIPTION = "Description";
    private static final String FIELD_AMOUNT = "Amount";
    private static final String FIELD_START = "Start";
    private static final String FIELD_END = "End";
    private static final String FIELD_PERIOD = "Period";
    private static final String FIELD_PERIOD_UNITS = "Units";
    private static final String FIELD_CATEGORY = "Category";
    private static final String FIELD_ENABLED = "Enabled";

    private final LocalDateRange budgetYear;
    private final String path;

    protected Application(int year, String path) {
        budgetYear = new LocalDateRange(LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year + 1, Month.JANUARY, 1));
        this.path = path;
    }

    /**
     * Performs the work.
     */
    private void run() throws IOException {
        List<Budget> budgets = getBudgets();
        List<RecurringTransaction> recurringTransactions = getRecurringTransactions();
        populateMonthlyBudgets(budgets, recurringTransactions);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        NumberFormat nf = new DecimalFormat("$#.00");
        for (Budget period : budgets) {
            System.out.println(dtf.format(period.getDateRange().getStartInclusive()) + " - "
                    + dtf.format(period.getDateRange().getEndExclusive().minus(1, ChronoUnit.DAYS)));
            System.out.println("=======================");
            for (String category : period.getCategories()) {
                System.out.println("  " + category + ": " + nf.format(toCurrency(period.getBudgetFor(category))));
                System.out.println("  - - - - - - - - - - - - - -");
                for (Transaction t : period.getTransactionsFor(category)) {
                    System.out.println("  " + t.getDate() + " " + t.getDescription() + " "
                            + nf.format(toCurrency(t.getAmount())));
                }
                System.out.println();
                System.out.println();
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
    protected List<RecurringTransaction> getRecurringTransactions() throws IOException {
        List<RecurringTransaction> list = new ArrayList<>();
        Reader in = new FileReader(path);
        for (CSVRecord record : CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in)) {
            boolean enabled = record.get(FIELD_ENABLED).equalsIgnoreCase("true");
            if (enabled) {
                addRecurringTransaction(record, list);
            }
        }
        return list;
    }

    private void addRecurringTransaction(CSVRecord record, List<RecurringTransaction> list) {
        String description = record.get(FIELD_DESCRIPTION);
        if (description != null) {
            description = description.trim();
        }
        int amount = (int) Math.round(100 * Double.parseDouble(record.get(FIELD_AMOUNT)));
        LocalDate start = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(record.get(FIELD_START)));
        String endString = record.get(FIELD_END);
        LocalDate end = null;
        if (endString != null && !endString.trim().isEmpty()) {
            end = LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(record.get(FIELD_END)));
        }
        int period = Integer.parseInt(record.get(FIELD_PERIOD));
        ChronoUnit units = ChronoUnit.valueOf(record.get(FIELD_PERIOD_UNITS));
        String category = record.get(FIELD_CATEGORY);

        RecurringTransaction recurringTransaction = new RecurringTransaction(description, amount, category,
                start, end, getPeriod(period, units));

        list.add(recurringTransaction);
    }

    private Period getPeriod(int period, ChronoUnit units) {
        switch (units) {
            case DAYS:
                return Period.ofDays(period);
            case WEEKS:
                return Period.ofWeeks(period);
            case MONTHS:
                return Period.ofMonths(period);
            case YEARS:
                return Period.ofYears(period);
            default:
                throw new IllegalArgumentException("Unsupported unit of time: " + units);
        }
    }

    /**
     * Updates a budget to include a given list of recurring transactions. Note that if these transactions have already
     * been included in the budget, they will be added again and will not replace or update the previously included
     * transactions.
     *
     * @param budgets the budget to update
     * @param recurringTransactions the transactions to include in the budget
     */
    protected void populateMonthlyBudgets(List<Budget> budgets, List<RecurringTransaction> recurringTransactions) {
        for (RecurringTransaction recurringTransaction : recurringTransactions) {
            populateMonthlyBudgets(budgets, recurringTransaction);
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
    protected void populateMonthlyBudgets(List<Budget> budgets, RecurringTransaction recurringTransaction) {
        for (Transaction transaction : recurringTransaction) {
            if (transaction.getDate().isBefore(budgetYear.getStartInclusive())) {
                continue;
            }
            if (!transaction.getDate().isBefore(budgetYear.getEndExclusive())) {
                break;
            }
            for (Budget budget : budgets) {
                budget.addTransaction(transaction);
            }
        }
    }

    /**
     * Main entry point.
     *
     * @param args ignored... for now.
     */
    public static void main(String... args) {
        try {
            int year = Integer.parseInt(args[0]);
            String path = args[1];
            Application application = new Application(year, path);
            try {
                application.run();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        } catch (Exception ignored) {
            System.out.println("Invoke with the four-digit year and path to CSV file as arguments, in that order");
        }
    }

}
