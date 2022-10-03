package gov.sir.core.web.helpers.correccion.validate.utils.convertors;

import gov.sir.core.util.DateFormatUtil;

import java.util.Date;
import java.text.ParseException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BasicConvertorActions {

  public static String toString( String string ) {

    // null conversion
    return string;
  }


  public static String toString( double value, String format ) {

     DecimalFormat df = new DecimalFormat();
     df.applyPattern( format );
     return df.format( value );

  } // end-method: toString

  public static String toString( Date date, String format ) {

          if( null == date ) {
                  return null;
          }

          SimpleDateFormat df = new SimpleDateFormat();
          df.applyPattern( format );
          df.setLenient( false );

          return df.format( date );

  } // :toString

  public static Date toDate( String tx, String format ) {

          if( null == tx ) {
                  return null;
          }

          Date result = null;
          result = type_FormatDate( tx, format );
          return result;

  } // :toString

  public static String Nvl( Object obj, String replace ) {
          if( null == obj )
                  return replace;
          return (String)obj;
  }

  // from BasicDateFormatComparator
  private static java.util.Date fixDateAccordingFormat( java.util.Date date, String format ) {

      String buffer = DateFormatUtil.format( date );
      java.util.Date result = null;
      try {
          result = DateFormatUtil.parse(buffer);
      }
      catch (ParseException ex) {
          result = date;
      }

      return result;
    }

    public static java.util.Date
    type_FormatDate( String tx, String format ) {

      SimpleDateFormat df = new SimpleDateFormat();
      df.applyPattern( format );
      df.setLenient( false );

      String localValue = tx;

      Date result = null;
      try {
           result = df.parse( localValue );//, pos );
      }
      catch (ParseException e) {

      }
      return result;

    }
}
