package by.jpdm.test.jsf.mocks.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Singleton;

import by.jpdm.model.beans.scheme.PropertyType;
import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.TargetType;
import by.jpdm.model.beans.scheme.UpdateAction;
import by.jpdm.model.dao.scheme.SchemeDAO;
import by.jpdm.model.dao.scheme.UpdateActionDAO;
import by.jpdm.test.jsf.mocks.view.dao.SchemeDaoMock;
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
    private SchemeDAO schemeDao = new SchemeDaoMock();

    @Override
    public ModelTypeNode buildModelTree(Scheme scheme) throws ModelUpdatingException {
        ModelTypeNode root = ModelTypeNode.createRoot();
        root.setSchemeName(Scheme.DEFAULT_NAME);
        ModelTypeNode item = root.addChild("Item", UUID.randomUUID());
        item.setSchemeName(Scheme.DEFAULT_NAME);
        @SuppressWarnings("unused")
        ModelTypeNode asm = item.addChild("Assembly", UUID.randomUUID());
        asm.setSchemeName(Scheme.DEFAULT_NAME);
        ModelTypeNode detail = item.addChild("Detail", UUID.randomUUID());
        detail.setSchemeName(Scheme.DEFAULT_NAME);
        detail.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256)).setSchemeName(Scheme.DEFAULT_NAME);
        detail.addProperty(new ModelTypeProperty("det_type", PropertyType.VARCHAR_256)).setSchemeName(Scheme.DEFAULT_NAME);

        if (scheme.isDefault()) {
            root = appendInstalledUpdates(root);
            return root;
        }
        
        updatesMockDataGenerator(root);

        root = appendUpdatesForScheme(root, scheme);
        return root;
    }
    
    public ModelTypeNode appendInstalledUpdates(ModelTypeNode root) {
        List<Scheme> schemes = schemeDao.getSchemes();
        for(Scheme scheme: schemes) {
            if(!scheme.isInstalled())
                continue;
            
            appendUpdatesForScheme(root, scheme);
        }
        return root;
    }

    public ModelTypeNode appendUpdatesForScheme(ModelTypeNode root, Scheme scheme) {
        List<UpdateAction> updates = updateActionDAO.getUpdateActionsForScheme(scheme);
        for (UpdateAction update : updates) {
            if (update.getTargetType().equals(TargetType.TYPE)) {
                appendType(root, update);
            } else if (update.getTargetType().equals(TargetType.PROPERTY)) {
                appendProperty(root, update);
            }
        }
        return root;
    }

    private void appendType(ModelTypeNode root, UpdateAction update) {
        String parent = update.getParentName();
        ModelTypeNode node = root.findSubType(parent);
        ModelTypeNode child = node.addChild(update.getName(), update.getUuid());
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
        updateActionDAO.appendSingleAction(act1);

        UpdateAction act2 = new UpdateAction();
        act2.setName("SubAssembly2");
        act2.setParentName("Assembly");
        act2.setUuid(UUID.randomUUID());
        act2.setTargetType(TargetType.TYPE);
        act2.setSchemeName("test_1");
        updateActionDAO.appendSingleAction(act2);

        UpdateAction act3 = new UpdateAction();
        act3.setName("SubAssembly3");
        act3.setParentName("Assembly");
        act3.setUuid(UUID.randomUUID());
        act3.setTargetType(TargetType.TYPE);
        act3.setSchemeName("test_1");
        updateActionDAO.appendSingleAction(act3);

        UpdateAction act4 = new UpdateAction();
        act4.setName("propX");
        act4.setParentName("Assembly");
        act4.setUuid(UUID.randomUUID());
        act4.setTargetType(TargetType.PROPERTY);
        act4.setSchemeName("test_1");
        updateActionDAO.appendSingleAction(act4);

        UpdateAction act5 = new UpdateAction();
        act5.setName("propY");
        act5.setParentName("Assembly");
        act5.setUuid(UUID.randomUUID());
        act5.setTargetType(TargetType.PROPERTY);
        act5.setSchemeName("test_2");
        updateActionDAO.appendSingleAction(act5);
    }

    @Override
    public Map<Integer, String> getTypesMap() {
        return PropertyType.getTypeNames();
    }

    @Override
    public void saveScheme(Scheme scheme, ModelTypeNode root) {
        List<UpdateAction> updateActions = assembleActionsList(scheme, root);
        for(UpdateAction updateAction: updateActions) {
            System.out.println(updateAction);
        }
        updateActionDAO.setUpdateActionsForScheme(updateActions, scheme);
    }

    private List<UpdateAction> assembleActionsList(Scheme scheme, ModelTypeNode root) {
        List<UpdateAction> updateActions = new ArrayList<>();

        List<ModelTypeNode> types = root.getAllSubtypes();

        List<ModelTypeProperty> properties = new ArrayList<>();
        for (ModelTypeNode type : types) {
            properties.addAll(type.getProperties());
        }

        types.sort(Comparator.comparingInt(ModelTypeNode::getLevel));
        for (ModelTypeNode type : types) {
            if (!type.getSchemeName().equals(scheme.getFullName()))
                continue;

            UpdateAction update = new UpdateAction();
            update.setName(type.getName());
            update.setSchemeName(scheme.getFullName());
            update.setParentName(type.getParent().getName());
            update.setUuid(type.getUuid());
            update.setTargetType(TargetType.TYPE);
            updateActions.add(update);
        }

        for (ModelTypeProperty property : properties) {
            if (!property.getSchemeName().equals(scheme.getFullName()))
                continue;

            UpdateAction update = new UpdateAction();
            update.setName(property.getName());
            update.setSchemeName(scheme.getFullName());
            update.setUuid(property.getId());
            update.setTargetType(TargetType.PROPERTY);
            update.setPropertyType(property.getType());
            update.setParentName(property.getParent().getName());
            update.setArray(property.isArrayProperty());
            update.setMaster(property.isMasterProperty());
            updateActions.add(update);
        }

        return updateActions;
    }

    @Override
    public void exportScheme(Scheme scheme) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deployScheme(Scheme scheme) {
        
    }
}
