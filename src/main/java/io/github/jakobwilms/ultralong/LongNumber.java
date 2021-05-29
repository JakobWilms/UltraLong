package io.github.jakobwilms.ultralong;

/**
 * Interface used for Numbers like {@link UltraLong}
 */
public interface LongNumber {

    void add(int i);

    void add(long i);

    void add(LongNumber i);

    void subtract(int i);

    void sub(int i);

    void subtract(long i);

    void sub(long i);

    void subtract(LongNumber i);

    void sub(LongNumber i);

    @Override
    String toString();

    String asNumber(boolean readable);

    String toObjectString();

}
