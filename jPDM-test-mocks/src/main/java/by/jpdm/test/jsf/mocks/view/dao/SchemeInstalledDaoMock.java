package by.jpdm.test.jsf.mocks.view.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.beans.scheme.SchemeInstalled;
import by.jpdm.model.dao.scheme.SchemeInstalledDAO;

public class SchemeInstalledDaoMock implements SchemeInstalledDAO {
    private static final long serialVersionUID = 1L;
    private static List<SchemeInstalled> schemes = new ArrayList<>();
    
    @Override
    public List<SchemeInstalled> getInstalledSchemes() {
        return schemes;
    }

    @Override
    public void insertInstalledScheme(Scheme scheme) {
        SchemeInstalled schemeInstalled = new SchemeInstalled();
        schemeInstalled.setName(scheme.getFullName());
        schemeInstalled.setDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
