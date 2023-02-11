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
    private boolean groupSelected = false;
    private List<User> selectedUsers = new ArrayList<>();
    private List<User> bufferCopyUsers = new ArrayList<>();

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
            bufferCopyUsers.clear();
            bufferCopyUsers = groupDao.getUsers(selectedGroup);
        } catch (Exception e) {
            processError(e);
        }
    }

    public void copySelectedUsers() {
        try {
            bufferCopyUsers.clear();
            bufferCopyUsers.addAll(selectedUsers);
        } catch (Exception e) {
            processError(e);
        }
    }

    public void deleteSelectedGroup() {
        if (selectedGroup == null)
            return;

        try {
            groupDao.deleteGroup(selectedGroup);
            resetSelection();
            resetUsersSelection();
        } catch (Exception e) {
            processError(e);
        }
    }
    
    public void clearSelectedGroup() {
        if (selectedGroup == null)
            return;

        try {
            groupDao.clearGroup(selectedGroup);
            resetUsersSelection();
        } catch (Exception e) {
            processError(e);
        }
    }
    
    public void removeUsersFromGroup() {
        if(selectedUsers == null || selectedUsers.size()<1 || selectedGroup == null)
            return;
        
        try {
            groupDao.removeUsers(selectedGroup, selectedUsers);
            resetUsersSelection();
        } catch (Exception e) {
            processError(e);
        }
    }

    public void pasteAllGroupUsers() {
        try {
            if (selectedGroup == null)
                throw new Exception("Group is not selected!");

            groupDao.addUsers(selectedGroup, bufferCopyUsers);
            bufferCopyUsers.clear();
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
        bufferCopyUsers.clear();
    }

    public void resetUsersSelection() {
        selectedUsers.clear();
        bufferCopyUsers.clear();
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
        return bufferCopyUsers.size() > 0;
    }

    public void processError(Exception e) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
        FacesContext.getCurrentInstance().addMessage("sticky-key", message);
    }
}
