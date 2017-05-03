package symbol;

public enum Type {
	INTEGER, BOOLEAN, CST_STRING;

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
}
