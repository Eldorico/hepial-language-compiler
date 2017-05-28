package codeProduction;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import symbol.ArraySymbol;
import symbol.Type;
import symbol.VariableSymbol;

/**
 * @description: This class is a representation of the .j file that will be written.
 *   Each function (each Block) of the program will represent a .j file.
 *   This class is able to write down the jasmin code to a file.
 */
class Block {

    private String blockName;
    private String parentName;
    private String outputFolderPath;
    private boolean isStaticMainBlock;

    ConstructorProducer constructor;
    Fields localFields;  // represents the variables that we declare in a block declaration.
    FunctionInstructions instructions;



    Block(String blockName, String parentName, ArrayList<SimpleEntry<String, VariableSymbol>> parameters, String outputFolderPath, boolean isStaticMainBlock, Type returnType) {
        this.blockName = CodeProducer.capitaliseFirstChar(blockName);
        this.parentName = (parentName == null) ? null : CodeProducer.capitaliseFirstChar(parentName);
        this.outputFolderPath = outputFolderPath;
        this.isStaticMainBlock = isStaticMainBlock;
        instructions = isStaticMainBlock ? new StaticMainInstructions() : new FunctionInstructions(parameters, returnType);
        this.localFields = new Fields(blockName);
        constructor = new ConstructorProducer(this.blockName, parentName, instructions, localFields);
    }

    /**
     * @description: returns a type like:
     * I
     * [I
     * [[I
     * @param variableSymbol
     * @param type
     * @return
     */
    static String getJTypeAsStr(VariableSymbol variableSymbol, Type type){
        if(variableSymbol instanceof ArraySymbol){
            ArraySymbol arraySymbol = (ArraySymbol) variableSymbol;
            String bracketChar = "";
            for(int i=0; i<arraySymbol.getNbDimensions(); i++){
                bracketChar += "[";
            }
            return new String(bracketChar+Type.jTypeObject(type));
        }else{
            return new String(Type.jTypeObject(type));
        }
    }

    /**
     * @description:
     */
    void produceJasminFile(){
        String fileName = blockName+".j";
        try{
            // create the file
            PrintWriter writer = new PrintWriter(outputFolderPath+"/"+fileName, "UTF-8");

            // write the .class
            writer.printf(".class %s\n", blockName);

            // write the .super
            writer.printf(".super java/lang/Object\n");

            // write the fields
            writer.println(localFields.getJCodeAsString());

            // write the constructor (used to manage the variables scope)
            if(!isStaticMainBlock){
                writer.println(constructor.getJCodeAsString());
            }

            // write the instructions
            writer.println(instructions.getJCodeAsString());

            writer.close();
        } catch (IOException e) {
           System.err.println("Error on producing the jasmin file: "+fileName);
           System.err.print(e.getMessage());
           System.exit(-2);
        }
    }

}
