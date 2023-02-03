package by.jpdm.jsf.model;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
	private User selectedUser;

	public List<Department> getDepartmentsList() {
		return departmentDao.getDepartments();
	}

	public List<User> getUserList() {
		return userDao.findUsersOfDepartment(selectedDepartment);
	}
	
	public LazyDataModel<User> getUserLazyList() {
	    return lazyDataModel;
	}

	public List<User> getUsersOfDepartment(Department department) {
		return userDao.findUsersOfDepartment(department);
	}

	public int getUsersNumber() {
		return userDao.getUsersNumber();
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public Department getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setSelectedDepartment(Department selectedDepartment) {
		selectedUser = null;
		this.selectedDepartment = selectedDepartment;
		((UserLazyModel)lazyDataModel).setSelectedDepartment(selectedDepartment);
	}

	public boolean hasSelectedUser() {
		return selectedUser != null;
	}

	public boolean hasSelectedDepartment() {
		return selectedDepartment != null;
	}

	public void resetSelection() {
		selectedDepartment = null;
		selectedUser = null;
		((UserLazyModel)lazyDataModel).setSelectedDepartment(null);
	}
}
