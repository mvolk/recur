package com.volksys.recur.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@link RecurringTransaction}.
 */
public class RecurringTransactionTest {

    /** Iterator returns the initial occurrence first. */
    @Test
    @DisplayName("Iterator starts with initial occurrence")
    public void testIteratorStartsAtInitialOccurrence() {
        LocalDate now = LocalDate.now();
        Period period = Period.ofMonths(1);
        RecurringTransaction recurringTransaction = new RecurringTransaction(20, "foo", now, period);
        Iterator<Transaction> iterator = recurringTransaction.iterator();
        assertEquals(now, iterator.next().getDate());
    }

    /** Iterator returns transactions in chronological order. */
    @Test
    @DisplayName("Iterator returns transactions in chronological order.")
    public void testIteratorRecurrencePeriod() {
        LocalDate now = LocalDate.now();
        Period period = Period.ofMonths(1);
        RecurringTransaction recurringTransaction = new RecurringTransaction(20, "foo", now, period);
        Iterator<Transaction> iterator = recurringTransaction.iterator();
        LocalDate current = now;
        for (int i = 0; i < 3; i++) {
            assertEquals(current, iterator.next().getDate());
            current = current.plus(period);
        }
    }

}
