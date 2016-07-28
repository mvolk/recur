package com.volksys.recur.model;

/**
 * Represents the amount of time that passes between occurrences of an event.
 */
public class Periodicity {
    private final int count;
    private final int units;

    /**
     * Constructor.
     *
     * @param count the count of units in each period.
     * @param units the unit of time measurement, given by a {@link java.util.Calendar} constant such as
     *              {@link java.util.Calendar#MONTH}.
     */
    public Periodicity(int count, int units) {
        this.count = count;
        this.units = units;
    }

    /**
     * The number of time units in each period.
     *
     * @return length of the period
     */
    public int getCount() {
        return count;
    }

    /**
     * The units of measurement for the length of each period
     *
     * @return amount of time in which time is expressed by {@link #getCount()}
     */
    public int getUnits() {
        return units;
    }
}
