package com.volksys.recur;

import com.volksys.recur.model.BudgetPeriod;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application.
 */
@SuppressWarnings("PMD.SystemPrintln")
public class Application {

    private final ZoneId zoneId;
    private final int year;

    protected Application(ZoneId zoneId, int year) {
        this.zoneId = zoneId;
        this.year = year;
    }

    /**
     * Performs the work.
     */
    private void run() {
        List<BudgetPeriod> budgetPeriods = getBudgetPeriods();
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        for (BudgetPeriod period : budgetPeriods) {
            System.out.println(dtf.format(period.getStartDate().atZone(zoneId)) + " - "
                    + dtf.format(period.getEndDate().atZone(zoneId).minus(1, ChronoUnit.DAYS)) + ": $0.00");
        }
    }

    /**
     * Generates an ordered list of {@link BudgetPeriod}s covering the entire year.
     *
     * @return a list of budget periods ready to be populated
     */
    protected List<BudgetPeriod> getBudgetPeriods() {
        List<BudgetPeriod> budgetPeriods = new ArrayList<>(12);
        for (Month month : Month.values()) {
            budgetPeriods.add(createBudgetPeriod(year, month));
        }
        return budgetPeriods;
    }

    private BudgetPeriod createBudgetPeriod(int year, Month month) {
        LocalDate firstOfTheMonth = LocalDate.of(year, month, 1);
        ZonedDateTime start = firstOfTheMonth.atStartOfDay(zoneId);
        ZonedDateTime end = firstOfTheMonth.plusMonths(1).atStartOfDay(zoneId);
        return new BudgetPeriod(start.toInstant(), end.toInstant());
    }

    /**
     * Main entry point.
     *
     * @param args ignored... for now.
     */
    public static void main(String... args) {
        new Application(ZoneId.of("America/Los_Angeles"), 2016).run();
    }

}
