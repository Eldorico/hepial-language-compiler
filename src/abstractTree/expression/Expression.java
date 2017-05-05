package abstractTree.expression;

import symbol.Type;
import abstractTree.AbstractTree;

public abstract class Expression extends AbstractTree {

    public abstract Type getType();

    public boolean semanticErrorsDetected(int declarationLineNumber){
        return false;
    }
}
