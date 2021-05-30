package io.github.jakobwilms.ultralong;

public interface LongComparable {

    /**
     * Compare
     * @param other The Number to compare
     *
     * @return Whether this Number is less than the other
     */
    boolean less(LongComparable other);

    /**
     * Compare
     * @param other The Number to compare
     *
     * @return Whether this Number is greater than the other
     */
    boolean greater(LongComparable other);

    /**
     * Compare
     * @param other The Number to compare
     *
     * @return Whether this Number is equal to the other
     */
    boolean equals(LongComparable other);

    /**
     * Compare
     * @param other The Number to compare
     *
     * @return Whether this Number is less or equal to the other
     */
    boolean lessOrEquals(LongComparable other);

    /**
     * Compare
     * @param other The Number to compare
     *
     * @return Whether this Number is less or equal to the other
     */
    boolean greaterOrEquals(LongComparable other);
}
