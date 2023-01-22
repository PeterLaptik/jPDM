package model.service;

import model.beans.org.User;

public interface UserService {
	
	User createUser(String login, String name, String password);
	
	boolean matchPassword(String rawPassword, String encodedPassword);
}
