package codeProduction;

import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.BooleanKeyword;
import abstractTree.expression.CstString;
import abstractTree.expression.Expression;
import abstractTree.expression.ExpressionList;
import abstractTree.expression.FctCallExpression;
import abstractTree.expression.Identifier;
import abstractTree.expression.IntNumber;
import abstractTree.expression.NotExpression;
import abstractTree.expression.RelationalBooleanExpression;
import abstractTree.expression.RelationalExpression;

public interface JEvaluator {
    public JasminExpression jEvaluate(Expression evaluable);
    public JasminExpression jEvaluate(ArithmeticExpression evaluable);
    public JasminExpression jEvaluate(RelationalExpression evaluable);
    public JasminExpression jEvaluate(BooleanKeyword evaluable);
    public JasminExpression jEvaluate(CstString evaluable);
    public JasminExpression jEvaluate(ExpressionList evaluable);
    public JasminExpression jEvaluate(FctCallExpression evaluable);
    public JasminExpression jEvaluate(Identifier evaluable);
    public JasminExpression jEvaluate(IntNumber evaluable);
    public JasminExpression jEvaluate(NotExpression evaluable);
    public JasminExpression jEvaluate(RelationalBooleanExpression evaluable);

}
