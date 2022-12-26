package model.managers;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.beans.org.User;
import model.dao.DepartmentDAO;
import model.dao.UserDAO;

@Component
@ViewScoped
@ManagedBean
public class UserManager implements Serializable {
	private static final long serialVersionUID = -1111462741588988358L;
	@Inject
	private UserDAO userDao;
	@Inject
	private DepartmentDAO dao;

	private Department selectedDepartment;
	private User selectedUser;

	public List<Department> getDepartmentsList() {
		return dao.getDepartments();
	}

	public List<User> getUserList() {
		return userDao.getUsersOfDepartment(selectedDepartment);
	}

	public List<User> getUsersOfDepartment(Department department) {
		return userDao.getUsersOfDepartment(department);
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
	}
	
	public boolean hasSelectedUser() {
		return selectedUser!=null;
	}
	
	public boolean hasSelectedDepartment() {
		return selectedDepartment!=null;
	}
	
	public void resetSelection() {
		selectedDepartment = null;
	}
}
