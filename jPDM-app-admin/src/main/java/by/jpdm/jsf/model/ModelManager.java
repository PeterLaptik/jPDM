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
import by.jpdm.jsf.prime.PrimeTreeTypeHolder;
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
//    private TreeNode<ModelTypeNode> selectedNode;
    private List<ModelTypeProperty> propertyList;
    private List<String> schemeNames = new ArrayList<>();

    private String selectedScheme;

//    private Map<Scheme, TreeNode<ModelTypeNode>> schemesRootMaps; 
    private boolean showInheritedProps = true;

    private PrimeTreeTypeHolder primeTreeMapper = new PrimeTreeTypeHolder();

    // Move to mapper
//    private PrimePropertyExtractor propertyExtractor = new PrimePropertyExtractor();

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
        TreeNode<ModelTypeNode> selectedNode = primeTreeMapper.getSelectedNode();
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

    public void handleNewTypePropertyReturn(SelectEvent<Object> evt) {
        TreeNode<ModelTypeNode> selectedNode = primeTreeMapper.getSelectedNode();
        if (selectedNode == null) {
            errorProcessor.processError("No parent type selected!");
            return;
        }

        try {
            ModelTypeProperty property = (ModelTypeProperty) evt.getObject();
            selectedNode.getData().addProperty(property);
            updatePropertyList();
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    public List<String> getSchemeNames() {
        return schemeNames;
    }

    public TreeNode<ModelTypeNode> getRootNode() {
        return primeTreeMapper.getRootNode();
    }

    public TreeNode<ModelTypeNode> getSelectedNode() {
        return primeTreeMapper.getSelectedNode();
    }

    public void setSelectedNode(TreeNode<ModelTypeNode> selectedNode) {
        primeTreeMapper.setSelectedNode(selectedNode);
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
        primeTreeMapper.setCurrentScheme(selectedScheme);
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

        for (Scheme scheme : schemes) {
            schemeNames.add(scheme.getFullName());
        }
    }

    private void updatePropertyList() {
        propertyList = primeTreeMapper.getPropertyList(showInheritedProps);
    }
    
    public void saveCurrentScheme() {
        Scheme scheme = getActualScheme();
        if(scheme==null)
            return;
        
        if(scheme.getFullName().equals(Scheme.DEFAULT_NAME)) {
            errorProcessor.processError("Default scheme cannot be saved");
            return;
        }
            
        if(scheme.isInstalled()) {
            errorProcessor.processError("The scheme has already been deployed");
            return;
        }
        
        TreeNode<ModelTypeNode> root = primeTreeMapper.getRootNode();
        modelDriver.saveScheme(scheme, root.getData());
    }
    
    public void deployCurrentScheme() {
        Scheme scheme = getActualScheme();
        if(scheme==null)
            return;
        
        if(scheme.getFullName().equals(Scheme.DEFAULT_NAME)) {
            errorProcessor.processError("Default scheme cannot be deployed");
            return;
        }
            
        if(scheme.isInstalled()) {
            errorProcessor.processError("The scheme has already been deployed");
            return;
        }
        
        
        schemeDao.installScheme(Scheme.proxyOfValue(selectedScheme));
        recreateTypeStructure();
    }
    
    private Scheme getActualScheme() {
        Scheme scheme = null;
        List<Scheme> actualSchemes = schemeDao.getSchemes();
        for(Scheme actualScheme: actualSchemes) {
            if(actualScheme.getFullName().equals(primeTreeMapper.getCurrentScheme().getFullName())) {
                scheme = actualScheme;
                break;
            }
        }
        
        if(scheme==null) {
            errorProcessor.processError("Unknown error");
        }
        
        return scheme;
    }
}
