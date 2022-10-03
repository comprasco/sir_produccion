package gov.sir.core.web.helpers.comun;

import java.util.ArrayList;
import java.util.List;

public class JvLocalUtils {

   public static final String DEFAULT_SEPARATOR = ",";


	public static String[] csvStringToStringArray( String local_Object, String local_Separator, boolean ignoreNulls ) {
		if( null == local_Object ) {
			return null;
		}

		String[] local_Result;

		local_Result = local_Object.split( local_Separator );

		if( ignoreNulls ) {
			List tmp_Result;
			tmp_Result = new ArrayList();

			for( int i = 0; i < local_Result.length; i++ ){
				if( null == local_Result[i] ) {
					continue;
				}
				tmp_Result.add( local_Result[i] );
			}
			local_Result = new String[tmp_Result.size()];
			local_Result = (String[])tmp_Result.toArray( local_Result );

		}

		return local_Result;

	} // end-method: csvStringToStringArray


	public static String stringArrayToCsvString( String[] local_Object, String local_Separator, boolean ignoreNulls ) {

		if( null == local_Object )
			return null;

		if( 0 == local_Object.length )
			return "";

		String local_Result;
		local_Result = "";

		int first;
		first = 0;

		for( int i=0; i < local_Object.length; i++ ) {

			if( ignoreNulls && ( null == local_Object[i] ) ) {
				continue;
			}

			if( 0 != first ) {
				local_Result += local_Separator;
			}
			local_Result += local_Object[i];
			first ++;
		}

		return local_Result;

	} // end-method: stringArrayToCsvString


	public static void printArray( String[] local_Object, String name ) {
		if( null ==  local_Object ) {
			return;
		} // if

		String msg;
		msg  = "";
		for( int i=0; i < local_Object.length; i++ ) {
			msg += ( i + ": " + local_Object[i] + "\n" );
		}

	} // end-method: printArray

}
