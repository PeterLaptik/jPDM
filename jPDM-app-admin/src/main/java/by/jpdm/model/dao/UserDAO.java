package by.jpdm.model.dao;

import java.io.Serializable;
import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface UserDAO extends Serializable {
	
	boolean createUser(User user, Department department);
	
    int getUsersNumber();
    
    User findUserByLogin(String login);
    
    List<User> findUsersOfDepartment(Department department);
    
    List<Group> getUserGroups(User user);
}
