package com.volksys.recur.model;

import java.time.LocalDate;

/**
 * A half-open date range.
 */
public class LocalDateRange {
    private final LocalDate startInclusive;
    private final LocalDate endExclusive;

    /**
     * Constructor.
     *
     * @param startInclusive dates before this date are excluded from the date range
     * @param endExclusive dates equal to or after this date are excluded from the date range
     * @throws IllegalArgumentException if {@code endExclusive} is not after {@code startInclusive}
     */
    public LocalDateRange(LocalDate startInclusive, LocalDate endExclusive) {
        if (!endExclusive.isAfter(startInclusive)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    /**
     * The beginning of the date range.
     *
     * @return the earliest date that is included in this date range
     */
    public LocalDate getStartInclusive() {
        return startInclusive;
    }

    /**
     * The end of the date range.
     *
     * @return the earliest date that after {@link #getStartInclusive()} and which is excluded from the date range.
     */
    public LocalDate getEndExclusive() {
        return endExclusive;
    }

    /**
     * Indicates whether a given date falls within this date range.
     *
     * @param localDate the date to test
     * @return {@code true} if the {@code localDate} falls within this date range; {@code false} otherwise
     */
    public boolean contains(LocalDate localDate) {
        return !localDate.isBefore(startInclusive) && localDate.isBefore(endExclusive);
    }

}
