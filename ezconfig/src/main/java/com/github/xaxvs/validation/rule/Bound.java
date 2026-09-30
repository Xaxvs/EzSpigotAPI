package com.github.xaxvs.validation.rule;

import com.github.xaxvs.validation.ValidationRule;
import org.jspecify.annotations.NonNull;

import java.math.BigDecimal;

public class Bound<N extends Number> implements ValidationRule {

    private final BigDecimal upper;
    private final BigDecimal lower;


    /**
     * Creates a range where {@code lower <= value < upper}.
     * Arguments retain the order (upper, lower).
     * Numbers are compared using their decimal string representations.
     *
     * @throws NullPointerException if either bound is null
     * @throws IllegalArgumentException if a bound cannot be represented as a
     *         finite decimal, or lower is not less than upper
     */
    public Bound(@NonNull N upper, @NonNull N lower) {
        this.upper = toDecimal(upper);
        this.lower = toDecimal(lower);

        if (this.lower.compareTo(this.upper) >= 0) {
            throw new IllegalArgumentException("lower must be less than upper");
        }
    }

    @Override
    public <T> boolean isValid(T value) {
        if (!(value instanceof Number number)) {
            return false;
        }

        try {
            BigDecimal num = toDecimal(number);
            return num.compareTo(lower) >= 0 && num.compareTo(upper) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getErrReason() {
        return "Expected number to be between (inclusive) " + lower + " and " + upper ;
    }

    private static BigDecimal toDecimal(Number number) {
        if (number instanceof BigDecimal decimal) {
            return decimal;
        }
        return new BigDecimal(number.toString());
    }
}