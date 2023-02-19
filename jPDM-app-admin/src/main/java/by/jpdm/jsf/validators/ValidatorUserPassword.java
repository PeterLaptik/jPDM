package by.jpdm.jsf.validators;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
@FacesValidator("user.password.validator")
public class ValidatorUserPassword extends BaseValidator implements Serializable {
    private static final long serialVersionUID = 1L;

    public ValidatorUserPassword() {
        super("user.password.validator");
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (shouldBeIgnored(context))
            return; // ignore on cancel

        if (value == null) {
            return;
        }

        if (value.toString().isEmpty()) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Password cannot be empty."));
        }
    }
}
