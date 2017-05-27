package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.expression.AdditionExpression;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;
import abstractTree.expression.LesserThanExpression;
import abstractTree.expression.TabValueIdentifier;
import abstractTree.instruction.AffectationInstruction;
import abstractTree.instruction.BlocInstruction;
import abstractTree.instruction.ForLoopInstruction;
import abstractTree.instruction.GoToInstruction;
import abstractTree.instruction.IfInstruction;
import abstractTree.instruction.Instruction;
import abstractTree.instruction.ReadInstruction;
import abstractTree.instruction.WriteInstruction;


/**
 * @description: this class represents the instructions of a block (a function)
 * The instructions will be written in jasmin code from this class, in the mainFunction of a Block.
 *
 */
class FunctionInstructions extends JasminCodeProducer{

    protected int stackSizeNeeded = 0;
    protected int localsSizeNeeded = 0;

    protected int nbThenLabelsUsed = 0;
    protected int nbForLabelsUsed = 0;

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
     * @description: We add instructions from here.
     * @param instruction
     */
    void addInstruction(Instruction instruction){
        if(instruction instanceof AffectationInstruction){
            addAffectationInstruction((AffectationInstruction) instruction, false);
        }else if(instruction instanceof WriteInstruction){
            addWriteInstruction((WriteInstruction) instruction);
        }else if(instruction instanceof ReadInstruction){
            addReadInstruction((ReadInstruction) instruction);
        }else if(instruction instanceof IfInstruction){
            addIfInstruction((IfInstruction) instruction);
        }else if(instruction instanceof ForLoopInstruction){
            addForLoopInstruction((ForLoopInstruction)instruction);
        }else if(instruction instanceof GoToInstruction){
            addGoToInstruction((GoToInstruction)instruction);
        }
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

    protected void addReturnInstruction(Expression returnExpression){
        String returnExpressionStrRepresentation = (returnExpression == null) ? "null" : returnExpression.toString();
        jtext.addLine("");
        jtext.addIndentedLine("; return the following expression: "+returnExpressionStrRepresentation);
        // TODO: addReturnInstruction(): evaluate expression!
        // put the return value on the stack

        // add the correct return expression
        jtext.addIndentedLine(getReturnKeyWord());
    }

    protected void addAffectationInstruction(AffectationInstruction instruction, boolean sourceIsReadInstruction){
        int stackSizeNeededForInstruction = 0;
        int localsSizeNeededForInstruction = 0;
        VariableSymbol destinationSymbol =  (VariableSymbol)SymbolTable.getInstance().getSymbol(instruction.getDestination().getName());
        Identifier destinationIdentifier = instruction.getDestination();
        String currentBlockName = SymbolTable.getInstance().getCurrentBlockLocation();
        boolean destinationInParentBlock = (!destinationSymbol.getBlockName().equals(currentBlockName)) ? true : false;
        String destinationBlockName = destinationInParentBlock ? SymbolTable.getInstance().getMainBlockName() : CodeProducer.capitaliseFirstChar(currentBlockName);

        // write comment
        jtext.addLine("");
        if(sourceIsReadInstruction){
            jtext.addIndentedLine("; compute read <- "+instruction.getDestination().toString());
        }else{
            jtext.addIndentedLine("; compute "+instruction.toString());
        }

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
        if(sourceIsReadInstruction){
            jtext.addIndentedLine("new java/util/Scanner");
            jtext.addIndentedLine("dup");
            jtext.addIndentedLine("getstatic java/lang/System/in Ljava/io/InputStream;");
            jtext.addIndentedLine("invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V");
            jtext.addIndentedLine("invokevirtual java/util/Scanner/nextInt()I");
            stackSizeNeededForInstruction += 5;
        }else{
            JasminExpression jexpression = JasminExpressionEvaluator.getInstance().jEvaluate(instruction.getSource());
            jtext.addText(jexpression.getJCodeAsString());
            stackSizeNeededForInstruction += jexpression.maxStackSizeNeeded;
            localsSizeNeededForInstruction += jexpression.maxLocalsSizeNeeded;
        }

        // affect to the destination field
        if(instruction.getDestination() instanceof TabValueIdentifier){
            jtext.addIndentedLine("iastore");
        }else{
            jtext.addIndentedLine("putfield "+destinationBlockName+"/"+destinationIdentifier.getName()+" "+Block.getJTypeAsStr(destinationSymbol, destinationSymbol.type()));
        }


        // update the stack and locals size needed
        updateLocalsAndStackNeeded(stackSizeNeededForInstruction, localsSizeNeededForInstruction);
    }

    protected void addWriteInstruction(WriteInstruction instruction){
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
        if(instruction.getOutputExpression().getType() == Type.CST_STRING){
            jtext.addIndentedLine("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        }else{
            jtext.addIndentedLine("invokevirtual java/io/PrintStream/println(I)V");
        }

        // update the stack and locals size needed
        updateLocalsAndStackNeeded(stackSizeNeededForInstruction, localsSizeNeededForInstruction);
    }

    protected void addReadInstruction(ReadInstruction instruction){
        AffectationInstruction instr = new AffectationInstruction(instruction.getDestination(), null, instruction.getDeclarationLineNumber());
        addAffectationInstruction(instr, true);
    }

    protected void addIfInstruction(IfInstruction instruction){
        int stackSizeNeededForInstruction = 0;
        int localsSizeNeededForInstruction = 0;
        int labelsSuffix = nbThenLabelsUsed;
        nbThenLabelsUsed ++;

        // write comment
        jtext.addLine("");
        jtext.addIndentedLine("; compute "+instruction.getCondition().toString());

        // loads the condition representation and add the label to jump if
        JasminExpression jcondition = JasminExpressionEvaluator.getInstance().jEvaluate(instruction.getCondition());
        jtext.addText(jcondition.getJCodeAsString());
        stackSizeNeededForInstruction += jcondition.maxStackSizeNeeded;
        localsSizeNeededForInstruction += jcondition.maxLocalsSizeNeeded;

        // we should have a positive value on the stack if the condition is true. jump to 'Then' if we have a positive value.
        jtext.addIndentedLine("ifgt Then"+labelsSuffix);

        // produce the 'else' part and add jump to endif label
        for(Instruction elseInstruction: instruction.getElseBlockInstructions()){
            addInstruction(elseInstruction);
        }
        jtext.addIndentedLine("goto Endif"+labelsSuffix);

        // produce the 'then' part
        jtext.addLine("Then"+labelsSuffix+":");
        for(Instruction thenInstruction: instruction.getThenBlockInstructions()){
            addInstruction(thenInstruction);
        }

        // add the endif label
        jtext.addLine("Endif"+labelsSuffix+":");

        // end routine...
        updateLocalsAndStackNeeded(stackSizeNeededForInstruction, localsSizeNeededForInstruction);
    }

    protected void addForLoopInstruction(ForLoopInstruction instruction){

        Identifier i = instruction.getIdentifier();

        // write comment
        jtext.addLine("");
        jtext.addIndentedLine("; compute for loop: "+instruction.getLowerBoundExpression().toString()+" -> "+instruction.getUpperBoundExpression().toString());

        // add affectation instruction i = lowerBound
        addAffectationInstruction(new AffectationInstruction(instruction.getIdentifier(), instruction.getLowerBoundExpression(), -1), false);

        // add label For
        int suffixLabel = nbForLabelsUsed;
        nbForLabelsUsed ++;
        jtext.addLine("For"+suffixLabel+":");

        // update then instructions adding : i ++ and goto for
        BlocInstruction thenInstructions = instruction.getInstructions();
        thenInstructions.addInstructionAtEnd(new AffectationInstruction(i, new AdditionExpression(i, new IntNumber(1)), -1));
        thenInstructions.addInstructionAtEnd(new GoToInstruction(-1, "For"+suffixLabel));

        // add if condition: if identifier < upperBound, then instructions, else goto endFor
        LesserThanExpression condition = new LesserThanExpression(i, instruction.getUpperBoundExpression());
        GoToInstruction goToInstruction = new GoToInstruction(-1, "EndFor"+suffixLabel);
        BlocInstruction elseInstructions  = new BlocInstruction(goToInstruction);
        addIfInstruction(new IfInstruction(condition, thenInstructions, elseInstructions, -1));

        // add the endFor label
        jtext.addLine("EndFor"+suffixLabel+":");
    }

    protected void addGoToInstruction(GoToInstruction instruction){
        jtext.addLine("");
        jtext.addIndentedLine("; Go to: "+instruction.getLabel());
        jtext.addIndentedLine("goto "+instruction.getLabel());
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
