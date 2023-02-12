package jpdm.db.modeller.tree;

import java.util.UUID;

public class ModelTypeProperty {
    private UUID id;
    private String name;
    private PropertyType type;
    private boolean masterProperty;

    public ModelTypeProperty(String name, PropertyType type, boolean masterProperty) {
        id = UUID.randomUUID();
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
