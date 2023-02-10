package by.jpdm.model.service;

import by.jpdm.model.beans.org.User;

public interface UserFactory {
    
    User createUser(String login, String name, String password);
    
}
