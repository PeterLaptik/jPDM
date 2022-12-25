package model.dao;

import java.io.Serializable;
import java.util.List;

import model.beans.org.Department;
import model.beans.org.User;

public interface UserDAO extends Serializable {
    int getUsersNumber();

    List<User> getUsersOfDepartment(Department department);
}
