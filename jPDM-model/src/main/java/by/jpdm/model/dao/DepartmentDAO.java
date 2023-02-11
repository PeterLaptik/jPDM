package by.jpdm.model.dao;

import java.io.Serializable;
import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;

public interface DepartmentDAO extends Serializable {
	
	void createDepartment(Department department);
	
	void deleteDepartment(Department department);
	
	List<Department> getDepartments();
	
	int getUsersNumber(Department department);
	
	List<User> getUsers(Department department);
}
