package abstractTree.instruction;

import abstractTree.expression.Expression;

/**
 * @description:
 * This class represents a while loop.
 * While condition
 * 	instructions
 * endWhile
 */
public class WhileLoopInstrcution extends Instruction {

	Expression condition;
	BlocInstruction instructions;

	public WhileLoopInstrcution(Expression condition, BlocInstruction instructions, int declarationLineNumber) {
	    super(declarationLineNumber);

		this.condition = condition;
		this.instructions = instructions;
	}

	@Override
	public String toString() {
		return String.format("WhileLoop: condition= (%s) do:\n%sEndWhileLoop\n", condition.toString(), instructions.toString());
	}

    @Override
    public boolean semanticErrorsDetected(){
        // TODO: semanticErrorsDetected
        return false;
    }

}
