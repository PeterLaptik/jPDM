package jpdm.db.model;

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
	TYPE_TIMESTAMP(10);
	
	
	private static final Map<Integer, PropertyType> AOWABLE_VALUES = new HashMap<Integer, PropertyType>();
	static {
		for (PropertyType propType : values()) {
			AOWABLE_VALUES.put(propType.getType(), propType);
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
		return AOWABLE_VALUES.get(value);
	}
}
