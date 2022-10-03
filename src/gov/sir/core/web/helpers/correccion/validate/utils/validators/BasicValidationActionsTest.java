package gov.sir.core.web.helpers.correccion.validate.utils.validators;

import junit.framework.TestCase;

public class BasicValidationActionsTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BasicValidationActionsTest.class);
	}

	public void testIsDouble() {
		assertTrue( BasicValidationActions.isDouble("0.02") );
	}

	public void testIsInteger() {
		assertTrue( BasicValidationActions.isInteger("1") );
		assertFalse( BasicValidationActions.isInteger("1.02") );
	}

	public void testIsInRange() {
		assertTrue( BasicValidationActions.isInRange(0.08,0.50,0.36) );
	}

	public void testObjectNotNull() {
		assertFalse( BasicValidationActions.objectNotNull(null) );
	}

	public void testStringMaxLength() {
		assertTrue( BasicValidationActions.stringMaxLength("esta",5) );
		assertFalse( BasicValidationActions.stringMaxLength("estacadena",5) );
	}

	public void testStringMinLength() {
		assertFalse( BasicValidationActions.stringMinLength("esta",5) );
		assertTrue( BasicValidationActions.stringMinLength("estacadena",5) );
	}

	public void testStringNotNullOrNotEmpty() {
		assertFalse( BasicValidationActions.stringNotNullOrNotEmpty("") );
		assertFalse( BasicValidationActions.stringNotNullOrNotEmpty(null) );
		assertTrue(  BasicValidationActions.stringNotNullOrNotEmpty("estacadena") );
	}

	public void testStringEqualsPattern() {
		assertTrue( BasicValidationActions.stringEqualsPattern("temporal", "temporal",false,false) );
		assertTrue( BasicValidationActions.stringEqualsPattern(" temporal ", "temporal",false,true) );
		assertTrue( BasicValidationActions.stringEqualsPattern(null, "",true,false) );
		assertTrue( BasicValidationActions.stringEqualsPattern(null, " ",true,true) );
		assertTrue( BasicValidationActions.stringEqualsPattern(null, null,true,true) );
	}

	public void testStringNotNullOrNotEmptyArray() {
		String[] testStringArray = new String[] {
		  ""
		, "temporal"
		};
		assertFalse( BasicValidationActions.stringNotNullOrNotEmptyArray( testStringArray ) );

		testStringArray[1] = null;

		assertFalse( BasicValidationActions.stringNotNullOrNotEmptyArray( testStringArray ) );

	}

}
