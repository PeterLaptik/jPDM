package model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.beans.org.User;
import model.dao.UserDAO;

@Component
public class UserManager {
    @Autowired
    private UserDAO userDao;
    private User selectedUser;

    public List<User> list() {
        return userDao.getUsersOfDepartment(null);
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
}
