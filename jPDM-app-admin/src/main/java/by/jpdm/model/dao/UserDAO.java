package by.jpdm.model.dao;

import java.io.Serializable;
import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface UserDAO extends Serializable {
    
    int getUsersNumber();
    
	boolean createUser(User user);
	
	void deleteUser(User user);
        
    List<Group> getUserGroups(User user);
}
