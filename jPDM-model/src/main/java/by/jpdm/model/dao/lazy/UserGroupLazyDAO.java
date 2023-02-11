package by.jpdm.model.dao.lazy;

import java.util.List;
import java.util.Map;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;

/**
 * DAO for users lazy fetch (limited windows with applied filters and sorting)
 * (paginated output for JSF-pages)
 * 
 * @author Peter Laptik
 */
public interface UserGroupLazyDAO {

    /**
     * Filtered count of users
     * 
     * @param group - if null, counts all users
     * @param filterMap  keys - 'login', 'name', values - substring which name /
     *                   login contains
     * @return
     */
    public int count(Group group, Map<String, String> filterMap);

    /**
     * Filtered and ordered list window
     * 
     * @param group - if null, returns all users
     * @param first      - offset
     * @param pageSize   - window size
     * @param orderMap   keys - 'login', 'name', values - order: -1 / 0 / 1
     * @param filterMap  keys - 'login', 'name', values - substring which name /
     *                   login contains
     * @return page with users
     */
    public List<User> load(Group group, int first, int pageSize, Map<String, Integer> orderMap,
            Map<String, String> filterMap);

    public User getUserById(String id);
}
