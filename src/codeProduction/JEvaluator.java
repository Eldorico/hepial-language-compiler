package codeProduction;

import abstractTree.expression.AndExpression;
import abstractTree.expression.ArithmeticExpression;
import abstractTree.expression.BooleanKeyword;
import abstractTree.expression.CstString;
import abstractTree.expression.DifferentThanExpression;
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
import abstractTree.expression.NotExpression;
import abstractTree.expression.OrExpression;

public interface JEvaluator {
    public JasminExpression jEvaluate(Expression evaluable);
    public JasminExpression jEvaluate(ArithmeticExpression evaluable);
    public JasminExpression jEvaluate(AndExpression evaluable);
    public JasminExpression jEvaluate(BooleanKeyword evaluable);
    public JasminExpression jEvaluate(CstString evaluable);
    public JasminExpression jEvaluate(DifferentThanExpression evaluable);
    public JasminExpression jEvaluate(EqualEqualExpression evaluable);
    public JasminExpression jEvaluate(ExpressionList evaluable);
    public JasminExpression jEvaluate(FctCallExpression evaluable);
    public JasminExpression jEvaluate(GreaterEqualExpression evaluable);
    public JasminExpression jEvaluate(GreaterThanExpression evaluable);
    public JasminExpression jEvaluate(Identifier evaluable);
    public JasminExpression jEvaluate(IntNumber evaluable);
    public JasminExpression jEvaluate(LesserThanExpression evaluable);
    public JasminExpression jEvaluate(LesserEqualExpression evaluable);
    public JasminExpression jEvaluate(NotExpression evaluable);
    public JasminExpression jEvaluate(OrExpression evaluable);
}
