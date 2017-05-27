package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;
import abstractTree.expression.Identifier;
import abstractTree.expression.TabValueIdentifier;
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
        int stackSizeNeededForInstruction = 0;
        int localsSizeNeededForInstruction = 0;
        VariableSymbol destinationSymbol =  (VariableSymbol)SymbolTable.getInstance().getSymbol(instruction.getDestination().getName());
        Identifier destinationIdentifier = instruction.getDestination();
        String currentBlockName = SymbolTable.getInstance().getCurrentBlockLocation();
        boolean destinationInParentBlock = (!destinationSymbol.getBlockName().equals(currentBlockName)) ? true : false;
        String destinationBlockName = destinationInParentBlock ? SymbolTable.getInstance().getMainBlockName() : CodeProducer.capitaliseFirstChar(currentBlockName);

        // write comment
        jtext.addLine("");
        jtext.addIndentedLine("; compute "+instruction.toString());

        // if the destination is in the parent block, load the parent
        if(destinationInParentBlock){
            jtext.addIndentedLine("aload 0");
            jtext.addIndentedLine("aload 0");
            jtext.addIndentedLine("getfield "+SymbolTable.getInstance().getMainBlockName()+"/"+destinationIdentifier.getName()+" "+Block.getJTypeAsStr(destinationSymbol, destinationSymbol.type()));
            stackSizeNeededForInstruction = Math.max(stackSizeNeededForInstruction, 2);

        // else load this
        }else{
            jtext.addIndentedLine("aload 0");
            stackSizeNeededForInstruction = Math.max(stackSizeNeededForInstruction, 1);
        }

        // if we affect an array, the array must come first
        if(instruction.getDestination() instanceof TabValueIdentifier){
            // get the array
            TabValueIdentifier tabIdentifier = (TabValueIdentifier) instruction.getDestination();
            jtext.addIndentedLine("getfield "+destinationBlockName+"/"+destinationIdentifier.getName()+" "+Block.getJTypeAsStr(destinationSymbol, destinationSymbol.type()));

            // get the values of the indexes
            for(int i=0; i<tabIdentifier.getNbIndexes(); i++){
                JasminExpression indexJExpression = JasminExpressionEvaluator.getInstance().jEvaluate(tabIdentifier.getIndex(i));
                jtext.addText(indexJExpression.getJCodeAsString());
                stackSizeNeededForInstruction = Math.max(stackSizeNeededForInstruction, 1+indexJExpression.maxStackSizeNeeded);
                localsSizeNeededForInstruction = Math.max(localsSizeNeededForInstruction, 1+indexJExpression.maxLocalsSizeNeeded);
            }

        }

        // get the jtext representing the computation of the source expression and add it to the instructions
        JasminExpression jexpression = JasminExpressionEvaluator.getInstance().jEvaluate(instruction.getSource());
        jtext.addText(jexpression.getJCodeAsString());
        stackSizeNeededForInstruction += jexpression.maxStackSizeNeeded;
        localsSizeNeededForInstruction += jexpression.maxLocalsSizeNeeded;

        // affect to the destination field
        if(instruction.getDestination() instanceof TabValueIdentifier){
            jtext.addIndentedLine("iastore");
        }else{
            jtext.addIndentedLine("putfield "+destinationBlockName+"/"+destinationIdentifier.getName()+" "+Block.getJTypeAsStr(destinationSymbol, destinationSymbol.type()));
        }


        // update the stack and locals size needed
        updateLocalsAndStackNeeded(stackSizeNeededForInstruction, localsSizeNeededForInstruction);
    }

    void addWriteInstruction(WriteInstruction instruction){
        int stackSizeNeededForInstruction = 0;
        int localsSizeNeededForInstruction = 0;

        // write comment
        jtext.addLine("");
        jtext.addIndentedLine("; compute "+instruction.toString());

        // load the static print ln
        jtext.addIndentedLine("getstatic java/lang/System/out Ljava/io/PrintStream;");
        stackSizeNeededForInstruction += 1;

        // get the jasmin representation of the expression to write
        JasminExpression jexpression = JasminExpressionEvaluator.getInstance().jEvaluate(instruction.getOutputExpression());
        jtext.addText(jexpression.getJCodeAsString());
        stackSizeNeededForInstruction += jexpression.maxStackSizeNeeded;
        localsSizeNeededForInstruction += jexpression.maxLocalsSizeNeeded;

        // invoke the print function
        jtext.addIndentedLine("invokevirtual java/io/PrintStream/println(I)V");

        // update the stack and locals size needed
        updateLocalsAndStackNeeded(stackSizeNeededForInstruction, localsSizeNeededForInstruction);
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

    private void updateLocalsAndStackNeeded(int potentialMaxStackNeeded, int potentialMaxLocalsNeeded){
        stackSizeNeeded = Math.max(stackSizeNeeded, potentialMaxStackNeeded);
        localsSizeNeeded = Math.max(localsSizeNeeded, potentialMaxLocalsNeeded);
    }

    /**
     * @description:
     * @return
     */
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
