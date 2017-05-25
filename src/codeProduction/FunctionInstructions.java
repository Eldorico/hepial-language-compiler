package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.SymbolTable;
import symbol.Type;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;


/**
 * @description: this class represents the instructions of a block (a function)
 * The instructions will be written in jasmin code from this class, in the mainFunction of a Block.
 *
 */
class FunctionInstructions extends JasminCodeProducer{

    protected int stackSizeNeeded = 0;
    protected int localsSizeNeeded = 0;

    protected String blockMainFunctionName;
    protected String blockFunctionSignature;
    protected Type returnType;
    protected ArrayList<SimpleEntry<String, Type>> parametersType;  // varName, type

    FunctionInstructions(ArrayList<SimpleEntry<String, Type>> parametersType, Type returnType){
        this.parametersType = parametersType;
        this.blockMainFunctionName = SymbolTable.getInstance().getMainFunctionName();
        this.blockFunctionSignature = this.computeBlockFunctionSignature(parametersType);
        this.returnType = returnType;

        // TODO: write the jasmin code to put the locals into the fields, before we add more instructions via add functions
    }

    // default constructor needed by the child StaticMainInstructions
    protected FunctionInstructions(){}

    /**
     * @description:
     * @param blockNameToCall
     * @param blockNameOfFctOwner
     * @param parameters
     * @param returnType
     */
    void addFunctionCall(String blockNameToCall, String blockNameOfFctOwner, ExpressionList parameters, Type returnType){
        String blkNameToCall = CodeProducer.capitaliseFirstChar(blockNameToCall);
        String fctInvocationSignature = getFunctionCallSignature(parameters, returnType);
        String fctName = SymbolTable.getInstance().getMainFunctionName();

        int nbOfElementsOnStackNeeded = 3;

        addLine("");
        addIndentedLine("; invoke "+blkNameToCall+fctInvocationSignature);
        addIndentedLine("new "+blkNameToCall);
        addIndentedLine("dup");
        addIndentedLine("invokespecial "+blkNameToCall+"/<init>()V");
        addIndentedLine("invokevirtual "+blkNameToCall+"/"+fctName+fctInvocationSignature);

        stackSizeNeeded = Math.max(stackSizeNeeded, nbOfElementsOnStackNeeded);
    }

    void addReturnInstruction(Expression returnExpression){
        String returnExpressionStrRepresentation = (returnExpression == null) ? "null" : returnExpression.toString();
        addLine("");
        addIndentedLine("; return the following expression: "+returnExpressionStrRepresentation);
        // TODO: addReturnInstruction(): evaluate expression!
        // put the return value on the stack

        // add the correct return expression
        addIndentedLine(getReturnKeyWord());
    }

    /**
     * @description: returns the signature of the block's main function.
     * Signature is like : (III)V
     * @param parametersType
     * @return
     */
    protected String computeBlockFunctionSignature(ArrayList<SimpleEntry<String, Type>> parametersType){
        // TODO: computeBlockFunctionSignature
        return null;
    }

    private String getReturnKeyWord(){
        if(returnType == Type.BOOLEAN || returnType == Type.INTEGER){
            return "ireturn";
        }else if(returnType == Type.VOID){
            return "return";
        }else{
            // hope we dont arrive here...
            System.err.println("FunctionInstructions.getReturnKeyWord(): the return type isnt a boolean, an int, neither a void. \nreturnType = "+Type.strType(returnType)+"\n.Program will exit now");
            System.exit(-2);
            return ""; // haha
        }
    }

    private String getFunctionCallSignature(ExpressionList parameters, Type returnType){
        // TODO: getFunctionCallSignature()
        return new String("()"+Type.jTypeObject(returnType));
    }

    /**
     * @description: returns the jasmin code of the main function definition of the class,
     * with the instructions inside of it.
     */
    @Override
    String getJCodeAsString(){
        String linesBefore = "\n; main function declaration\n";
        linesBefore += ".method public "+blockMainFunctionName+blockFunctionSignature+"\n";
        linesBefore += (stackSizeNeeded > 0) ? indent()+".limit stack "+stackSizeNeeded+"\n" : "";
        linesBefore += (localsSizeNeeded > 0) ? indent()+".limit locals "+localsSizeNeeded+"\n" : "";
        jInstructions = linesBefore + jInstructions;
        addLine(".end method");

        return jInstructions;
    }
}
