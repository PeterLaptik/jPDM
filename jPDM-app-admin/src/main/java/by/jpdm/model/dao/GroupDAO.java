package by.jpdm.model.dao;

import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

public interface GroupDAO {

    List<User> getUsers(Group group);
    
    void addUsers(Group group, List<User> users);
}
