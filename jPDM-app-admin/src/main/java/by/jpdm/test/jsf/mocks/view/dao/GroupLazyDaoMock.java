package by.jpdm.test.jsf.mocks.view.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.inject.Inject;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.model.dao.lazy.GroupLazyDAO;
import by.jpdm.test.jsf.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class GroupLazyDaoMock implements GroupLazyDAO {

    @Inject @TestViewMock
    private UserDAO userdao;
    
    @Override
    public int count(Map<String, String> filterMap) {
        List<Group> allGroups = new ArrayList<>(UserDaoMock.groups);

        String filterName = filterMap.get("name");
        String filterDescr = filterMap.get("description");

        if (filterName != null)
            allGroups = allGroups.stream().filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());

        if (filterDescr != null)
            allGroups = allGroups.stream()
                    .filter(u -> (u.getDescription().toLowerCase().startsWith(filterDescr.toLowerCase())))
                    .collect(Collectors.toList());
        return allGroups.size();
    }

    @Override
    public List<Group> load(int first, int pageSize, Map<String, Integer> orderMap, Map<String, String> filterMap) {
        List<Group> allGroups = new ArrayList<>(UserDaoMock.groups);

        String filterName = filterMap.get("name");
        String filterDescription = filterMap.get("description");

        if (filterName != null)
            allGroups = allGroups.stream().filter(u -> (u.getName().toLowerCase().startsWith(filterName.toLowerCase())))
                    .collect(Collectors.toList());

        if (filterDescription != null)
            allGroups = allGroups.stream()
                    .filter(u -> (u.getDescription().toLowerCase().startsWith(filterDescription.toLowerCase())))
                    .collect(Collectors.toList());

        Integer orderName = orderMap.get("name");
        Integer orderDescription = orderMap.get("description");

        if (orderName != null) {
            allGroups.sort(new Comparator<Group>() {
                @Override
                public int compare(Group o1, Group o2) {
                    int result = o1.getName().compareTo(o2.getName());
                    return result * orderName;
                }
            });
        }

        if (orderDescription != null) {
            allGroups.sort(new Comparator<Group>() {
                @Override
                public int compare(Group o1, Group o2) {
                    int result = o1.getDescription().compareTo(o2.getDescription());
                    return result * orderDescription;
                }
            });
        }

        List<Group> limitedUsers = allGroups.stream().skip(first).limit(pageSize).collect(Collectors.toList());
        return limitedUsers;
    }

    @Override
    public Group getGroupById(String id) {
        UUID uuid = UUID.fromString(id);
        for (Group group : UserDaoMock.groups)
            if (group.getId().equals(uuid))
                return group;

        return null;
    }
}
