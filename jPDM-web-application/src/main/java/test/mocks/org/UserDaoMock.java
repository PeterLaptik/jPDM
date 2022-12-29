package test.mocks.org;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import model.beans.org.Department;
import model.beans.org.User;
import model.dao.UserDAO;

@Component
@SessionScope
public class UserDaoMock implements UserDAO {
	private static final long serialVersionUID = 5174823840998139278L;
	private static List<User> users = new ArrayList<User>();

    static {
        List<Department> departments = DepartmentDaoMock.departments;
        int counter = 0;
        for(Department dep: departments) {
            for (int i = 0; i < 100; i++) {
                String login = "login_" + counter;
                String name = "user_" + counter++;
                User user = new User(login, name);
                user.setDepartmentId(dep.getId());
                users.add(user);
            }
        }
    }

    @Override
    public int getUsersNumber() {
        return users.size();
    }

    @Override
    public List<User> getUsersOfDepartment(Department department) {
        // All users
        if(department==null)
            return users;
        // Department users
        List<User> result = new ArrayList<>();
        for(User user: users) {
            if(user.getDepartmentId().equals(department.getId()))
                result.add(user);
        }
        return result;
    }
}
