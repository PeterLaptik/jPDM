package by.jpdm.test.jsf.mocks.view.dao;

import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.model.dao.exceptions.JpdmModelException;
import by.jpdm.test.jsf.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class GroupDaoMock implements GroupDAO {
    
    @Override
    public List<User> getUsers(Group group) {
        if (group == null)
            return new ArrayList<User>();

        return getMockedUsers(group);
    }

    private List<User> getMockedUsers(Group group) {
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
    public void addUsers(Group group, List<User> users) {
        for (User user : users) {
            if (!user.getGroups().contains(group))
                user.getGroups().add(group);
        }
    }

    @Override
    public void deleteGroup(Group group) {
        String groupName = group.getName();
        if (groupName.equals(Group.GROUP_ADMIN) || groupName.equals(Group.GROUP_DBA_ADMIN)
                || groupName.equals(Group.GROUP_DEFAULT))
            throw new JpdmModelException("The group '" + groupName + "' cannot be deleted!");

        for (User user : UserDaoMock.users)
            if (user.getGroups().contains(group))
                throw new JpdmModelException("The group is not empty!");

        UserDaoMock.groups.remove(group);
    }

    @Override
    public void createGroup(Group group) {
        UserDaoMock.groups.add(group);
    }

    @Override
    public void clearGroup(Group group) {
        String groupName = group.getName();
        if (groupName.equals(Group.GROUP_ADMIN) || groupName.equals(Group.GROUP_DBA_ADMIN)
                || groupName.equals(Group.GROUP_DEFAULT))
            throw new JpdmModelException("The group '" + groupName + "' cannot be empty!");

        for (User user : UserDaoMock.users) {
            user.getGroups().remove(group);
        }
    }

    @Override
    public void removeUsers(Group group, List<User> users) {
        String groupName = group.getName();
        if (groupName.equals(Group.GROUP_DEFAULT))
            throw new JpdmModelException("The group '" + groupName + "' is immutable!");
        
        for(User user: users) {
            user.getGroups().remove(group);
        }
    }
}
