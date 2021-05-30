package io.github.jakobwilms.ultralong;

/**
 * Interface used for Numbers like {@link UltraLong}
 */
public interface LongNumber extends LongComparable {

    /**
     * Add an Integer
     * @param i The Number to add
     */
    void add(int i);

    /**
     * Add a Long
     * @param i The Number to add
     */
    void add(long i);

    /**
     * Add another LongNumber
     * @param i The Number to add
     */
    void add(LongNumber i);

    /**
     * Subtract an Integer
     * @param i The Number to subtract
     */
    void subtract(int i);

    /**
     * Subtract an Integer
     * @param i The Number to subtract
     */
    void sub(int i);

    /**
     * Subtract a Long
     * @param i The Number to subtract
     */
    void subtract(long i);

    /**
     * Subtract a Long
     * @param i The Number to subtract
     */
    void sub(long i);

    /**
     * Subtract another LongNumber
     * @param i The Number to subtract
     */
    void subtract(LongNumber i);

    /**
     * Subtract another LongNumber
     * @param i The Number to subtract
     */
    void sub(LongNumber i);

    /**
     * Get the Number as a String
     * @return The String-Representation of the Number
     */
    @Override
    String toString();

    /**
     * Get the Number as a String
     * @param readable If the Number should be separated with '_': 1_222_333
     *
     * @return The String-Representation of the Number
     */
    String asNumber(boolean readable);

    /**
     * Get a String containing all elements of the LongNumber
     * @return A String-Representation of the Number
     */
    String toObjectString();

}
