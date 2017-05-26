package codeProduction;

import symbol.Type;


class StaticMainInstructions extends FunctionInstructions{

    public StaticMainInstructions() {
        super();
        parametersType = null; //new ArrayList<SimpleEntry<String, Type>>();
        blockMainFunctionName = new String("static main");
        returnType = Type.VOID;
        blockFunctionSignature = new String("([Ljava/lang/String;)V");
    }
}
