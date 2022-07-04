package com.epam.jap.calc;

import java.math.BigDecimal;

enum JAPOperator {
    ADD("+") {
        @Override
        BigDecimal calculate(BigDecimal x, BigDecimal y) {
            return x.add(y);
        }
    },
    SUBTRACT("-") {
        @Override
        BigDecimal calculate(BigDecimal x, BigDecimal y) {
            return x.subtract(y);
        }
    },
    MULTIPLY("*") {
        @Override
        BigDecimal calculate(BigDecimal x, BigDecimal y) {
            return x.multiply(y);
        }
    },
    DIVIDE("/") {
        @Override
        BigDecimal calculate(BigDecimal x, BigDecimal y) {
            return x.divide(y);
        }
    },
    LEFTBRACKET("("),
    RIGHTBRACKET(")"),
    EMPTY("");

    private final String operation;

    JAPOperator(String operation) {
        this.operation = operation;
    }

    static JAPOperator getEnumByOperation(String input) {
        for (JAPOperator jap : values()) {
            if (jap.operation.equals(input))
                return jap;
        }
        return EMPTY;
    }

    BigDecimal calculate(BigDecimal x, BigDecimal y) {
        return x;
    }

}
