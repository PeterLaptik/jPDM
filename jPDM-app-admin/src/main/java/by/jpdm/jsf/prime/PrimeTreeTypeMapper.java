package by.jpdm.jsf.prime;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;

/**
 * Maps a type hierarchy to Primefaces TreeNodes
 * 
 * @author Peter Laptik
 */
public class PrimeTreeTypeMapper {

    public PrimeTreeTypeMapper() {

    }

    /**
     * Builds full tree of types for a tree-table
     * @param modelDriver - driver
     * @return - root TreeNode
     * @throws Exception
     */
    public TreeNode<ModelTypeNode> buildPrimeTreeTableData(ModelDriver modelDriver) throws Exception {
        TreeNode<ModelTypeNode> rootNode;
        // Build node tree from type system data
        ModelTypeNode root = null;
        root = modelDriver.buildModelTree();
        // Create a single dummy node if a structure could not be built
        if (root == null)
            root = ModelTypeNode.createRoot();
        // Create PrimeFaces TreeNode mapping for the type structure
        rootNode = buildSubTree(null, root);
        return rootNode;
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
}
