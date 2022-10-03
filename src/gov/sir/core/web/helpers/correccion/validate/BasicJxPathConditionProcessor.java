package gov.sir.core.web.helpers.correccion.validate;

import org.apache.commons.jxpath.JXPathContext;

import gov.sir.core.web.helpers.correccion.validate.utils.validators.ConditionalValidator;


// TODO: un validator que funcione sobre un objeto,
// que lo recorra en profundidadad y aplique las validaciones respectivas sobre el path
// indicado.

public class BasicJxPathConditionProcessor {

	String pathToTest;
	ConditionalValidator[] validationsToApply;
	Object source;
	public BasicJxPathConditionProcessor( String pathToTest, ConditionalValidator[] validationsToApply, Object source ) {
		this.pathToTest = pathToTest;
		this.validationsToApply = validationsToApply;
		this.source = source;
	}
	
	public void evaluate() {
		JXPathContext sourceContext = JXPathContext.newContext( source );
		
		Object propertyValue = null;
		propertyValue = getProperty( sourceContext, pathToTest );
		validationsToApply[0].validate();		

	}
	
	public static Object getProperty( JXPathContext context, String path ) {
		Object propertyValue = null;
		try {
			propertyValue = context.getValue( path );
		}
		catch( Exception e ) {
			
		}
		return propertyValue;
	}
	


}
