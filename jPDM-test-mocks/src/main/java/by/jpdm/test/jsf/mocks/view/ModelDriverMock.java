package by.jpdm.test.jsf.mocks.view;

import java.util.Map;

import javax.inject.Singleton;

import by.jpdm.test.jsf.qualifiers.TestModelDriverMock;
import jpdm.db.modeller.tree.ModelDriver;
import jpdm.db.modeller.tree.ModelTypeNode;
import jpdm.db.modeller.tree.ModelTypeProperty;
import jpdm.db.modeller.tree.PropertyType;

@Singleton
@TestModelDriverMock
public class ModelDriverMock implements ModelDriver {

    @Override
    public ModelTypeNode buildModelTree() throws Exception {
        ModelTypeNode root = ModelTypeNode.createRoot();
        ModelTypeNode item = root.addChild("Item");
        @SuppressWarnings("unused")
        ModelTypeNode asm = item.addChild("Assembly");
        ModelTypeNode detail = item.addChild("Detail");
        detail.addProperty(new ModelTypeProperty("name", PropertyType.VARCHAR_256, false));
        detail.addProperty(new ModelTypeProperty("det_type", PropertyType.VARCHAR_256, false));
        return root;
    }

    @Override
    public Map<Integer, String> getTypesMap() {
        return PropertyType.getTypeNames();
    }
}
