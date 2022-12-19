package test.mocks.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import model.beans.org.Company;
import model.beans.org.Department;
import model.dao.CompanyDAO;

@Component
public class CompanyDaoMock implements CompanyDAO {
    private static final List<Company> mockList = new ArrayList<Company>();
    private static final Map<UUID, List<Department>> depList = new HashMap<>();

    static {
        Company cmp = new Company("default", "default company");
        mockList.add(cmp);
        depList.put(cmp.getId(),
                Arrays.asList(new Department("Managment"), new Department("Engineering"), new Department("Process")));
        for (int i = 0; i < 3; i++) {
            Company ncmp = new Company("company " + i);
            if (i != 3)
                depList.put(ncmp.getId(), Arrays.asList(new Department("Managment"), new Department("Engineering"),
                        new Department("Process")));
            mockList.add(ncmp);
        }
    }

    @Override
    public void addCompany(Company company) {
        mockList.add(company);
    }

    @Override
    public List<Company> getCompanyList() {
        return mockList;
    }

    @Override
    public List<Department> getDepartmentsFor(Company cmp) {
        if (cmp == null)
            return new ArrayList<Department>();
        List<Department> result = depList.get(cmp.getId());
        return result != null ? result : new ArrayList<Department>();
    }
}
