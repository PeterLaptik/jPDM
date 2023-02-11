package by.jpdm.model.dao;

import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface GroupDAO {

    void createGroup(Group group);
    
    void deleteGroup(Group group);
    
    void clearGroup(Group group);
    
    List<User> getUsers(Group group);
    
    void addUsers(Group group, List<User> users);
    
    void removeUsers(Group group, List<User> users);
}
