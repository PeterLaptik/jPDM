package test.mocks.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import model.beans.org.Company;
import model.beans.org.Department;
import model.dao.CompanyDAO;

@Component
public class CompanyDaoMock implements CompanyDAO {
	private static final List<Company> mockList = new ArrayList<Company>();

	static {
		Company cmp = new Company("default", "default company");
		mockList.add(cmp);
		for (int i = 0; i < 3; i++) {
			Company ncmp = new Company("company " + i);
			ncmp.setDepartments(Arrays.asList(new Department("Managment"), new Department("Engineering"),
					new Department("Process")));
			mockList.add(ncmp);
		}
	}

	@Override
	public void AddCompany(Company company) {
		mockList.add(company);
	}

	@Override
	public List<Company> GetCompanyList() {
		return mockList;
	}
}
