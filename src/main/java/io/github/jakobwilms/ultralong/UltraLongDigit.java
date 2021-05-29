package io.github.jakobwilms.ultralong;

public class UltraLongDigit {

    public static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private char digit;

    public UltraLongDigit() {
        this(DIGITS[0]);
    }

    private UltraLongDigit(int init) {
        this(String.valueOf(init).charAt(0));
        if (String.valueOf(init).length() > 1) {
            throw new UltraLongOperationFailedException("UltraLongDigit can only have one character");
        }
    }

    public UltraLongDigit(char value) {
        if (possible(value)) {
            this.digit = value;
        } else {
            throw new IllegalArgumentException("Value not supported!  " + value);
        }
    }

    private boolean possible(char value) {
        for (char c : DIGITS) {
            if (c == value) {
                return true;
            }
        }
        return false;
    }

    public int add(UltraLongDigit other) {
        return add(other, 0);
    }

    public void add(int other) {
        add(new UltraLongDigit(other));
    }

    public int add(UltraLongDigit other, int i) {
        int value = Integer.parseInt(String.valueOf(getDigit()));
        int otherValue = Integer.parseInt(String.valueOf(other.getDigit()));
        String add = String.valueOf(value + otherValue + i);
        if (add.length() == 1) {
            this.digit = add.charAt(0);
            return 0;
        } else if (add.length() == 2) {
            this.digit = add.charAt(1);
            return add.charAt(0);
        } else {
            throw new UltraLongOperationFailedException("Added value to high: " + add);
        }
    }

    public int subtract(UltraLongDigit other, int cache) {
        int value = Integer.parseInt(String.valueOf(getDigit()));
        int otherValue = Integer.parseInt(String.valueOf(other.getDigit()));
        String subtract = String.valueOf(value - otherValue - cache);
        if (subtract.length() == 1) {
            this.digit = subtract.charAt(0);
            return 0;
        } else if (subtract.length() == 3) {
            this.digit = subtract.charAt(2);
            return 1;
        } else {
            throw new UltraLongOperationFailedException("Subtracted value to low / high: " + subtract);
        }
    }

    public void subtract(int other) {
        subtract(new UltraLongDigit(other));
    }

    public int subtract(UltraLongDigit other) {
        return subtract(other, 0);
    }

    public char getDigit() {
        return digit;
    }

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
