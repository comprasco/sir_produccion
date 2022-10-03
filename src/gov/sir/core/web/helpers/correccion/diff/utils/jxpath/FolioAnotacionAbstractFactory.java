package gov.sir.core.web.helpers.correccion.diff.utils.jxpath;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import gov.sir.core.negocio.modelo.Documento;
import gov.sir.core.negocio.modelo.TipoDocumento;
import gov.sir.core.negocio.modelo.OficinaOrigen;
import gov.sir.core.negocio.modelo.TipoOficina;
import gov.sir.core.negocio.modelo.Vereda;
import gov.sir.core.negocio.modelo.Municipio;
import gov.sir.core.negocio.modelo.Departamento;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.EstadoAnotacion;
import gov.sir.core.negocio.modelo.NaturalezaJuridica;


/**
 *
 * Crear un objeto Anotacion
 * a partir de los contextos de jxpath.
 *
 */
public class FolioAnotacionAbstractFactory extends org.apache.commons.jxpath.AbstractFactory{


    public boolean
    createObject( JXPathContext context, Pointer pointer, Object parent, String name, int index ){
       if( parent instanceof Anotacion ) {
           if( "documento".equals( name ) ) {
               ((Anotacion)parent).setDocumento( new Documento() );
               return true;
           }
           if( "estado".equals( name ) ) { //estado o estadoFolio ?
               ((Anotacion)parent).setEstado( new EstadoAnotacion() );
               return true;
           }
           if( "naturalezaJuridica".equals( name ) ) { //estado o estadoFolio ?
               ((Anotacion)parent).setNaturalezaJuridica( new NaturalezaJuridica() );
               return true;
           }
       }
       else if( parent instanceof Documento ) {
           if( "tipoDocumento".equals( name ) ) {
               ((Documento)parent).setTipoDocumento( new TipoDocumento() );
               return true;
           }
           if( "oficinaOrigen".equals( name ) ) {
               ((Documento)parent).setOficinaOrigen( new OficinaOrigen() );
               return true;
           }
       }
       else if( parent instanceof OficinaOrigen ) {
           if( "tipoOficina".equals( name ) ){
               ((OficinaOrigen)parent).setTipoOficina( new TipoOficina() );
               return true;
           }
           if( "vereda".equals( name ) ){
               ((OficinaOrigen)parent).setVereda( new Vereda() );
               return true;
           }
       }
       else if( parent instanceof Vereda ) {
           if( "municipio".equals( name ) ){
               ((Vereda)parent).setMunicipio( new Municipio() );
               return true;
           }
       }
       else if( parent instanceof Municipio ) {
           if( "departamento".equals( name ) ){
               ((Municipio)parent).setDepartamento( new Departamento() );
               return true;
           }
       }

       return false;
    }
}

