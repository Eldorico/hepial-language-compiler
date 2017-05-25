package codeProduction;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.Type;


/**
 * @description: this class represents the fields of a block (a function)
 * The fields will be written in jasmin code from this class, in the "headers"? of a Block.
 */
class Fields extends JasminCodeProducer{

    private ArrayList<SimpleEntry<String, Type>> fields = new ArrayList<SimpleEntry<String, Type>>();

    void addField(String fieldName, Type fieldType){

    }

    @Override
    String getJCodeAsString() {
         return new String(); // TODO!
    }

}
