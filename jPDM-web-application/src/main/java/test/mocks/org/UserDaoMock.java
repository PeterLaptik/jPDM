package test.mocks.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.beans.org.Group;
import model.beans.org.User;
import model.dao.UserDAO;
import model.service.UserService;

@Component
@Scope("prototype")
public class UserDaoMock implements UserDAO {
	private static final long serialVersionUID = 5174823840998139278L;
	private static List<User> users = new ArrayList<User>();
	private static UserServiceTest service = new UserServiceTest();
	
	public static List<Group> groups;

    static {
    	Group groupAdmin = new Group(Group.GROUP_ADMIN, Group.GROUP_ADMIN);
    	Group groupDba = new Group(Group.GROUP_DBA_ADMIN, Group.GROUP_DBA_ADMIN);
    	Group groupDefault = new Group(Group.GROUP_DEFAULT, Group.GROUP_DEFAULT);
    	
    	groups = Arrays.asList(groupAdmin, groupDba, groupDefault);
    	
        List<Department> departments = DepartmentDaoMock.departments;
        int counter = 0;
        for(Department dep: departments) {
            for (int i = 0; i < 100; i++) {
            	User user = service.createUser("login_" + counter, "user_" + counter++, "user");
            	user.setGroups(new ArrayList<Group>());
            	user.getGroups().add(groupDefault);
                user.setDepartmentId(dep.getId());
                users.add(user);
            }
        }
        User admin = service.createUser("admin", "Admin", "admin");
        admin.setGroups(new ArrayList<Group>());
        admin.getGroups().add(groupDefault);
        admin.getGroups().add(groupAdmin);
        admin.setDepartmentId(departments.get(0).getId());
        users.add(admin);
        
        User uu = service.createUser("user", "user", "user");
        uu.setGroups(new ArrayList<Group>());
        uu.getGroups().add(groupDefault);
        uu.setDepartmentId(departments.get(0).getId());
        users.add(uu);
        
        User dba = service.createUser("dba", "Admin db", "dba");
        dba.setGroups(new ArrayList<Group>());
        dba.getGroups().add(groupDefault);
        dba.getGroups().add(groupDba);
        dba.setDepartmentId(departments.get(0).getId());
        users.add(dba);
    }

    @Override
    public int getUsersNumber() {
        return users.size();
    }

    @Override
    public List<User> findUsersOfDepartment(Department department) {
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

	@Override
	public User findUserByLogin(String login) {
		for(User i: users) {
			if(i.getLogin().equals(login))
				return i;
		}
		return null;
	}

	@Override
	public List<Group> getUserGroups(User user) {
		return user.getGroups();
	}

	@Override
	public boolean createUser(User user, Department department) {
		user.setDepartmentId(department.getId());
		user.setGroups(new ArrayList<Group>());
		for(Group group: groups) {
			if(group.getName().equals(Group.GROUP_DEFAULT))
				user.getGroups().add(group);
		}
		return true;
	}
}
