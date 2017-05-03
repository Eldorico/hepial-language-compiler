package utils;

import java.util.ArrayList;



/**
 * @description: uses the Singleton pattern
 *
 */
public class ErrorPrinter {

    private static ErrorPrinter instance = new ErrorPrinter();
    private ArrayList<String> errorsMsg = new ArrayList<String>();

    public static ErrorPrinter getInstance(){
        return instance;
    }

    public void logError(String msg, int lineNumber){
        errorsMsg.add(String.format("%s on line %d", msg, lineNumber));
    }

    public void printErrors(){
        for(String msg: errorsMsg){
            System.out.println(msg);
        }
    }

}
