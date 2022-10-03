package gov.sir.core.web.helpers.correccion.validate.utils.validators;

import gov.sir.core.web.helpers.correccion.diff.utils.comparators.BasicStringComparator;
import java.util.Date;
import gov.sir.core.web.helpers.correccion.validate.utils.convertors.BasicConvertorActions;

public class BasicValidationActions {


    /**
     * Regresa true si se pudo efectuar la conversion a double
     * */
	public static boolean isDouble( String paramAsString ){

	    if( null == paramAsString )
	        return false;

	    paramAsString = paramAsString.trim();

	    boolean passTest = false;

	    double passTestResult = -1d;

	    try {
	        passTestResult = Double.parseDouble( paramAsString );
	        passTest = true;
	    }
	    catch( NumberFormatException e ){
	        passTest = false;
	    }

	    return passTest;
	}

	/**
	 * Regresa true si se pudo efectuar la conversion a entero */
	public static boolean isInteger( String paramAsString ) {
	    // pass - test

	    if( null == paramAsString )
	        return false;

	    paramAsString = paramAsString.trim();

	    boolean passTest = false;

	    long  passTestResult = -1;

	    try {
	        //passTestResult = Integer.parseInt( paramAsString );
	    	passTestResult = Long.parseLong(paramAsString );
	        passTest = true;
	    }
	    catch( NumberFormatException e ){
	        passTest = false;
	    }

	    return passTest;
	}

        /**
         * Regresa true si se pudo efectuar la conversion a java.util.Date */
        public static boolean isDate( String paramAsString, String paramFormat ) {
            // pass - test

            if( null == paramAsString )
                return false;

            paramAsString = paramAsString.trim();

            boolean passTest = false;

            Date testDate;
            testDate = BasicConvertorActions.type_FormatDate( paramAsString,paramFormat );

            passTest = ( null != testDate )?( true ) :( passTest );

            return passTest;
        }


    /* recibe un Double con el objeto a probar */
    public static boolean isInRange( double left, double right, double testedValue ) {
        // pass - test

        // left < a < right

        if( ( left  <= testedValue )
          &&( right >= testedValue ) ) {
           return true;
        }
        return false;

    }

    /** Regresa true el objeto NO es null */
    public static boolean objectNotNull( Object param ) {
        // pass - test
        if( null == param )
            return false;
        return true;
    }

	/**
	 * Si la longitud de la cadena a probar cumple 0 <= string.length <= maxLength pasa la prueba
	 * */
	public static boolean stringMaxLength( String paramAsString, int maxLength ){
        if( null == paramAsString )
            return false;
        boolean result = paramAsString.length() <= maxLength;
        return result;

	}
	/**
	 * Si la cadena a probar tiene como minimo minlength, se pasa la prueba
	 * */
	public static boolean stringMinLength( String paramAsString, int minLength ){
        if( null == paramAsString )
            return false;
        boolean result = paramAsString.length() >= minLength;
        return result;

	}

	public static boolean stringNotNullOrNotEmpty( String paramAsString ) {
		final String PATTERN = "";

		return !stringEqualsPattern(paramAsString,PATTERN, true, true);

	}

	public static boolean stringEqualsPattern( String paramAsString, String pattern, boolean activateNullComparison, boolean trimEnabled ) {
		BasicStringComparator basicStringComparator  = new BasicStringComparator( activateNullComparison, trimEnabled );
		int comparisonResult = basicStringComparator.compare( paramAsString, pattern );

		return( comparisonResult == 0 );
	}

	public static boolean stringNotNullOrNotEmptyArray( String[] paramAsStringArray ) {
		for (int i = 0; i < paramAsStringArray.length; i++) {
			if( !stringNotNullOrNotEmpty( paramAsStringArray[i] ) ){
				return false;
			}
		}
		return true;
	}



}
