package com.epam.jap.calc;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;

@Test
public class CalcTest {

    public void shouldReadAndUnderstandCommandLineArguments() {
        //given
        String[] expected = {"--notation=prefix", "+", "2", "2"};
        Calc calc = new Calc();
        //when
        String[] actual = calc.readInputIntoArray(expected);
        //then
        SoftAssert sf = new SoftAssert();
        for (int i = 0; i < actual.length; i++) {
            sf.assertEquals(actual[i], expected[i]);
        }
    }

    public void shouldThrowExceptionIfWrongNotationIsChosen() {
        //given
        Calc calc = new Calc();
        Main.main(new String[]{"--notation=prefi", "+", "2", "2"});
        //when
        String[] actual = calc.readInputIntoArray(Main.getArgs());
        Notation notation = calc.readNotation(actual);
        //then
        Assertions.assertThatIllegalArgumentException();
    }

    public void shouldUnderstandToPickPrefix() {
        //given
        Calc calc = new Calc();
        Main.main(new String[]{"--notation=prefix", "+", "2", "2"});
        //when
        String[] actual = calc.readInputIntoArray(Main.getArgs());
        Notation notation = calc.readNotation(actual);
        //then
        Assert.assertEquals(notation.name(), "PREFIX");
    }

    public void shouldUnderstandToPickInfixWithACapitalLetter() {
        //given
        Calc calc = new Calc();
        String[] actual = {"--notation=Infix", "+", "2", "2"};
        //when
        Notation notation = calc.readNotation(actual);
        //then
        Assert.assertEquals(notation.name(), "INFIX");
    }

    public void shouldCreatePrefixTree() {
        //given
        Calc calc = new Calc();
        //when
        String[] actualString = new String[]{"+", "2", "2"};
        Notation notation = Notation.PREFIX;
        //then
        JAPTree actual = notation.createTree(actualString);
        Assert.assertEquals(actual.toString(), "( 2 + 2 )");
    }

    public void shouldCreatePostfixTree() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualString = new String[]{"1", "2", "-", "3", "/"};
        Notation notation = Notation.POSTFIX;
        //then
        JAPTree actual = notation.createTree(actualString);

        Assert.assertEquals(actual.toString(), "( 3 / ( 2 - 1 ) )");
    }

    public void shouldCreateInfixTreeForSimpleAddition() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "+", "1"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( 2 + 1 )");
    }

    public void shouldCreateInfixTreeForSimpleSubtraction() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "-", "1", "-", "1"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 - 1 ) - 1 )");
    }

    public void shouldCreateInfixTreeForSimpleMultiplication() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "*", "1"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( 2 * 1 )");
    }

    public void shouldCreateInfixTreeForComplicatedMultiplication() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "*", "1", "*", "3"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 * 1 ) * 3 )");
    }

    public void shouldCreateInfixTreeForSimpleDivision() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "/", "1"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( 2 / 1 )");
    }

    public void shouldCreateInfixTreeForComplicatedDivision() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "/", "1", "/", "5"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 / 1 ) / 5 )");
    }

    public void shouldCreateInfixTreeForMixedAdditionAndSubtraction() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "+", "1", "-", "5"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 + 1 ) - 5 )");
    }

    public void shouldCreateInfixTreeForMixedMultiplicationAndDivision() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "*", "1", "/", "5"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 * 1 ) / 5 )");
    }

    public void shouldCreateInfixTreeForMixedAdditionAndMultiplication() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "+", "1", "*", "5"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( 2 + ( 1 * 5 ) )");
    }

    public void shouldCreateInfixTreeForMixedSubtractionAndDivision() {
        //given

        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "/", "1", "-", "5"};
        Notation notation = Notation.INFIX;
        //then
        JAPTree actual = notation.createTree(actualStringArray);

        Assert.assertEquals(actual.toString(), "( ( 2 / 1 ) - 5 )");
    }

    public void shouldCountTree() {
        //given
        Calc calc = new Calc();
        //when
        String[] actualStringArray = new String[]{"2", "/", "1", "-", "5"};
        Notation notation = Notation.INFIX;
        JAPTree tree = notation.createTree(actualStringArray);

        String whatTreeLooksLike = "( ( 2 / 1 ) - 5 )";
        BigDecimal expectedResult = BigDecimal.valueOf(-3);
        BigDecimal actualResult = tree.calculateTree(tree);
        //then
        Assert.assertEquals(actualResult, expectedResult);

    }

    public void shouldGetInfoAboutConversion(){
    //given

        // --convert=(prefix|infix|postfix)
    //when

    //then

    }



}