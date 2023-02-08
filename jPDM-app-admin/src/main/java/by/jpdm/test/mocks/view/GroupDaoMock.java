package by.jpdm.test.mocks.view;

import java.util.ArrayList;
import java.util.List;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.test.qualifiers.TestViewMock;

@TestViewMock
public class GroupDaoMock implements GroupDAO {
    @Override
    public List<User> getUsers(Group group) {
        if(group==null)
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
        for(User user: users) {
            if(!user.getGroups().contains(group))
                user.getGroups().add(group);
        }
    }
    
    
}
