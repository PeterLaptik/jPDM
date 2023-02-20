package by.jpdm.jsf.model.lazy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.lazy.UserDepLazyDAO;

/**
 * Paginated user list provider. See user table in users.xhtml
 * 
 * @author Peter Laptik
 */
public class UserDepLazyModel extends LazyDataModel<User> {
    private static final long serialVersionUID = 1L;
    private Department selectedDepartment;

    @Inject
    private UserDepLazyDAO userLazyDao;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        return userLazyDao.count(selectedDepartment, filterMap);
    }

    @Override
    public List<User> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        Map<String, Integer> orderMap = new HashMap<>();
        for (Map.Entry<String, SortMeta> me : sortBy.entrySet())
            orderMap.put(me.getKey(), me.getValue().getOrder().intValue());

        return userLazyDao.load(selectedDepartment, first, pageSize, orderMap, filterMap);
    }

    @Override
    public String getRowKey(User user) {
        return String.valueOf(user.getId());
    }

    @Override
    public User getRowData(String rowKey) {
        return userLazyDao.getUserById(rowKey);
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }
}
