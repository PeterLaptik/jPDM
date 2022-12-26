package test.mocks.org;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.dao.DepartmentDAO;

@Component
public class DepartmentDaoMock implements DepartmentDAO {
	private static final long serialVersionUID = -5936212917192611146L;
	public static List<Department> departments = new ArrayList<>();

    static {
        departments.add(new Department("Engineering"));
        departments.add(new Department("Process"));
        departments.add(new Department("Management"));
        departments.add(new Department("External"));
    }

    @Override
    public List<Department> getDepartments() {
        return departments;
    }
}
