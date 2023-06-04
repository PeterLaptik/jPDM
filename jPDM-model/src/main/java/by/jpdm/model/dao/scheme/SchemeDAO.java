package by.jpdm.model.dao.scheme;

import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;

public interface SchemeDAO {
    
    List<Scheme> getSchemes();
    
    void createScheme(Scheme scheme);
    
    void deleteScheme(Scheme scheme);
    
    void installScheme(Scheme scheme);
}
