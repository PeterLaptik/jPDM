package by.jpdm.test.mocks.view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.lazy.UserGroupLazyDAO;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class UserGroupLazyDaoMock implements UserGroupLazyDAO {

    @Override
    public int count(Group group, Map<String, String> filterMap) {
        List<User> allUsers = getUsers(group);

        String filterLogin = filterMap.get("login");
        String filterName = filterMap.get("name");

        if (filterLogin != null)
            allUsers = allUsers.stream().filter(u -> (u.getLogin().toLowerCase().startsWith(filterLogin.toLowerCase())))
                    .collect(Collectors.toList());

        if (filterName != null)
            allUsers = allUsers.stream().filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());
        return allUsers.size();
    }

    @Override
    public List<User> load(Group group, int first, int pageSize, Map<String, Integer> orderMap,
            Map<String, String> filterMap) {
        @SuppressWarnings("unused")
        UserDaoMock mock = new UserDaoMock();
        List<User> allUsers = getUsers(group);

        String filterLogin = filterMap.get("login");
        String filterName = filterMap.get("name");

        if (filterLogin != null)
            allUsers = allUsers.stream().filter(u -> (u.getLogin().toLowerCase().startsWith(filterLogin.toLowerCase())))
                    .collect(Collectors.toList());

        if (filterName != null)
            allUsers = allUsers.stream().filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());

        Integer orderLogin = orderMap.get("login");
        Integer orderName = orderMap.get("name");

        if (orderName != null) {
            allUsers.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    int result = o1.getName().compareTo(o2.getName());
                    return result * orderName;
                }
            });
        }

        if (orderLogin != null) {
            allUsers.sort(new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    int result = o1.getLogin().compareTo(o2.getLogin());
                    return result * orderLogin;
                }
            });
        }

        List<User> limitedUsers = allUsers.stream().skip(first).limit(pageSize).collect(Collectors.toList());
        return limitedUsers;
    }

    private List<User> getUsers(Group group) {
        if(group==null)
            return UserDaoMock.users;
        
        List<User> result = new ArrayList<User>();
        for (User user : UserDaoMock.users) {
            for (Group g : user.getGroups()) {
                if (g.getId().equals(group.getId())) {
                    result.add(user);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public User getUserById(String id) {
        for (User user : UserDaoMock.users) {
            if (UUID.fromString(id).equals(user.getId()))
                return user;
        }
        return null;
    }
}
