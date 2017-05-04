package abstractTree.instruction;

import abstractTree.expression.Expression;

public class ReturnInstruction extends Instruction{

    Expression returnExpression;

    public ReturnInstruction(Expression returnExpression, int declarationLineNumber) {
        super(declarationLineNumber);
        this.returnExpression = returnExpression;
    }

    @Override
    public boolean semanticErrorsDetected() {
        // TODO semanticErrorsDetected()
        return false;
    }

    @Override
    public String toString() {
        return String.format("Return expression: %s\n", returnExpression.toString());
    }

}
