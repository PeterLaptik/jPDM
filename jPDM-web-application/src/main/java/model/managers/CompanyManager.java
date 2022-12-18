package model.managers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.Company;
import model.dao.CompanyDAO;

@Component
public class CompanyManager {
	@Autowired
	private CompanyDAO dao;
	private Company selected;

	public List<Company> list() {
		return dao.GetCompanyList();
	}

	public Company getSelected() {
		return selected;
	}

	public void setSelected(Company selected) {
		this.selected = selected;
	}
}
