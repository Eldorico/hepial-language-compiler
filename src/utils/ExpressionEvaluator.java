package utils;

import symbol.Symbol;
import symbol.SymbolTable;
import abstractTree.expression.BinaryExpression;
import abstractTree.expression.BooleanKeyword;
import abstractTree.expression.CstString;
import abstractTree.expression.Expression;
import abstractTree.expression.FctCallExpression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;
import abstractTree.expression.UnaryExpression;

public class ExpressionEvaluator {

    /**
     * @description: returns true if the expression is only composed by instances of classes described in the filters.
     *  For example, if I want to check that '3 + asdf' is only made of IntNumber, Identifier and CstIntBoolSymbol, I will use these parameters:
     *  expressionClassesFilter =  {IntNumber, Identifier}, symbolClassesFilter = {CstIntBoolSymbol}
     * @param expressionClassesFilter
     * @param symbolClassesFilter
     * @return
     */
    public static boolean expressionContainsOnly(Class [] expressionClassesFilter, Class [] symbolClassesFilter, Expression expression){
        // manage BooleanKeyword, CstString, IntNumber
        if(expression instanceof BooleanKeyword || expression instanceof CstString || expression instanceof IntNumber){
            return expressionHasSuperClassInArray(expressionClassesFilter, expression);
        }

        // manage UnaryExpressions
        if(expression instanceof UnaryExpression){
            // if a unary expression is allowed, check if the expression of the unary expression is allowed.
            if(expressionHasSuperClassInArray(expressionClassesFilter, expression)){
                UnaryExpression castedExpression = (UnaryExpression) expression;
                return expressionContainsOnly(expressionClassesFilter, symbolClassesFilter, castedExpression.getExpression());
            }else{
                return false;
            }
        }

        // manage BinaryExpressions
        if(expression instanceof BinaryExpression){
            // if a binary expression is allowed, check its left and right operands.
            if(expressionHasSuperClassInArray(expressionClassesFilter, expression)){
                BinaryExpression castedExpression = (BinaryExpression) expression;
                if(   expressionContainsOnly(expressionClassesFilter, symbolClassesFilter, castedExpression.getLeftOperand())
                   && expressionContainsOnly(expressionClassesFilter, symbolClassesFilter, castedExpression.getRightOperand())){
                    return true;
                }
            }
            return false;
        }

        // manage Identifiers / fct call
        if(expression instanceof Identifier || expression instanceof FctCallExpression){
            // if an identifier/fct call is allowed is allowed, check if its symbol is allowed
            if(expressionHasSuperClassInArray(expressionClassesFilter, expression)){
                Identifier identifiersExpression;
                if(expression instanceof Identifier){
                    identifiersExpression = (Identifier) expression;
                }else{
                    FctCallExpression fctCallExpression = (FctCallExpression) expression;
                    identifiersExpression = fctCallExpression.getFctName();
                }

                Symbol symbolOfExpression = SymbolTable.getInstance().getSymbol(identifiersExpression.getName());
                return symbolHasSuperClassInArray(symbolClassesFilter, symbolOfExpression);
            }
            return false;
        }


        // hope we dont arrive here...
        System.err.println("ExpressionEvaluator.expressionContainsOnly(): case not managed!");
        return false;
    }

    /**
     * @description:
     * @param array
     * @param expression
     */
    private static boolean expressionHasSuperClassInArray(Class [] array, Expression expression){
        for(Class c : array){
            if(c.isInstance(expression)){
                return true;
            }
        }
        return false;
    }

    /**
     * @description:
     * @param array
     * @param expression
     */
    private static boolean symbolHasSuperClassInArray(Class [] array, Symbol symbol){
        for(Class<Symbol> s : array){
            //if(symbol.getClass().isInstance(s)){
            if(s.isInstance(symbol)){
                return true;
            }
        }
        return false;
    }

}
