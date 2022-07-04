Feature: Conversion between notations

    Scenario Outline: Prefix to Postfix
        Given a source notation: "prefix"
        And a target notation: "postfix"
        When I convert the following expression: "<expression>"
        And I evaluate the resulting expression
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        |
            | + + 1 2 3      | 6             |
            | + - 1 2 3      | 2             |
            | - + 1 2 3      | 0             |
            | - - 1 2 3      | -4            |
            | * * 2 1 3      | 6             |
            | * / 2 1 3      | 6             |
            | / * 2 1 3      | 0             |
            | / / 2 1 3      | 0             |
            | + * 2 2 2      | 6             |
            | * + 2 2 2      | 8             |
            | + 1 + 2 3      | 6             |
            | + 1 - 2 3      | 0             |
            | - 1 + 2 3      | -4            |
            | - 1 - 2 3      | 2             |
            | * 2 * 1 3      | 6             |
            | * 2 / 1 3      | 0             |
            | / 2 * 3 1      | 0             |
            | / 2 / 2 1      | 1             |
            | + 2 * 2 2      | 6             |
            | * 2 + 2 2      | 8             |

    Scenario Outline: Prefix to Infix
        Given a source notation: "prefix"
        And a target notation: "infix"
        When I convert the following expression: "<expression>"
        And I evaluate the resulting expression
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        |
            | + + 1 2 3      | 6             |
            | + - 1 2 3      | 2             |
            | - + 1 2 3      | 0             |
            | - - 1 2 3      | -4            |
            | * * 2 1 3      | 6             |
            | * / 2 1 3      | 6             |
            | / * 2 1 3      | 0             |
            | / / 2 1 3      | 0             |
            | + * 2 2 2      | 6             |
            | * + 2 2 2      | 8             |
            | + 1 + 2 3      | 6             |
            | + 1 - 2 3      | 0             |
            | - 1 + 2 3      | -4            |
            | - 1 - 2 3      | 2             |
            | * 2 * 1 3      | 6             |
            | * 2 / 1 3      | 0             |
            | / 2 * 3 1      | 0             |
            | / 2 / 2 1      | 1             |
            | + 2 * 2 2      | 6             |
            | * 2 + 2 2      | 8             |
