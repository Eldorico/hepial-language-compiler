package abstractTree.instruction;

import symbol.Type;
import utils.ErrorPrinter;
import abstractTree.expression.Expression;

public class IfInstruction extends Instruction {

	Expression condition;
	BlocInstruction thenInstructions;
	BlocInstruction elseInstructions;

	public IfInstruction(Expression condition, BlocInstruction thenInstructions, BlocInstruction elseInstructions, int declarationLineNumber) {
	    super(declarationLineNumber);

		this.condition = condition;
		this.thenInstructions = thenInstructions;
		this.elseInstructions = elseInstructions;
	}

	@Override
	public String toString() {
		return String.format("IfInstruction: (%s) ?\nThen: \n%sElse:\n%sEndIfInstruction\n", condition.toString(), thenInstructions.toString(), elseInstructions.toString());
	}

    /**
     * @description: checks into the condition expression for semantic errors
     * checks that the condition is a boolean expression
     * checks into the thenInstructions and else Instructions for any semantic errors
     */
    @Override
    public boolean semanticErrorsDetected(){
        // checks into the condition expression for semantic errors
        boolean errorsDetected = condition.semanticErrorsDetected(declarationLineNumber);

        // checks that the condition is a boolean expression
        if(condition.getType() != Type.BOOLEAN){
            ErrorPrinter.getInstance().logError(condition.toString()+" : condition has to be a boolean expression.", declarationLineNumber);
            errorsDetected = true;
        }

        // checks into the thenInstructions and else Instructions for any semantic errors
        if(thenInstructions.semanticErrorsDetected()){
            errorsDetected = true;
        }
        if(elseInstructions != null && elseInstructions.semanticErrorsDetected()){
            errorsDetected = true;
        }

        return errorsDetected;
    }

    public BlocInstruction getThenBlockInstructions(){
        return thenInstructions;
    }

    public BlocInstruction getElseBlockInstructions(){
        return elseInstructions;
    }

}
