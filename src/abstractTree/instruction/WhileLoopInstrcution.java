package abstractTree.instruction;

import symbol.Type;
import utils.ErrorPrinter;
import abstractTree.expression.Expression;

/**
 * @description:
 * This class represents a while loop.
 * While condition
 * 	instructions
 * endWhile
 */
public class WhileLoopInstrcution extends Instruction { // TODO: correct the spelling of this class XD

	Expression condition;
	BlocInstruction instructions;

	public WhileLoopInstrcution(Expression condition, BlocInstruction instructions, int declarationLineNumber) {
	    super(declarationLineNumber);

		this.condition = condition;
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return String.format("WhileLoop: condition= (%s) do:\n%sEndWhileLoop", condition.toString(), instructions.toString());
	}

   /**
    * @description: checks into the condition expression for semantic errors
    * checks that the condition is a boolean expression
    * checks into the instructions for any semantic errors
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

        // checks the instruction
        if(instructions.semanticErrorsDetected()){
            errorsDetected = true;
        }

        return errorsDetected;
    }

    public BlocInstruction getInstructions(){
        return instructions;
    }
}
