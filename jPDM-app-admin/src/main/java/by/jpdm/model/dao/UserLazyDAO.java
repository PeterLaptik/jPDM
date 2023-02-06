package by.jpdm.model.dao;

import java.util.List;
import java.util.Map;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;

/**
 * DAO for users lazy fetch (limited windows with applied filters and sorting)
 * 
 * @author Peter Laptik
 */
public interface UserLazyDAO {
    
    /**
     * Filtered count of users 
     * @param department
     * @param filterMap keys - 'login', 'name', values - substring which
     * name / login contains
     * @return
     */
    public int count(Department department, Map<String, String> filterMap);
    
    /**
     * Filtered and ordered list window
     * @param department
     * @param first - offset
     * @param pageSize - window size
     * @param orderMap keys - 'login', 'name', values - order: -1 / 0 / 1
     * @param filterMap keys - 'login', 'name', values - substring which
     * name / login contains
     * @return page with users
     */
    public List<User> load(Department department, int first, int pageSize, Map<String, Integer> orderMap,
            Map<String, String> filterMap);

    public User getUserById(String id);
}
