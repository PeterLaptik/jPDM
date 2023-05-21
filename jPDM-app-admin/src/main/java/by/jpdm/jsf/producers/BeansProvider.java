package by.jpdm.jsf.producers;

import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;

import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.dao.lazy.GroupLazyDAO;
import by.jpdm.model.dao.lazy.UserDepLazyDAO;
import by.jpdm.model.dao.lazy.UserGroupLazyDAO;
import by.jpdm.model.dao.scheme.SchemeDAO;
import by.jpdm.model.service.UserFactory;
import by.jpdm.model.service.UserFactoryImpl;
import by.jpdm.security.PasswordEncoder;
import by.jpdm.test.jsf.mocks.security.PasswordEncoderMock;
import by.jpdm.test.jsf.mocks.view.ModelDriverMock;
import by.jpdm.test.jsf.mocks.view.dao.DepartmentDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.GroupDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.GroupLazyDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.SchemeDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.UserDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.UserDepLazyDaoMock;
import by.jpdm.test.jsf.mocks.view.dao.UserGroupLazyDaoMock;
import jpdm.db.modeller.tree.ModelDriver;

@SessionScoped
public class BeansProvider {

    @Produces
    public DepartmentDAO getDepartmentDAO() {
        return new DepartmentDaoMock();
    }

    @Produces
    public UserDAO getUserDAO() {
        return new UserDaoMock();
    }

    @Produces
    public GroupDAO getGroupDAO() {
        return new GroupDaoMock();
    }

    @Produces
    public PasswordEncoder getPasswordEncoder() {
        return new PasswordEncoderMock();
    }

    @Produces
    public ModelDriver getModelDriver() {
        return new ModelDriverMock();
    }
    
    @Produces
    public SchemeDAO getSchemeDao() {
        return new SchemeDaoMock();
    }

    @Produces
    public UserDepLazyDAO getUserDepLazyDAO() {
        return new UserDepLazyDaoMock(getDepartmentDAO());
    }
    
    @Produces
    public UserGroupLazyDAO getUserGroupLazyDAO() {
        return new UserGroupLazyDaoMock();
    }

    @Produces
    public GroupLazyDAO getGroupLazyDAO() {
        return new GroupLazyDaoMock();
    }

    @Produces
    public UserFactory getUserFactory() {
        return new UserFactoryImpl(getPasswordEncoder());
    }

//    @Produces
//    public DepartmentService getDepartmentService() {
//        ErrorProcessor err = new ErrorProcessor();
//        DepartmentDAO dao = getDepartmentDAO();
//        return new DepartmentServiceImpl(dao, err);
//    }
}
