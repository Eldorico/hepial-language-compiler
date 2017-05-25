package codeProduction;

import java.io.IOException;
import java.io.PrintWriter;

import symbol.Type;

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
    Fields parameters = new Fields();  // represents the variables that will come as parameters when the block (function) will be instanciated.

    Block(String blockName, String parentName, String outputFolderPath, boolean isStaticMainBlock) {
        this.blockName = CodeProducer.capitaliseFirstChar(blockName);
        this.parentName = (parentName == null) ? null : CodeProducer.capitaliseFirstChar(parentName);
        this.outputFolderPath = outputFolderPath;
        instructions = isStaticMainBlock ? new StaticMainInstructions() : new FunctionInstructions();
    }

    /**
     * @description: this function is used to add variable comming from the parameters of the block.
     * Example, in this code, we use this function to add the fields from n and m.
     * entier maFct(entier n, entier m)
     *  booleen b;
     * debutfct
     *  [...]
     * finfct
     * @param fieldName
     * @param fieldType
     */
    void addFunctionParameterField(String fieldName, Type fieldType){
        // TODO: check if we have to
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
