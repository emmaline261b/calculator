package com.epam.jap.calc;

import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.text.NumberFormatter;
import java.util.Formatter;
import java.util.LinkedList;

public enum Notation {
    PREFIX {
        @Override
        public JAPTree createTree(String[] input) {
            return createPrefixTree(input, new JAPTree(), 0);
        }
        @Override
        public String convert(JAPTree tree) {
            if ((NumberUtils.isCreatable(tree.head)) && (tree.leftBranch == null) && (tree.rightBranch == null)) {
                return "" + tree.head;
            }

            if (tree.leftBranch != null && tree.rightBranch != null) {
                if ((NumberUtils.isCreatable(tree.leftBranch.head)) && (NumberUtils.isCreatable(tree.rightBranch.head))) {
                    return "" + tree.head + " " + tree.leftBranch.head + " " + tree.rightBranch.head;
                }
            }

            String result = "" + tree.head + " " + convert(tree.leftBranch) + " " + convert(tree.rightBranch);

            return result;
        }

    }, INFIX {
        @Override
        public JAPTree createTree(String[] inputArray) {
            JAPTree tree = new JAPTree();
            LinkedList<JAPTree> input = new LinkedList<>();
            for (int i = 0; i < inputArray.length; i++) {
                input.add(new JAPTree(inputArray[i]));
            }
            return createInfixTree(input, tree, 0);
        }

        @Override
        public String convert(JAPTree tree) {
            if ((NumberUtils.isCreatable(tree.head)) && (tree.leftBranch == null) && (tree.rightBranch == null)) {
                return "" + tree.head;
            }

            if (tree.leftBranch != null && tree.rightBranch != null) {
                if ((NumberUtils.isCreatable(tree.leftBranch.head)) && (NumberUtils.isCreatable(tree.rightBranch.head))) {
                    return "" + tree.leftBranch.head + " " + tree.head + " " + tree.rightBranch.head;
                }
            }

            String result = "" + convert(tree.leftBranch) + " " + tree.head + " " + convert(tree.rightBranch);

            return result;
        }
    },

    POSTFIX {
        @Override
        public JAPTree createTree(String[] input) {
            return createPostfixTree(input, new JAPTree(), input.length - 1);
        }

        public String convert(JAPTree tree) {
            if ((NumberUtils.isCreatable(tree.head)) && (tree.leftBranch == null) && (tree.rightBranch == null)) {
                return "" + tree.head;
            }

            if (tree.leftBranch != null && tree.rightBranch != null) {
                if ((NumberUtils.isCreatable(tree.leftBranch.head)) && (NumberUtils.isCreatable(tree.rightBranch.head))) {
                    return "" + tree.leftBranch.head + " " + tree.rightBranch.head + " " + tree.head;

                }
            }

            String result = "" + convert(tree.leftBranch) + " " + convert(tree.rightBranch) + " " + tree.head;

            return result;
        }

    }, EMPTY;

    private static JAPTree createPrefixTree(String[] input, JAPTree tree, int i) {
        //recursion-breaking condition
        if (i == input.length - 1) {
            if (NumberUtils.isCreatable(input[i])) {
                return new JAPTree(input[i]);
            } else throw new IllegalArgumentException("Wrong input!");
        }

        //op
        if (JAPOperator.getEnumByOperation(input[i]) != JAPOperator.EMPTY) {
            tree.head = input[i];
        }

        //op -> op
        if (JAPOperator.getEnumByOperation(input[i + 1]) != JAPOperator.EMPTY && tree.leftBranch == null) {
            tree.leftBranch = createPrefixTree(input, new JAPTree(), i + 1);
        }
        if (JAPOperator.getEnumByOperation(input[i + 1]) != JAPOperator.EMPTY && tree.leftBranch != null && tree.rightBranch == null) {
            tree.rightBranch = createPrefixTree(input, new JAPTree(), i + 1);
        }

        //op -> num
        if (NumberUtils.isCreatable(input[i + 1]) && tree.leftBranch == null) {
            tree.leftBranch = new JAPTree(input[i + 1]);
        }
        //op -> num -> num
        if (NumberUtils.isCreatable(input[i + 1]) && NumberUtils.isCreatable(input[i + 2])) {
            tree.rightBranch = new JAPTree(input[i + 2]);
        }
        //op -> num -> op
        if (NumberUtils.isCreatable(input[i + 1]) && JAPOperator.getEnumByOperation(input[i + 2]) != JAPOperator.EMPTY) {
            tree.rightBranch = createPrefixTree(input, new JAPTree(), i + 2);
        }
        return tree;
    }

    private static JAPTree createPostfixTree(String[] input, JAPTree tree, int i) {
        //recursion-breaking condition
        if (i == 0) {
            if (NumberUtils.isCreatable(input[i])) {
                return new JAPTree(input[i]);
            } else throw new IllegalArgumentException("Wrong input!");
        }

        //op
        if (JAPOperator.getEnumByOperation(input[i]) != JAPOperator.EMPTY) {
            tree.head = input[i];
        }

        //op -> op
        if (JAPOperator.getEnumByOperation(input[i - 1]) != JAPOperator.EMPTY && tree.leftBranch == null) {
            tree.leftBranch = createPostfixTree(input, new JAPTree(), i - 1);
        }
        if (JAPOperator.getEnumByOperation(input[i - 1]) != JAPOperator.EMPTY && tree.leftBranch != null && tree.rightBranch == null) {
            tree.rightBranch = createPostfixTree(input, new JAPTree(), i - 1);
        }

        //op -> num
        if (NumberUtils.isCreatable(input[i - 1]) && tree.leftBranch == null) {
            tree.leftBranch = new JAPTree(input[i - 1]);
        }
        //op -> num -> num
        if (NumberUtils.isCreatable(input[i - 1]) && NumberUtils.isCreatable(input[i - 2])) {
            tree.rightBranch = new JAPTree(input[i - 2]);
        }
        //op -> num -> op
        if (NumberUtils.isCreatable(input[i - 1]) && JAPOperator.getEnumByOperation(input[i - 2]) != JAPOperator.EMPTY) {
            tree.rightBranch = createPostfixTree(input, new JAPTree(), i - 2);
        }
        return tree;
    }

    private static JAPTree createInfixTree(LinkedList<JAPTree> input, JAPTree tree, int j) {
        input = checkForBrackets(input);
        LinkedList<JAPTree> mdList = new LinkedList<>();

        for (int i = 0; i < input.size(); i++) {

            if ((JAPOperator.getEnumByOperation(input.get(i).head) == JAPOperator.MULTIPLY) || (JAPOperator.getEnumByOperation(input.get(i).head) == JAPOperator.DIVIDE)) {
                JAPTree temp2 = new JAPTree(input.get(i).head, mdList.removeLast(), input.get(i + 1));
                mdList.add(temp2);
                i++;
                continue;
            }
            mdList.add(input.get(i));

        }

        LinkedList<JAPTree> asList = new LinkedList<>();
        for (int i = 0; i < mdList.size(); i++) {
            if ((JAPOperator.getEnumByOperation(mdList.get(i).head) == JAPOperator.ADD) || (JAPOperator.getEnumByOperation(mdList.get(i).head) == JAPOperator.SUBTRACT)) {
                JAPTree temp = new JAPTree(mdList.get(i).head, asList.removeLast(), mdList.get(i + 1));
                asList.add(temp);
                i++;
                continue;
            }
            asList.add(mdList.get(i));
        }

        return asList.get(0);
    }

    private static LinkedList<JAPTree> checkForBrackets(LinkedList<JAPTree> input) {
        boolean noBrackets = true;
        for (JAPTree tree : input) {
            if (JAPOperator.getEnumByOperation(tree.head) == JAPOperator.LEFTBRACKET) {
                noBrackets = false;
            }
        }
        if (noBrackets) return input;

        LinkedList<JAPTree> bracketList = new LinkedList<>();
        LinkedList<JAPTree> resultList = new LinkedList<>();
        for (int i = 0; i < input.size(); i++) {
            if (JAPOperator.getEnumByOperation(input.get(i).head) == JAPOperator.LEFTBRACKET) {
                bracketList.add(input.get(i));
                continue;
            }

            if (JAPOperator.getEnumByOperation(bracketList.get(0).head) == JAPOperator.LEFTBRACKET && JAPOperator.getEnumByOperation(input.get(i).head) != JAPOperator.RIGHTBRACKET) {
                bracketList.add(input.get(i));
                continue;
            }

            if (JAPOperator.getEnumByOperation(bracketList.get(0).head) == JAPOperator.LEFTBRACKET && JAPOperator.getEnumByOperation(input.get(i).head) == JAPOperator.RIGHTBRACKET) {
                bracketList.removeFirst();
                bracketList = checkForBrackets(bracketList);

                JAPTree bracketTree = createInfixTree(bracketList, new JAPTree(), 0);
                resultList.add(bracketTree);
                continue;
            }
            resultList.add(input.get(i));
        }

        return resultList;
    }


    public JAPTree createTree(String[] input) {
        return new JAPTree();
    }


    public String convert(JAPTree tree) {
        return "";
    }
}
