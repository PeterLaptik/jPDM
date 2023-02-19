package by.jpdm.jsf.service;

import java.util.UUID;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;

public interface UserService {
    
    void createUser(User user, Department department);
    
    void deleteUser(User user);
    
    void updateUser(User user);
    
    User getUserById(UUID id);
    
    void updatePassword(User user, String newPassword);
}
