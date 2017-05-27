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
import abstractTree.expression.SubstractionExpression;


/**
 * @description: this class is a singleton instance that converts expressions as
 * JasminExpression.
 * It is used by FunctionInstructions to produce the function's / mainBlock instructions into
 * jasmin code.
 */
class JasminExpressionEvaluator implements JEvaluator {

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
        }else if(evaluable instanceof AndExpression){
            return jEvaluate((AndExpression)evaluable);
        }else if(evaluable instanceof BooleanKeyword){
            return jEvaluate((BooleanKeyword)evaluable);
        }else if(evaluable instanceof CstString){
            return jEvaluate((CstString)evaluable);
        }else if(evaluable instanceof DifferentThanExpression){
            return jEvaluate((DifferentThanExpression)evaluable);
        }else if(evaluable instanceof EqualEqualExpression){
            return jEvaluate((EqualEqualExpression)evaluable);
        }else if(evaluable instanceof ExpressionList){
            return jEvaluate((ExpressionList)evaluable);
        }else if(evaluable instanceof FctCallExpression){
            return jEvaluate((FctCallExpression)evaluable);
        }else if(evaluable instanceof GreaterEqualExpression){
            return jEvaluate((GreaterEqualExpression)evaluable);
        }else if(evaluable instanceof GreaterThanExpression){
            return jEvaluate((GreaterThanExpression)evaluable);
        }else if(evaluable instanceof Identifier){
            return jEvaluate((Identifier)evaluable);
        }else if(evaluable instanceof IntNumber){
            return jEvaluate((IntNumber)evaluable);
        }else if(evaluable instanceof LesserEqualExpression){
            return jEvaluate((LesserEqualExpression)evaluable);
        }else if(evaluable instanceof LesserThanExpression){
            return jEvaluate((LesserThanExpression)evaluable);
        }else if(evaluable instanceof NotExpression){
            return jEvaluate((NotExpression)evaluable);
        }else if(evaluable instanceof OrExpression){
            return jEvaluate((OrExpression)evaluable);
        }else{
            System.out.println("JasminExpressionEvaluator: expression non evaluable: "+evaluable.getClass().getName());
            System.exit(-3);
            return null;
        }
    }

    /**
     *@description: evaluates an Arithmetic Expression
     */
    @Override
    public JasminExpression jEvaluate(ArithmeticExpression evaluable) {
        // evaluate the left and right operand
        JasminExpression left  = jEvaluate(evaluable.getLeftOperand());
        JasminExpression right = jEvaluate(evaluable.getRightOperand());

        // merge the left and right operations
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addText(left.jtext.getJCodeAsString());
        toReturn.jtext.addText(right.jtext.getJCodeAsString());
        toReturn.maxStackSizeNeeded += left.maxStackSizeNeeded + right.maxStackSizeNeeded;
        toReturn.maxLocalsSizeNeeded += left.maxLocalsSizeNeeded + right.maxLocalsSizeNeeded;

        // debug
        toReturn.jtext.addIndentedLine("; left needed "+left.maxStackSizeNeeded+" for "+evaluable.getLeftOperand().toString());
        toReturn.jtext.addIndentedLine("; right needed "+right.maxStackSizeNeeded+" for "+evaluable.getRightOperand().toString());

        // do the math
        if(evaluable instanceof AdditionExpression){
            toReturn.jtext.addIndentedLine("iadd");
        }else if(evaluable instanceof SubstractionExpression){
            toReturn.jtext.addIndentedLine("isub");
        }else if(evaluable instanceof MultiplyExpression){
            toReturn.jtext.addIndentedLine("imul");
        }else if(evaluable instanceof DivideExpression){
            toReturn.jtext.addIndentedLine("idiv");
        }

        // update the stack size needed
        toReturn.maxStackSizeNeeded = Math.max(toReturn.maxStackSizeNeeded, 2);

        return toReturn;
    }

    /**
     *@description: evaluates an And Expression
     */
    @Override
    public JasminExpression jEvaluate(AndExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Boolean keyword
     */
    @Override
    public JasminExpression jEvaluate(BooleanKeyword evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Cst String Expression
     */
    @Override
    public JasminExpression jEvaluate(CstString evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Different than Expression
     */
    @Override
    public JasminExpression jEvaluate(DifferentThanExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an EqualEqual Expression
     */
    @Override
    public JasminExpression jEvaluate(EqualEqualExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: this method should not be used
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
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Greather Equal Expression
     */
    @Override
    public JasminExpression jEvaluate(GreaterEqualExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Greather Than Expression
     */
    @Override
    public JasminExpression jEvaluate(GreaterThanExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Identifier Expression
     */
    @Override
    public JasminExpression jEvaluate(Identifier evaluable) {
        JasminExpression toReturn = new JasminExpression();
        Symbol identifierSymbol =  SymbolTable.getInstance().getSymbol(evaluable.getName());
        boolean identifierDefinedInCurrentBlock = SymbolTable.getInstance().getCurrentBlockLocation().equals(identifierSymbol.getBlockName()) ? true : false;
        //String identifierOwnerBlockName = identifierDefinedInCurrentBlock ? CodeProducer.capitaliseFirstChar(identifierSymbol.)
        String identifierOwnerBlockName = CodeProducer.capitaliseFirstChar(identifierSymbol.getBlockName());
        String currentBlockNameWithCapitals = CodeProducer.capitaliseFirstChar(SymbolTable.getInstance().getCurrentBlockLocation());
        String identifierJTypeAsStr = Block.getJTypeAsStr((VariableSymbol)identifierSymbol, ((IntBoolSymbol) identifierSymbol).type());

        // if identifier represents an IntBoolSymbol
        if(identifierSymbol instanceof IntBoolSymbol){
            // if identifier is defined in the current block, get the field from the current block
            if(identifierDefinedInCurrentBlock){
                toReturn.jtext.addIndentedLine("aload 0");
                toReturn.jtext.addIndentedLine("getfield "+identifierOwnerBlockName+"/"+evaluable.getName()+" "+identifierJTypeAsStr);
                toReturn.maxStackSizeNeeded += 2;
                return toReturn;

            // else, get the field from the parents block
            }else{
                toReturn.jtext.addIndentedLine("aload 0");
                toReturn.jtext.addIndentedLine("getfield "+currentBlockNameWithCapitals+"/"+CodeProducer.lowerCaseFirstChar(identifierSymbol.getBlockName())+" L"+SymbolTable.getInstance().getMainBlockName()+";");
                toReturn.jtext.addIndentedLine("getfield "+identifierOwnerBlockName+"/"+evaluable.getName()+" "+identifierJTypeAsStr);
                toReturn.maxStackSizeNeeded += 3;
                return toReturn;
            }

        // if identifier represents an ArraySymbol
        }else if(identifierSymbol instanceof ArraySymbol){
            toReturn.jtext.addIndentedLine(";TODO! JasminExpressionEvaluator.jEvaluate(Identifier evaluable): Identifier ArraySymbol");
            return toReturn;

        // if identifier represents a FunctionSymbol
        }else if(identifierSymbol instanceof FunctionSymbol){
            toReturn.jtext.addIndentedLine(";TODO! JasminExpressionEvaluator.jEvaluate(Identifier evaluable): Identifier FunctionSymbol");
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
     */
    @Override
    public JasminExpression jEvaluate(IntNumber evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("ldc "+evaluable.getValue());
        toReturn.maxStackSizeNeeded = 1;
        return toReturn;
    }

    /**
     *@description: evaluates an Lesser than Expression
     */
    @Override
    public JasminExpression jEvaluate(LesserThanExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Lesser Equal Expression
     */
    @Override
    public JasminExpression jEvaluate(LesserEqualExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }


    /**
     *@description: evaluates an Not Expression
     */
    @Override
    public JasminExpression jEvaluate(NotExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }

    /**
     *@description: evaluates an Or Expression
     */
    @Override
    public JasminExpression jEvaluate(OrExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;

    }





}
