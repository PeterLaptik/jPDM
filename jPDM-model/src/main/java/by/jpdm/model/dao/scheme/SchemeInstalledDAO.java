package by.jpdm.model.dao.scheme;

import java.io.Serializable;
import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.SchemeInstalled;

public interface SchemeInstalledDAO extends Serializable {
    
    List<SchemeInstalled> getInstalledSchemes();
    
    void insertInstalledScheme(Scheme scheme);
}
