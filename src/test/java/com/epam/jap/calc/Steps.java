package com.epam.jap.calc;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import org.testng.Assert;

public class Steps {

    private String from;
    private String to;
    private String result;

    @Given("a source notation: {string}")
    public void setSourceNotation(String notation) {
        this.from = notation;
    }

    @Given("no target notation")
    public void resetTargetNotation() {
        to = null;
    }

    @Given("a target notation: {string}")
    public void setTargetNotation(String notation) {
        this.to = notation;
    }

    @When("I evaluate the resulting expression")
    public void evaluateExpression() throws IOException {
        this.to = null;
        evaluateExpression(result);
    }

    @When("I evaluate the following expression: {string}")
    public void evaluateExpression(String expression) throws IOException {
        processExpression(expression, from, null);
    }

    @When("I convert the following expression: {string}")
    public void convertExpression(String expression) throws IOException {
        processExpression(expression, from, to);
    }

    private void processExpression(String expression, String from, String to) throws IOException {
        String[] cmd = ("--from=" + from + (to != null ? " --to="+to : "")).split(" ");
        String[] expr = expression.split(" +");
        String[] args = Stream.of(cmd, expr)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
        System.err.println(String.join(" ", args));
        var os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));
        Main.main(args);
        try (var scanner = new Scanner(new ByteArrayInputStream(os.toByteArray()))) {
            result = scanner.nextLine();
        }
    }

    @Then("I get the following result: {string}")
    public void checkResult(String result) {
        Assert.assertEquals(this.result, result);
    }
}
