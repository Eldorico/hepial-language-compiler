package codeProduction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Map.Entry;

import symbol.FunctionSymbol;
import symbol.SymbolTable;
import symbol.Type;
import symbol.VariableSymbol;
import abstractTree.expression.Identifier;
import abstractTree.instruction.BlocInstruction;
import abstractTree.instruction.Instruction;

public class CodeProducer {

    private String outputFolderName;
    private BlocInstruction mainInstructions;
    private String jasminLibPath = "lib/jasmin.jar";
    private String programStaticFileName;

    // This class is a singleton because i wanted to use it statically
    private static CodeProducer instance = new CodeProducer();
    public static CodeProducer getInstance(){
        return instance;
    }

    /**
     * @description:
     */
    public void produceProgram(String programName, String outputFolderName, BlocInstruction mainInstructions){
        this.mainInstructions = mainInstructions;
        this.programStaticFileName = capitaliseFirstChar(programName)+".j";

        // create outputFolder
        this.outputFolderName = outputFolderName;
        executeShellProcess("rm -f -r "+outputFolderName);
        executeShellProcess("mkdir "+outputFolderName);

        // create the jasmin file that will contain the static main method. (The file will have te name of the program)
        Block staticMainBlock = new Block(programName, null, null, outputFolderName, true, null);
        staticMainBlock.instructions.addFunctionCall(SymbolTable.getInstance().getMainBlockName(), null, null, Type.VOID);
        staticMainBlock.instructions.addReturnInstruction(null);
        staticMainBlock.produceJasminFile();

        // produce the static main block .class
        if(produceJasminToClass(programStaticFileName, outputFolderName, jasminLibPath) != 0){
            System.exit(-3);
        }

        // for each block (functions), produce a new jasmin file
        ArrayList<String> blockLists = SymbolTable.getInstance().getBlocNameList();
        String mainBlockName = SymbolTable.getInstance().getMainBlockName();
        for(String blocName : blockLists){
            String parentBlocName = (blocName.equals(mainBlockName)) ? null : mainBlockName;
            produceFunctionBlock(blocName, parentBlocName);
        }

        // for each block generated, compile it with jasmin
        for(String blocName : blockLists){
            produceJasminToClass(capitaliseFirstChar(blocName)+".j", outputFolderName, jasminLibPath);
        }

        // debug
        System.out.println("\nLaunch compiled program for debugging...");
        System.out.println("------------------------------------------");
        executeShellProcess("java -cp "+outputFolderName+" "+capitaliseFirstChar(programName));

    }

    public static String capitaliseFirstChar(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String lowerCaseFirstChar(String str){
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String getFunctionSignature(Identifier functionIdentifier){
        String functionSignature = "(";
        FunctionSymbol functionSymbol = (FunctionSymbol)SymbolTable.getInstance().getSymbol(functionIdentifier.getName());
        ArrayList<SimpleEntry<String, VariableSymbol>> parametersSymbol = functionSymbol.getParameters();
        for(Entry<String, VariableSymbol> parameterSymbol : parametersSymbol){
            functionSignature += Block.getJTypeAsStr(parameterSymbol.getValue(), parameterSymbol.getValue().type());
        }
        functionSignature += ")"+Type.jTypeObject(functionSymbol.returnType());
        return functionSignature;
    }

    public static String getTab(){
        return "   ";
    }

    public String getFolderName(){
        return outputFolderName;
    }

    /**
     * @description:
     * @param blockName
     */
    private void produceFunctionBlock(String blockName, String parentBlockName){
        boolean blockIsMainBlock = blockName.equals(SymbolTable.getInstance().getMainBlockName()) ? true : false;

        // create the block
        Block block;
        if(blockIsMainBlock){
            block = new Block(blockName, null, new ArrayList<SimpleEntry<String, VariableSymbol>>(), outputFolderName, false, Type.VOID);
        }else{
            FunctionSymbol fSymbol = (FunctionSymbol) SymbolTable.getInstance().getSymbol(blockName);
            block = new Block(blockName, parentBlockName, fSymbol.getParameters(), outputFolderName, false, fSymbol.returnType());
        }

        // enter in correct block
        SymbolTable.getInstance().enterBloc(blockName);

        // add fields
        ArrayList<SimpleEntry<String, VariableSymbol>> fieldsList = SymbolTable.getInstance().getNormalFieldsOf(blockName);
        for(SimpleEntry<String, VariableSymbol> entry : fieldsList){
            block.localFields.addField(entry.getKey(), entry.getValue());
        }


        // add instructions
        BlocInstruction instructionsList;
        if(blockName.equals(SymbolTable.getInstance().getMainBlockName())){
            instructionsList = mainInstructions;
        }else{
            FunctionSymbol fSymbol = (FunctionSymbol)SymbolTable.getInstance().getSymbol(blockName);
            instructionsList = fSymbol.getInstructions();
        }

        for(Instruction instruction: instructionsList){
            block.instructions.addInstruction(instruction);
        }

        // add return value if we are in MainBlock
        if(blockIsMainBlock){
            block.instructions.addReturnInstruction(null);
        }

        // exit from current block (if we are not into the main block)
        if(!SymbolTable.getInstance().getCurrentBlockLocation().equals(SymbolTable.getInstance().getMainBlockName())){
            SymbolTable.getInstance().exitCurrentBloc();
        }


        // produce block
        block.produceJasminFile();
    }

    private static int produceJasminToClass(String fileName, String outputFolderName, String jasminLibPath){
        return executeShellProcess("java -jar "+jasminLibPath+" -d "+outputFolderName+" "+outputFolderName+"/"+fileName);
    }

    /**
     * @description: executes a shell command.
     * @param command the command to execute
     * @return
     */
    private static int executeShellProcess(String command){
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(command);
            BufferedReader stdoutReader = new BufferedReader( new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = stdoutReader.readLine()) != null) {
               System.out.println(line);
            }

            BufferedReader stderrReader = new BufferedReader( new InputStreamReader(process.getErrorStream()));
            while ((line = stderrReader.readLine()) != null) {
               System.err.println(line);
            }

            process.waitFor();
        }catch(Exception e){
            System.err.print(e.getMessage());
        }
        return process.exitValue();
    }


}
