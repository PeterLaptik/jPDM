package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.model.lazy.GroupLazyModel;
import by.jpdm.jsf.model.lazy.UserGroupLazyModel;
import by.jpdm.jsf.model.objects.ClipboardBuffer;
import by.jpdm.jsf.service.GroupService;
import by.jpdm.model.beans.org.Group;
import by.jpdm.model.beans.org.User;
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
    private ClipboardBuffer<User> clipBoard = new ClipboardBuffer<>();

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
        clipBoard.clear();
        clipBoard.cut(groupService.getGroupUsers(selectedGroup));
    }

    public void copySelectedUsers() {
        clipBoard.clear();
        clipBoard.cut(selectedUsers);
    }

    public void deleteSelectedGroup() {
        groupService.deleteGroup(selectedGroup);
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

        groupService.appendUsers(selectedGroup, clipBoard.flush());
        clipBoard.clear();
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
        clipBoard.clear();
    }

    public void resetUsersSelection() {
        selectedUsers.clear();
        clipBoard.clear();
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
        return !clipBoard.isEmpty();
    }
}
