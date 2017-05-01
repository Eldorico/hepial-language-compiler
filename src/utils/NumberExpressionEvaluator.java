package utils;

import symbol.FunctionSymbol;
import symbol.Symbol;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.Expression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;

public class NumberExpressionEvaluator {


    /**
     * @description: Determines if an Expression is an Integer Expression.
     *  If it is an IntNumber, an ArithmeticExpression, or an Identifier whose type is Integer, the function returns true.
     *  Else returns false.
     * @param expression to evaluate
     * @return 1 if it is an Integer expression, 0 if not, or -1 if could not evaluate expression
     */
    public static int isIntegerExpression(Expression expression){

        // if the expression represents an Integer expression
        if(expression instanceof IntNumber){
            return 1;
        }

        // if the expression is an arithmetic Expression, make the test for the left and the right of the operand
        if(expression instanceof ArithmeticExpression){
            ArithmeticExpression castedExpression = (ArithmeticExpression) expression;
            if(     NumberExpressionEvaluator.isIntegerExpression(castedExpression.getLeftOperand()) == 1
               &&   NumberExpressionEvaluator.isIntegerExpression(castedExpression.getRightOperand()) == 1 ){
                return 1;
            }else{
                return 0;
            }
        }

        // if the expression is an identifier, check if the type of the identifier is an integer type
        if(expression instanceof Identifier){
            Identifier castedExpression = (Identifier)expression;
            Symbol symbolOfExpression = symbol.SymbolTable.getInstance().getSymbol(castedExpression.getName());

            // if the symbol is not identfied, return -1
            if(symbolOfExpression == null){
                return -1;
            }

            // if we have a VariableSymbol
            if(symbolOfExpression instanceof VariableSymbol){
                VariableSymbol castedSymbol = (VariableSymbol) symbolOfExpression;
                if(castedSymbol.type() == Type.INTEGER){
                    return 1;
                }

             // if we have a function Symbol
            }else{
                FunctionSymbol castedSymbol = (FunctionSymbol) symbolOfExpression;
                if(castedSymbol.returnType() == Type.INTEGER){
                    return 1;
                }
            }
        }

        return 0;
    }
}
