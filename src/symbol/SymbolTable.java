package symbol;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import utils.ErrorPrinter;

/**
 * @description: The symbol Table Instance. It uses the singleton pattern
 *
 */
public class SymbolTable {

    private String programName;
    private String mainFunctionName = new String("mainFunction");

	private static SymbolTable instance = new SymbolTable();
	private HashMap<String, HashMap<String, Symbol>> symbolTable; // HashMap<BlocName, HashMap<VarName, SymbolVariable>>
	private ArrayList<Symbol> symbolsList; // used to browse quickly every symbol added to symbolTable, in the order they have been added
	private HashMap<String, Integer> declaredFunctionsNameList = new HashMap<String, Integer>(); // used to detected functions redefinitions. HashMap<functionName, declarationLineNumber>
	private HashMap<String, ArrayList<SimpleEntry<String, VariableSymbol>>> declaredFields = new HashMap<String, ArrayList<SimpleEntry<String, VariableSymbol>>>(); // HashMap<BlocName, ArrayList<SimpleEntry<identificator, VariableSymbol>>// used to store the variables declared in each block. (We dont stock FunctionSymbols and symbols that are parameters)

	private Stack<String> openedBlocs = new Stack<String>();
	private String mainBlocName = new String("MainBlock");
	private String currentBlocName;

	private boolean duplicateSymbolsFound = false;

	private SymbolTable(){
		this.symbolTable = new HashMap<String, HashMap<String, Symbol>>();
		this.symbolsList = new ArrayList<Symbol>();
		this.enterBloc(mainBlocName);
	}

	public static SymbolTable getInstance(){
		return instance;
	}

	public String getProgramName(){
	    return programName;
	}

	public void setProgramName(String progName){
	    programName = progName;
	}

	public String getMainBlockName(){
	    return mainBlocName;
	}

	public String getMainFunctionName(){
	    return mainFunctionName;
	}

	public ArrayList<String> getBlocNameList(){
	    ArrayList<String> toReturn = new ArrayList<String>();
	    toReturn.add(getMainBlockName());
	    for(Entry<String, Integer> entry : declaredFunctionsNameList.entrySet()){
	        toReturn.add(entry.getKey());
	    }
	    return toReturn;
	}

	/**
	 * @description: returns false if a symbol exists allready on the current bloc.
	 * 		If symbol doesnt exists, the function adds the symbol to the symbol table.
	 * @param symbolIdentifier
	 * @param symbol
	 * @return
	 */
	public boolean addSymbol(String symbolIdentifier, Symbol symbol){
	    // if symbol allready added, log error
	    if(symbolTable.get(currentBlocName).containsKey(symbolIdentifier)){
			System.err.printf("SymbolTable: duplicate symbol: %s\n", symbolIdentifier);
			ErrorPrinter.getInstance().logError(symbolIdentifier+" : symbol allready defined on line "+getSymbol(symbolIdentifier).declarationLineNumber, symbol.declarationLineNumber);
			duplicateSymbolsFound = true;
			return false;
	    }else if(symbol instanceof FunctionSymbol && declaredFunctionsNameList.containsKey(symbolIdentifier)){
	        ErrorPrinter.getInstance().logError(symbolIdentifier+" : function allready defined on line "+declaredFunctionsNameList.get(symbolIdentifier), symbol.declarationLineNumber);
	        duplicateSymbolsFound = true;
	        return false;
        // if a function is defined in another function, prohibit it
	    }else if(symbol instanceof FunctionSymbol && !currentBlocName.equals(mainBlocName)){
            ErrorPrinter.getInstance().logError(symbolIdentifier+" : cannot declare a function into another function.", symbol.declarationLineNumber);
            duplicateSymbolsFound = true;
            return false;
        // avoid variable/function declaration with the names 'mainFunction'
	    }else if(symbolIdentifier.equals(mainFunctionName)){
            ErrorPrinter.getInstance().logError(symbolIdentifier+" : cannot declare a function with the name "+mainFunctionName, symbol.declarationLineNumber);
            duplicateSymbolsFound = true;
            return false;
		// else, add the symbol into the table
		}else{

		    // if the symbol is a function
			if(symbol instanceof FunctionSymbol){

			    // add the parameters of the function
			    addFunctionParameters(symbolIdentifier, (FunctionSymbol)symbol);
			    // register function name to avoid functions redefinitions
			    declaredFunctionsNameList.put(symbolIdentifier, symbol.declarationLineNumber);
			// if the symbol is not a function, add the symbol in this list. (it wont be a parameter symbol because they are added in the block above)
			}else{
			    declaredFields.get(currentBlocName).add(new SimpleEntry<String, VariableSymbol>(symbolIdentifier, (VariableSymbol)symbol));
			}

			// add the symbol to the table
	        symbolTable.get(currentBlocName).put(symbolIdentifier, symbol);
	        symbolsList.add(symbol);
	        //System.out.printf("SymbolTable in bloc %s: added %s: %s\n", currentBlocName, symbolIdentifier, symbol.toString());

	        // set the owner of the symbol
	        symbol.setBlockName(currentBlocName);

			return true;
		}
	}

	public Symbol getSymbol(String symbolIdentifier){
		if(!symbolTable.get(currentBlocName).containsKey(symbolIdentifier)){
			return symbolTable.get(mainBlocName).get(symbolIdentifier);
		}else{
			return symbolTable.get(currentBlocName).get(symbolIdentifier);
		}
	}

	public String getCurrentBlockLocation(){
	    return currentBlocName;
	}

	/**
	 * @description: enters the block desired. If the block doesnt exists, it is created.
	 * @param blocName : the block name to enter
	 */
	public void enterBloc(String blocName){
		if(!symbolTable.containsKey(blocName)){
			symbolTable.put(blocName, new HashMap<String, Symbol>());
			ArrayList<SimpleEntry<String, VariableSymbol>> fieldsList = new ArrayList<SimpleEntry<String, VariableSymbol>>();
			declaredFields.put(blocName, fieldsList);
		}
		openedBlocs.add(blocName);
		currentBlocName = blocName;
	}

	public void exitCurrentBloc(){
		if(currentBlocName.equals(mainBlocName)){
			System.err.println("SymbolTable : cannot exit mainBloc");
		}else{
			openedBlocs.pop();
			currentBlocName = openedBlocs.peek();
		}
	}

	/**
	 * @description: returns the declared variables symbol that are not a function symbol,
	 * neither the parameters of a function symbol.
	 * @param blockName
	 * @return
	 */
	public ArrayList<SimpleEntry<String, VariableSymbol>> getNormalFieldsOf(String blockName){
	    return declaredFields.get(blockName);
	}

	/**
	 * @description: Checks the functions declarations and the const declarations.
	 * 	If some errors are detected, they are reported to the ErrorPrinter
	 * 	and the function returns false;
	 * @return
	 */
	public boolean semanticErrorsDetected(){
	    boolean returnValue = duplicateSymbolsFound;
	    if(symbolTable.containsKey(programName)){
	        ErrorPrinter.getInstance().logError(programName+": cannot define a function with the same name as the program name", 0);
	        returnValue = true;
	    }
	    for(HashMap<String, Symbol> blockTable : symbolTable.values() ){
	        if(blockTable.containsKey(programName)){
	            ErrorPrinter.getInstance().logError(programName+": cannot define a variable with the same name as the program name", blockTable.get(programName).declarationLineNumber);
	            returnValue = true;
	        }
	    }
	    for(Symbol symbol: symbolsList){
	        if(symbol.semanticErrorsDetected()){
	            returnValue = true;
	        }
	    }
	    return returnValue;
	}

	/**
	 * @description: adds the function parameters to the symbol table
	 * @param functionSymbol
	 */
	private void addFunctionParameters(String functionIdentifier, FunctionSymbol functionSymbol){
	    // enter the bloc
	    enterBloc(functionIdentifier);

	    // add the parameters
	    for(SimpleEntry<String, VariableSymbol> parameter : functionSymbol.parameters){
	        this.addSymbol(parameter.getKey(), parameter.getValue());
	    }

	    // exit the bloc
	    exitCurrentBloc();
	}

}
