package com.volksys.recur;

import com.volksys.recur.model.Budget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link ApplicationTest}.
 */
public class ApplicationTest {

    /** Budget periods are generated correctly. */
    @Test
    @DisplayName("Budget periods are generated for each month in the year")
    public void testGetBudgetPeriods() {
        Application application = new Application(2012);
        List<Budget> budgetPeriodList = application.getBudgets();
        assertEquals(12, budgetPeriodList.size());
        int month = 1;
        for (Budget period : budgetPeriodList) {
            LocalDate start = period.getDateRange().getStartInclusive();
            verify(start, 2012, month, 1);
            LocalDate end = period.getDateRange().getEndExclusive();
            if (month < 12) {
                verify(end, 2012, month + 1, 1);
            } else {
                verify(end, 2013, 1, 1);
            }
            month++;
        }
    }

    private void verify(LocalDate date, int expectedYear, int expectedMonth, int expectedDay) {
        assertEquals(expectedYear, date.getYear());
        assertEquals(expectedMonth, date.getMonthValue());
        assertEquals(expectedDay, date.getDayOfMonth());
    }

}
