package jpdm.db.modeller.tree;

import java.util.Map;

import by.jpdm.model.beans.scheme.Scheme;

public interface ModelDriver {
    
    ModelTypeNode buildModelTree(Scheme scheme) throws ModelUpdatingException;
    
    Map<Integer,String> getTypesMap();
    
    void saveScheme(Scheme scheme, ModelTypeNode root);
    
    void exportScheme(Scheme scheme);
    
    void deployScheme(Scheme scheme);
}
