package gov.sir.core.web.helpers.correccion.validate.utils.validators;


public abstract class BasicConditionalValidator extends ConditionalValidator {
	public static final String OBJECT_TO_VALIDATE = "OBJECT_TO_VALIDATE";

	public Object getObjectToValidate() {
		return parameters.get( OBJECT_TO_VALIDATE );
	}
	public void setObjectToValidate( Object objectToValidate ) {
		parameters.put( OBJECT_TO_VALIDATE, objectToValidate );
	}

	protected boolean execute( java.util.Map parameters ) {
		Object objectToValidate = getObjectToValidate();
                // if( null == objectToValidate ) {
                //    throw new RuntimeException( "No se ha especificado el parametro basico a validar " );
                // } // TODO: cambiar por: ver si el key existe dentro del map.
		return execute( objectToValidate );
	}

	protected abstract boolean execute( Object objectToValidate );

}
