package by.jpdm.jsf.prime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.dao.exceptions.JpdmModelException;
import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;

/**
 * Maps a type hierarchy to Primefaces TreeNodes
 * 
 * @author Peter Laptik
 */
public class PrimeTreeTypeMapper implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Scheme> schemeList;
    private Map<Scheme, TreeNode<ModelTypeNode>> schemesRootMap = new HashMap<>();
    private Scheme currentScheme;
    private TreeNode<ModelTypeNode> currentNode;
    private TreeNode<ModelTypeNode> selectedNode;

    /**
     * Builds full tree of types for a tree-table
     * 
     * @param modelDriver - driver
     * @return - root TreeNode
     * @throws Exception
     */
    public void buildPrimeTreeTableData(ModelDriver modelDriver, List<Scheme> schemes) {
        if(schemes.isEmpty()) {
            throw new JpdmModelException("No actual data schemes found!");
        }
        
        schemeList = schemes;
        for (Scheme scheme : schemes) {
            TreeNode<ModelTypeNode> root = buildStructure(modelDriver, scheme);
            schemesRootMap.put(scheme, root);
        }
        
        currentScheme = schemes.get(0);
        currentNode = schemesRootMap.get(currentScheme);
        
//        TreeNode<ModelTypeNode> rootNode;
//        // Build node tree from type system data
//        ModelTypeNode root = null;
//        root = modelDriver.buildModelTree();
//        // Create a single dummy node if a structure could not be built
//        if (root == null)
//            root = ModelTypeNode.createRoot();
//        // Create PrimeFaces TreeNode mapping for the type structure
//        rootNode = buildSubTree(null, root);
//        return rootNode;
    }

    public TreeNode<ModelTypeNode> buildStructure(ModelDriver modelDriver, Scheme scheme) {
        // Build node tree from type system data
        ModelTypeNode root = modelDriver.buildModelTree(scheme);
        // Create a single dummy node if a structure could not be built
        if (root == null)
            root = ModelTypeNode.createRoot();
        // Create PrimeFaces TreeNode mapping for the type structure
        return buildSubTree(null, root);
    }

    /**
     * Tree recursive building. See rebuildStructure
     * 
     * @param parent - parent tree node
     * @param node   - model node to add to parent tree node
     * @return created tree node
     */
    private TreeNode<ModelTypeNode> buildSubTree(TreeNode<ModelTypeNode> parent, ModelTypeNode node) {
        TreeNode<ModelTypeNode> subNode = new DefaultTreeNode<>(node, parent);
        List<ModelTypeNode> children = node.getChildren();
        for (ModelTypeNode child : children)
            buildSubTree(subNode, child);
        return subNode;
    }

    public Scheme getCurrentScheme() {
        return currentScheme;
    }

    public void setCurrentScheme(Scheme currentScheme) {
        this.currentScheme = currentScheme;
    }

    public TreeNode<ModelTypeNode> getCurrentNode() {
        return currentNode;
    }

    public List<Scheme> getSchemeList() {
        return schemeList;
    }

    public TreeNode<ModelTypeNode> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<ModelTypeNode> selectedNode) {
        this.selectedNode = selectedNode;
    }
}
