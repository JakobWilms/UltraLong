package io.github.jakobwilms.ultralong;

public class UltraLongDigit implements LongComparable {

    /**
     * A constant holding all chars a digit can have
     */
    public static final char[] DIGITS;

    static {
        DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    /**
     * The Digit
     */
    private char digit;

    /**
     * Construct a new UltraLongDigit <br>
     * init = 0
     */
    public UltraLongDigit() {
        this(DIGITS[0]);
    }

    /**
     * Construct a new UltraLongDigit
     * @param init The value the digit should have
     */
    private UltraLongDigit(int init) {
        this(String.valueOf(init).charAt(0));
        if (String.valueOf(init).length() > 1) {
            throw new UltraLongOperationFailedException("UltraLongDigit can only have one character");
        }
    }

    /**
     * Construct a new UltraLongDigit
     * @param value The value the digit should have
     */
    public UltraLongDigit(char value) {
        if (possible(value)) {
            this.digit = value;
        } else {
            throw new IllegalArgumentException("Value not supported!  " + value);
        }
    }

    @Override
    public boolean less(LongComparable other) {
        return !greater(other) && !equals(other);
    }

    @Override
    public boolean greater(LongComparable other) {
        if (!(other instanceof UltraLongDigit))
            throw new UltraLongOperationFailedException("No UltraLongDigit. Can not compare!");
        UltraLongDigit otherDigit = (UltraLongDigit) other;
        char value = this.getDigit();
        switch (otherDigit.getDigit()) {
            case '0':
                return value != '0';
            case '1':
                return (value != '0') && (value != '1');
            case '2':
                return (value != '0') && (value != '1') && (value != '2');
            case '3':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3');
            case '4':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4');
            case '5':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4') && (value != '5');
            case '6':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4') && (value != '5') && (value != '6');
            case '7':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4') && (value != '5') && (value != '6') && (value != '7');
            case '8':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4') && (value != '5') && (value != '6') && (value != '7') && (value != '8');
            case '9':
                return (value != '0') && (value != '1') && (value != '2') && (value != '3') && (value != '4') && (value != '5') && (value != '6') && (value != '7') && (value != '8') && (value != '9');
            default:
                return false;
        }

    }

    @Override
    public boolean equals(LongComparable other) {
        if (!(other instanceof UltraLongDigit))
            throw new UltraLongOperationFailedException("No UltraLongDigit. Can not compare!");
        UltraLongDigit otherDigit = (UltraLongDigit) other;
        return this.getDigit() == otherDigit.getDigit();
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
     * Check a value
     * @param value The value to check
     *
     * @return If the Value is a possible digit
     */
    private boolean possible(char value) {
        for (char c : DIGITS) {
            if (c == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add another digit
     * @param other The UltraLongDigit to add
     */
    public void add(UltraLongDigit other) {
        add(other, 0);
    }

    /**
     * Add another number - only one digit
     * @param other The number to add
     */
    public void add(int other) {
        add(new UltraLongDigit(other));
    }

    /**
     * Add another number
     * @param other The UltraLongDigit to add
     * @param cache The cache to add
     *
     * @return The cache
     */
    public int add(UltraLongDigit other, int cache) {
        int value = Integer.parseInt(String.valueOf(getDigit()));
        int otherValue = Integer.parseInt(String.valueOf(other.getDigit()));
        String add = String.valueOf(value + otherValue + cache);
        if (add.length() == 1) {
            this.digit = add.charAt(0);
            return 0;
        } else if (add.length() == 2) {
            this.digit = add.charAt(1);
            String s = String.valueOf(add.charAt(0));
            return Integer.parseInt(s);
        } else {
            throw new UltraLongOperationFailedException("Added value to high: " + add);
        }
    }

    /**
     * Subtract another number
     * @param other The UltraLongDigit to subtract
     * @param cache The cache to subtract
     *
     * @return The subtract
     */
    public int subtract(UltraLongDigit other, int cache) {
        int value = Integer.parseInt(String.valueOf(getDigit()));
        int otherValue = Integer.parseInt(String.valueOf(other.getDigit()));
        String subtract = (value >= otherValue) ? String.valueOf(value - otherValue - cache) : String.valueOf((10 + value) - otherValue - cache);
        if (subtract.length() == 1) {
            this.digit = subtract.charAt(0);
            return (value >= otherValue) ? 0 : 1;
        } else {
            throw new UltraLongOperationFailedException("Subtracted value to low / high: " + subtract);
        }
    }

    /**
     * Subtract another number - only one digit
     * @param other The number to subtract
     */
    public void subtract(int other) {
        subtract(new UltraLongDigit(other));
    }

    /**
     * Subtract another number
     * @param other The UltraLongDigit to subtract
     */
    public void subtract(UltraLongDigit other) {
        subtract(other, 0);
    }

    /**
     * Get the digit
     * @return The Digit
     */
    public char getDigit() {
        return digit;
    }

    /**
     * Set the digit
     * @param digit The digit to set
     */
    public void setDigit(char digit) {
        this.digit = digit;
    }

    @Override
    public String toString() {
        return "UltraLongDigit{" +
                "digit=" + digit +
                '}';
    }

}
