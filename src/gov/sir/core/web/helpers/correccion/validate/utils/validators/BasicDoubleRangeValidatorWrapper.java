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
public class BasicDoubleRangeValidatorWrapper extends BasicConditionalValidator {

    protected double left = Double.NEGATIVE_INFINITY;
    protected double right = Double.POSITIVE_INFINITY;

    protected boolean parseString = false;

    public BasicDoubleRangeValidatorWrapper() {

    }
    public BasicDoubleRangeValidatorWrapper( boolean parseString ) {
        this.parseString = parseString;
    }

    public BasicDoubleRangeValidatorWrapper( double left, double right ) {
        this();

        this.left = left;
        this.right = right;
    }

    /* recibe un Double o un string con el objeto a probar */

	protected boolean execute(Object objectToValidate) {
        Double paramAsDouble = null;

        if( parseString ) {
            String paramAsString = (String) objectToValidate;
            objectToValidate = Double.valueOf( paramAsString.trim() );
        }

        paramAsDouble = (Double)objectToValidate;

        double testedValue = paramAsDouble.doubleValue();

		return BasicValidationActions.isInRange(this.left, this.right, testedValue);
	}

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getRight() {
        return right;
    }

    public double getLeft() {
        return left;
    }

}
