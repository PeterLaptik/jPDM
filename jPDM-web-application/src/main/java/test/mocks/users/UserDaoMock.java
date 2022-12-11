package test.mocks.users;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import model.beans.org.User;
import model.dao.UserDAO;

@Component
public class UserDaoMock implements UserDAO {
	private static List<User> users = new ArrayList<User>();

	static {
		for (int i = 0; i < 10; i++) {
			String name = "user_" + i;
			User user = new User(name);
			users.add(user);
		}
	}

	@Override
	public int getUsersNumber() {
		return users.size();
	}

	@Override
	public List<User> getUsers() {
		return users;
	}
}
