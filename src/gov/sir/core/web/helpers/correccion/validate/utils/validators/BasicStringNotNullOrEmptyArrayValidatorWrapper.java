package gov.sir.core.web.helpers.correccion.validate.utils.validators;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BasicStringNotNullOrEmptyArrayValidatorWrapper extends BasicConditionalValidator {

	protected boolean execute(Object objectToValidate) {
        // pass - test
        if( null == objectToValidate )
            return false;
        String[] paramAsStringArray = (String[])objectToValidate;
		return BasicValidationActions.stringNotNullOrNotEmptyArray( paramAsStringArray );
	}
}
