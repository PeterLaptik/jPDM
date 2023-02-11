package by.jpdm.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface UserDAO extends Serializable {
    
    int getUsersNumber();
    
	void createUser(User user);
	
	void updateUser(User user);
	
	void deleteUser(User user);
	
	User getUserById(UUID id);
        
    List<Group> getUserGroups(User user);
}
