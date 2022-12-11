package model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.User;
import model.dao.UserDAO;

@Component
public class UserManager {
	@Autowired
	private UserDAO userDao;
	
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	
	public int getUsersNumber() {
		return userDao.getUsersNumber();
	}
}
