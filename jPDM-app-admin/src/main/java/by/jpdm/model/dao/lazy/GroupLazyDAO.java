package by.jpdm.model.dao.lazy;

import java.util.List;
import java.util.Map;

import by.jpdm.model.beans.org.Group;

/**
 * DAO for groups lazy fetch (limited windows with applied filters and sorting)
 * (paginated output)
 * 
 * @author Peter Laptik
 */
public interface GroupLazyDAO {

    /**
     * Filtered count of groups
     * 
     * @param filterMap keys - 'name', 'description', values - substring which name
     *                  / description contains
     * @return
     */
    public int count(Map<String, String> filterMap);

    /**
     * Filtered and ordered list window
     * 
     * @param first     - offset
     * @param pageSize  - window size
     * @param orderMap  keys - 'login', 'description', values - order: -1 / 0 / 1
     * @param filterMap keys - 'login', 'description', values - substring which name
     *                  / description contains
     * @return page with groups
     */
    public List<Group> load(int first, int pageSize, Map<String, Integer> orderMap, Map<String, String> filterMap);

    public Group getGroupById(String id);
}
