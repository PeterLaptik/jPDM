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

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.beans.org.User;
import by.jpdm.model.dao.DepartmentDAO;
import by.jpdm.model.dao.UserDAO;
import by.jpdm.test.qualifiers.TestViewMock;
import jakarta.inject.Named;

@Named
@ManagedBean
@ViewScoped
public class UserManager implements Serializable {
	private static final long serialVersionUID = 1L;
	private Department selectedDepartment;
	private List<User> selectedUsers;
	private List<User> cutBuffer = new ArrayList<User>();

	@Inject
	@TestViewMock
	private UserDAO userDao;

	@Inject
	@TestViewMock
	private DepartmentDAO departmentDao;

	@Inject
	private UserLazyModel lazyDataModel;

	public void deleteUsers() {
		try {
			for (User user : selectedUsers)
				userDao.deleteUser(user);
		} catch (Exception e) {
			processError(e);
		}
	}

	public void deleteDepartment() {
		try {
			departmentDao.deleteDepartment(selectedDepartment);
		} catch (Exception e) {
			processError(e);
		}
	}

	public void cutUsers() {
		cutBuffer.clear();

		if (selectedUsers == null)
			return;

		cutBuffer.addAll(selectedUsers);
	}

	public void cutAllUsers() {
		cutBuffer.clear();

		if (selectedDepartment == null)
			return;

		List<User> users = getUserList();
		cutBuffer.addAll(users);
	}

	public void pasteUsers() {
		if (selectedDepartment == null)
			return;

		if (cutBuffer.size() < 1)
			return;

		try {
			for (User user : cutBuffer) {
				User existingUser = userDao.getUserById(user.getId());
				existingUser.setDepartmentId(selectedDepartment.getId());
				userDao.updateUser(existingUser);
			}
		} catch (Exception e) {
			processError(e);
		}

		cutBuffer.clear();
	}

	public List<Department> getDepartmentsList() {
		return departmentDao.getDepartments();
	}

	public List<User> getUserList() {
		return departmentDao.getUsers(selectedDepartment);
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
		return cutBuffer != null && cutBuffer.size() > 0;
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

	private void processError(Exception e) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
		FacesContext.getCurrentInstance().addMessage("sticky-key", message);
	}
}
