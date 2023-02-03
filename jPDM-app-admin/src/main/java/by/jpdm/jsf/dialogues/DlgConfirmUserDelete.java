package by.jpdm.jsf.dialogues;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import jakarta.inject.Named;

@Named
@ManagedBean
@SessionScoped
public class DlgConfirmUserDelete {
	public void confirm() {
		addMessage("Confirmed", "You have accepted");
	}

	public void delete() {
		addMessage("Confirmed", "Record deleted");
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
