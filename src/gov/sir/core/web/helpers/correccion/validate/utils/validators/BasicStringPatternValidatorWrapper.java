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
public class BasicStringPatternValidatorWrapper extends BasicConditionalValidator {

    protected String pattern;
    boolean activateNullComparison = false;
    boolean whenPatternNotMatchActive = false; // si la condicion devuelve true, el resultado de la ejecucion es false y viceversa

    public BasicStringPatternValidatorWrapper( String pattern ) {
        this.pattern = pattern;
    }
    public BasicStringPatternValidatorWrapper( String pattern, boolean activateNullComparison ) {
        this( pattern );
        this.activateNullComparison = activateNullComparison;
    }
    public BasicStringPatternValidatorWrapper( String pattern, boolean activateNullComparison, boolean whenPatternNotMatchActive ) {
        this( pattern, activateNullComparison );
        this.whenPatternNotMatchActive = whenPatternNotMatchActive;
    }

    protected boolean execute(Object objectToValidate) {
            String paramAsString = (String)objectToValidate;
            boolean executionResult = BasicValidationActions.stringEqualsPattern(paramAsString,pattern,activateNullComparison,true);
            if( whenPatternNotMatchActive ) {
                return !executionResult;
            }
            return executionResult;
    }
}
