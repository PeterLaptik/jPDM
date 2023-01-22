package controllers.jsf.dlg;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import model.beans.org.Department;
import model.dao.DepartmentDAO;

@Component
@RequestScoped
@ManagedBean
public class DlgCreateDep implements Serializable {
	private static final long serialVersionUID = -7564515641058266138L;
	private static String DLG_CREATE_DEP = "create-dep";
	private String name;
	private String description;
	@Autowired
	private DepartmentDAO dao;

	public void createDepartmentShow() {
		DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("350px").responsive(true)
				.build();

		PrimeFaces.current().dialog().openDynamic(DLG_CREATE_DEP, options, null);
	}

	public void cancel() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void create() {
		Department dep = new Department(name, description);
		dao.createDepartment(dep);
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description != null ? description : "";
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
