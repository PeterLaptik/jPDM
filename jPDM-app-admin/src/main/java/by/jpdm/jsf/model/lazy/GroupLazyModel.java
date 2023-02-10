package by.jpdm.jsf.model.lazy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import by.jpdm.model.beans.org.Group;
import by.jpdm.model.dao.lazy.GroupLazyDAO;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Paginated user list provider. See user table in groups.xhtml
 * 
 * @author Peter Laptik
 */
public class GroupLazyModel extends LazyDataModel<Group> {
    private static final long serialVersionUID = 1L;
    
    @Inject
    @TestViewMock
    GroupLazyDAO groupLazyDao;

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        return groupLazyDao.count(filterMap);
    }

    @Override
    public List<Group> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        Map<String, String> filterMap = new HashMap<>();
        for (Map.Entry<String, FilterMeta> me : filterBy.entrySet())
            filterMap.put(me.getKey(), me.getValue().getFilterValue().toString());

        Map<String, Integer> orderMap = new HashMap<>();
        for (Map.Entry<String, SortMeta> me : sortBy.entrySet())
            orderMap.put(me.getKey(), me.getValue().getOrder().intValue());

        return groupLazyDao.load(first, pageSize, orderMap, filterMap);
    }

    @Override
    public String getRowKey(Group group) {
        return String.valueOf(group.getId());
    }

    @Override
    public Group getRowData(String rowKey) {
        return groupLazyDao.getGroupById(rowKey);
    }
}
