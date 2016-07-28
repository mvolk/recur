package com.volksys.recur.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Executable;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for {@link LocalDateRange}.
 */
public class LocalDateRangeTest {

    @Test
    @DisplayName("Constructor throws if end date is the same as the beginning date")
    public void testConstructorEmptyRange() {
        LocalDate now = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new LocalDateRange(now, now));
    }

    @Test
    @DisplayName("Constructor throws if end date is before the beginning date")
    public void testConstructorInvertedRange() {
        LocalDate now = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new LocalDateRange(now, now.minusDays(1)));
    }

    @Test
    @DisplayName("Constructor successful for valid range")
    public void testConstructorValidRange() {
        LocalDate now = LocalDate.now();
        new LocalDateRange(now, now.plusDays(1));
    }

    @Test
    @DisplayName("getStartInclusive returns the beginning of the range")
    public void testGetStartInclusive() {
        LocalDate now = LocalDate.now();
        assertEquals(now, new LocalDateRange(now, now.plusDays(1)).getStartInclusive());
    }

    @Test
    @DisplayName("getEndExclusive returns the end of the range")
    public void testGetEndExclusive() {
        LocalDate now = LocalDate.now();
        assertEquals(now.plusDays(1), new LocalDateRange(now, now.plusDays(1)).getEndExclusive());
    }

    @Test
    @DisplayName("A date prior to the range is not contained in the range")
    public void testContainsPrior() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.minusDays(1)));
    }

    @Test
    @DisplayName("A date after the range is not contained in the range")
    public void testContainsAfter() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.plusDays(3)));
    }

    @Test
    @DisplayName("A date in the range is contained in the range")
    public void testContainsIn() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertTrue(range.contains(now.plusDays(1)));
    }

    @Test
    @DisplayName("The start date of the range is contained in the range")
    public void testContainsStart() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertTrue(range.contains(now));
    }

    @Test
    @DisplayName("The end date of the range is not contained in the range")
    public void testContainsEnd() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.plusDays(2)));
    }

    private <T extends Throwable> void assertThrows(Class<T> expectedType, Executable executable) {
        //noinspection ThrowableResultOfMethodCallIgnored
        expectThrows(expectedType, executable);
    }

    private <T extends Throwable> T expectThrows(Class<T> expectedType, Executable executable) {
        try {
            executable.execute();
        } catch (Throwable actualException) {
            if (expectedType.isInstance(actualException)) {
                //noinspection unchecked
                return (T) actualException;
            } else {
                String msg = String.format("Expected %s to be thrown, but %s was thrown.", expectedType.getName(),
                        actualException.getClass().getName());
                throw new AssertionError(msg, actualException);
            }
        }
        String msg = String.format("Expected %s to be thrown, but nothing was thrown.", expectedType.getName());
        throw new AssertionError(msg);
    }

}
