package jpdm.db.model;

public class ModelTypeProperty {
	private String name;
	private PropertyType type;
	private boolean masterProperty;

	public ModelTypeProperty(String name, PropertyType type, boolean masterProperty) {
		this.name = name.trim();
		this.type = type;
		this.masterProperty = masterProperty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public boolean isMasterProperty() {
		return masterProperty;
	}

	public void setMasterProperty(boolean masterProperty) {
		this.masterProperty = masterProperty;
	}
}
