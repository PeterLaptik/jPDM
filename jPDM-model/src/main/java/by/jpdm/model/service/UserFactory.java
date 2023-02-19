package by.jpdm.model.service;

import by.jpdm.model.beans.org.User;

public interface UserFactory {

    /**
     * Creates user object, encodes raw password, creates salt, etc. New users have
     * to be created using the factory in order to encode passwords automatically.
     * 
     * @param login
     * @param name
     * @param password
     * @return
     */
    User createUser(String login, String name, String rawPassword);

}
