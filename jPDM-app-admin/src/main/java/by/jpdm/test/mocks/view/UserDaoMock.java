package by.jpdm.test.mocks.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.UserDAO;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
public class UserDaoMock implements UserDAO {
	private static final long serialVersionUID = 5174823840998139278L;
	private static UserServiceTestMock service = new UserServiceTestMock();
	
	public static List<User> users = new ArrayList<User>();
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
	public List<Group> getUserGroups(User user) {
		return user.getGroups();
	}

	@Override
	public boolean createUser(User user) {
		user.setGroups(new ArrayList<Group>());
		for(Group group: groups) {
			if(group.getName().equals(Group.GROUP_DEFAULT))
				user.getGroups().add(group);
		}
		users.add(user);
		return true;
	}

	@Override
	public void deleteUser(User user) {
		Iterator<User> it = users.iterator();
		while(it.hasNext()) {
			User u = (User)it.next();
			if(u.getId().equals(user.getId()))
				it.remove();
		}
	}
}
