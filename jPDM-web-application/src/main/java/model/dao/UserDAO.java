package model.dao;

import java.util.List;

import model.beans.org.Department;
import model.beans.org.User;

public interface UserDAO {
    int getUsersNumber();

    List<User> getUsersOfDepartment(Department department);
}
