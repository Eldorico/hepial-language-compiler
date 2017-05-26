package codeProduction;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

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

    FunctionInstructions instructions;
    Fields localFields = new Fields();  // represents the variables that we declare in a block declaration.

    Block(String blockName, String parentName, ArrayList<SimpleEntry<String, VariableSymbol>> parameters, String outputFolderPath, boolean isStaticMainBlock, Type returnType) {
        this.blockName = CodeProducer.capitaliseFirstChar(blockName);
        this.parentName = (parentName == null) ? null : CodeProducer.capitaliseFirstChar(parentName);
        this.outputFolderPath = outputFolderPath;
        instructions = isStaticMainBlock ? new StaticMainInstructions() : new FunctionInstructions(parameters, returnType);
        // TODO: write the jasmin code to put the locals into the fields, before we add more instructions via add functions
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

            // write the constructor (used to manage the variables scope)

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
