package by.jpdm.jsf.model.errors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import by.jpdm.model.dao.exceptions.JpdmModelException;
import jakarta.inject.Named;

/**
 * Shows error message in a 'p:growl'-element after pop-up dialog is closed. The
 * bean must be assigned as a listener on a 'dialogReturn'-event
 * 
 * @author Peter Laptik
 */
@Named
@ManagedBean
@RequestScoped
public class ErrorProcessor {
    private static final String ERROR_RECIEVER = "sticky-key";

    public void handleReturn(SelectEvent<Object> evt) {
        Object object = evt.getObject();

        if (!(object instanceof Exception))
            return;

        String msg = ((Exception) object).getMessage();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg);
        FacesContext.getCurrentInstance().addMessage(ERROR_RECIEVER, message);
    }
}
