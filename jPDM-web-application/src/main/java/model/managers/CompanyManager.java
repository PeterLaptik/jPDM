package model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.Company;
import model.beans.org.Department;
import model.dao.CompanyDAO;

@Component
public class CompanyManager {
    @Autowired
    private CompanyDAO dao;
    private Company selectedCompany;
    private Department selectedDepartment;

    public List<Company> list() {
        return dao.getCompanyList();
    }

    public List<Department> departments() {
        return dao.getDepartmentsFor(selectedCompany);
    }

    public Company getSelectedCompany() {
        return selectedCompany;
    }

    public void setSelectedCompany(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }
}
