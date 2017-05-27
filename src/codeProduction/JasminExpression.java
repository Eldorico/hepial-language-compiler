package codeProduction;

/**
 * @description this class represents the jasmin code of an expression.
 * It contains the jtextCode (the jasmin code), the max stack size, and max locals size
 * needed to produce the jasmin code.
 */
class JasminExpression {

    int maxStackSizeNeeded = 0;
    int maxLocalsSizeNeeded = 0;
    JasminCodeText jtext = new JasminCodeText();

}
