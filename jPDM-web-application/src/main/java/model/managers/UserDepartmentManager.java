package model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.dao.DepartmentDAO;

@Component
public class UserDepartmentManager {
    @Autowired
    private DepartmentDAO dao;
    private Department selectedDepartment;

    public List<Department> list() {
        return dao.getDepartments();
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }
}
