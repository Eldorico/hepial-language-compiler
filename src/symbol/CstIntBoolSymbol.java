package symbol;

import abstractTree.expression.Expression;

public class CstIntBoolSymbol extends IntBoolSymbol {

	Expression value;

	public CstIntBoolSymbol(int declarationLinesNumber, Type type, Expression value) {
		super(declarationLinesNumber, type);
		this.value = value;
	}

	@Override
	public String toString(){
		return String.format("Const Variable %s. No Ligne: %d. Valeur: %s", getType(), declarationLinesNumber, value.toString());
	}

}
