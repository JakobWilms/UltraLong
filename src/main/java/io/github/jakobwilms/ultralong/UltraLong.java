package io.github.jakobwilms.ultralong;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Class for creating and computing UltraLong numbers <br>
 * Used when computing numbers greater than 2<sup>63</sup>-1 or less than -2<sup>63</sup>
 */
public class UltraLong implements LongNumber{

    /**
     * A constant holding the max number of digits an UltraLong can have
     */
    public static final int MAX_DIGITS = 1000;
    /**
     * An Array containing all digits of the UltraLong <br>
     * Represents the Number of the UltraLong
     */
    private UltraLongDigit[] digits;
    /**
     * The max number of digits this UltraLong can have
     */
    private int maxDigits;
    /**
     * True = Positive <br>
     * False = Negative
     */
    private boolean positive;
    public UltraLong(long initValue, int maxDigits, boolean positive) {
        if (!(maxDigits < UltraLong.MAX_DIGITS)) throw new IllegalArgumentException("Max Digits to high: " + maxDigits);
        this.maxDigits = maxDigits;
        this.positive = positive;
        this.digits = new UltraLongDigit[maxDigits];
        initDigits(initValue);
    }

    public UltraLong(UltraLong copy) {
        this.digits = new UltraLongDigit[copy.digits.length];
        for (int i = 0; i < copy.digits.length; i++) {
            this.digits[i] = new UltraLongDigit(copy.digits[i].getDigit());
        }
        this.maxDigits = copy.maxDigits;
        this.positive = copy.positive;
    }

    public UltraLong(int maxDigits, boolean positive) {
        this(0L, maxDigits, positive);
    }

    public UltraLong(int maxDigits) {
        this(maxDigits, true);
    }

    public UltraLong() {
        this(100);
    }

    public UltraLong(long initValue, int maxDigits) {
        this(initValue, maxDigits, true);
    }

    public UltraLong(long initValue) {
        this(initValue, 100);
    }

    @Override
    public void add(int i) {
        add((long) i);
    }

    @Override
    public void add(long i) {
        add((LongNumber) new UltraLong(i, String.valueOf(i).length()));
    }

    @Override
    public void add(LongNumber i) {
        add((UltraLong) i);
    }

    @Override
    public void subtract(int i) {
        subtract((long) i);
    }

    @Override
    public void sub(int i) {
        subtract(i);
    }

    @Override
    public void subtract(long i) {
        subtract((LongNumber) new UltraLong(i, String.valueOf(i).length()));
    }

    @Override
    public void sub(long i) {
        subtract(i);
    }

    @Override
    public void subtract(LongNumber i) {
        subtract((UltraLong) i);
    }

    @Override
    public void sub(LongNumber i) {
        subtract(i);
    }

    private void add(@NotNull UltraLong other) {
        if (!other.isPositive()) {
            System.out.println("other negative");
            subtract(other);
            return;
        }
        if (!this.isPositive()) {
            UltraLong l = new UltraLong(other);
            l.subtract(this);
            this.maxDigits = l.maxDigits;
            this.digits = l.digits;
            this.positive = l.positive;
        }
        int max = Math.max(this.maxDigits, other.maxDigits);
        this.setMaxDigits(max + 1);
        this.grow();
        int cache = 0;
        int length = this.size();
        for (int i = 0; i < this.size() && i < other.size(); i++) {
            cache = this.getDigits()[length - i - 1].add(other.getDigits()[other.size() - i - 1], cache);
            if (i == this.size() - 1 || i == other.size() - 1) {
                this.getDigits()[0].add(cache);
            }
        }
    }

    private void subtract(@NotNull UltraLong other) {
        int max = Math.max(this.maxDigits, other.maxDigits);
        System.out.println("max: " + max);
        this.setMaxDigits(max + 1);
        this.grow();
        int cache = 0;
        for (int i = 0; i < this.size() && i < other.size(); i++) {
            cache = this.getDigits()[this.size() - i - 1].subtract(other.getDigits()[other.size() - i - 1], cache);
            if (i == this.size() - 1 || i == other.size() - 1) {
                this.getDigits()[0].subtract(cache);
            }
        }
    }

    private void grow() {
        if (getDigits().length < getMaxDigits()) {
            UltraLongDigit[] cache = new UltraLongDigit[getMaxDigits()];
            int oldLength = getDigits().length;
            for (int i = 0; i < cache.length; i++) {
                cache[cache.length - i - 1] = i < oldLength ? getDigits()[oldLength - i - 1] : new UltraLongDigit();
            }
            setDigits(cache);
        }
    }

    private void initDigits(long initValue) {
        String s = String.valueOf(initValue);
        int length = getDigits().length;
        for (int i = 0; i < length; i++) {
            digits[length - i - 1] = i < s.length() ? new UltraLongDigit(s.charAt(s.length() - i - 1)) : new UltraLongDigit();
        }
    }

    public int getMaxDigits() {
        return maxDigits;
    }

    public void setMaxDigits(int maxDigits) {
        this.maxDigits = maxDigits;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public UltraLongDigit[] getDigits() {
        return digits;
    }

    public void setDigits(UltraLongDigit[] digits) {
        this.digits = digits;
    }

    private int size() {
        return getDigits().length;
    }

    @Override
    public String toString() {
        return asNumber(true);
    }

    public String asNumber(boolean readable) {
        StringBuilder builder = new StringBuilder();
        boolean firstNull = true;
        for (UltraLongDigit digit : getDigits()) {
            if (!firstNull || digit.getDigit() != '0') {
                firstNull = false;
                builder.append(digit.getDigit());
            }
        }
        String string = builder.toString();
        int size = string.length();
        if (readable) {
            StringBuilder var1 = new StringBuilder();
            for (int i = 0; i < size; i++) {
                var1.append(string.charAt(i));
                if ((size - i - 1) % 3 == 0 && i + 1 != size) {
                    var1.append('_');
                }
            }
            return isPositive() ? var1.toString() : ("-" + var1);
        }
        return isPositive() ? builder.toString() : ("-" + builder);
    }

    public String toObjectString() {
        return "UltraLong{" +
                "maxDigits=" + maxDigits +
                ", positive=" + positive +
                ", digits=" + Arrays.toString(digits) +
                '}';
    }
}
