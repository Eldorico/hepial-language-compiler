package codeProduction;

import symbol.VariableSymbol;


/**
 * @description: this class represents the fields of a block (a function)
 * The fields will be written in jasmin code from this class, in the "headers"? of a Block.
 */
class Fields extends JasminCodeProducer{

    void addField(String fieldName, VariableSymbol symbol){

        jtext.addLine(".field "+fieldName+" "+Block.getJTypeAsStr(symbol, symbol.type()));
        // TODO: if it is an array, add something in the constructor hook
    }

    @Override
    String getJCodeAsString() {
        if(!jtext.isEmpty()){
            jtext.insertBefore("\n");
        }
        return jtext.getJCodeAsString();
    }

}
