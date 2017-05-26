package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;
import abstractTree.instruction.AffectationInstruction;
import abstractTree.instruction.WriteInstruction;


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

    FunctionInstructions(ArrayList<SimpleEntry<String, VariableSymbol>> parameters, Type returnType){
        this.blockMainFunctionName = SymbolTable.getInstance().getMainFunctionName();
        this.blockFunctionSignature = this.computeBlockFunctionSignature(parameters, returnType);
        this.returnType = returnType;
    }

    // default constructor needed by the child StaticMainInstructions
    protected FunctionInstructions(){}

    String getBlockFunctionSignature(){
        return blockFunctionSignature;
    }

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

        jtext.addLine("");
        jtext.addIndentedLine("; invoke "+blkNameToCall+fctInvocationSignature);
        jtext.addIndentedLine("new "+blkNameToCall);
        jtext.addIndentedLine("dup");
        jtext.addIndentedLine("invokespecial "+blkNameToCall+"/<init>()V");
        jtext.addIndentedLine("invokevirtual "+blkNameToCall+"/"+fctName+fctInvocationSignature);

        stackSizeNeeded = Math.max(stackSizeNeeded, nbOfElementsOnStackNeeded);
    }

    void addReturnInstruction(Expression returnExpression){
        String returnExpressionStrRepresentation = (returnExpression == null) ? "null" : returnExpression.toString();
        jtext.addLine("");
        jtext.addIndentedLine("; return the following expression: "+returnExpressionStrRepresentation);
        // TODO: addReturnInstruction(): evaluate expression!
        // put the return value on the stack

        // add the correct return expression
        jtext.addIndentedLine(getReturnKeyWord());
    }

    void addAffectationInstruction(AffectationInstruction instruction){
        // write comment
        jtext.addLine("");
        jtext.addIndentedLine("; compute "+instruction.toString());

        // load this
        jtext.addIndentedLine("aload 0");

        // get the jtext representing the computation of the source expression
        JasminExpression jexpression = JasminExpressionEvaluator.getInstance().jEvaluate(instruction.getSource());
        System.out.println(jexpression.jtext.getJCodeAsString());

    }

    void addWriteInstruction(WriteInstruction instruction){

    }

    /**
     * @description: returns the signature of the block's main function.
     * Signature is like : (III)V
     * @param parametersType
     * @return
     */
    protected String computeBlockFunctionSignature(ArrayList<SimpleEntry<String, VariableSymbol>> parameters, Type returnType){
        String signature = new String("(");
        for(SimpleEntry<String, VariableSymbol> entry : parameters){
            signature += Block.getJTypeAsStr(entry.getValue(), entry.getValue().type());
        }
        signature += ")"+Type.jTypeObject(returnType);
        return signature;
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
        linesBefore += (stackSizeNeeded > 0) ? jtext.indent()+".limit stack "+stackSizeNeeded+"\n" : "";
        linesBefore += (localsSizeNeeded > 0) ? jtext.indent()+".limit locals "+localsSizeNeeded+"\n" : "";
        jtext.insertBefore(linesBefore);
        jtext.addLine(".end method");

        return jtext.getJCodeAsString();
    }
}