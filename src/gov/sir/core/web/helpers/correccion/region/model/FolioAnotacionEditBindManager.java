package gov.sir.core.web.helpers.correccion.region.model;

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
public class FolioAnotacionEditBindManager extends BindManager {


    public static final Binder[] DEFAULT_BINDS = new Binder[]{
       // datos: anotacion/documento/**
       new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento" , "documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento" )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre"         , "documento/oficinaOrigen/vereda/municipio/departamento/nombre" )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio"                 , "documento/oficinaOrigen/vereda/municipio/idMunicipio" )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre"                      , "documento/oficinaOrigen/vereda/municipio/nombre"       )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda"                              , "documento/oficinaOrigen/vereda/idVereda" )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre"                                , "documento/oficinaOrigen/vereda/nombre" )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen"                              , "documento/oficinaOrigen/idOficinaOrigen"  )
      /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/version"                                      , "documento/oficinaOrigen/version"  )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/numero"                                       , "documento/oficinaOrigen/numero"  )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaOrigen/nombre"                                       , "documento/oficinaOrigen/nombre"  )
     , new Binder( "field:folio:anotacion[i]:documento/oficinaInternacional"                                       , "documento/oficinaInternacional" )
     , new Binder( "field:folio:anotacion[i]:documento/idDocumento"                                                , "documento/idDocumento" )
     , new Binder( "field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento"                              , "documento/tipoDocumento/idTipoDocumento" )
     , new Binder( "field:folio:anotacion[i]:documento/tipoDocumento/nombre"                                       , "documento/tipoDocumento/nombre" )
     , new Binder( "field:folio:anotacion[i]:documento/numero"                                                     , "documento/numero"  )
     , new Binder( "field:folio:anotacion[i]:documento/fecha"                                                      , "documento/fecha"  )
     // datos: anotacion//**
     , new Binder( "field:folio:anotacion[i]:orden"           , "orden" )
     , new Binder( "field:folio:anotacion[i]:fechaRadicacion" , "fechaRadicacion" )
     , new Binder( "field:folio:anotacion[i]:numRadicacion"   , "numRadicacion" )
     // datos: anotacion//**
     , new Binder( "field:folio:anotacion[i]:valor" , "valor") // es double
     , new Binder( "field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica", "naturalezaJuridica/idNaturalezaJuridica" )
      /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Se establese el valor de la propiedad version
	 */
     , new Binder( "field:folio:anotacion[i]:naturalezaJuridica/version", "naturalezaJuridica/version" )       
     , new Binder( "field:folio:anotacion[i]:comentario", "comentario" )
     // datos: anotacion//**
     , new Binder( "field:folio:anotacion[i]:estado/idEstadoAn", "estado/idEstadoAn" )
     // datos: anotacion//**
     , new Binder( "field:folio:anotacion[i]:anotacionesCancelacion", "anotacionesCancelacion" )

    };

    public void associate() {
        Binder[] binders = DEFAULT_BINDS;
        for( int i=0; i < binders.length; i++ ) {
            binderList.add( binders[i] );
        }
    }
}
