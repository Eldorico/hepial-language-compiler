package codeProduction;

import abstractTree.expression.AdditionExpression;
import abstractTree.expression.AndExpression;
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
        if(evaluable instanceof AdditionExpression){
            return jEvaluate((AdditionExpression)evaluable);
        }else if(evaluable instanceof AndExpression){
            return jEvaluate((AndExpression)evaluable);
        }else if(evaluable instanceof BooleanKeyword){
            return jEvaluate((BooleanKeyword)evaluable);
        }else if(evaluable instanceof CstString){
            return jEvaluate((CstString)evaluable);
        }else if(evaluable instanceof DifferentThanExpression){
            return jEvaluate((DifferentThanExpression)evaluable);
        }else if(evaluable instanceof DivideExpression){
            return jEvaluate((DivideExpression)evaluable);
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
        }else if(evaluable instanceof MultiplyExpression){
            return jEvaluate((MultiplyExpression)evaluable);
        }else if(evaluable instanceof NotExpression){
            return jEvaluate((NotExpression)evaluable);
        }else if(evaluable instanceof OrExpression){
            return jEvaluate((OrExpression)evaluable);
        }else if(evaluable instanceof SubstractionExpression){
            return jEvaluate((SubstractionExpression)evaluable);
        }else{
            System.out.println("JasminExpressionEvaluator: expression non evaluable: "+evaluable.getClass().getName());
            System.exit(-3);
            return null;
        }
    }

    /**
     *@description: evaluates an Addition Expression
     */
    @Override
    public JasminExpression jEvaluate(AdditionExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
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
     *@description: evaluates an Divide Expression
     */
    @Override
    public JasminExpression jEvaluate(DivideExpression evaluable) {
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
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;
    }

    /**
     *@description: evaluates an Int number Expression
     */
    @Override
    public JasminExpression jEvaluate(IntNumber evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        toReturn.jtext.addIndentedLine("Wesh!");
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
     *@description: evaluates an Multiply Expression
     */
    @Override
    public JasminExpression jEvaluate(MultiplyExpression evaluable) {
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

    /**
     *@description: evaluates an Substraction Expression
     */
    @Override
    public JasminExpression jEvaluate(SubstractionExpression evaluable) {
        JasminExpression toReturn = new JasminExpression();
        toReturn.jtext.addIndentedLine("TODO!");
        return toReturn;
    }



}
