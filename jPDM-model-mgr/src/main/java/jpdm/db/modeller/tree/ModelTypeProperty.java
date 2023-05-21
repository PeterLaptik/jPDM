package jpdm.db.modeller.tree;

import java.util.UUID;

public class ModelTypeProperty {
    private UUID id;
    private String name;
    private PropertyType type;
    private boolean masterProperty;
    private boolean arrayProperty;
    private String schemeName;

    public ModelTypeProperty(String name, PropertyType type) {
        id = UUID.randomUUID();
        this.name = name.trim();
        this.type = type;
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

    public boolean isArrayProperty() {
        return arrayProperty;
    }

    public void setArrayProperty(boolean arrayProperty) {
        this.arrayProperty = arrayProperty;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}
