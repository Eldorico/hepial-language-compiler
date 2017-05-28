package symbol;

public enum Type {
	INTEGER, BOOLEAN, CST_STRING, VOID;

	public static String strType(Type type){
	    if(type == Type.BOOLEAN){
	        return "boolean";
	    }else if(type == Type.INTEGER){
	        return "integer";
	    }else if(type == Type.CST_STRING){
	        return "string";
	    }else{
	        return null;
	    }
	}

	public static String jTypeObject(Type type){
	    if(type == Type.INTEGER || type == Type.BOOLEAN){
	        return "I";
	    }else if(type == Type.CST_STRING){
	        return "Ljava/lang/String;";
	    }else if(type == Type.VOID){
	        return "V";
	    }else{
	        return null;
	    }
	}
}
