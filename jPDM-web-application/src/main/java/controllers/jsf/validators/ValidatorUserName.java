package controllers.jsf.validators;

import java.util.Map;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@ManagedBean
@SessionScoped
@FacesValidator("user.name.validator")
public class ValidatorUserName extends AbstractValidator {
	private static final String ID_CANCEL_MASK = "btn-cancel";
	private static final String ID = "user.name.validator";
	private static final String NAME_PATTERN = "[_A-Za-z0-9- ]+";
	private Pattern pattern;

	public ValidatorUserName() {
		pattern = Pattern.compile(NAME_PATTERN);
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

	@Override
	public String getValidatorId() {
		return ID;
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (shouldBeIgnored(context, ID_CANCEL_MASK))
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
