package gov.sir.core.web.helpers.correccion.diff.utils.jxpath;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Cancelacion;


/**
 *
 * Crear un objeto Anotacion
 * a partir de los contextos de jxpath.
 *
 */
public class FolioAnotacionCancelacionAbstractFactory extends org.apache.commons.jxpath.AbstractFactory{


    public boolean
    createObject( JXPathContext context, Pointer pointer, Object parent, String name, int index ){
       if( parent instanceof Cancelacion ) {
           if( "cancelada".equals( name ) ) {
               ((Cancelacion)parent).setCancelada( new Anotacion() );
               return true;
           }
           if( "canceladora".equals( name ) ) { //estado o estadoFolio ?
               ((Cancelacion)parent).setCanceladora( new Anotacion() );
               return true;
           }
       }
       return false;
    }
}

