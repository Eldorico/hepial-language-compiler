package abstractTree.instruction;

import symbol.Type;
import abstractTree.expression.Expression;

public class ReturnInstruction extends Instruction{

    Expression returnExpression;

    public ReturnInstruction(Expression returnExpression, int declarationLineNumber) {
        super(declarationLineNumber);
        this.returnExpression = returnExpression;
    }

    public Type getType(){
        return returnExpression.getType();
    }

    @Override
    public String toString() {
        return String.format("Return expression: %s", returnExpression.toString());
    }

    public Expression getReturnExpression(){
        return returnExpression;
    }

    /**
     * @description: checks for errors into the return expression
     */
    @Override
    public boolean semanticErrorsDetected() {
        return returnExpression.semanticErrorsDetected(declarationLineNumber);
    }

}
