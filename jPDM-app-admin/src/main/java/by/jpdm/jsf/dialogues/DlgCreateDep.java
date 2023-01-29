package by.jpdm.jsf.dialogues;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;

import by.jpdm.model.beans.org.Department;
import by.jpdm.model.dao.DepartmentDAO;

@Model
public class DlgCreateDep implements Serializable {
	private static final long serialVersionUID = 1L;
	private static String DLG_CREATE_DEP = "create-dep";
	private String name;
	private String description;
	@Inject
	private DepartmentDAO departmentDao;

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
		departmentDao.createDepartment(dep);
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
