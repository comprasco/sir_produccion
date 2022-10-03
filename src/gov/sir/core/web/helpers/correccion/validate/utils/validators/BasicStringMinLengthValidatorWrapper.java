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
public class BasicStringMinLengthValidatorWrapper extends BasicConditionalValidator {

    protected int minLength = Integer.MIN_VALUE;
    public BasicStringMinLengthValidatorWrapper( int minLength ) {
        this.minLength = minLength;
    }

        protected boolean execute(Object objectToValidate) {
        // pass - test
        if( null == objectToValidate )
            return false;
        String paramAsString = (String)objectToValidate;

                return BasicValidationActions.stringMinLength(paramAsString, this.minLength);
        }

}
