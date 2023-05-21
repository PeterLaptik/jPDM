package by.jpdm.model.beans.scheme;

import java.util.UUID;

/**
 * Single update step description: 
 * schemeName - name for a scheme, 
 * parent - parent element (parent for new sub-type, or type for new property)
 * targetType - new sub-type / or new property is created
 * name - name of new type or property
 * 
 * For properties only fields:
 * propertyType enum, isMaster, isArray
 * 
 * Description for each property/type appending is kept in DB.
 * 
 * All descriptions related to declared scheme are applied during the scheme deployment.
 * 
 * @author Peter Laptik
 *
 */
public class UpdateAction {
    private String schemeName;

    // Derived type / property uuid
    private UUID uuid;

    // Updating type name (sub-type / or property containing type)
    private String parentName;

    // whether new type or property is created
    private TargetType targetType;

    private String name;

    /* Only for new properties */
    private PropertyType propertyType;
    boolean isMaster;
    boolean isArray;

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(TargetType targetType) {
        this.targetType = targetType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean isMaster) {
        this.isMaster = isMaster;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean isArray) {
        this.isArray = isArray;
    }
}
