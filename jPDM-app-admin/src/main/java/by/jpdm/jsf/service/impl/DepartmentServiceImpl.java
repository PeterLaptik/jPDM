package by.jpdm.jsf.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.service.DepartmentService;
import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.test.jsf.qualifiers.TestViewMock;

public class DepartmentServiceImpl implements DepartmentService {
    @Inject
    @TestViewMock
    private DepartmentDAO departmentDao;

    @Inject
    private ErrorProcessor errorProcessor;

    @Override
    public void createDepartment(Department department) {
        try {
            departmentDao.createDepartment(department);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    @Override
    public void deleteDepartment(Department department) {
        departmentDao.deleteDepartment(department);
    }

    @Override
    public List<Department> getDepartmentsList() {
        List<Department> result = new ArrayList<Department>();
        try {
            result = departmentDao.getDepartments();
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
        return result;
    }

    @Override
    public List<User> getDepartmentUsers(Department department) {
        List<User> result = new ArrayList<User>();
        try {
            result = departmentDao.getUsers(department);
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
        return result;
    }
}
