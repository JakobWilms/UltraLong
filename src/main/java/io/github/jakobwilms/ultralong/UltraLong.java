package io.github.jakobwilms.ultralong;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Class for creating and computing UltraLong numbers <br>
 * Used when computing numbers greater than 2<sup>63</sup>-1 or less than -2<sup>63</sup>
 */
@SuppressWarnings("DuplicatedCode")
public class UltraLong implements LongNumber {

    /**
     * A constant holding the max number of digits an UltraLong can have
     */
    public static final int MAX_DIGITS = Integer.MAX_VALUE >> 1;

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

    /**
     * Construct a now UltraLong
     * @param initValue The value the UltraLong should have when initialized
     * @param maxDigits An Integer representing the max digits this UltraLong can have (can change)
     */
    public UltraLong(long initValue, int maxDigits) {
        if (!(maxDigits < UltraLong.MAX_DIGITS))
            throw new IllegalArgumentException("Max Digits to high: " + maxDigits);
        this.maxDigits = maxDigits;
        this.digits = new UltraLongDigit[maxDigits];
        initDigits(initValue);
    }

    /**
     * CopyConstructor <br>
     * Construct a now UltraLong
     * @param copy The UltraLong to copy
     */
    public UltraLong(UltraLong copy) {
        this.digits = new UltraLongDigit[copy.digits.length];
        for (int i = 0; i < copy.digits.length; i++) {
            this.digits[i] = new UltraLongDigit(copy.digits[i].getDigit());
        }
        this.maxDigits = copy.maxDigits;
        this.positive = copy.positive;
    }

    /**
     * Construct a now UltraLong <br>
     * initValue = 0
     * @param maxDigits An Integer representing the max digits this UltraLong can have (can change)
     */
    public UltraLong(int maxDigits) {
        this(0L, maxDigits);
    }

    /**
     * Construct a now UltraLong <br>
     * maxDigits = 100 <br>
     * initValue = 0
     */
    public UltraLong() {
        this(100);
    }

    /**
     * Construct a now UltraLong <br>
     * maxDigits = 100
     * @param initValue The value the UltraLong should have when initialized
     */
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
        if (!(i instanceof UltraLong))
            throw new UltraLongOperationFailedException("Not an UltraLong. Can not compute");
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
        if (!(i instanceof UltraLong))
            throw new UltraLongOperationFailedException("Not an UltraLong. Can not compute");
        subtract((UltraLong) i);
    }

    @Override
    public void sub(LongNumber i) {
        subtract(i);
    }

    @Override
    public boolean less(LongComparable other) {
        if (!(other instanceof UltraLong))
            throw new UltraLongOperationFailedException("No UltraLong. Can not compare!");
        UltraLong ultraLong = (UltraLong) other;

        if (!(this.isPositive() || ultraLong.isPositive())) {
            return greater(other);
        }
        if (!this.isPositive() && ultraLong.isPositive()) {
            return true;
        }
        if (this.isPositive() && !ultraLong.isPositive()) {
            return false;
        }

        boolean leadingZeroThis = true;
        boolean leadingZeroOther = true;
        int leadingZerosThis = 0;
        int leadingZerosOther = 0;

        for (int i = 0; i < Math.max(this.size(), ultraLong.size()); i++) {
            if (leadingZeroThis && this.getDigits()[i].getDigit() == '0') {
                leadingZerosThis++;
                if (leadingZeroOther && ultraLong.getDigits()[i].getDigit() == '0') {
                    leadingZerosOther++;
                } else {
                    leadingZeroOther = false;
                }
                continue;
            } else {
                leadingZeroThis = false;
            }
            if (leadingZeroOther && ultraLong.getDigits()[i].getDigit() == '0') {
                leadingZerosOther++;
                continue;
            }

            if (this.size() - leadingZerosThis < ultraLong.size() - leadingZerosOther) {
                return true;
            } else if (this.size() - leadingZerosThis > ultraLong.size() - leadingZerosOther) {
                return false;
            } else {
                break;
            }
        }

        int i = 0;
        while (true) {
            UltraLongDigit digitThis = this.getDigits()[leadingZerosThis + i];
            UltraLongDigit digitOther = ultraLong.getDigits()[leadingZerosOther + i];

            if (digitThis.less(digitOther)) {
                return true;
            } else if (digitThis.greater(digitOther)) {
                return false;
            }

            i++;
        }
    }

    @Override
    public boolean greater(LongComparable other) {
        if (!(other instanceof UltraLong))
            throw new UltraLongOperationFailedException("No UltraLong. Can not compare!");
        UltraLong ultraLong = (UltraLong) other;

        if (!(this.isPositive() || ultraLong.isPositive())) {
            return less(other);
        }
        if (this.isPositive() && !ultraLong.isPositive()) {
            return true;
        }
        if (!this.isPositive() && ultraLong.isPositive()) {
            return false;
        }

        boolean leadingZeroThis = true;
        boolean leadingZeroOther = true;
        int leadingZerosThis = 0;
        int leadingZerosOther = 0;

        for (int i = 0; i < Math.max(this.size(), ultraLong.size()); i++) {
            if (leadingZeroThis && this.getDigits()[i].getDigit() == '0') {
                leadingZerosThis++;
                if (leadingZeroOther && ultraLong.getDigits()[i].getDigit() == '0') {
                    leadingZerosOther++;
                } else {
                    leadingZeroOther = false;
                }
                continue;
            } else {
                leadingZeroThis = false;
            }
            if (leadingZeroOther && ultraLong.getDigits()[i].getDigit() == '0') {
                leadingZerosOther++;
                continue;
            }

            if (this.size() - leadingZerosThis > ultraLong.size() - leadingZerosOther) {
                return true;
            } else if (this.size() - leadingZerosThis < ultraLong.size() - leadingZerosOther) {
                return false;
            } else {
                break;
            }
        }

        int i = 0;
        while (true) {
            UltraLongDigit digitThis = this.getDigits()[leadingZerosThis + i];
            UltraLongDigit digitOther = ultraLong.getDigits()[leadingZerosOther + i];

            if (digitThis.greater(digitOther)) {
                return true;
            } else if (digitThis.less(digitOther)) {
                return false;
            }

            i++;
        }
    }

    @Override
    public boolean equals(LongComparable other) {
        if (!(other instanceof UltraLong))
            throw new UltraLongOperationFailedException("No UltraLong. Can not compare!");

        UltraLong ultraLong = (UltraLong) other;

        for (int i = 0; i < Math.max(this.size(), ultraLong.size()); i++) {
            if (i < this.size() && i < ultraLong.size()) {
                UltraLongDigit digitThis = this.getDigits()[this.size() - i - 1];
                UltraLongDigit digitOther = ultraLong.getDigits()[ultraLong.size() - i - 1];

                if (!digitThis.equals(digitOther)) {
                    return false;
                }
            } else {
                if (i >= this.size()) {
                    if (ultraLong.getDigits()[ultraLong.size() - i - 1].getDigit() != '0') {
                        return false;
                    }
                } else {
                    if (this.getDigits()[this.size() - i - 1].getDigit() != '0') {
                        return false;
                    }
                }
            }

        }
        return true;
    }

    @Override
    public boolean lessOrEquals(LongComparable other) {
        return equals(other) || less(other);
    }

    @Override
    public boolean greaterOrEquals(LongComparable other) {
        return equals(other) || greater(other);
    }

    /**
     * Add another UltraLong
     * @param other The Number to add
     */
    private void add(@NotNull UltraLong other) {
        if (!other.isPositive() && this.isPositive()) {
            UltraLong l = new UltraLong(other);
            l.setPositive(true);
            subtract(l);
            return;
        }
        if (!this.isPositive() && other.isPositive()) {
            UltraLong l = new UltraLong(other);
            this.setPositive(true);
            l.subtract(this);
            this.positive = l.positive;
            this.maxDigits = l.maxDigits;
            this.digits = l.digits;
            return;
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

    /**
     * Subtract another UltraLong
     * @param other The Number to subtract
     */
    private void subtract(@NotNull UltraLong other) {
        if (!this.isPositive() && other.isPositive()) {
            UltraLong l = new UltraLong(other);
            l.setPositive(false);
            add(l);
            return;
        }
        if (this.isPositive() && !other.isPositive()) {
            UltraLong l = new UltraLong(other);
            l.setPositive(true);
            add(l);
            return;
        }
        if (this.isPositive()) {
            if (this.less(other)) {
                this.setPositive(false);
            }
        } else {
            if (this.greater(other)) {
                this.setPositive(true);
            }
        }
        int max = Math.max(this.maxDigits, other.maxDigits);
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

    /**
     * Grow to maxDigits
     */
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

    /**
     * Initialize the value of this UltraLong - called by constructor
     * @param initValue The value to have
     */
    private void initDigits(long initValue) {
        String s = String.valueOf(initValue);
        if (s.charAt(0) == '-') {
            this.setPositive(false);
            s = s.substring(1);
        } else {
            this.setPositive(true);
        }
        int length = getDigits().length;
        for (int i = 0; i < length; i++) {
            digits[length - i - 1] = i < s.length() ? new UltraLongDigit(s.charAt(s.length() - i - 1)) : new UltraLongDigit();
        }
    }

    /**
     * Get the max number of digits
     * @return The max number of digits this UltraLong can have
     */
    public int getMaxDigits() {
        return maxDigits;
    }

    /**
     * Set the max number of digits
     * @param maxDigits The max number of digits this UltraLong can have
     *
     * @throws UltraLongOperationFailedException If the number is greater than UltraLong.MAX_DIGITS
     */
    public void setMaxDigits(int maxDigits) {
        if (maxDigits > UltraLong.MAX_DIGITS)
            throw new UltraLongOperationFailedException("maxDigits (" + maxDigits + ") greater than UltraLong.MAX_DIGITS (" + UltraLong.MAX_DIGITS + ")");
        this.maxDigits = maxDigits;
    }

    /**
     * Get the sign of the UltraLong
     * @return If the UltraLong is positive
     */
    public boolean isPositive() {
        return positive;
    }

    /**
     * Set the sign of the UltraLong
     * @param positive The sign to have (True = Positive, False = Negative)
     */
    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    /**
     * Get the digits
     * @return The Digits of the UltraLong
     */
    public UltraLongDigit[] getDigits() {
        return digits;
    }

    /**
     * Set the digits
     * @param digits The digits the UltraLong should have
     *
     * @throws UltraLongOperationFailedException When the list is the large ( > maxDigits)
     */
    public void setDigits(UltraLongDigit[] digits) {
        if (digits.length > this.maxDigits)
            throw new UltraLongOperationFailedException("To large list!");
        this.digits = digits;
    }

    /**
     * Get the size of the UltraLong
     * @return The Size of the UltraLong
     */
    public int size() {
        return getDigits().length;
    }

    /**
     * Get a String-Representation
     * @return A readable String-Representation of the UltraLong
     */
    @Override
    public String toString() {
        return asNumber(true);
    }

    /**
     * Get a String-Representation
     * @param readable If the Number should be separated with '_': 1_222_333
     *
     * @return A String-Representation of the UltraLong
     */
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
