package jpdm.db.modeller.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * Allowable data types
 * 
 * @author Peter Laptik
 */
public enum PropertyType {
	BOOLEAN(0),
	CHAR(1),
	VARCHAR_32(2),
	VARCHAR_128(3),
	VARCHAR_256(4),
	TEXT(5),
	INTEGER(6),
	BIGINT(7),
	DOUBLE(8),
	FLOAT(9),
	BIG_DECIMAL(10),
	BIG_INTEGER(11),
	TIMESTAMP(12);
	
	
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
