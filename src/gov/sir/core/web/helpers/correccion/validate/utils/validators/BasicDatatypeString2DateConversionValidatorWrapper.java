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
public class BasicDatatypeString2DateConversionValidatorWrapper extends BasicConditionalValidator  {

    protected String format = "dd/MM/yyyy";
    public BasicDatatypeString2DateConversionValidatorWrapper( String newFormat ) {
        format = newFormat;
    }

	protected boolean execute(Object objectToValidate) {
        // pass - test
        if( null == objectToValidate )
            return false;
        String paramAsString = (String)objectToValidate;

		return BasicValidationActions.isDate( paramAsString, format );
	}


}
