package codeProduction;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import symbol.SymbolTable;
import symbol.Type;

public class CodeProducer {

    private String outputFolderName;
    private static CodeProducer instance = new CodeProducer();

    /**
     * @description:
     */
    public void produceProgram(String programName, String outputFolderName){

        // create outputFolder
        this.outputFolderName = outputFolderName;
        executeShellProcess("rm -f -r "+outputFolderName);
        executeShellProcess("mkdir "+outputFolderName);

        // create the jasmin file that will contain the static main method. (The file will have te name of the program)
        Block staticMainBlock = new Block(programName, null, outputFolderName, true);
        staticMainBlock.instructions.addFunctionCall(SymbolTable.getInstance().getMainBlockName(), null, null, Type.VOID);
        staticMainBlock.instructions.addReturnInstruction(null);
        staticMainBlock.produceJasminFile();

    }

    public static String capitaliseFirstChar(String str){
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String getTab(){
        return "   ";
    }

    public String getFolderName(){
        return outputFolderName;
    }

    public static CodeProducer getInstance(){
        return instance;
    }

    /**
     * @description:
     * @param blockName
     */
    private void produceFunctionBlock(String blockName, String parentBlockName){



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
