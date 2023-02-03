package by.jpdm.model.dao;

import java.io.Serializable;
import java.util.List;

import by.jpdm.model.beans.org.Department;

public interface DepartmentDAO extends Serializable {
	List<Department> getDepartments();

	void createDepartment(Department department);
	
	int getUsersNumber(Department department);
}
