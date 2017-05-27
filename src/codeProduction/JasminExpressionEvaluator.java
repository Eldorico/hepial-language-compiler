package codeProduction;

import symbol.ArraySymbol;
import symbol.FunctionSymbol;
import symbol.IntBoolSymbol;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.VariableSymbol;
import abstractTree.expression.AdditionExpression;
import abstractTree.expression.AndExpression;
import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.BooleanKeyword;
import abstractTree.expression.CstString;
import abstractTree.expression.DifferentThanExpression;
import abstractTree.expression.DivideExpression;
import abstractTree.expression.EqualEqualExpression;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;
import abstractTree.expression.FctCallExpression;
import abstractTree.expression.GreaterEqualExpression;
import abstractTree.expression.GreaterThanExpression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;
import abstractTree.expression.LesserEqualExpression;
import abstractTree.expression.LesserThanExpression;
import abstractTree.expression.MultiplyExpression;
import abstractTree.expression.NotExpression;
import abstractTree.expression.OrExpression;
import abstractTree.expression.RelationalBooleanExpression;
import abstractTree.expression.RelationalExpression;
import abstractTree.expression.SubstractionExpression;
import abstractTree.expression.TabValueIdentifier;


/**
 * @description: this class is a singleton instance that converts expressions as
 * JasminExpression.
 * It is used by FunctionInstructions to produce the function's / mainBlock instructions into
 * jasmin code.
 */
class JasminExpressionEvaluator implements JEvaluator {

    //int nbLocalsUsed = 1; // we have to start to 2 to leave some place for the this, and parent

    private static JasminExpressionEvaluator instance = new JasminExpressionEvaluator();
    public static JasminExpressionEvaluator getInstance(){
        return instance;
    }

    /**
     *@description: redirects the jEvaluate to the correct method
     */
    @Override
    public JasminExpression jEvaluate(Expression evaluable) {
        if(evaluable instanceof ArithmeticExpression){
            return jEvaluate((ArithmeticExpression)evaluable);
        }else if(evaluable instanceof RelationalBooleanExpression){
            return jEvaluate((RelationalBooleanExpression)evaluable);
        }else if(evaluable instanceof RelationalExpression){
            return jEvaluate((RelationalExpression)evaluable);
        }else if(evaluable instanceof BooleanKeyword){
            return jEvaluate((BooleanKeyword)evaluable);
        }else if(evaluable instanceof CstString){
            return jEvaluate((CstString)evaluable);
        }else if(evaluable instanceof ExpressionList){
            return jEvaluate((ExpressionList)evaluable);
        }else if(evaluable instanceof FctCallExpression){
            return jEvaluate((FctCallExpression)evaluable);
        }else if(evaluable instanceof Identifier){
            return jEvaluate((Identifier)evaluable);
        }else if(evaluable instanceof IntNumber){
            return jEvaluate((IntNumber)evaluable);
        }else if(evaluable instanceof NotExpression){
            return jEvaluate((NotExpression)evaluable);
        }else{
            System.out.println("JasminExpressionEvaluator: expression non evaluable: "+evaluable.getClass().getName());
            System.exit(-3);
            return null;
        }
    }

    /**
     *@description: evaluates an Arithmetic Expression
     *It puts the result on the operation on the stack.
     */
    @Override
    public JasminExpression jEvaluate(ArithmeticExpression evaluable) {
        // evaluate the left and right operand
        JasminExpression left  = jEvaluate(evaluable.getLeftOperand());
        JasminExpression right = jEvaluate(evaluable.getRightOperand());

        // merge the left and right operations
        JasminExpression toReturn = new JasminExpression();
        toReturn.addText(left.getJCodeAsString());
        toReturn.addText(right.getJCodeAsString());
        toReturn.maxStackSizeNeeded += left.maxStackSizeNeeded + right.maxStackSizeNeeded +1;
        toReturn.maxLocalsSizeNeeded += left.maxLocalsSizeNeeded + right.maxLocalsSizeNeeded;

        // do the math
        if(evaluable instanceof AdditionExpression){
            toReturn.addIndentedLine("iadd");
        }else if(evaluable instanceof SubstractionExpression){
            toReturn.addIndentedLine("isub");
        }else if(evaluable instanceof MultiplyExpression){
            toReturn.addIndentedLine("imul");
        }else if(evaluable instanceof DivideExpression){
            toReturn.addIndentedLine("idiv");
        }

        // update the stack size needed
        toReturn.maxStackSizeNeeded = Math.max(toReturn.maxStackSizeNeeded, 2);

        return toReturn;
    }

    /**
     *@description: evaluates an Relational Expression.
     *It puts a positive value on the stack if the result if true.
     *(puts a 0 or negative on the stack if the result is false).
     */
    @Override
    public JasminExpression jEvaluate(RelationalExpression evaluable) {
        // evaluate the left and right operand
        JasminExpression left  = jEvaluate(evaluable.getLeftOperand());
        JasminExpression right = jEvaluate(evaluable.getRightOperand());

        // merge the left and right operations, adding a zero between. (we will do the comparisons with long variables, so 2 stacks of int)
        JasminExpression toReturn = new JasminExpression();
        toReturn.addText(left.getJCodeAsString());
        toReturn.addIndentedLine("i2l");
        toReturn.addText(right.getJCodeAsString());
        toReturn.addIndentedLine("i2l");
        toReturn.maxStackSizeNeeded += left.maxStackSizeNeeded + right.maxStackSizeNeeded +2;
        toReturn.maxLocalsSizeNeeded += left.maxLocalsSizeNeeded + right.maxLocalsSizeNeeded;

        // add the relational element
        if(evaluable instanceof EqualEqualExpression){
            toReturn.addIndentedLine("lcmp ");
            toReturn.addIndentedLine("dup ");
            toReturn.addIndentedLine("imul ");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("ixor");
        }else if(evaluable instanceof DifferentThanExpression){
            toReturn.addIndentedLine("lcmp ");
            toReturn.addIndentedLine("dup ");
            toReturn.addIndentedLine("imul ");
        }else if(evaluable instanceof LesserThanExpression){
            toReturn.addIndentedLine("lcmp ");
            toReturn.addIndentedLine("ineg ");
        }else if(evaluable instanceof LesserEqualExpression){
            toReturn.addIndentedLine("lcmp");
            toReturn.addIndentedLine("ineg");
            toReturn.addIndentedLine("ldc 2");
            toReturn.addIndentedLine("imul");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("iadd");
        }else if(evaluable instanceof GreaterThanExpression){
            toReturn.addIndentedLine("lcmp");
        }else if(evaluable instanceof GreaterEqualExpression){
            toReturn.addIndentedLine("lcmp ");
            toReturn.addIndentedLine("ldc 2");
            toReturn.addIndentedLine("imul");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("iadd");
        }
        return toReturn;
    }

    @Override
    public JasminExpression jEvaluate(RelationalBooleanExpression evaluable){
        JasminExpression toReturn = new JasminExpression();


        if(evaluable instanceof AndExpression){
            // compute the left assignment.
            JasminExpression jleft = jEvaluate(evaluable.getLeftOperand());
            toReturn.maxLocalsSizeNeeded = Math.max(toReturn.maxLocalsSizeNeeded, jleft.maxLocalsSizeNeeded);
            toReturn.maxStackSizeNeeded = Math.max(toReturn.maxStackSizeNeeded, jleft.maxStackSizeNeeded);
            toReturn.addText(jleft.getJCodeAsString());
            // on the stack, we should have a negative, positive, or 0 value. convert it to -1, 0, 1
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("ldc 0");
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("lcmp");

            // compute the right assignment.
            JasminExpression jright = jEvaluate(evaluable.getRightOperand());
            toReturn.maxLocalsSizeNeeded += jright.maxLocalsSizeNeeded;
            toReturn.maxStackSizeNeeded += jright.maxStackSizeNeeded;
            toReturn.addText(jright.getJCodeAsString());

            // on the stack, we should have two negative, positive, or 0 value. Convert the first it to -1, 0, 1
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("ldc 0");
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("lcmp");

            // here, we have two int of values 1, 0, or -1
            toReturn.addIndentedLine("iadd");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("isub");
        }else if(evaluable instanceof OrExpression){
            // compute the left assignment.
            JasminExpression jleft = jEvaluate(evaluable.getLeftOperand());
            toReturn.maxLocalsSizeNeeded = Math.max(toReturn.maxLocalsSizeNeeded, jleft.maxLocalsSizeNeeded);
            toReturn.maxStackSizeNeeded = Math.max(toReturn.maxStackSizeNeeded, jleft.maxStackSizeNeeded);
            toReturn.addText(jleft.getJCodeAsString());
            // on the stack, we should have a negative, positive, or 0 value. convert it to -1, 0, 1
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("ldc 0");
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("lcmp");

            // multiply by 2, and add 1
            toReturn.addIndentedLine("ldc 2");
            toReturn.addIndentedLine("imul");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("iadd");

            // compute the right assignment.
            JasminExpression jright = jEvaluate(evaluable.getRightOperand());
            toReturn.maxLocalsSizeNeeded += jright.maxLocalsSizeNeeded;
            toReturn.maxStackSizeNeeded += jright.maxStackSizeNeeded;
            toReturn.addText(jright.getJCodeAsString());

            // on the stack, we should have two negative, positive, or 0 value. Convert the first it to -1, 0, 1
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("ldc 0");
            toReturn.addIndentedLine("i2l");
            toReturn.addIndentedLine("lcmp");

             // multiply by 2, and add 1
            toReturn.addIndentedLine("ldc 2");
            toReturn.addIndentedLine("imul");
            toReturn.addIndentedLine("ldc 1");
            toReturn.addIndentedLine("iadd");

            // add the two numbers
            toReturn.addIndentedLine("iadd");
        }

        return toReturn;
    }

    /**
     *@description: evaluates an Boolean keyword
     */
    @Override
    public JasminExpression jEvaluate(BooleanKeyword evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Cst String Expression
     *It puts the result on the stack.
     */
    @Override
    public JasminExpression jEvaluate(CstString evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.addIndentedLine("ldc "+evaluable.toString());
        toReturn.maxStackSizeNeeded = 1;
        return toReturn;

    }

    /**
     *@description: this method should not be used
     *It will exit the program and show an error
     */
    @Override
    public JasminExpression jEvaluate(ExpressionList evaluable) {
        System.out.println("JasminExpressionEvaluator: ExpressionList should not be evaluated. System will exit now");
        System.exit(-3);
        return null;

    }

    /**
     *@description: evaluates an FctCall Expression
     */
    @Override
    public JasminExpression jEvaluate(FctCallExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.addIndentedLine("TODO!");
        return toReturn;

    }


    /**
     *@description: evaluates an Identifier Expression
     *It puts the value affected to the identifier on the stack.
     */
    @Override
    public JasminExpression jEvaluate(Identifier evaluable) {
        JasminExpression toReturn = new JasminExpression();
        Symbol identifierSymbol =  SymbolTable.getInstance().getSymbol(evaluable.getName());
        boolean identifierDefinedInCurrentBlock = SymbolTable.getInstance().getCurrentBlockLocation().equals(identifierSymbol.getBlockName()) ? true : false;
        String identifierOwnerBlockName = CodeProducer.capitaliseFirstChar(identifierSymbol.getBlockName());
        String currentBlockNameWithCapitals = CodeProducer.capitaliseFirstChar(SymbolTable.getInstance().getCurrentBlockLocation());
        String identifierJTypeAsStr = Block.getJTypeAsStr((VariableSymbol)identifierSymbol, ((VariableSymbol) identifierSymbol).type());

        // if identifier represents an IntBoolSymbol or an array
        if(identifierSymbol instanceof IntBoolSymbol || identifierSymbol instanceof ArraySymbol){
            // if identifier is defined in the current block, get the field from the current block
            if(identifierDefinedInCurrentBlock){
                toReturn.addIndentedLine("aload 0");
                toReturn.addIndentedLine("getfield "+identifierOwnerBlockName+"/"+evaluable.getName()+" "+identifierJTypeAsStr);
                toReturn.maxStackSizeNeeded += 2;

            // else, get the field from the parents block
            }else{
                toReturn.addIndentedLine("aload 0");
                toReturn.addIndentedLine("getfield "+currentBlockNameWithCapitals+"/"+CodeProducer.lowerCaseFirstChar(identifierSymbol.getBlockName())+" L"+SymbolTable.getInstance().getMainBlockName()+";");
                toReturn.addIndentedLine("getfield "+identifierOwnerBlockName+"/"+evaluable.getName()+" "+identifierJTypeAsStr);
                toReturn.maxStackSizeNeeded += 3;
            }

            //if identifier represents an Array:
            if(evaluable instanceof TabValueIdentifier){
                // load the dimensions
                TabValueIdentifier tabValueIdentifier = (TabValueIdentifier) evaluable;
                for(int i=0; i<tabValueIdentifier.getNbIndexes(); i++){
                    JasminExpression indexJExpression = JasminExpressionEvaluator.getInstance().jEvaluate(tabValueIdentifier.getIndex(i));
                    toReturn.addText(indexJExpression.getJCodeAsString());
                    toReturn.maxStackSizeNeeded = Math.max(toReturn.maxStackSizeNeeded, 1+indexJExpression.maxStackSizeNeeded);
                    toReturn.maxLocalsSizeNeeded = Math.max(toReturn.maxLocalsSizeNeeded, 1+indexJExpression.maxLocalsSizeNeeded);
                }
                // load the array value
                toReturn.addIndentedLine("iaload");
            }

            return toReturn;

        // if identifier represents a FunctionSymbol
        }else if(identifierSymbol instanceof FunctionSymbol){
            toReturn.addIndentedLine(";TODO! JasminExpressionEvaluator.jEvaluate(Identifier evaluable): Identifier FunctionSymbol");
            return toReturn;

        // hope we dont arrive here...
        }else{
            System.err.println("JasminExpressionEvaluator.jEvaluate(Idenfifier evaluable): symbol could not been identified. Program will exit now.");
            System.exit(-3);
            return null; // for compilator
        }
    }

    /**
     *@description: evaluates an Int number Expression
     *It puts the value on the stack
     */
    @Override
    public JasminExpression jEvaluate(IntNumber evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.addIndentedLine("ldc "+evaluable.getValue());
        toReturn.maxStackSizeNeeded = 1;
        return toReturn;
    }

    /**
     *@description: evaluates an Not Expression
     */
    @Override
    public JasminExpression jEvaluate(NotExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.addIndentedLine("TODO!");
        return toReturn;

    }



}
