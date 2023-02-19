package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.model.lazy.GroupLazyModel;
import by.jpdm.jsf.model.lazy.UserGroupLazyModel;
import by.jpdm.jsf.service.GroupService;
import by.jpdm.jsf.service.UserService;
import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.GroupDAO;
import by.jpdm.model.dao.lazy.GroupLazyDAO;
import by.jpdm.test.jsf.qualifiers.TestViewMock;
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
    private UserService userService;

    @Inject
    private GroupService groupService;

    @Inject
    private UserGroupLazyModel lazyUserDataModel;

    @Inject
    private GroupLazyModel lazyGroupDataModel;

    @Inject
    private ErrorProcessor errorProcessor;

    public GroupManager() {

    }

    public void createGroupHandler(SelectEvent<Object> evt) {
        Object object = evt.getObject();
        if (object == null)
            return;

        if (!(object instanceof Group)) {
            errorProcessor.processError("No group to create: " + object);
            return;
        }

        groupService.createGroup((Group) object);
    }

    public void copyAllGroupUsers() {
        bufferCopyUsers.clear();
        bufferCopyUsers = groupService.getGroupUsers(selectedGroup);
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
        groupService.deleteGroup(selectedGroup);
//        if (selectedGroup == null)
//            return;
//
//        try {
//            groupDao.deleteGroup(selectedGroup);
//            resetSelection();
//            resetUsersSelection();
//        } catch (Exception e) {
//            processError(e);
//        }
    }

    public void clearSelectedGroup() {
        if (selectedGroup == null)
            return;

        groupService.clearGroup(selectedGroup);
    }

    public void removeUsersFromGroup() {
        if (selectedUsers == null || selectedUsers.size() < 1 || selectedGroup == null)
            return;

        groupService.removeUsers(selectedGroup, selectedUsers);
    }

    public void pasteAllGroupUsers() {
        if (selectedGroup == null)
            return;

        groupService.appendUsers(selectedGroup, bufferCopyUsers);
        bufferCopyUsers.clear();
        selectedUsers.clear();

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
