package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import utils.ErrorPrinter;
import abstractTree.instruction.BlocInstruction;
import abstractTree.instruction.ForLoopInstruction;
import abstractTree.instruction.IfInstruction;
import abstractTree.instruction.Instruction;
import abstractTree.instruction.ReturnInstruction;
import abstractTree.instruction.WhileLoopInstrcution;

public class FunctionSymbol extends Symbol {

    String functionName;

	Type returnType;
	ArrayList<SimpleEntry<String, VariableSymbol>> parameters;
	BlocInstruction instructions;

	public FunctionSymbol(int declarationLinesNumber, Type returnType, ArrayList<SimpleEntry<String, VariableSymbol>> parameters, BlocInstruction instructions, String functionName) {
		super(declarationLinesNumber);
		this.functionName = functionName;
		this.returnType = returnType;
		this.parameters = parameters;
		this.instructions = instructions;
	}

	@Override
	public String toString(){
		String strToReturn =  String.format("Function. Return type: %s. No Ligne: %d. Parametres:\n", getReturnType(), declarationLineNumber);
		for(SimpleEntry<String, VariableSymbol> parameter: parameters){
			strToReturn += String.format("%s: (%s)\n", parameter.getKey(), parameter.toString());
		}
		strToReturn += String.format("Instructions:\n%s",instructions.toString());
		return strToReturn;
	}

	/**
	 * TODO: this function shoud be removed...
	 * @description: returns the returnType as a string
	 * @return
	 */
	public String getReturnType(){
		return Type.strType(returnType);
	}

	/**
	 * @description: returns the returnType of the function
	 * @return returns the returnType of the function
	 */
	public Type returnType(){
		return this.returnType;
	}

    /**
     * @description:
     * checks for semantic errors into the function instruction
     * checks that all the return types are of the correct type.
     * checks if we can execute the instructions without passing through a returnInstructions
     */
    @Override
    public boolean semanticErrorsDetected() {
        // enters the function bloc
        SymbolTable.getInstance().enterBloc(functionName);

        // checks for semantic errors into the function instruction (and return if any errors to avoid problems with undefined expressions)
        boolean errorsDetected = instructions.semanticErrorsDetected();
        if(errorsDetected){
            SymbolTable.getInstance().exitCurrentBloc();
            return errorsDetected;
        }

        // checks that all the return types are of the correct type.
        if(!allReturnTypesEquals(instructions, returnType)){
            errorsDetected = true;
        }

        // checks if we can execute the instructions without passing through a returnInstructions
        if(instructionsExecutableWithoutReturnInstruction(instructions, true)){
            errorsDetected = true;
        }

        SymbolTable.getInstance().exitCurrentBloc();
        return errorsDetected;
    }

    /**
     * @description: checks if we can execute the blocInstruction without passing through a ReturnInstruction.
     * @param: instructions : the instructions to browse
     * @param: logError: if true, the function will log the error. Else it will not log the error
     * @return true if we can execute the instructions without passing through a returnInstructions. Else returns false.
     */
    private boolean instructionsExecutableWithoutReturnInstruction(BlocInstruction instructions, boolean logError){
        // browse the instructions from the end
        for(int i=instructions.size()-1; i>=0; i--){
            Instruction instruction = instructions.get(i);

            // if we have a ReturnInstruction, return false
            if(instruction instanceof ReturnInstruction){
                return false;
            }

            // if we have a IfInstruction and it has both 'then' and 'else' instructions
            if(instruction instanceof IfInstruction){
                IfInstruction ifInstruction = (IfInstruction) instruction;
                if(ifInstruction.getThenBlockInstructions() != null && ifInstruction.getElseBlockInstructions() != null){
                    // if both ('then' and 'else' are'nt executable without passing through a return instruction, return false
                    if(  !instructionsExecutableWithoutReturnInstruction(ifInstruction.getThenBlockInstructions(), false)
                      && !instructionsExecutableWithoutReturnInstruction(ifInstruction.getElseBlockInstructions(), false) ){
                        return false;
                    }
                }
            }
        }

        if(logError){
            ErrorPrinter.getInstance().logError("Function "+functionName+" : There is a way to execute the function without returning an "+returnType+" expression.", declarationLineNumber);
        }
        return true;
    }

    /**
     * @description: search every return type into the instructionList and checks if its returnType equals returnType
     * If a return type doesnt equals return type, it logs the error into the ErrorPrinter and returns false.
     * @param instructionList the instructionList to browse
     * @param returnType returns false if a return type isnt equal to the returnType
     * @return
     */
    private boolean allReturnTypesEquals(BlocInstruction instructionList, Type returnType){
        boolean allReturnTypesEqualsReturnType = true;

        // browse all the instructions
        for(Instruction instruction : instructionList){

            // if it is a return instruction, check its returnType
            if(instruction instanceof ReturnInstruction){
                if( ((ReturnInstruction) instruction).getType() != returnType ){
                    ErrorPrinter.getInstance().logError(instruction.toString()+" : return instruction must return a "+Type.strType(returnType)+" expression", instruction.getDeclarationLineNumber());
                    allReturnTypesEqualsReturnType = false;
                }

            // if it is a IfInstruction, check for its "then" and "else" instructions
            }else if(instruction instanceof IfInstruction){
                IfInstruction ifInstructions = (IfInstruction) instruction;

                if(!allReturnTypesEquals(ifInstructions.getThenBlockInstructions(), returnType)){
                    allReturnTypesEqualsReturnType = false;
                }

                if( ifInstructions.getElseBlockInstructions() != null){
                    if(!allReturnTypesEquals(ifInstructions.getElseBlockInstructions(), returnType)){
                        allReturnTypesEqualsReturnType = false;
                    }
                }

            // if it is a ForLoopInstruction, checks for its instructions
            }else if(instruction instanceof ForLoopInstruction){
                ForLoopInstruction forLoopInstructions = (ForLoopInstruction) instruction;
                if(!allReturnTypesEquals(forLoopInstructions.getInstructions(), returnType)){
                    allReturnTypesEqualsReturnType = false;
                }

            // if it is a WhileLoopInstrcution, checks for its instructions
            }else if(instruction instanceof WhileLoopInstrcution){
                WhileLoopInstrcution whileLoopInstructions = (WhileLoopInstrcution) instruction;
                if(!allReturnTypesEquals(whileLoopInstructions.getInstructions(), returnType)){
                    allReturnTypesEqualsReturnType = false;
                }
            }

        }
        return allReturnTypesEqualsReturnType;
    }

    public int getNbParameters(){
        return parameters.size();
    }

    public Type getTypeOfParameter(int i){
        return parameters.get(i).getValue().type;
    }

}
