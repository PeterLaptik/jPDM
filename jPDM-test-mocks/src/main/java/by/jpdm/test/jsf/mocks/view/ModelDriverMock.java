package by.jpdm.test.jsf.mocks.view;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.jpdm.model.beans.scheme.PropertyType;
import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.TargetType;
import by.jpdm.model.beans.scheme.UpdateAction;
import by.jpdm.model.dao.scheme.UpdateActionDAO;
import by.jpdm.test.jsf.mocks.view.dao.UpdateActionDaoMock;
import by.jpdm.test.jsf.qualifiers.TestModelDriverMock;
import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;
import jpdm.db.modeller.tree.ModelUpdatingException;

@Singleton
@TestModelDriverMock
public class ModelDriverMock implements ModelDriver {
    private UpdateActionDAO updateActionDAO = new UpdateActionDaoMock();

    @Override
    public ModelTypeNode buildModelTree(Scheme scheme) throws ModelUpdatingException {
        ModelTypeNode root = ModelTypeNode.createRoot();
        ModelTypeNode item = root.addChild("Item");
        @SuppressWarnings("unused")
        ModelTypeNode asm = item.addChild("Assembly");
        ModelTypeNode detail = item.addChild("Detail");
        detail.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256));
        detail.addProperty(new ModelTypeProperty("det_type", PropertyType.VARCHAR_256));

        if (scheme.isDefault())
            return root;

        updatesMockDataGenerator(root);
        
        root = appendUpdates(root, scheme);
        return root;
    }

    public ModelTypeNode appendUpdates(ModelTypeNode root, Scheme scheme) {
        List<UpdateAction> updates = updateActionDAO.getUpdateActionsForScheme(scheme);
        for(UpdateAction update: updates) {
            if(update.getTargetType().equals(TargetType.TYPE)) {
                appendType(root, update);
            } else if(update.getTargetType().equals(TargetType.PROPERTY)) {
                appendProperty(root, update);
            }
        }
        return root;
    }
    
    private void appendType(ModelTypeNode root, UpdateAction update) {
        String parent = update.getParentName();
        ModelTypeNode node = root.findSubType(parent);
        ModelTypeNode child = node.addChild(update.getName());
        child.setSchemeName(update.getSchemeName());
    }
    
    private void appendProperty(ModelTypeNode root, UpdateAction update) {
        String parentName = update.getParentName();
        String name = update.getName();
        PropertyType type = update.getPropertyType();
        UUID uuid = update.getUuid();
        boolean isArray = update.isArray();
        boolean isMaster = update.isMaster();
        String schemeName = update.getSchemeName();
        
        ModelTypeProperty property = new ModelTypeProperty(name, type, uuid);
        property.setArrayProperty(isArray);
        property.setMasterProperty(isMaster);
        property.setSchemeName(schemeName);
        
        ModelTypeNode node = root.findSubType(parentName);
        node.addProperty(property);
    }

    private void updatesMockDataGenerator(ModelTypeNode root) {
        if (updateActionDAO.getNumberOfUpdates() > 0)
            return;
        
        UpdateAction act1 = new UpdateAction();
        act1.setName("SubAssembly1");
        act1.setParentName("Assembly");
        act1.setUuid(UUID.randomUUID());
        act1.setTargetType(TargetType.TYPE);
        act1.setSchemeName("test_1");
        updateActionDAO.appendAction(act1);
        
        UpdateAction act2 = new UpdateAction();
        act2.setName("SubAssembly2");
        act2.setParentName("Assembly");
        act2.setUuid(UUID.randomUUID());
        act2.setTargetType(TargetType.TYPE);
        act2.setSchemeName("test_1");
        updateActionDAO.appendAction(act2);
        
        UpdateAction act3 = new UpdateAction();
        act3.setName("SubAssembly3");
        act3.setParentName("Assembly");
        act3.setUuid(UUID.randomUUID());
        act3.setTargetType(TargetType.TYPE);
        act3.setSchemeName("test_1");
        updateActionDAO.appendAction(act3);
        
        UpdateAction act4 = new UpdateAction();
        act4.setName("propX");
        act4.setParentName("Assembly");
        act4.setUuid(UUID.randomUUID());
        act4.setTargetType(TargetType.PROPERTY);
        act4.setSchemeName("test_1");
        updateActionDAO.appendAction(act4);
        
        UpdateAction act5 = new UpdateAction();
        act5.setName("propY");
        act5.setParentName("Assembly");
        act5.setUuid(UUID.randomUUID());
        act5.setTargetType(TargetType.PROPERTY);
        act5.setSchemeName("test_2");
        updateActionDAO.appendAction(act5);
    }

    @Override
    public Map<Integer, String> getTypesMap() {
        return PropertyType.getTypeNames();
    }
}
