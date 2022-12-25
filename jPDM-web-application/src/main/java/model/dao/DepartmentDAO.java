package model.dao;

import java.io.Serializable;
import java.util.List;

import model.beans.org.Department;

public interface DepartmentDAO extends Serializable {
    List<Department> getDepartments();
}
