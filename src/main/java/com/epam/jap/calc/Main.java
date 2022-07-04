package com.epam.jap.calc;

public class Main {

    private static String[] savedArgs;

    public static String[] getArgs() {
        return savedArgs;
    }

    public static void main(String[] args) {
        //savedArgs = new String[]{"--notation=infix", "2", "+", "2", "*", "5", "--convert=prefix"};
        savedArgs = args;
        Calc calc = new Calc();
        calc.pickConversionOrEvaluation(savedArgs);

    }

}
