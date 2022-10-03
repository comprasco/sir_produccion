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
public class BasicStringNotNullOrEmptyValidatorWrapper extends BasicConditionalValidator {

	protected boolean execute(Object objectToValidate) {

        if( null == objectToValidate )
            return false;
        String paramAsString = (String)objectToValidate;

		return BasicValidationActions.stringNotNullOrNotEmpty( paramAsString );
	}
}
