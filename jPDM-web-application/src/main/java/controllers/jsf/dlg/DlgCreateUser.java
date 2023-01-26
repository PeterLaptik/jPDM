package controllers.jsf.dlg;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DialogFrameworkOptions;
import org.springframework.stereotype.Component;

import model.beans.org.Department;

@Component
@RequestScoped
@ManagedBean
public class DlgCreateUser implements Serializable {
	private static final long serialVersionUID = 8266889222254223297L;
	private static String DLG_CREATE_USER = "create-user";
	private String name;
	private String login;
	private String password;

	private Department itemDepartment;

	public void createUserShow() {
		DialogFrameworkOptions options = DialogFrameworkOptions.builder().modal(true).width("330px").responsive(true)
				.build();

		PrimeFaces.current().dialog().openDynamic(DLG_CREATE_USER, options, null);
	}

	public void create() {
		System.out.println("Create:");
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
//		FacesContext.getCurrentInstance().getExternalContext().
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void cancel() {
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getItemDepartment() {
		return itemDepartment;
	}

	public void setItemDepartment(Department itemDepartment) {
		this.itemDepartment = itemDepartment;
	}
}
