package gov.sir.core.web.helpers.correccion.validate.utils.validators;

import junit.framework.TestCase;

public class BasicStringNotNullOrEmptyValidatorWrapperTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BasicStringNotNullOrEmptyValidatorWrapperTest.class);
	}

	/*
	 * Class under test for boolean execute(Object)
	 */
	public void testExecuteObject() {
		ConditionalValidator validator = new BasicStringNotNullOrEmptyValidatorWrapper();

		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, null );
		validator.validate();

		assertFalse( validator.getResult() );

		validator.resetParameters();

		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "" );
		validator.validate();

		assertFalse( validator.getResult() );

		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "algo" );
		validator.validate();

		assertTrue( validator.getResult() );

	}

}
