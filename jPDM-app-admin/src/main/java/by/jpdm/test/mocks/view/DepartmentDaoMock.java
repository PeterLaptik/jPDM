package by.jpdm.test.mocks.view;

import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class DepartmentDaoMock implements DepartmentDAO {
	private static final long serialVersionUID = 1L;
	public static List<Department> departments = new ArrayList<>();

	public static void init() {
		departments.add(new Department(Department.DEFAULT));
		departments.add(new Department("Engineering"));
		departments.add(new Department("Process"));
		departments.add(new Department("Management"));
		departments.add(new Department("External"));
	}

	@Override
	public List<Department> getDepartments() {
		return departments;
	}

	@Override
	public void createDepartment(Department department) {
		departments.add(department);
	}

	@Override
	public int getUsersNumber(Department department) {
		@SuppressWarnings("unused")
		UserDaoMock mock = new UserDaoMock();
		List<User> users = getUsers(department);
		return users.size();
	}

	@Override
	public List<User> getUsers(Department department) {
		@SuppressWarnings("unused")
		UserDaoMock mock = new UserDaoMock();
		// All users
		if (department == null)
			return UserDaoMock.users;
		// Department users
		List<User> result = new ArrayList<>();
		for (User user : UserDaoMock.users) {
			if (user.getDepartmentId().equals(department.getId()))
				result.add(user);
		}
		return result;
	}

	@Override
	public void deleteDepartment(Department department) {
		// TODO Auto-generated method stub

	}
}
