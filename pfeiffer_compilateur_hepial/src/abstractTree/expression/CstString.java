package abstractTree.expression;

import symbol.Type;
import codeProduction.JEvaluator;

public class CstString extends Expression {

	String value;

	public CstString(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public Type getType(){
	    return Type.CST_STRING;
	}

    @Override
    public void accept(JEvaluator visitor) {
        visitor.jEvaluate(this);
    }
}
