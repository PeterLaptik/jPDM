package by.jpdm.test.mocks.view;

import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.DepartmentDAO;

public class DepartmentDaoMock implements DepartmentDAO {
	private static final long serialVersionUID = -5936212917192611146L;
	public static List<Department> departments = new ArrayList<>();

    static {
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
        UserDaoMock mock = new UserDaoMock();
        List<User> users = mock.findUsersOfDepartment(department);
        return users.size();
    }
}
