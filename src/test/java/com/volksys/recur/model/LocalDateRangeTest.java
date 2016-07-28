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
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidCatchingGenericException"})
public class LocalDateRangeTest {

    /** Constructor must throw an IllegalArgumentException if the date range is empty. */
    @Test
    @DisplayName("Constructor throws if end date is the same as the beginning date")
    public void testConstructorEmptyRange() throws Throwable {
        LocalDate now = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new LocalDateRange(now, now));
    }

    /** Constructor must throw an IllegalArgumentException if the date range is inverted. */
    @Test
    @DisplayName("Constructor throws if end date is before the beginning date")
    public void testConstructorInvertedRange() throws Throwable {
        LocalDate now = LocalDate.now();
        assertThrows(IllegalArgumentException.class, () -> new LocalDateRange(now, now.minusDays(1)));
    }

    /** Constructor must not throw if the date range is valid. */
    @Test
    @DisplayName("Constructor successful for valid range")
    public void testConstructorValidRange() {
        LocalDate now = LocalDate.now();
        new LocalDateRange(now, now.plusDays(1));
    }

    /** Trivial getter test for coverage. */
    @Test
    @DisplayName("getStartInclusive returns the beginning of the range")
    public void testGetStartInclusive() {
        LocalDate now = LocalDate.now();
        assertEquals(now, new LocalDateRange(now, now.plusDays(1)).getStartInclusive());
    }

    /** Trivial getter test for coverage. */
    @Test
    @DisplayName("getEndExclusive returns the end of the range")
    public void testGetEndExclusive() {
        LocalDate now = LocalDate.now();
        assertEquals(now.plusDays(1), new LocalDateRange(now, now.plusDays(1)).getEndExclusive());
    }

    /** A date range does not contain dates that fall prior to the range. */
    @Test
    @DisplayName("A date prior to the range is not contained in the range")
    public void testContainsPrior() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.minusDays(1)));
    }

    /** A date range does not contain dates that fall after to the range. */
    @Test
    @DisplayName("A date after the range is not contained in the range")
    public void testContainsAfter() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.plusDays(3)));
    }

    /** A date range contains dates that fall inside the range. */
    @Test
    @DisplayName("A date in the range is contained in the range")
    public void testContainsIn() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertTrue(range.contains(now.plusDays(1)));
    }

    /** A date range contains dates that fall on the inclusive start of the range. */
    @Test
    @DisplayName("The start date of the range is contained in the range")
    public void testContainsStart() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertTrue(range.contains(now));
    }

    /** A date range does not contain dates that fall on the exclusive end date of the range. */
    @Test
    @DisplayName("The end date of the range is not contained in the range")
    public void testContainsEnd() {
        LocalDate now = LocalDate.now();
        LocalDateRange range = new LocalDateRange(now, now.plusDays(2));
        assertFalse(range.contains(now.plusDays(2)));
    }

    private <T extends Exception> void assertThrows(Class<T> expectedType, Executable executable) throws Throwable {
        //noinspection ThrowableResultOfMethodCallIgnored
        expectThrows(expectedType, executable);
    }

    private <T extends Exception> T expectThrows(Class<T> expectedType, Executable executable) throws Throwable {
        try {
            executable.execute();
        } catch (Exception actualException) {
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
