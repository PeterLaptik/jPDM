package controllers.jsf.validators;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.primefaces.validate.ClientValidator;

public abstract class AbstractValidator implements Validator, ClientValidator {
	/**
	 * Checks if a certain button has been clicked and the validation should be
	 * ignored. For example, check for 'cancel' click (ignore validation for
	 * cancel).
	 * 
	 * @param context  - faces context
	 * @param ignoreButtonId - id of a button to check
	 * @return true if a button id contains 'ignoreButtonId'-value, otherwise false
	 */
	public boolean shouldBeIgnored(FacesContext context, String ignoreButtonId) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		for (Map.Entry<String, String> me : params.entrySet()) {
			if (me.getKey().contains(ignoreButtonId))
				return true;
		}
		return false;
	}
}
