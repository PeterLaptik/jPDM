package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import by.jpdm.jsf.model.errors.ErrorProcessor;
import by.jpdm.jsf.model.lazy.UserDepLazyModel;
import by.jpdm.jsf.model.objects.ClipboardBuffer;
import by.jpdm.jsf.service.DepartmentService;
import by.jpdm.jsf.service.UserService;
import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import jakarta.inject.Named;

/**
 * Managed bean for users.xhtml
 * 
 * @author Peter Laptik
 */
@Named
@ManagedBean
@ViewScoped
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Department selectedDepartment;
    private List<User> selectedUsers;
    private ClipboardBuffer<User> cutBuffer = new ClipboardBuffer<>();

    @Inject
    private UserService userService;
    
    @Inject
    private DepartmentService departmentService;

    @Inject
    private ErrorProcessor errorProcessor;

    @Inject
    private UserDepLazyModel lazyDataModel;

    public UserManager() {

    }

    public void createDepartmentHandler(SelectEvent<Object> evt) {
        Object object = evt.getObject();
        if (object == null)
            return;

        if (!(object instanceof Department)) {
            errorProcessor.processError("No object to create: " + object);
            return;
        }
        departmentService.createDepartment((Department) object);
    }

    public void createUserHandler(SelectEvent<Object> evt) {
        Object object = evt.getObject();
        if (object == null)
            return;

        if (selectedDepartment == null) {
            errorProcessor.processError("No selected department");
            return;
        }

        if (!(object instanceof User)) {
            errorProcessor.processError("No object to create: " + object);
            return;
        }

        User user = (User) object; // Raw user from dialogue (no encoded password, no department)
        userService.createUser(user, selectedDepartment);
    }

    public void updatePasswordHandler(SelectEvent<Object> evt) {
        Object object = evt.getObject();
        if (object == null)
            return;

        if (!(object instanceof String)) {
            errorProcessor.processError("No password to update: " + object);
            return;
        }
        
        if (selectedUsers.size()==0) {
            errorProcessor.processError("No user selected");
            return;
        }
        
        userService.updatePassword(selectedUsers.get(0), (String)object);
    }

    public void deleteUsers() {
        for (User user : selectedUsers)
            userService.deleteUser(user);
    }

    public void deleteDepartment() {
        departmentService.deleteDepartment(selectedDepartment);
    }

    public void cutUsers() {
        cutBuffer.cut(selectedUsers);
    }

    public void cutAllUsers() {
        if (selectedDepartment == null)
            return;

        List<User> users = getDepartmentUserList();
        cutBuffer.cut(users);
    }

    public void pasteUsers() {
        if (selectedDepartment == null)
            return;

        List<User> usersToPaste = cutBuffer.flush();
        // Change department id to a selected department value for all cut users
        try {
            for (User user : usersToPaste) {
                User existingUser = userService.getUserById(user.getId());
                existingUser.setDepartmentId(selectedDepartment.getId());
                userService.updateUser(existingUser);
            }
        } catch (Exception e) {
            errorProcessor.processError(e);
        }
    }

    public List<Department> getDepartmentsList() {
        return departmentService.getDepartmentsList();
    }

    public List<User> getDepartmentUserList() {
        return departmentService.getDepartmentUsers(selectedDepartment);
    }

    public LazyDataModel<User> getUserLazyList() {
        return lazyDataModel;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        selectedUsers = null;
        this.selectedDepartment = selectedDepartment;
        lazyDataModel.setSelectedDepartment(selectedDepartment);
    }

    public boolean hasSelectedUsers() {
        return selectedUsers != null && selectedUsers.size() > 0;
    }

    public boolean isSingleUserSelected() {
        return selectedUsers != null && selectedUsers.size() == 1;
    }

    public boolean hasSelectedDepartment() {
        return selectedDepartment != null;
    }

    public boolean hasCutUsers() {
        return !cutBuffer.isEmpty();
    }

    public void resetSelection() {
        selectedDepartment = null;
        selectedUsers = null;
        lazyDataModel.setSelectedDepartment(null);
        cutBuffer.clear();
    }

    public void resetUserSelection() {
        selectedUsers = null;
        cutBuffer.clear();
    }
}
