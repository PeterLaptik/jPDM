package model.dao;

import java.util.List;

import model.beans.org.Company;
import model.beans.org.Department;

public interface CompanyDAO {

    void addCompany(Company company);

    List<Company> getCompanyList();
    
    List<Department> getDepartmentsFor(Company cmp);
}
