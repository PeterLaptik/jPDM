package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.prime.PrimePropertyExtractor;
import by.jpdm.jsf.prime.PrimeTreeTypeMapper;
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
    private boolean showInheritedProps = true;

    private PrimeTreeTypeMapper primeTreeMapper = new PrimeTreeTypeMapper();
    private PrimePropertyExtractor propertyExtractor = new PrimePropertyExtractor();

    @Inject
    private ErrorProcessor errorProcessor;

    @Inject
    private ModelDriver modelDriver;

    /**
     * Processes result of type creation via dialogue
     * 
     * @param evt
     */
    public void handleNewTypeReturn(SelectEvent<Object> evt) {
        if (selectedNode == null) {
            errorProcessor.processError("No parent type selected!");
            return;
        }

        try {
            String name = (String) evt.getObject();
            ModelTypeNode created = selectedNode.getData().addChild(name);
            new DefaultTreeNode<ModelTypeNode>(created, selectedNode);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    public TreeNode<ModelTypeNode> getRootNode() {
        return rootNode;
    }

    public TreeNode<ModelTypeNode> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<ModelTypeNode> selectedNode) {
        this.selectedNode = selectedNode;
        updatePropertyList();
    }

    public List<ModelTypeProperty> getPropertyList() {
        return propertyList;
    }

    public boolean isShowInheritedProps() {
        return showInheritedProps;
    }

    public void setShowInheritedProps(boolean showInheritedProps) {
        this.showInheritedProps = showInheritedProps;
        updatePropertyList();
    }

    /**
     * Builds initial type structure tree
     */
    @PostConstruct
    private void recreateTypeStructure() {
        try {
            rootNode = primeTreeMapper.buildPrimeTreeTableData(modelDriver);
        } catch (Exception e) {
            ModelTypeNode root = ModelTypeNode.createRoot();
            rootNode = new DefaultTreeNode<ModelTypeNode>(root, null);
        }
        updatePropertyList();
    }

    private void updatePropertyList() {
        propertyList = propertyExtractor.getPropertyList(selectedNode, showInheritedProps);
    }
}
