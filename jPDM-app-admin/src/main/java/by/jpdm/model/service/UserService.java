package by.jpdm.model.service;

import by.jpdm.model.beans.org.User;

public interface UserService {
    
    public User createUser(String login, String name, String password);
    
}
