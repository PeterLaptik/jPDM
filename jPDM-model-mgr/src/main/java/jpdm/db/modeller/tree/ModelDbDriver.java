package jpdm.db.modeller.tree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import by.jpdm.model.beans.scheme.PropertyType;

public class ModelDbDriver {
    String url;
    String user;
    String pass;

    public ModelDbDriver(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public ModelTypeNode buildModelTree() throws SQLException, ModelUpdatingException {
        ModelTypeNode root = ModelTypeNode.createRoot();
        Connection conn = DriverManager.getConnection(url, user, pass);

        // Get types
        ResultSet results = conn.createStatement().executeQuery("SELECT * FROM pdm_type_hierarchy");
        while (results.next()) {
            String superType = results.getString("base_type_name").trim();
            String typeName = results.getString("type_name").trim();
            String uuidStringValue = results.getString("id");
            UUID uuid = uuidStringValue!=null ? UUID.fromString(uuidStringValue) : null;
            
            if (superType.isEmpty()) {
                root.addChild(typeName, uuid);
            } else {
                ModelTypeNode parent = root.findSubType(superType);
                if (parent == null)
                    throw new ModelUpdatingException(String.format("Could not find super-type for '%s'.", typeName));
                parent.addChild(typeName, uuid);
            }
        }
        // Get properties
        ResultSet propResults = conn.createStatement().executeQuery(
                "SELECT * FROM entity_properties JOIN entity_types ON entity_types.type_id = entity_properties.type_id;");

        ModelTypeNode currentNode = null;
        while (propResults.next()) {
            String objType = propResults.getString("type_name");
            String propName = propResults.getString("property_name");
            UUID uuid = propResults.getObject("id", UUID.class);
            int propertyType = propResults.getInt("property_type");
            if (currentNode == null || !currentNode.getName().equals(objType)) {
                currentNode = root.findSubType(objType);
                if (currentNode == null)
                    throw new ModelUpdatingException(
                            String.format("Could not find type for '%s' for property '%s'.", objType, propName));
                currentNode.addProperty(new ModelTypeProperty(propName, PropertyType.valueOf(propertyType), uuid));
            } else {
                currentNode.addProperty(new ModelTypeProperty(propName, PropertyType.valueOf(propertyType), uuid));
            }
        }
        return root;
    }
}
