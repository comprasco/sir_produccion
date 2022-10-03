package gov.sir.core.web.helpers.correccion.diff.utils;

import gov.sir.core.negocio.modelo.util.Log;
import org.apache.commons.jxpath.JXPathContext;

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
public class BasicJxPathUtils {

    public static int compareProperty( JXPathContext sourceContext, JXPathContext targetContext, String path, java.util.Comparator comparatorToApply ) {

            Object nodeSource = null;
            Object nodeTarget = null;

            try {
                    nodeSource = sourceContext.getValue( path );
            }
            catch( Exception e ){
               // logger.error(e);
            }

            try {
                nodeTarget = targetContext.getValue( path );
            }
            catch( Exception e ){
              //  logger.error(e);
            }
            /*
            * @autor         : HGOMEZ 
            * @mantis        : 11631 
            * @Requerimiento : 036_453_Correccion_Actuaciones_Administrativas  
            * @descripcion   : Se controla excepción de comparación 
            * número long y dobles.
            */
            try {
                return comparatorToApply.compare( nodeSource, nodeTarget );
            }
            catch( Exception e ){
                //  logger.error(e);
            }
            return 0;            
    }

    public static void setProperty( JXPathContext context, String path, Object propertyValue ) {
            try {
                    // context.setValue( path, propertyValue );
                    context.createPathAndSetValue( path, propertyValue );
            }
            catch( Exception e ) {
            	   Log.getInstance().error(BasicJxPathUtils.class,e);
            }
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
