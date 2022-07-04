Feature: Reverse Polish Notation - evaluation

    Background:
        Given a source notation: "postfix"
        And no target notation

    Scenario Outline: A single number
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression | result        | comment                  |
            | 1          | 1             | A single positive number |
            | -1         | -1            | A single negative number |
            | 2147483648 | 2147483648    | A single big number      |
            | 1.1        | 1.1           | A floating-point number  |

    Scenario Outline: Simple addition
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        | comment                                 |
            | 0 0 +          | 0             | 0 is neutral for +                      |
            | 1 0 +          | 1             | 0 is neutral for + (L)                  |
            | 0 1 +          | 1             | 0 is neutral for + (R)                  |
            | 1 1 +          | 2             | 1 is not neutral for +                  |
            | 1 2 +          | 3             | + is commutative (1)                    |
            | 2 1 +          | 3             | + is commutative (2)                    |
            | 0 -1 +         | -1            | + works correctly with negative numbers |
            | 2147483647 1 + | 2147483648    | Integer overflow                        |
            | 1.1 2.2 +      | 3.3           | Floating-point numbers                  |

    Scenario Outline: Simple subtraction
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression      | result        | comment                                 |
            | 0 0 -           | 0             | 0 is neutral for - (1)                  |
            | 1 0 -           | 1             | 0 is neutral for - (2)                  |
            | 0 1 -           | -1            | - is not commutative                    |
            | 1 2 -           | -1            | - is not commutative (1)                |
            | 2 1 -           | 1             | - is not commutative (2)                |
            | 0 -1 -          | 1             | - works correctly with negative numbers |
            | -1 2147483647 - | -2147483648   | Integer underflow                       |
            | -1.1 2.2 -      | -3.3         | Negative floating-point numbers         |

    Scenario Outline: Simple multiplication
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        | comment                                      |
            | 0 0 *          | 0             | Anything multiplied by zero is 0 (1)         |
            | 1 0 *          | 0             | Anything multiplied by zero is 0 (2)         |
            | 0 1 *          | 0             | Anything multiplied by zero is 0 (3)         |
            | 1 1 *          | 1             | 1 is neutral for *                           |
            | 2 1 *          | 2             | 1 is neutral for *                           |
            | 1 2 *          | 2             | * is commutative (1)                         |
            | 2 1 *          | 2             | * is commutative (2)                         |
            | 2 -1 *         | -2            | If one is negative, the result is negative   |
            | -2 -1 *        | 2             | If boat are negative, the result is positive |
            | 1.1 1.1 *      | 1.21         | Floating-point numbers are rounded           |

    Scenario Outline: Simple division
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        | comment                                      |
            | 0 1 /          | 0             | 1 is neutral for /                           |
            | 1 1 /          | 1             | 1 is neutral for /                           |
            | 2 1 /          | 2             | 1 is neutral for /                           |
            | 3 2 /          | 1             | Integer division                             |
            | 4 2 /          | 2             | / is not commutative (1)                     |
            | 2 4 /          | 0             | / is not commutative (2)                     |
            |  2 -1 /        | -2            | If one is negative, the result is negative   |
            | -2 -1 /        | 2             | If boat are negative, the result is positive |
            | 2.2 2 /        | 1             | Floating-point numbers are rounded           |

    Scenario Outline: Compound expressions
        When I evaluate the following expression: "<expression>"
        Then I get the following result: "<result>"
        Examples:
            | expression     | result        | comment                                 |
            | 1 2 + 3 +      | 6             |                                         |
            | 1 2 - 3 +      | 2             |                                         |
            | 1 2 + 3 -      | 0             |                                         |
            | 1 2 - 3 -      | -4            |                                         |
            | 2 1 * 3 *      | 6             |                                         |
            | 2 1 / 3 *      | 6             |                                         |
            | 2 1 * 3 /      | 0             |                                         |
            | 2 1 / 3 /      | 0             |                                         |
            | 2 2 * 2 +      | 6             |                                         |
            | 2 2 + 2 *      | 8             |                                         |
            | 1 2 3 + +      | 6             |                                         |
            | 1 2 3 - +      | 0             |                                         |
            | 1 2 3 + -      | -4            |                                         |
            | 1 2 3 - -      | 2             |                                         |
            | 2 1 3 * *      | 6             |                                         |
            | 2 1 3 / *      | 0             |                                         |
            | 2 3 1 * /      | 0             |                                         |
            | 2 2 1 / /      | 1             |                                         |
            | 2 2 2 * +      | 6             |                                         |
            | 2 2 2 + *      | 8             |                                         |
