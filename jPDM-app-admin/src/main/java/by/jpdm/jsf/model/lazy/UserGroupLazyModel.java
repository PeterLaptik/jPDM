package by.jpdm.jsf.model.lazy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.lazy.UserGroupLazyDAO;

/**
 * Paginated user list provider. See user table in groups.xhtml
 * 
 * @author Peter Laptik
 */
public class UserGroupLazyModel extends LazyDataModel<User> {
    private static final long serialVersionUID = 1L;
    private Group selectedGroup;

    @Inject
    private UserGroupLazyDAO userLazyDao;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        return userLazyDao.count(selectedGroup, filterMap);
    }

    @Override
    public List<User> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        Map<String, Integer> orderMap = new HashMap<>();
        for (Map.Entry<String, SortMeta> me : sortBy.entrySet())
            orderMap.put(me.getKey(), me.getValue().getOrder().intValue());

        return userLazyDao.load(selectedGroup, first, pageSize, orderMap, filterMap);
    }

    @Override
    public String getRowKey(User user) {
        return String.valueOf(user.getId());
    }

    @Override
    public User getRowData(String rowKey) {
        return userLazyDao.getUserById(rowKey);
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }
}
