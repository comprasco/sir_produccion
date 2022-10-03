package gov.sir.core.web.helpers.correccion.region.model;

import java.util.ArrayList;

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
public class EditFoliosProcessManager implements ProcessManager {

    // implementation for backward compatibility

    public static class PermisosCorreccionAspectModelConstants implements PermisosCorreccionConstants {

     //   public static final Process.Binder[] PERMISOS_LIST1 = new Process.Binder[]{
     //           new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "folio.datosbasicos.ubicacion" ) // to change
     //         , new Process.Binder( FOLIO_DIRECCION_ID, "folio.direcciones" )
     //         , new Process.Binder( FOLIO_CABIDAYLINDEROS_ID, "folio.lindero" )
     //         , new Process.Binder( FOLIO_COMPLEMENTACION_ID, "folio.complementacion" )
     //         , new Process.Binder( FOLIO_NUMEROCATASTRAL_ID, "folio.codCatastral" )
     //         , new Process.Binder( FOLIO_FOLIOSMATRICES_ID, "" )
     //         , new Process.Binder( FOLIO_FOLIOSSEGREGADOS_ID, "" )
     //         , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "folio.datosApertura" )
     //
     //         // , new Region.Binder( ANOTACION_ID, "htRgn_AnotacionesMain" )
     //         , new Process.Binder( FOL_ANT_CREAR, "create(folio.anotacionNormal)" )
     //         , new Process.Binder( FOL_ANT_CREAR, "create(folio.anotacionCancelacion)" )
     //
     //         , new Process.Binder( ANOTACION_NUMERO_ID, "anotacion.setOrden" )
     //         , new Process.Binder( ANOTACION_FECHA_ID, "anotacion.setFechaRadicacion" )
     //         , new Process.Binder( ANOTACION_RADICACIONMANUAL_ID, "anotacion.numRadicacion" )
     //         , new Process.Binder( ANOTACION_DOCUMENTO_ID, "anotacion.setDocumento" )
     //         , new Process.Binder( ANOTACION_VALORACTO_ID, "anotacion.setValor" )
     //
     //         , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "anotacion.naturalezaJuridica.idNaturaleza" )
     //         , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "anotacion.comentario" )
//   //           , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "anotacion.estadoAnotacion" )
     //
     //         , new Process.Binder( ANOTACION_CIUDADANOS_ID, "anotacion.ciudadanos" )
     //         , new Process.Binder( ANOTACION_ROLDECIUDADANOSENANOTACION_ID, "" )
     //   };


        public static final Process.Binder[] PERMISOS_LIST2 = new Process.Binder[]{

                //  temporales, para ensayar con documento -----------------------------------------
                new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/municipio/departamento/nombre" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/municipio/idMunicipio" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/municipio/nombre" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/idVereda" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/vereda/nombre" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/idOficinaOrigen" )
               /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/version" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/numero" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/oficinaOrigen/nombre" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/idDocumento" )

              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/tipoDocumento/idTipoDocumento" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/tipoDocumento/nombre" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/numero" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:documento/fecha" )
              // bug 3580: -----------------------------------------------------------------------------------
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:radicacion" )
              , new Process.Binder( FOLIO_DATOSDEAPERTURA_ID, "field:folio:fechaApertura" )
              // -----------------------------------------------------------------------------------
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/idVereda" ) // to change
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/nombre" ) // to change
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/municipio/idMunicipio" ) // to change
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/municipio/nombre" ) // to change
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/municipio/departamento/idDepartamento" ) // to change
              , new Process.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "field:folio:zonaRegistral/vereda/municipio/departamento/nombre" ) // to change

            , new Process.Binder( FOLIO_DIRECCION_ID            , "field:folio:direcciones" ) // (not basic binding) // to change
            , new Process.Binder( FOLIO_CABIDAYLINDEROS_ID      , "field:folio:lindero" )
            , new Process.Binder( FOLIO_COMPLEMENTACION_ID      , "field:folio:complementacion/complementacion" )
            , new Process.Binder( FOLIO_COMPLEMENTACIONADD_ID   , "field:folio:complementacion/complementacion+" )
            , new Process.Binder( FOLIO_NUMEROCATASTRAL_ID      , "field:folio:codCatastral" )
            , new Process.Binder( FOLIO_NUMEROCATASTRAL_ID      , "field:folio:codCatastralAnterior" )
            , new Process.Binder( FOLIO_NUPRE_ID                , "field:folio:nupre" )
            , new Process.Binder( FOLIO_DETERMINAINM_ID         , "field:folio:determinaInm" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:privMetros" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:privCentimetros" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:consMetros" )    
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:consCentimetros" )
            , new Process.Binder( FOLIO_COEFICIENTE_ID          , "field:folio:coeficiente" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:hectareas" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:metros" )
            , new Process.Binder( FOLIO_AREA_ID                 , "field:folio:centimetros" )
            , new Process.Binder( FOLIO_FOLIOSMATRICES_ID       , "" )
            , new Process.Binder( FOLIO_FOLIOSSEGREGADOS_ID     , "" )

            , new Process.Binder( FOLIO_TIPOPREDIO_ID     , "field:folio:tipoPredio/idPredio" )
            , new Process.Binder( FOLIO_ESTADO_ID     , "field:folio:estado/idEstado" )
            , new Process.Binder( FOLIO_ESTADO_ID     , "field:folio:estado/comentario" )

            // , new Region.Binder( ANOTACION_ID, "htRgn_AnotacionesMain" )
            , new Process.Binder( FOL_ANT_CREAR                 , "action:folio.anotaciones:create(folio.anotacionNormal)" )
            , new Process.Binder( FOL_ANT_CREAR                 , "action:folio.anotaciones:create(folio.anotacionCancelacion)" )

            , new Process.Binder( ANOTACION_NUMERO_ID           , "field:folio:anotacion[i]:orden" )
            , new Process.Binder( ANOTACION_FECHA_ID            , "field:folio:anotacion[i]:fechaRadicacion" )
            , new Process.Binder( ANOTACION_RADICACIONMANUAL_ID , "field:folio:anotacion[i]:numRadicacion" )

            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/idDepartamento" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/departamento/nombre" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/idMunicipio" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/municipio/nombre" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/idVereda" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/vereda/nombre" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/idOficinaOrigen" )
             /*
                 *  @author Carlos Torres
                 *  @chage   se agrega validacion de version diferente
                 *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                 */
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/version" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/numero" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/oficinaOrigen/nombre" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/idDocumento" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/tipoDocumento/idTipoDocumento" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/tipoDocumento/nombre" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/numero" )
            , new Process.Binder( ANOTACION_DOCUMENTO_ID, "field:folio:anotacion[i]:documento/fecha" )

            , new Process.Binder( ANOTACION_VALORACTO_ID     , "field:folio:anotacion[i]:valor" ) // es double
            , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "field:folio:anotacion[i]:naturalezaJuridica/idNaturalezaJuridica" )
              /**
                * @Autor: Carlos Torres
                * @Mantis: 0012705
                * @Requerimiento:  056_453_Modificiación_de_Naturaleza_Jurídica
                * @Descripcion: Se inclulle en la lista de capos el campo version
                */
            , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "field:folio:anotacion[i]:naturalezaJuridica/version" )
            , new Process.Binder( ANOTACION_ESPECIFICACION_ID, "field:folio:anotacion[i]:comentario" )

            , new Process.Binder( ANOTACION_CIUDADANOS_ID    , "field:folio:anotacion[i]:anotacionesCiudadano" ) // multiple
            , new Process.Binder( ANOTACION_ROLDECIUDADANOSENANOTACION_ID, "field:folio:anotacion[i]:anotacionesCiudadano[j]/rolPersona" )

            , new Process.Binder( ANOTACION_ESTADO_ID, "field:folio:anotacion[i]:estado/idEstadoAn" )

            , new Process.Binder( ANOTACION_TCANCELASELECT_ID, "field:folio:anotacion[i]:anotacionesCancelacion" ) // bug 3547
        };



        public static final Process.Binder[] PERMISOSCONFIGURABLES_ARRAY = PERMISOS_LIST2; //PERMISOS_LIST1

    }

    java.util.ArrayList processList = new java.util.ArrayList();

    public EditFoliosProcessManager() {
        associate();
    }

    public Process getProcessById( String processId ) {
        Process process = new Process( processId );
        int indexOf = processList.indexOf( process );

        if( indexOf < 0 )
            return null;

        return (Process)processList.get( indexOf );
    }


    protected void associate() {
        String itemId;
        String contextName;

        Process process;

        Process.Binder[] permisosConfigurables = PermisosCorreccionAspectModelConstants.PERMISOSCONFIGURABLES_ARRAY;

        // create regions
        for( int i=0; i < permisosConfigurables.length; i++ ) {
            itemId = permisosConfigurables[i].getBindId();
            contextName = permisosConfigurables[i].getBindContextName();

            process = new Process( itemId );
            process.setContextName( contextName );
            this.processList.add( process );
        }
    }

    public void filter( java.util.HashMap permisosMap ) {
        // tiene el conjunto de context-id's
        // para cada region se manda a evaluar el aspecto, si esta relacionado.

        java.util.Iterator iterator;
        iterator = processList.iterator();

        for(;iterator.hasNext();){
            Process process = (Process)iterator.next();

            String regionContextName = process.getContextName();
            if( permisosMap.containsKey( regionContextName ) ) {
               Process.AspectModel aspecto = (Process.AspectModel)permisosMap.get( regionContextName );
               process.getConditionalProcess().evaluate( aspecto );
            }

        }

    }

    public static
    void main( String[] args ) {
       EditFoliosProcessManager prcMgr = new EditFoliosProcessManager();
       java.util.Iterator iterator = prcMgr.getProcessList().iterator();

       StringBuffer buffer = new StringBuffer();
       for(;iterator.hasNext();){
           Process process = (Process)iterator.next();
           // process.generateXmlTextFragment_XmlDebugImp( buffer );
       }
    }

    public ArrayList getProcessList() {
        return processList;
    }

}
