package by.jpdm.model.service;

import by.jpdm.model.beans.org.Department;

public interface DepartmentFactory {
    
    Department createDepartment(String name, String description);
    
}
