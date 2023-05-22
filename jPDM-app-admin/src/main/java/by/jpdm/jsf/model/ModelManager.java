package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
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
import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.dao.scheme.SchemeDAO;
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
//    private TreeNode<ModelTypeNode> rootNode;
    private TreeNode<ModelTypeNode> selectedNode;
    private List<ModelTypeProperty> propertyList;
    private List<String> schemeNames = new ArrayList<>();
    
    private String selectedScheme = "";
    
//    private Map<Scheme, TreeNode<ModelTypeNode>> schemesRootMaps; 
    private boolean showInheritedProps = true;

    private PrimeTreeTypeMapper primeTreeMapper = new PrimeTreeTypeMapper();
    
    private PrimePropertyExtractor propertyExtractor = new PrimePropertyExtractor();

    @Inject
    private ErrorProcessor errorProcessor;

    @Inject
    private ModelDriver modelDriver;
    
    @Inject
    private SchemeDAO schemeDao;

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

    public List<String> getSchemeNames() {
        return schemeNames;
    }

    public TreeNode<ModelTypeNode> getRootNode() {
        return primeTreeMapper.getCurrentNode();
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
    
    public String getSelectedScheme() {
        return selectedScheme;
    }

    public void setSelectedScheme(String selectedScheme) {
        this.selectedScheme = selectedScheme;
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
        List<Scheme> schemes = schemeDao.getSchemes();
        primeTreeMapper.buildPrimeTreeTableData(modelDriver, schemes);
        updatePropertyList();
        
        for(Scheme scheme: schemes) {
            schemeNames.add(scheme.getFullName());
        }
    }

    private void updatePropertyList() {
        propertyList = propertyExtractor.getPropertyList(selectedNode, showInheritedProps);
    }
}
