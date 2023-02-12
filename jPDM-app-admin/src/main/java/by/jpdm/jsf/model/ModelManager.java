package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import by.jpdm.test.jsf.qualifiers.TestModelDriverMock;
import jakarta.inject.Named;
import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;

/**
 * Managed bean for model.xhtml
 * 
 * @author Peter Laptik
 */
@Named
@ManagedBean
@ViewScoped
public class ModelManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private TreeNode<ModelTypeNode> rootNode;
    private TreeNode<ModelTypeNode> selectedNode;
    private List<ModelTypeProperty> propertyList;

    @Inject
    @TestModelDriverMock
    private ModelDriver modelDriver;

    public TreeNode<ModelTypeNode> getRootNode() {
        return rootNode;
    }

    public TreeNode<ModelTypeNode> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<ModelTypeNode> selectedNode) {
        this.selectedNode = selectedNode;
        
        if(selectedNode!=null)
            propertyList = selectedNode.getData().getProperties();
    }

    public List<ModelTypeProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<ModelTypeProperty> propertyList) {
        this.propertyList = propertyList;
    }

    /**
     * Builds type structure and saves root tree node as a 'rootNode'-value.
     * Information about 'ModelTypeNode' and other types see in jPDM-model-mgr
     * module
     * 
     * @see ModelDriver, ModelTypeNode
     */
    @PostConstruct
    private void rebuildTypeStructure() {
        // Build node tree from type system data
        ModelTypeNode root = null;
        try {
            root = modelDriver.buildModelTree();
        } catch (Exception e) {
            processError(e);
        }
        // Create a single dummy node if a structure could not be built
        if (root == null)
            root = ModelTypeNode.createRoot();
        // Create PrimeFaces TreeNode mapping for the type structure
        rootNode = buildSubTree(null, root);
    }

    /**
     * Tree recursive building. See rebuildStructure
     * 
     * @param parent - parent tree node
     * @param node   - model node to add to parent tree node
     * @return created tree node
     */
    private TreeNode<ModelTypeNode> buildSubTree(TreeNode<ModelTypeNode> parent, ModelTypeNode node) {
        TreeNode<ModelTypeNode> subNode = new DefaultTreeNode<ModelTypeNode>(node, parent);
        List<ModelTypeNode> children = node.getChildren();
        for (ModelTypeNode child : children)
            buildSubTree(subNode, child);
        return subNode;
    }

    private void processError(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        FacesContext.getCurrentInstance().addMessage("sticky-key", message);
    }
}
