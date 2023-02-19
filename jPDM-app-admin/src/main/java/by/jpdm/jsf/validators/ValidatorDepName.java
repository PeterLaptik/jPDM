package by.jpdm.jsf.validators;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

@Named
@RequestScoped
@FacesValidator("department.name.validator")
public class ValidatorDepName extends BaseValidator implements Serializable {
    private static final long serialVersionUID = 1L;
	private static final String NAME_PATTERN = "[_A-Za-z0-9- ]+";
	private Pattern pattern;

	public ValidatorDepName() {
	    super("department.name.validator");
		pattern = Pattern.compile(NAME_PATTERN);
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Name cannot be empty."));
		}

		if (!pattern.matcher(value.toString()).matches()) {
			throw new ValidatorException(
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validation Error", "Name contains bad characters"));
		}
	}
}
