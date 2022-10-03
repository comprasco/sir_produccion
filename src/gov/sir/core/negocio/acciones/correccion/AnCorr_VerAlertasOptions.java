package gov.sir.core.negocio.acciones.correccion;


import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;

import gov.sir.hermod.interfaz.HermodServiceInterface;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException;

import java.util.ArrayList;
import java.util.Iterator;
import gov.sir.core.negocio.modelo.Folio;
import java.util.List;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;

import org.auriga.util.ExceptionPrinter;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.eventos.correccion.Evn_CorrSimpleMain_VerAlertasOptions;
import gov.sir.core.negocio.modelo.DeltaFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.eventos.correccion.EvnResp_CorrSimpleMain_VerAlertasOptions;

public class
AnCorr_VerAlertasOptions
extends SoporteAccionNegocio {

	/**
	 * ServicesLayer: forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * ServicesLayer: hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * ServiceLocatorInstance
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANCorrección.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public AnCorr_VerAlertasOptions() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
        buildServices( service );

	}



   public void
   buildServices( ServiceLocator locator )
   throws EventoException {

      try {
              hermod =
                      (HermodServiceInterface) service.getServicio("gov.sir.hermod");

              forseti =
                      (ForsetiServiceInterface) service.getServicio(
                              "gov.sir.forseti");

      }
      catch (ServiceLocatorException e) {
              throw new ServicioNoEncontradoException(
                      "Error instanciando el servicio Hermod",
                      e);
      }

      if( null == hermod ) {
              throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
      }

      if( null == forseti ) {
              throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
      }
   } // :buildServices



    public EventoRespuesta
    perform( Evento e )
    throws EventoException {

		if( null == e  ) {
		   return null;
		}
		if( !(  e instanceof Evn_CorrSimpleMain_VerAlertasOptions ) ) {
		   throw new RuntimeException( "Mensaje Inesperado" + AnCorr_VerAlertasOptions.class.getName() + ":" + e.getClass().getName()  );
		}

		Evn_CorrSimpleMain_VerAlertasOptions evento
		 = (Evn_CorrSimpleMain_VerAlertasOptions) e;


          String local_TipoEvento;
          local_TipoEvento = evento.getTipoEvento();

          if( Evn_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENT.equals( local_TipoEvento ) ) {
              return processVerAlertas_Iniciar( evento );
          }

          return null;

    } // end-method: perform


	private EventoRespuesta
	processVerAlertas_Iniciar( Evn_CorrSimpleMain_VerAlertasOptions evento )
	throws EventoException {


          // unwrap ---------------------------------
          gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;
          gov.sir.core.negocio.modelo.Turno   local_ParamTurno;

          // get params
          local_ParamUsuarioSir = evento.getUsuarioSir();
          local_ParamTurno      = evento.getTurno();
          // -----------------------------------------

          EvnResp_CorrSimpleMain_VerAlertasOptions local_Result = null;

          // process ---------------------------------
          FolioPk local_FolioID;

          String local_Folio_ZonaRegistral = null;
          String local_Folio_IdMatricula = null;

          // tmpvalriables to getvalues

          // List< Folio >: lista de folios en el turno
          List local_Result_FoliosInTurno = new ArrayList();

          // List< Folio >: lista de folios en el turno, con salvedades
          List local_Result_FoliosInTurnoSinCambios = new ArrayList();


          try {


             //
             // TODO: calls
             //
             // 1. obtener folios con deltas
             // 2. para cada uno de los folios observar si tiene salvedades temporales
             // 3. devolver el listado de folios para el turno
             // 4. devlover el listado de folios con anotaciones temporales

             // CALL: List< SolicitudFolio > lst = forseti.getFoliosInTurno
             // for( SolicitudFolio elem: lst )
             //  add List< folio >: result_List1
             //  observar si tiene salvedades; add to result_List2: List< folio > with salvedadtx

             //

             Turno local_Turno;
             Folio local_Folio;
             FolioPk local_FolioId;

             // list1







             local_Turno = hermod.getTurnobyWF( local_ParamTurno.getIdWorkflow() );
             
             if(local_Turno.getHistorials()!=null && local_Turno.getHistorials().size()>0){
            	 Iterator itHistorial = local_Turno.getHistorials().iterator();
            	 boolean esRevisarAprobar = false;
            	 gov.sir.core.negocio.modelo.Usuario tempUsuarioSir = null;
            	 if(local_Turno.getIdFase().equals(CFase.COR_REVISAR_APROBAR)){
            		 esRevisarAprobar = true;
            	 }
            	 while(itHistorial.hasNext()){
            		 TurnoHistoria turnoHistoria = (TurnoHistoria)itHistorial.next();
            		 if(turnoHistoria.getFase().equals(CFase.COR_CORRECCION_SIMPLE)){
            			 tempUsuarioSir = turnoHistoria.getUsuarioAtiende();
            		 }
            	 }
            	 if(esRevisarAprobar){
            		 local_ParamUsuarioSir = tempUsuarioSir;
            	 }
             }

             // List< SolicitudFolio >
             List           local_TurnoSolicitudSolicitudFolios;
             Iterator       local_TurnoSolicitudSolicitudFoliosIterator;
             SolicitudFolio local_TurnoSolicitudSolicitudFoliosElement;

             local_TurnoSolicitudSolicitudFolios = local_Turno.getSolicitud().getSolicitudFolios();
             local_TurnoSolicitudSolicitudFoliosIterator = local_TurnoSolicitudSolicitudFolios.iterator();

             List local_FilteredSalvedades;

             boolean evaluateAlert_IsAlertActive;

             for( ;local_TurnoSolicitudSolicitudFoliosIterator.hasNext(); ) {
                local_TurnoSolicitudSolicitudFoliosElement = (SolicitudFolio)local_TurnoSolicitudSolicitudFoliosIterator.next();

                local_Folio_IdMatricula   = local_TurnoSolicitudSolicitudFoliosElement.getIdMatricula();


                // get some Folio
                local_FolioId = new FolioPk();
                local_FolioId.idMatricula = local_Folio_IdMatricula;
                
                // verificar existe folio
                local_Folio = forseti.getFolioByID( local_FolioId, 
                		forseti.getUsuarioBloqueoFolio(local_FolioId) );
                if(local_Folio.getAnotaciones()==null || local_Folio.getAnotaciones().isEmpty()){
                	local_Folio.setAnotaciones(forseti.getAnotacionesFolioTMP(local_FolioId));
                }

                local_Result_FoliosInTurno.add( local_Folio );

                // ------------------------------------------------------------
                // Bug 3569

                // Evaluacion 1:
                // revisar si existen cambios propuestos

                //TFS 3570: SE TOMA EL FOLIO Y SE VERIFICAN SI TIENE SALVEDADES A NIVEL DE FOLIO, 
                //SI NO SE VERIFICA SI TIENE SALVEDADES SOBRE LAS ANOTACIONES
                boolean noTieneModificaciones = false;
                if(local_Folio.getSalvedades()==null || local_Folio.getSalvedades().size()==0){
                	//Se revisa si no tiene salvedades a nivel de folio
                	noTieneModificaciones = true;
                }//Si tiene salvedades hay que verificar que las que tenga sean del turno
                else{
                	Iterator itSalvedadFolio = local_Folio.getSalvedades().iterator();
                	boolean tieneSalvedadesFolios = false;
                	while(itSalvedadFolio.hasNext()){
                		SalvedadFolio salvedadFolio = 
                			(SalvedadFolio)itSalvedadFolio.next();
                		if(salvedadFolio.getNumRadicacion().
                				equals(local_ParamTurno.getIdWorkflow())){
                			tieneSalvedadesFolios = true;
                			break;
                		}
                	}
                	if(!tieneSalvedadesFolios)
                		noTieneModificaciones = true;
                }
                //Si no tiene salvedades a nivel del folio se revisa a nivel de anotaciones
                if(noTieneModificaciones){
                	List anotaciones = local_Folio.getAnotaciones();
                	if(anotaciones!=null){
                		//Se itera sobre las anotaciones
	                	Iterator itAnotaciones = anotaciones.iterator();
	                	boolean terminaIteracion = false;
	                	while(itAnotaciones.hasNext()){
	                		if(terminaIteracion)
	                			break;
	                		Anotacion anotacion = (Anotacion)itAnotaciones.next();
	                		List anotacionesSalvedades = anotacion.getSalvedades();
	                		if(anotacionesSalvedades!=null){
	                			//Se itera sobre las salvedades de la anotación
	                			Iterator itAnotacionesSalvedades = anotacionesSalvedades.iterator();
	                			while(itAnotacionesSalvedades.hasNext()){
	                				SalvedadAnotacion salvedadAnotacion = 
		                				(SalvedadAnotacion)itAnotacionesSalvedades.next();
	                				//Si la salvedad de la anotación es temporal y es del turno,
	                				//el folio si tiene anotaciones y se retira de la iteración
	                				if(salvedadAnotacion.getNumRadicacion().
	                							equals(local_ParamTurno.getIdWorkflow())){
	                					noTieneModificaciones = false;
	                					terminaIteracion = true;
	                					break;
	                				}
	                			}
	                		}
	                	}
                	}else{
                		noTieneModificaciones = true;
                	}
                }
                //Si después de la validación, el folio no tiene modificaciones, se añade a la lista
                //de folios no modificados
                if(noTieneModificaciones){
                	local_Result_FoliosInTurnoSinCambios.add( local_Folio );
                }

                /*evaluateAlert_IsAlertActive = evaluateAlert_FolioSinCambios(
                	local_FolioId
               	  , local_ParamUsuarioSir
               	  , forseti
               	  , hermod
                );

                if( evaluateAlert_IsAlertActive ) {
                	local_Result_FoliosInTurnoSinCambios.add( local_Folio );
                } // if*/


                // ------------------------------------------------------------

             } // for

            // eof:replace :

          }
          catch( ForsetiException fe ) {
                   List l;
                   l = fe.getErrores();
                   NegocioCorreccionesCorreccionSimpleCollectorException cfe
                     = new NegocioCorreccionesCorreccionSimpleCollectorException();

                   cfe.addError(fe.getMessage());
                   throw cfe;
          }
          catch( Throwable e ) {
                   ExceptionPrinter printer = new ExceptionPrinter(e);
                   Log.getInstance().error(AnCorr_VerAlertasOptions.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                   throw new EventoException(e.getMessage(), e);
           }

           // wrap ------------------------------------
           local_Result = new EvnResp_CorrSimpleMain_VerAlertasOptions( EvnResp_CorrSimpleMain_VerAlertasOptions.CORRECCIONSIMPLEMAIN_VERALERTASOPTIONS_STEP0_BTNSTART__EVENTRESP_OK );

           // set results
           local_Result.setFoliosInTurnoList( local_Result_FoliosInTurno );
           local_Result.setFoliosInTurnoSinCambiosList( local_Result_FoliosInTurnoSinCambios );

          // -----------------------------------------
          // send-message ---------------------------------
          return local_Result;

          // -----------------------------------------

	} // end-method: processVerAlertas_Iniciar

// -----------------------------------------
// -----------------------------------------

public boolean
evaluateAlert_FolioSinCambios( FolioPk local_FolioId, gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir, ForsetiServiceInterface forseti, HermodServiceInterface hermod )
throws ForsetiException, EventoException, Throwable {

	boolean isAlertActive;


        DeltaFolio local_Delta;

		long tiempoInicial =  System.currentTimeMillis();
		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"\n*******************************************************");
		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"((AN Corr_Ver_Alertas) ANTES LLAMADO cargarCambiosPropuestos) > "+local_FolioId.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"\n*******************************************************\n");
        
	local_Delta = forseti.getCambiosPropuestos( local_FolioId, local_ParamUsuarioSir );

		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"\n*******************************************************");
		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"((AN Corr_Ver_Alertas) DESPUES LLAMADO cargarCambiosPropuestos) > "+local_FolioId.idMatricula+ " > "+ ((System.currentTimeMillis() - tiempoInicial )/1000.0d) );
		Log.getInstance().debug(AnCorr_VerAlertasOptions.class,"\n*******************************************************\n");
	
        isAlertActive = false;

        if( !isAlertActive ) {
           if( null == local_Delta ) {
              isAlertActive = true;
           } // if
        } // if

        if( !isAlertActive ) {

          local_SearchImpl_jx: {

            // :: local variables ----------------------------------------------
            // ...... source variables
            org.apache.commons.jxpath.JXPathContext local_JxContext;
            Object local_JxContextSource;
            String local_JxSearchString;

            // ...... target variables
            List local_TargetSalvedadesList;

            // target jx-object
            local_JxContextSource = local_Delta;

            // :: initialize
            local_JxContext = org.apache.commons.jxpath.JXPathContext.newContext(local_JxContextSource);
            local_JxContext.setLenient(true);

            // (declare variables)
            // local_JxContext.getVariables().declareVariable("local_NumRadicacion", "");

            local_JxSearchString = "count( /diferencias ) > 0";
            //local_JxContext.setValue("$local_NumRadicacion", local_Param_TurnoId);


            // :: get the results
            // single object   : local_JxContext.getValue
            // multiple object : local_JxContext.iterate
            Boolean foundedElelementsCount = (Boolean)local_JxContext.getValue( local_JxSearchString );

            // -----------------------------------------------------------------
            // fix the results

            // si no hay diferencias, encender alerta

            if( foundedElelementsCount.booleanValue() == false ){
               isAlertActive= true;
            } //

          } // :local_SearchImpl_jx

        } // if

	return isAlertActive;

} // end-method:evaluateAlert_FolioSinCambios












} // end-class
