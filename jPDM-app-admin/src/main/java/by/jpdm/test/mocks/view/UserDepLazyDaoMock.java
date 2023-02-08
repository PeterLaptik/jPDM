package by.jpdm.test.mocks.view;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.model.dao.lazy.UserDepLazyDAO;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class UserDepLazyDaoMock implements UserDepLazyDAO {
	@Inject @TestViewMock
	DepartmentDAO departmentDao;

    @Override
    public int count(Department department, Map<String,String> filterMap) {    
        List<User> allUsers = departmentDao.getUsers(department);
        
        String filterLogin = filterMap.get("login");
        String filterName = filterMap.get("name");
        
        if(filterLogin!=null)
            allUsers = allUsers.stream()
                    .filter(u -> (u.getLogin().toLowerCase().startsWith(filterLogin.toLowerCase())))
                    .collect(Collectors.toList());
        
        if(filterName!=null)
            allUsers = allUsers.stream()
                    .filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());
        return allUsers.size();
    }

    @Override
    public List<User> load(Department department, int first, int pageSize, Map<String, Integer> orderMap, Map<String,String> filterMap) {
        @SuppressWarnings("unused")
		UserDaoMock mock = new UserDaoMock();        
        List<User> allUsers = departmentDao.getUsers(department);
        
        String filterLogin = filterMap.get("login");
        String filterName = filterMap.get("name");
        
        if(filterLogin!=null)
            allUsers = allUsers.stream()
                    .filter(u -> (u.getLogin().toLowerCase().startsWith(filterLogin.toLowerCase())))
                    .collect(Collectors.toList());
        
        if(filterName!=null)
            allUsers = allUsers.stream()
                    .filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());
        
        Integer orderLogin = orderMap.get("login");
        Integer orderName = orderMap.get("name");

        if(orderName!=null) {
            allUsers.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    int result =  o1.getName().compareTo(o2.getName());
                    return result*orderName;
                }
            });
        }
        
        if(orderLogin!=null) {
            allUsers.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    int result =  o1.getLogin().compareTo(o2.getLogin());
                    return result*orderLogin;
                }
            });
        }
        
        List<User> limitedUsers = allUsers.stream().skip(first).limit(pageSize).collect(Collectors.toList());
        return limitedUsers;
    }

    @Override
    public User getUserById(String id) {
        for(User user: UserDaoMock.users) {
            if(UUID.fromString(id).equals(user.getId()))
                return user;
        }
        return null;
    }
}
