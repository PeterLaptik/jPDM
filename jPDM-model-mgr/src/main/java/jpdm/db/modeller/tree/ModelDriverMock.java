package jpdm.db.modeller.tree;

@TestModelDriverMock
public class ModelDriverMock implements ModelDriver {

    @Override
    public ModelTypeNode buildModelTree() throws Exception {
        ModelTypeNode root = ModelTypeNode.createRoot();
        ModelTypeNode item = root.addChild("Item");
        @SuppressWarnings("unused")
        ModelTypeNode asm = item.addChild("Assembly");
        ModelTypeNode detail = item.addChild("Detail");
        root.addProperty(new ModelTypeProperty("name", PropertyType.TYPE_VARCHAR_256, false));
        detail.addProperty(new ModelTypeProperty("det_type", PropertyType.TYPE_VARCHAR_256, false));
        return root;
    }
}
