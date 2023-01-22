package model.dao;

import java.io.Serializable;
import java.util.List;

import model.beans.org.Department;
import model.beans.org.Group;
import model.beans.org.User;

public interface UserDAO extends Serializable {
    int getUsersNumber();
    
    User findUserByLogin(String login);
    
    List<User> findUsersOfDepartment(Department department);
    
    List<Group> getUserGroups(User user);
}
