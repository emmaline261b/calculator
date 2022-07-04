package com.epam.jap.calc;

import java.math.BigDecimal;

public class Calc {

    private Notation notation;

    public String[] readInputIntoArray(String[] expected) {
        return expected;
    }

    public BigDecimal calculate(String[] args) {
        Notation notation = readNotation(args);
        String[] input = getUserMathInput(args);
        JAPTree tree = notation.createTree(input);
        System.out.println(tree);
        BigDecimal result = tree.calculateTree(tree);
        return result;
    }

    public Notation readNotation(String[] savedArgs) {
        if (savedArgs[0].toLowerCase().contains("prefix")) return Notation.PREFIX;
        if (savedArgs[0].toLowerCase().contains("infix")) return Notation.INFIX;
        if (savedArgs[0].toLowerCase().contains("postfix")) return Notation.POSTFIX;
        return Notation.EMPTY;
    }

    public void pickConversionOrEvaluation(String[] savedArgs) {
        Notation fromNotation;
        Notation toNotation;
        for (int i = 0; i < savedArgs.length; i++) {
            if (savedArgs[i].toLowerCase().contains("--convert=prefix")) {
                toNotation = Notation.PREFIX;
                System.out.println(convert(savedArgs, toNotation));
                break;
            }
            if (savedArgs[i].toLowerCase().contains("--convert=infix")) {
                toNotation = Notation.INFIX;
                System.out.println(convert(savedArgs, toNotation));
                break;
            }
            if (savedArgs[i].toLowerCase().contains("--convert=postfix")) {
                toNotation = Notation.POSTFIX;
                System.out.println(convert(savedArgs, toNotation));
                break;
            }
        }
        System.out.println(calculate(savedArgs));
    }

    private String convert(String[] savedArgs, Notation toNotation) {
        Notation fromNotation = readNotation(savedArgs);
        String[] input = getUserMathInput(savedArgs);
        JAPTree tree = fromNotation.createTree(input);
        System.out.println("notation: " + fromNotation.name() + ": " + tree);
        String result = tree.convertTo(tree, toNotation);
        return result;
    }


    String[] getUserMathInput(String[] savedArgs) {
        String[] result = new String[savedArgs.length - 1];
        for (int i = 0; i < savedArgs.length - 1; i++) {
            result[i] = savedArgs[i + 1];
        }
        return result;
    }

}
