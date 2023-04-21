package jpdm.db.modeller.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Allowable data types
 * 
 * @author Peter Laptik
 */
public enum PropertyType {
	TYPE_BOOLEAN(0),
	TYPE_CHAR(1),
	TYPE_VARCHAR_32(2),
	TYPE_VARCHAR_128(3),
	TYPE_VARCHAR_256(4),
	TYPE_TEXT(5),
	TYPE_INTEGER(6),
	TYPE_BIGINT(7),
	TYPE_DOUBLE(8),
	TYPE_FLOAT(9),
	TYPE_BIG_DECIMAL(10),
	TYPE_TIMESTAMP(11);
	
	
	private static final Map<Integer, PropertyType> ALLOWABLE_VALUES = new HashMap<Integer, PropertyType>();
	static {
		for (PropertyType propType : values()) {
			ALLOWABLE_VALUES.put(propType.getType(), propType);
		}
	}
	
	private int type;
	
	PropertyType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static PropertyType valueOf(int value) {
		return ALLOWABLE_VALUES.get(value);
	}
	
	public static Map<Integer,String> getTypeNames() {
	    Map<Integer,String> result = new HashMap<>();
	    for(Map.Entry<Integer, PropertyType> me: ALLOWABLE_VALUES.entrySet())
	        result.put(me.getKey(), me.getValue().toString());
	    
	    return result;
	}
}
