package com.volksys.recur;

import com.volksys.recur.model.BudgetPeriod;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit tests for {@link ApplicationTest}.
 */
public class ApplicationTest {

    /** Budget periods are generated correctly. */
    @Test
    public void testGetBudgetPeriods() {
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        Application application = new Application(zoneId, 2012);
        List<BudgetPeriod> budgetPeriodList = application.getBudgetPeriods();
        assertEquals(12, budgetPeriodList.size());
        int month = 1;
        for (BudgetPeriod period : budgetPeriodList) {
            ZonedDateTime start = period.getStartDate().atZone(zoneId);
            verify(start, 2012, month, 1);
            ZonedDateTime end = period.getEndDate().atZone(zoneId);
            if (month < 12) {
                verify(end, 2012, month + 1, 1);
            } else {
                verify(end, 2013, 1, 1);
            }
            month++;
        }
    }

    private void verify(ZonedDateTime temporal,
                        int expectedYear, int expectedMonth, int expectedDay) {
        verify(temporal, expectedYear, expectedMonth, expectedDay, 0, 0, 0, 0);
    }

    private void verify(ZonedDateTime temporal,
                        int expectedYear, int expectedMonth, int expectedDay,
                        int expectedHour, int expectedMinute, int expectedSecond,
                        long expectedNanoseconds) {
        assertEquals(expectedYear, temporal.getYear());
        assertEquals(expectedMonth, temporal.getMonthValue());
        assertEquals(expectedDay, temporal.getDayOfMonth());
        assertEquals(expectedHour, temporal.getHour());
        assertEquals(expectedMinute, temporal.getMinute());
        assertEquals(expectedSecond, temporal.getSecond());
        assertEquals(expectedNanoseconds, temporal.getNano());
    }


}
