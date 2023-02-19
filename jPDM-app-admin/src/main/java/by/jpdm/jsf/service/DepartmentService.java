package by.jpdm.jsf.service;

import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;

public interface DepartmentService {

    void createDepartment(Department department);

    void deleteDepartment(Department department);

    List<Department> getDepartmentsList();

    List<User> getDepartmentUsers(Department department);
}
