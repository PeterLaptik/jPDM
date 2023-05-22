package by.jpdm.jsf.prime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.TreeNode;

import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;

/**
 * Extracts type properties for a Primefaces TreeNodes for a table
 * 
 * @author Peter Laptik
 */
public class PrimePropertyExtractor implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Update property list for the selected type node
     */
    public List<ModelTypeProperty> getPropertyList(TreeNode<ModelTypeNode> selectedNode, boolean showInheritedProps) {
        List<ModelTypeProperty> propertyList = new ArrayList<ModelTypeProperty>();
        if (selectedNode == null)
            return propertyList;

        ModelTypeNode type = selectedNode.getData();
        propertyList.addAll(type.getProperties());
        
        if (showInheritedProps)
            appendInheritedProperties(type, propertyList);
        
        return propertyList;
    }

    /**
     * Recursive search for all inherited properties of the given type node
     * 
     * @param type - type node
     */
    private void appendInheritedProperties(ModelTypeNode type, List<ModelTypeProperty> propertyList) {
        ModelTypeNode parent = type.getParent();
        if (parent == null)
            return;

        propertyList.addAll(parent.getProperties());
        appendInheritedProperties(parent, propertyList);
    }
}
