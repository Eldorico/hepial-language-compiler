package symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

	private static SymbolTable instance = new SymbolTable();
	private HashMap<String, HashMap<String, Symbol>> symbolTable; // HashMap<BlocName, HashMap<VarName, SymbolVariable>>
	private ArrayList<Symbol> symbolsList; // used to browse quickly every symbol added to symbolTable, in the order they have been added

	private Stack<String> openedBlocs = new Stack<String>();
	private String mainBlocName = new String("main");
	private String currentBlocName;

	private SymbolTable(){
		this.symbolTable = new HashMap<String, HashMap<String, Symbol>>();
		this.symbolsList = new ArrayList<Symbol>();
		this.enterBloc(mainBlocName);
	}

	public static SymbolTable getInstance(){
		return instance;
	}

	/**
	 * @description: returns false if a symbol exists allready on the current bloc.
	 * 		If symbol doesnt exists, the function adds the symbol to the symbol table.
	 * @param symbolIdentifier
	 * @param symbol
	 * @return
	 */
	public boolean addSymbol(String symbolIdentifier, Symbol symbol){
		if(symbolTable.get(currentBlocName).containsKey(symbolIdentifier)){
			System.err.printf("SymbolTable: duplicate symbol: %s\n", symbolIdentifier);
			return false;
		}else{
			symbolTable.get(currentBlocName).put(symbolIdentifier, symbol);
			symbolsList.add(symbol);
			System.out.printf("SymbolTable in bloc %s: added %s: %s\n", currentBlocName, symbolIdentifier, symbol.toString());
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

	public void enterBloc(String blocName){
		if(!symbolTable.containsKey(blocName)){
			symbolTable.put(blocName, new HashMap<String, Symbol>());
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
	 * @description: Checks the functions declarations and the const declarations.
	 * 	If some errors are detected, they are reported to the ErrorPrinter
	 * 	and the function returns false;
	 * @return
	 */
	public boolean semanticErrorsDetected(){
	    boolean returnValue = false;
	    for(Symbol symbol: symbolsList){
	        if(symbol.semanticErrorsDetected()){
	            returnValue = true;
	        }
	    }
	    return returnValue;
	}

}
