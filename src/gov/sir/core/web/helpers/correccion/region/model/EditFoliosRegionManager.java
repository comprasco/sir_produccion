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
public class EditFoliosRegionManager implements RegionManager , java.io.Serializable{

    // implementation for backward compatibility

    public static class PermisosCorreccionAspectModelConstants implements PermisosCorreccionConstants , java.io.Serializable {

        public static final Region.Binder[] PERMISOSCONFIGURABLES_ARRAY = new Region.Binder[]{
                new Region.Binder( FOLIO_UBICACIONGEOGRAFICA_ID, "htRgn_UbicacionGeografica_Main" ) // Folio/DatosBasicos/Ubicacion
              , new Region.Binder( FOLIO_DIRECCION_ID, "htRgn_DireccionMain" ) //Folio.Direcciones
              , new Region.Binder( FOLIO_CABIDAYLINDEROS_ID, "htRgn_LinderoMain" ) //Folio.Lindero
              , new Region.Binder( FOLIO_COMPLEMENTACION_ID, "htRgn_ComplementacionMain" ) //Folio.Complementacion
              , new Region.Binder( FOLIO_COMPLEMENTACIONADD_ID, "htRgn_ComplementacionAdicionMain" ) //Folio.Complementacion
              , new Region.Binder( FOLIO_NUMEROCATASTRAL_ID, "htRgn_NumeroCatastralMain" ) //Folio/codigoCatastral
              , new Region.Binder( FOLIO_NUMEROCATASTRAL_ID, "htRgn_NumeroCatastralAnteriorMain" ) //Folio/codigoCatastralAnterior
              , new Region.Binder( FOLIO_NUPRE_ID, "htRgn_NupreMain" ) //Folio/nupre
              , new Region.Binder( FOLIO_DETERMINAINM_ID, "htRgn_DeterminaInmMain" ) //Folio/determinaInm
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_PrivMetrosMain" ) //Folio/privMetros
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_PrivCentimetrosMain" ) //Folio/privCentimetros
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_ConsMetrosMain" ) //Folio/consMetros
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_ConsCentimetrosMain" ) //Folio/consCentimetros
              , new Region.Binder( FOLIO_COEFICIENTE_ID, "htRgn_CoeficienteMain" ) //Folio/coeficiente
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_HectareasMain" ) //Folio/hectareas
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_MetrosMain" ) //Folio/metros
              , new Region.Binder( FOLIO_AREA_ID, "htRgn_CentimetrosMain" ) //Folio/centimetros
              , new Region.Binder( FOLIO_FOLIOSMATRICES_ID, "htRgn_FoliosMatricesMain" ) //Tab2.Folios.Matriz-Segregacion.Matriz
              , new Region.Binder( FOLIO_FOLIOSSEGREGADOS_ID, "htRgn_FoliosSegregadosMain" ) //Tab2.Folios.Matriz-Segregacion.Segregacion
              , new Region.Binder( FOLIO_DATOSDEAPERTURA_ID, "htRgn_DatosDeAperturaMain" ) //Folio.Documento
              , new Region.Binder( FOL_ANT_CREAR, "htRgn_AnotacionesCrearAnotacionNormal" ) //Folio.AnotacionNormal-Crear
              , new Region.Binder( FOL_ANT_CREAR, "htRgn_AnotacionesCrearAnotacionCanceladora" ) // Folio.AnotacionCanceladora-Crear
              , new Region.Binder( ANOTACION_NUMERO_ID, "htRgn_AnotacionesNumero_Main" )
              , new Region.Binder( ANOTACION_FECHA_ID, "htRgn_AnotacionesFechaMain" )
              , new Region.Binder( ANOTACION_RADICACIONMANUAL_ID, "htRgn_AnotacionesRadicacionManualMain" )
              , new Region.Binder( ANOTACION_DOCUMENTO_ID, "htRgn_AnotacionesDocumentoMain" )
              , new Region.Binder( ANOTACION_VALORACTO_ID, "htRgn_AnotacionesValorActoMain" )

              , new Region.Binder( ANOTACION_ESPECIFICACION_ID, "htRgn_AnotacionesEspecificacionNaturalezaJuridicaMain" )
              , new Region.Binder( ANOTACION_ESPECIFICACION_ID, "htRgn_AnotacionesEspecificacionComentarioMain" )
              //, new Region.Binder( ANOTACION_ESPECIFICACION_ID, "htRgn_AnotacionesEstadoAnotacionMain" )

              , new Region.Binder( ANOTACION_CIUDADANOS_ID, "htRgn_AnotacionesCiudadanosMain" )
              , new Region.Binder( ANOTACION_ROLDECIUDADANOSENANOTACION_ID, "htRgn_AnotacionesRolDeCiudadanosEnAnotacionMain" )

              , new Region.Binder( FOLIO_TIPOPREDIO_ID, "htRgn_FolioTipoPredio" )
              , new Region.Binder( FOLIO_ESTADO_ID, "htRgn_FolioEstado" )
              , new Region.Binder( FOLIO_ESTADO_ID, "htRgn_FolioEstado_Comentario" )
              , new Region.Binder( ANOTACION_ESTADO_ID, "htRgn_AnotacionEstado" )
              , new Region.Binder( ANOTACION_TCANCELASELECT_ID, "htRgn_AnotacionCanceladoraEditar_SelectItems" )

        };

    }

    java.util.ArrayList regionList = new java.util.ArrayList();

    public EditFoliosRegionManager() {
        associate();
    }

    public Region getRegionById( String regionId ) {
        Region region = new Region( regionId );
        int indexOf = regionList.indexOf( region );

        if( indexOf < 0 )
            return null;

        return (Region)regionList.get( indexOf );
    }


    protected void associate() {
        String itemId;
        String contextName;

        Region region;

        Region.Binder[] permisosConfigurables = PermisosCorreccionAspectModelConstants.PERMISOSCONFIGURABLES_ARRAY;

        // create regions
        for( int i=0; i < permisosConfigurables.length; i++ ) {
            itemId = permisosConfigurables[i].getBindId();
            contextName = permisosConfigurables[i].getBindContextName();

            region = new Region( itemId );
            region.setContextName( contextName );
            this.regionList.add( region );
        }
    }

    public void filter( java.util.HashMap permisosMap ) {
        // tiene el conjunto de context-id's
        // para cada region se manda a evaluar el aspecto, si esta relacionado.

        java.util.Iterator iterator;
        iterator = regionList.iterator();

        for(;iterator.hasNext();){
            Region region = (Region)iterator.next();

            String regionContextName = region.getContextName();
            if( permisosMap.containsKey( regionContextName ) ) {
               Region.AspectModel aspecto = (Region.AspectModel)permisosMap.get( regionContextName );
               region.getConditionalDisplay().evaluate( aspecto );
            }

        }

    }

    public static
    void main( String[] args ) {
       EditFoliosRegionManager rgnMgr = new EditFoliosRegionManager();
       java.util.Iterator iterator = rgnMgr.getRegionList().iterator();

       StringBuffer buffer = new StringBuffer();
       for(;iterator.hasNext();){
           Region region = (Region)iterator.next();
           region.generateXmlTextFragment_XmlDebugImp( buffer );
       }
    }

    public ArrayList getRegionList() {
        return regionList;
    }

}
