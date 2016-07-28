package com.volksys.recur;

import com.volksys.recur.model.BudgetPeriod;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit tests for {@link ApplicationTest}.
 */
public class ApplicationTest {

    /** Budget periods are generated correctly. */
    @Test
    public void testGetBudgetPeriods() {
        Application application = new Application(2012);
        List<BudgetPeriod> budgetPeriodList = application.getBudgetPeriods();
        assertEquals(12, budgetPeriodList.size());
        int month = 1;
        for (BudgetPeriod period : budgetPeriodList) {
            LocalDate start = period.getStartDate();
            verify(start, 2012, month, 1);
            LocalDate end = period.getEndDate();
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
