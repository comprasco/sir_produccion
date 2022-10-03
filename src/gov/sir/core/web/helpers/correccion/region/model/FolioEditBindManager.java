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
public class FolioEditBindManager extends BindManager {


    public static final Binder[] DEFAULT_BINDS = new Binder[]{
       // datos: folio/documento/**
       new Binder( "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento", "documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento" )
     , new Binder( "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre"        , "documento/oficinaOrigen/vereda/municipio/departamento/nombre" )
     , new Binder( "field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio"                , "documento/oficinaOrigen/vereda/municipio/idMunicipio" )
     , new Binder( "field:folio:documento/oficinaOrigen/vereda/municipio/nombre"                     , "documento/oficinaOrigen/vereda/municipio/nombre"       )
     , new Binder( "field:folio:documento/oficinaOrigen/vereda/idVereda"                             , "documento/oficinaOrigen/vereda/idVereda" )
     , new Binder( "field:folio:documento/oficinaOrigen/vereda/nombre"                               , "documento/oficinaOrigen/vereda/nombre" )
     , new Binder( "field:folio:documento/oficinaOrigen/idOficinaOrigen"                             , "documento/oficinaOrigen/idOficinaOrigen"  )
                 /*
         *  @author Carlos Torres
         *  @chage   se agrega validacion de version diferente
         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
         */
     , new Binder( "field:folio:documento/oficinaOrigen/version"                             , "documento/oficinaOrigen/version"  )
     , new Binder( "field:folio:documento/oficinaOrigen/numero"                                      , "documento/oficinaOrigen/numero"  )
     , new Binder( "field:folio:documento/oficinaOrigen/nombre"                                      , "documento/oficinaOrigen/nombre"  )
     , new Binder( "field:folio:documento/idDocumento"                                               , "documento/idDocumento" )

     , new Binder( "field:folio:documento/tipoDocumento/idTipoDocumento"                             , "documento/tipoDocumento/idTipoDocumento" )
     , new Binder( "field:folio:documento/tipoDocumento/nombre"                                      , "documento/tipoDocumento/nombre" )
     , new Binder( "field:folio:documento/numero"                                                    , "documento/numero"  )
     , new Binder( "field:folio:documento/fecha"                                                     , "documento/fecha"  )
     // datos: folio//**
     , new Binder( "field:folio:lindero"                                                             , "lindero"  )
     , new Binder( "field:folio:complementacion/complementacion"                                     , "complementacion/complementacion"  )
     , new Binder( "field:folio:complementacion/complementacion+"                                    , "complementacion/complementacion" )
     , new Binder( "field:folio:codCatastral"                                                        , "codCatastral" )
     , new Binder( "field:folio:codCatastralAnterior"                                                , "codCatastralAnterior" )
     , new Binder( "field:folio:nupre"                                                               , "nupre"  )
     , new Binder( "field:folio:determinaInm"                                                        , "determinaInm"  )
     , new Binder( "field:folio:privMetros"                                                          , "privMetros"  )
     , new Binder( "field:folio:privCentimetros"                                                     , "privCentimetros"  )       
     , new Binder( "field:folio:consMetros"                                                          , "consMetros"  )
     , new Binder( "field:folio:consCentimetros"                                                     , "consCentimetros"  )
     , new Binder( "field:folio:coeficiente"                                                         , "coeficiente"  ) 
     , new Binder( "field:folio:hectareas"                                                           , "hectareas"  ) 
     , new Binder( "field:folio:metros"                                                              , "metros"  )
     , new Binder( "field:folio:centimetros"                                                         , "centimetros"  )   
     , new Binder( "field:folio:estado/idEstado"                                                     , "estado/idEstado" )
     , new Binder( "field:folio:estado/comentario"                                                   , "estado/comentario" )

     // datos: folio/zonaRegistral//**
     , new Binder( "field:folio:zonaRegistral/vereda/idVereda"                                       , "zonaRegistral/vereda/idVereda"  )
     , new Binder( "field:folio:zonaRegistral/vereda/nombre"                                         , "zonaRegistral/vereda/nombre"  )
     , new Binder( "field:folio:zonaRegistral/vereda/municipio/idMunicipio"                          , "zonaRegistral/vereda/municipio/idMunicipio"  )
     , new Binder( "field:folio:zonaRegistral/vereda/municipio/nombre"                               , "zonaRegistral/vereda/municipio/nombre"  )
     , new Binder( "field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento"          , "zonaRegistral/vereda/municipio/departamento/idDepartamento"  )
     , new Binder( "field:folio:zonaRegistral/vereda/municipio/departamento/nombre"                  , "zonaRegistral/vereda/municipio/departamento/nombre"  )
     // datos: folio//**
     , new Binder( "field:folio:tipoPredio/idPredio"                                                 , "tipoPredio/idPredio"  )

     // datos: folio//** (bug:3580)
      , new Binder( "field:folio:radicacion"                                                         , "radicacion"  )
      , new Binder( "field:folio:fechaApertura"                                                      , "fechaApertura"  )


    };

    public void associate() {
        Binder[] binders = DEFAULT_BINDS;
        for( int i=0; i < binders.length; i++ ) {
            binderList.add( binders[i] );
        }
    }
}
