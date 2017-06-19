package abstractTree.expression;

import symbol.Type;
import codeProduction.JEvaluable;
import codeProduction.JEvaluator;

/**
 * @description
 * This class represents an Integer expression.
 *
 */
public class IntNumber extends Expression implements JEvaluable{

	int value;

	public IntNumber(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public Type getType(){
	    return Type.INTEGER;
	}

	@Override
	public Integer evaluateIntValue(){
	    return new Integer(value);
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }

    public int getValue(){
        return value;
    }

}
