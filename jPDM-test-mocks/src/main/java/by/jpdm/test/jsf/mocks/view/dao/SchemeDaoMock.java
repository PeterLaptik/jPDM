package by.jpdm.test.jsf.mocks.view.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import by.jpdm.model.beans.scheme.Scheme;
import by.jpdm.model.dao.scheme.SchemeDAO;
import jpdm.db.modeller.tree.ModelUpdatingException;

public class SchemeDaoMock implements SchemeDAO {
    private static List<Scheme> schemes = Arrays.asList(new Scheme("default", ""),
            new Scheme("test_1", "test description"), 
            new Scheme("test_2", "test 2 description"));
    
    static {
        for(Scheme scheme: schemes)
            scheme.setInstalled(false);
    }

    @Override
    public List<Scheme> getSchemes() {
        return schemes;
    }

    @Override
    public void createScheme(Scheme scheme) {
        for (Scheme i : schemes) {
            if (scheme.equals(i))
                throw new ModelUpdatingException("Scheme " + scheme.getFullName() + " already exists");
        }
    }

    @Override
    public void deleteScheme(Scheme scheme) {
        Iterator<Scheme> it = schemes.iterator();
        while (it.hasNext()) {
            Scheme existingScheme = it.next();
            if (existingScheme.equals(scheme)) {
                it.remove();
            }
        }
    }

    @Override
    public void installScheme(Scheme scheme) {
        for(Scheme i: schemes) {
            if(i.equals(scheme))
                i.setInstalled(true);
        }
    }
}
