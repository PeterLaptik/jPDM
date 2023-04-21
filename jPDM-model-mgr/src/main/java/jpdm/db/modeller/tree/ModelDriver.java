package jpdm.db.modeller.tree;

import java.util.Map;

public interface ModelDriver {
    
    ModelTypeNode buildModelTree() throws Exception;
    
    Map<Integer,String> getTypesMap();
    
}
