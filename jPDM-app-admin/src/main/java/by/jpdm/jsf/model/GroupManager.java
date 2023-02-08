package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;

import by.jpdm.jsf.model.lazy.GroupLazyModel;
import by.jpdm.jsf.model.lazy.UserGroupLazyModel;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.model.dao.exceptions.JpdmModelException;
import by.jpdm.model.dao.lazy.GroupLazyDAO;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

/**
 * Managed bean for groups.xhtml
 * 
 * @author Peter Laptik
 */
@Named
@ManagedBean
@ViewScoped
public class GroupManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Group selectedGroup;
    private List<User> selectedUsers;
    private boolean groupSelected = false;
    private List<User> cutUsers = new ArrayList<>();

    @Inject
    @TestViewMock
    private GroupLazyDAO groupLazyDao;
    
    @Inject
    @TestViewMock
    private GroupDAO groupDao;

    @Inject
    private UserGroupLazyModel lazyUserDataModel;

    @Inject
    private GroupLazyModel lazyGroupDataModel;

    
    public void copyAllGroupUsers() {
        try {
            cutUsers.clear();
            cutUsers = groupDao.getUsers(selectedGroup);
        } catch (Exception e) {
            processError(e);
        }
        
    }
    
    public void copySelectedUsers() {
        try {
            cutUsers.clear();
            cutUsers.addAll(selectedUsers);
        } catch (Exception e) {
            processError(e);
        }
        
    }
    
    public void pasteAllGroupUsers() {
        try {
            if(selectedGroup==null)
                throw new JpdmModelException("Group is not chosen!");
            
            groupDao.addUsers(selectedGroup, cutUsers);
            cutUsers.clear();
            selectedUsers.clear();
        } catch (Exception e) {
            processError(e);
        }
    }
    
    public LazyDataModel<Group> getGroupLazyList() {
        return lazyGroupDataModel;
    }
    
    public LazyDataModel<User> getUserLazyList() {
        return lazyUserDataModel;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
        lazyUserDataModel.setSelectedGroup(selectedGroup);
        groupSelected = selectedGroup != null ? true : false;
    }
    
    public void resetSelection() {
        setSelectedGroup(null);
        cutUsers.clear();
    }
    
    public void resetUsersSelection() {
        selectedUsers.clear();
        cutUsers.clear();
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public boolean isGroupSelected() {
        return groupSelected;
    }
    
    public boolean hasCutUsers() {
        return cutUsers.size()>0;
    }
    
    private void processError(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        FacesContext.getCurrentInstance().addMessage("sticky-key", message);
    }
}
