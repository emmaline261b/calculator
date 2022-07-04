package com.epam.jap.calc;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.ArrayList;

public class JAPTree {
    String head;
    JAPTree rightBranch;
    JAPTree leftBranch;

    public JAPTree() {
        this(null, null, null);
    }

    public JAPTree(String value) {
        this(value, null, null);
    }

    public JAPTree(JAPTree branch) {
        this(branch.head, branch.leftBranch, branch.rightBranch);
    }

    public JAPTree(String head, JAPTree leftBranch, JAPTree rightBranch) {
        this.head = head;
        this.leftBranch = leftBranch;
        this.rightBranch = rightBranch;
    }


    ArrayList<JAPTree> makeListFromStringArray(String[] array) {
        ArrayList<JAPTree> inputList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            inputList.add(new JAPTree(array[i]));
        }
        return inputList;
    }

    @Override
    public String toString() {
        if (leftBranch == null & rightBranch == null) return "" + head;
        if (leftBranch == null) return "" + head + rightBranch;
        if (rightBranch == null) return "" + leftBranch;

        return "( " + leftBranch + " " + head + " " + rightBranch + " )";

    }

    public BigDecimal calculateTree(JAPTree tree) {
        if (tree.head == null) return new BigDecimal(0);
        if ((tree.leftBranch == null) && (tree.rightBranch == null)) return new BigDecimal(tree.head);
        if (NumberUtils.isCreatable(tree.head)) return new BigDecimal(tree.head);

        BigDecimal x = BigDecimal.valueOf(1);
        BigDecimal y = BigDecimal.valueOf(1);

        if (tree.leftBranch != null) {
            if (NumberUtils.isCreatable(tree.leftBranch.head)) {
                x = new BigDecimal(tree.leftBranch.head);
            } else {
                x = calculateTree(tree.leftBranch);
            }
        }

        if (tree.rightBranch != null) {
            if (NumberUtils.isCreatable(tree.rightBranch.head)) {
                y = new BigDecimal(tree.rightBranch.head);
            } else {
                y = calculateTree(tree.rightBranch);
            }
        }

        tree.head = JAPOperator.getEnumByOperation(tree.head).calculate(x, y).toString();
        tree.leftBranch = null;
        tree.rightBranch = null;
        return new BigDecimal(tree.head);
    }

    public String convertTo(JAPTree tree, Notation toNotation) {
        return toNotation.convert(tree);

    }
}
