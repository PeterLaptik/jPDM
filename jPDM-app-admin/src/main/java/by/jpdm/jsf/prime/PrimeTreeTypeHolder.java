package by.jpdm.jsf.prime;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.dao.exceptions.JpdmModelException;
import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;

/**
 * Maps a type hierarchy to Primefaces TreeNodes
 * 
 * @author Peter Laptik
 */
public class PrimeTreeTypeHolder implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Scheme> schemeList;
    private Map<Scheme, TreeNode<ModelTypeNode>> schemesRootMap;
    private Scheme currentScheme;
    private TreeNode<ModelTypeNode> rootNode;
    private TreeNode<ModelTypeNode> selectedNode;
    
    private PrimePropertyExtractor propertyExtractor = new PrimePropertyExtractor();

    /**
     * Builds full tree of types for a tree-table
     * 
     * @param modelDriver - driver
     * @return - root TreeNode
     * @throws Exception
     */
    public void buildPrimeTreeTableData(ModelDriver modelDriver, List<Scheme> schemes) {
        if (schemes.isEmpty()) {
            throw new JpdmModelException("No actual data schemes found!");
        }
        
        schemesRootMap = new HashMap<>();
        schemeList = schemes;
        for (Scheme scheme : schemes) {
            TreeNode<ModelTypeNode> root = buildStructure(modelDriver, scheme);
            schemesRootMap.put(scheme, root);
        }

        currentScheme = schemes.get(0);
        rootNode = schemesRootMap.get(currentScheme);
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

    public void setCurrentScheme(String schemeName) {
        Optional<Scheme> scheme = schemeList.stream().filter(e -> e.getFullName().equals(schemeName)).findFirst();
        if(!scheme.isPresent())
            return;
        
        currentScheme = scheme.get();
        selectedNode = null;
        rootNode = schemesRootMap.get(scheme.get());
    }

    public TreeNode<ModelTypeNode> getRootNode() {
        return rootNode;
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
    
    public List<ModelTypeProperty> getPropertyList(boolean countInherited) {
        return propertyExtractor.getPropertyList(selectedNode, countInherited);
    }
}
