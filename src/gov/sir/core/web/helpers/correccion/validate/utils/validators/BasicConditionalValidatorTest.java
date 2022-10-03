package gov.sir.core.web.helpers.correccion.validate.utils.validators;

import gov.sir.core.web.helpers.correccion.validate.utils.conditions.BasicStaticBooleanCondition;
import gov.sir.core.web.helpers.correccion.validate.utils.conditions.ConditionComponent;

import junit.framework.TestCase;

public class BasicConditionalValidatorTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BasicConditionalValidatorTest.class);
	}

	public void testValidate() {

		ConditionComponent condition;

		// probar con cualquier validador condicional

		ConditionalValidator validator;

		validator = new BasicStringNotNullOrEmptyValidatorWrapper();
		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "algo" );

		condition = new BasicStaticBooleanCondition( false );
		validator.addCondition( condition );
		validator.validate();

		// si hay una condicion habilitada
		// pasa como true;
		assertTrue( validator.getResult() );

		validator.resetParameters();
		validator.resetConditions();

		validator = new BasicStringNotNullOrEmptyValidatorWrapper();
		validator.addParameter( BasicConditionalValidator.OBJECT_TO_VALIDATE, "algo" );

		condition = new BasicStaticBooleanCondition( true );
		validator.addCondition( condition );
		validator.validate();

		// la condicion ya esta habilitada
		assertTrue( validator.getResult() );


	}

}
