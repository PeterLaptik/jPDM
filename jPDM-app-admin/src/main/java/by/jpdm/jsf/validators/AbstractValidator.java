package by.jpdm.jsf.validators;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.primefaces.validate.ClientValidator;

public abstract class AbstractValidator implements Validator<Object>, ClientValidator {
	public static final String ID_CANCEL_MASK = "btn-cancel";
	
	/**
	 * Checks if a certain button has been clicked and the validation should be
	 * ignored. For example, check for 'cancel' click (ignore validation for
	 * cancel).
	 * 
	 * @param context  - faces context
	 * @param ignoreButtonId - id of a button to check
	 * @return true if a button id contains 'ignoreButtonId'-value, otherwise false
	 */
	public boolean shouldBeIgnored(FacesContext context) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		for (Map.Entry<String, String> me : params.entrySet()) {
			if (me.getKey().contains(ID_CANCEL_MASK))
				return true;
		}
		return false;
	}
}
