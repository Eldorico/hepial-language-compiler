package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.Type;


class StaticMainInstructions extends FunctionInstructions{

    public StaticMainInstructions() {
        super();
        parametersType = null; //new ArrayList<SimpleEntry<String, Type>>();
        blockMainFunctionName = new String("static main");
        returnType = Type.VOID;
        blockFunctionSignature = computeBlockFunctionSignature(parametersType);
    }

    @Override
    protected String computeBlockFunctionSignature(ArrayList<SimpleEntry<String, Type>> parametersType){
        return new String("([Ljava/lang/String;)V");
    }
}
