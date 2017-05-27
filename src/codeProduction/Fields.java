package codeProduction;

import symbol.ArraySymbol;
import symbol.CstIntBoolSymbol;
import symbol.Type;
import symbol.VariableSymbol;


/**
 * @description: this class represents the fields of a block (a function)
 * The fields will be written in jasmin code from this class, in the "headers"? of a Block.
 */
class Fields extends JasminCodeProducer{

    // the constructor class will add the text from constructorHook into the constructor method.
    JasminExpression constructorArraysHook = new JasminExpression();
    JasminExpression constructorConstantsHook = new JasminExpression();
    String blockName;

    Fields(String blockName){
        this.blockName = CodeProducer.capitaliseFirstChar(blockName);
    }

    void addField(String fieldName, VariableSymbol symbol){

        // add the field
        jtext.addLine(".field "+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));

        // if it is a cst value, add the affectation of the cst value in the constructor method
        if(symbol instanceof CstIntBoolSymbol){
            // create comment
            constructorConstantsHook.addLine("");
            constructorConstantsHook.addIndentedLine("; affect the following constant value: "+symbol.toString());

            // load this
            CstIntBoolSymbol cstSymbol = (CstIntBoolSymbol)symbol;
            constructorConstantsHook.addIndentedLine("aload 0");

            // load the value of the expression
            JasminExpression jExpression = JasminExpressionEvaluator.getInstance().jEvaluate(cstSymbol.getExpression());
            constructorConstantsHook.addText(jExpression.getJCodeAsString());

            // put it on the field
            constructorConstantsHook.addIndentedLine("putfield "+blockName+"/"+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));

            // update the stack size
            constructorConstantsHook.maxStackSizeNeeded = Math.max(constructorConstantsHook.maxLocalsSizeNeeded, 2+jExpression.maxStackSizeNeeded);
            constructorConstantsHook.maxLocalsSizeNeeded = Math.max(constructorConstantsHook.maxLocalsSizeNeeded, jExpression.maxLocalsSizeNeeded);
        }


        // if it is an array, add the creation of the array into the constructor method
        if(symbol instanceof ArraySymbol){
            // create comment
            constructorArraysHook.addLine("");
            constructorArraysHook.addIndentedLine("; construct the following array : "+symbol.toString());

            // load this
            ArraySymbol arraySymbol = (ArraySymbol)symbol;
            constructorArraysHook.addIndentedLine("aload 0");

            // load the dimensions
            for(int i=0; i<arraySymbol.getNbDimensions(); i++){
                int arraySize = arraySymbol.getDimensionSizeOf(i);
                constructorArraysHook.addIndentedLine("ldc "+arraySize);
            }

            // create array
            if(arraySymbol.getNbDimensions() == 1){
                String type = symbol.type() == Type.INTEGER ? "int" : "boolean";
                constructorArraysHook.addIndentedLine("newarray "+type);
            }else{
                constructorArraysHook.addIndentedLine("multianewarray "+Block.getJTypeAsStr(symbol, symbol.type()));
            }

            // put it on the field
            constructorArraysHook.addIndentedLine("putfield "+blockName+"/"+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));

            // update the stack size
            constructorArraysHook.maxStackSizeNeeded = Math.max(constructorArraysHook.maxLocalsSizeNeeded, 1 + arraySymbol.getNbDimensions());
        }

    }

    @Override
    String getJCodeAsString() {
        if(!jtext.isEmpty()){
            jtext.insertBefore("\n");
        }
        return jtext.getJCodeAsString();
    }

}
