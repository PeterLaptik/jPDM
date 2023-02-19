package by.jpdm.jsf.service;

import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface GroupService {
    
    void createGroup(Group group);
    
    void deleteGroup(Group group);
    
    void clearGroup(Group group);
    
    void appendUsers(Group group, List<User> users);
    
    void removeUsers(Group group, List<User> users);
    
    List<User> getGroupUsers(Group group);
}
