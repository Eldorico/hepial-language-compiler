package codeProduction;

import symbol.ArraySymbol;
import symbol.Type;
import symbol.VariableSymbol;


/**
 * @description: this class represents the fields of a block (a function)
 * The fields will be written in jasmin code from this class, in the "headers"? of a Block.
 */
class Fields extends JasminCodeProducer{

    // the constructor class will add the text from constructorHook into the constructor method.
    JasminExpression constructorHook = new JasminExpression();
    String blockName;

    Fields(String blockName){
        this.blockName = CodeProducer.capitaliseFirstChar(blockName);
    }

    void addField(String fieldName, VariableSymbol symbol){

        jtext.addLine(".field "+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));

        // if it is an array, add something the creation of the array into the constructor method
        if(symbol instanceof ArraySymbol){
            // create comment
            constructorHook.addLine("");
            constructorHook.addIndentedLine("; construct the following array : "+symbol.toString());

            // load this
            ArraySymbol arraySymbol = (ArraySymbol)symbol;
            constructorHook.addIndentedLine("aload 0");

            // load the dimensions
            for(int i=0; i<arraySymbol.getNbDimensions(); i++){
                int arraySize = arraySymbol.getDimensionSizeOf(i);
                constructorHook.addIndentedLine("ldc "+arraySize);
            }

            // create array
            if(arraySymbol.getNbDimensions() == 1){
                String type = symbol.type() == Type.INTEGER ? "int" : "boolean";
                constructorHook.addIndentedLine("newarray "+type);
            }else{
                constructorHook.addIndentedLine("multianewarray "+Block.getJTypeAsStr(symbol, symbol.type()));
            }

            // put it on the field
            constructorHook.addIndentedLine("putfield "+blockName+"/"+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));

            // update the stack size
            constructorHook.maxStackSizeNeeded = Math.max(constructorHook.maxLocalsSizeNeeded, 1 + arraySymbol.getNbDimensions());
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
