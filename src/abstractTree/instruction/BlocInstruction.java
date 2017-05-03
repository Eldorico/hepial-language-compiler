package abstractTree.instruction;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @description:
 * This class represents a list of instructions.
 *
 */
public class BlocInstruction extends Instruction {

	LinkedList<Instruction> instructionList;

	public BlocInstruction(Instruction instruction) {
	    super(-1); // we should not use the declarationLineNumber of the bloc instruction. Only the declarationNumber of the instructions into the instructionList...

		// if the instruction is a BlocInstruction, print error. (it should not be constructed
		// with a BlocInstruction from the parser. Use the addInstruction() instead.
		if(instruction.getClass().equals(BlocInstruction.class)){
			System.err.printf("BlocInstruction.constructor: instruction is allready a BlockInstruction. BlockInstruction should be constructed with a BlocInstruction from the parser. Use the addInstruction() instead.");
		}

		this.instructionList = new LinkedList<Instruction>();
		this.instructionList.add(instruction);
	}

	/**
	 * @description: adds the instruction to the list
	 * @param instruction: the instruction to add to the BlocInstruction
	 */
	public void addInstruction(Instruction instruction){
		// if the instruction is a BlocInstruction, print error.
		if(instruction.getClass().equals(BlocInstruction.class)){
			System.err.printf("BlocInstruction.addInstruction(): instruction is allready a BlockInstruction. BlockInstruction should not be added to a BlockInstruction from the parser. I think it is an error.");
		}
		instructionList.addFirst(instruction);
	}

	@Override
	public String toString() {
		String stringToReturn = new String("");
		Iterator<Instruction> i = instructionList.iterator();
		while(i.hasNext()){
			stringToReturn += i.next().toString();
		}
		return stringToReturn;
	}

	/**
	 * @description: checks every instruction to see if a semantic error is detected.
	 * @return: true if an error has been detected. True if not.
	 */
//	@Override
//	public boolean semanticErrorsDetected(){
//	    boolean errorsDetected = false;
//	    for(Instruction instruction : instructionList){
//	        if(instruction.semanticErrorsDetected()){
//	            errorsDetected = true;
//	        }
//	    }
//	    return errorsDetected;
//	}

}
