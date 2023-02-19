package by.jpdm.jsf.validators;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;

import org.primefaces.validate.ClientValidator;

public abstract class BaseValidator implements Validator, ClientValidator {
    private static final String ID_BUTTON_IGNORE_MASK = "btn-cancel";
	private final String ID;
	
	public BaseValidator(String vid) {
	    ID = vid;
	}
	
	@Override
    public Map<String, Object> getMetadata() {
        return null;
    }
	
	@Override
    public String getValidatorId() {
        return ID;
    }
	
	/**
	 * Checks if a certain button has been clicked and the validation should be
	 * ignored. For the base case: check for 'cancel' click (ignore validation for
	 * cancel).
	 * 
	 * @param context  - faces context
	 * @param ignoreButtonId - id of a button to check
	 * @return true if a button id contains 'ignoreButtonId'-value, otherwise false
	 */
	public boolean shouldBeIgnored(FacesContext context) {
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		for (Map.Entry<String, String> me : params.entrySet()) {
			if (me.getKey().contains(ID_BUTTON_IGNORE_MASK))
				return true;
		}
		return false;
	}
}
