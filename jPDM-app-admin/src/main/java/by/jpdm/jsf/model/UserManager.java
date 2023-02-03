package by.jpdm.jsf.model;

import java.io.Serializable;
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
import jakarta.inject.Named;

@Named
@ManagedBean
@ViewScoped
public class UserManager implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UserDAO userDao;
	
	@Inject
	private DepartmentDAO departmentDao;
	
	@Inject
	private LazyDataModel<User> lazyDataModel;
	
	private Department selectedDepartment;
	private List<User> selectedUsers;

	public List<Department> getDepartmentsList() {
		return departmentDao.getDepartments();
	}

	public List<User> getUserList() {
		return departmentDao.getUsers(selectedDepartment);
	}
	
	public void deleteUsers() {
		try {
			userDao.deleteUser(null);
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Deleting error");
	        FacesContext.getCurrentInstance().addMessage("sticky-key", message);
		}
	}
	
	public LazyDataModel<User> getUserLazyList() {
	    return lazyDataModel;
	}

	public int getUsersNumber() {
		return userDao.getUsersNumber();
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
		((UserLazyModel)lazyDataModel).setSelectedDepartment(selectedDepartment);
	}

	public boolean hasSelectedUsers() {
		return selectedUsers != null && selectedUsers.size()>0;
	}

	public boolean hasSelectedDepartment() {
		return selectedDepartment != null;
	}

	public void resetSelection() {
		selectedDepartment = null;
		selectedUsers = null;
		((UserLazyModel)lazyDataModel).setSelectedDepartment(null);
	}
}
