package by.jpdm.model.dao;

import java.util.List;
import java.util.Map;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;

public interface UserLazyDAO {

    public int count(String filterLike);

    public List<User> load(Department department, int first, int pageSize, Map<String, Integer> orderMap,
            Map<String, String> filterMap);

    public User getUserById(String id);
}
