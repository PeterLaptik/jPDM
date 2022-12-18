package model.dao;

import java.util.List;

import model.beans.org.Company;

public interface CompanyDAO {

	void AddCompany(Company company);

	List<Company> GetCompanyList();
}
