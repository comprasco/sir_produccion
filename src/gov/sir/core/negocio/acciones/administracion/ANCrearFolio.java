package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnCrearFolio;
import gov.sir.core.eventos.administracion.EvnRespCrearFolio;
import gov.sir.core.eventos.administracion.EvnRespCrearFolioOficio;
import gov.sir.core.eventos.administracion.EvnResp_AdminCrearFolioSaveInfo;
import gov.sir.core.eventos.administracion.Evn_AdminCrearFolioSaveInfo;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.CancelarAnotacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCreadoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionException;
import gov.sir.core.negocio.acciones.excepciones.NegocioCorreccionesCorreccionSimpleCollectorException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.DatosAntiguoSistema;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioDerivado;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupCodes;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleHojaDeRuta;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author ppabon
 * Clase que permite la creación de un nuevo folio por la pantalla administrativa.
 */
public class ANCrearFolio extends SoporteAccionNegocio {
    /**
     * Instancia del ServiceLocator
     */
    private ServiceLocator service = null;

    /**
     * Instancia de la interfaz de Hermod
     */
    private HermodServiceInterface hermod;

    /**
     * Instancia de la intefaz de Forseti
     */
    private ForsetiServiceInterface forseti;
    
    /**
     * Instancia de PrintJobs
     */
    private PrintJobsInterface printJobs;


    public ANCrearFolio() throws EventoException{
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
            printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }
        
        if (printJobs == null) {
            throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
        }

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        if (ev instanceof EvnCrearFolio){
			EvnCrearFolio evento=(EvnCrearFolio)ev;

			if (evento.getTipoEvento().equals(EvnCrearFolio.CREACION_FOLIO)){
				return registrarCreacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO)){
				return adminCrearFolioSaveInfo_ProcesarFolio(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.AGREGAR_ANOTACION)){
				return agregarAnotacionMatricula(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.GRABAR_ANOTACIONES_TEMPORALMENTE)){
				return grabarAnotacionesTemporal(evento);
			} 
			if (evento.getTipoEvento().equals(EvnCrearFolio.ELIMINAR_ANOTACION)){
				return eliminarAnotacionTemporal(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.VOLVER_EDICION)){
				return volverEdicion(evento);
			} 
			if (evento.getTipoEvento().equals(EvnCrearFolio.CANCELAR_ANOTACIONES)){
				return cancelarAnotacionTemporal(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.CREAR_SEGREGACION_ENGLOBE)){
				return crearSegregacionEnglobe(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.CREAR_DERIVACION)){
				return crearDerivacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.ELIMINAR_DERIVACION)){
				return eliminarDerivacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.CREAR_ENGLOBE)){
				return crearEnglobe(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolio.ELIMINAR_ENGLOBE)){
				return eliminarEnglobe(evento);
			}
			if(evento.getTipoEvento().equals(EvnCrearFolio.AGREGAR_CIUDADANO_ANOTACION)) {
				return validarCiudadanos(evento);
			}
			if(evento.getTipoEvento().equals(EvnCrearFolio.IMPRIMIR_HOJA_DE_RUTA)){
				return imprimirMatricula(evento);
			}
        }


        if( ev instanceof Evn_AdminCrearFolioSaveInfo ) {
        	Evn_AdminCrearFolioSaveInfo evento;
        	evento = (Evn_AdminCrearFolioSaveInfo)ev;

        	String local_TipoEvento;
        	local_TipoEvento = ev.getTipoEvento();

        	if( Evn_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENT.equals( local_TipoEvento ) ) {
        		return adminCrearFolioSaveInfo_ProcesarFetchFolio( evento );
        	} // if
        	if( Evn_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENT.equals( local_TipoEvento ) ) {
        		return adminCrearFolioSaveInfo_ProcesarLoadFolioById( evento );
        	} // if



        } // if

        if (ev instanceof EvnCiudadano){
        	EvnCiudadano evento=(EvnCiudadano)ev;

        	if (evento.getTipoEvento().equals(EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION)){
        		return consultarCiudadanoAnotacion(evento);
			}


        }



        return null;

    } // end-method:


    // @decorator
	private EventoRespuesta
	adminCrearFolioSaveInfo_ProcesarLoadFolioById( Evn_AdminCrearFolioSaveInfo evento ) throws EventoException {

		// @in

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolioEditado();
        local_UsuarioSir = evento.getUsuarioSir();

		// @proc

        // consultar el folio por el identificador

		Folio folioTemporal = null;

		try {

        	String local_FolioEditado_IdMatricula;
        	//String local_FolioEditado_IdZonaRegistral;


        	local_FolioEditado_IdMatricula = local_Folio.getIdMatricula();
        	//local_FolioEditado_IdZonaRegistral = forseti.getZonaRegistral( local_FolioEditado_IdMatricula );


			FolioPk id        = new FolioPk();
			id.idMatricula     = local_FolioEditado_IdMatricula;
			folioTemporal      = forseti.loadFolioByID(id, local_UsuarioSir );
			List anotaciones = folioTemporal.getAnotaciones();
			if ( anotaciones == null || anotaciones.isEmpty())
			{
				anotaciones = forseti.getAnotacionesFolioTMP(id);
				folioTemporal.setAnotaciones(anotaciones);
			}
			
			if (!(folioTemporal.getDirecto() == CFolio.FOLIO_CREADO_ADMINISTRATIVA_GRABACION)) {
				throw new ForsetiException("El folio no fue creado por Pantalla Administrativa de Grabación Directa");
			}
		
			//	Se tiene que validar si es temporal y esta asociado a un turno de Certificado
			List turnosAsociadosMatricula = hermod.getTurnosByMatricula(local_FolioEditado_IdMatricula);

			if (turnosAsociadosMatricula.size() > 0) {
				throw new ForsetiException("El folio es invalido, está asociado a un turno en tramite");
			}

		}
		catch( ForsetiException fe ) {
			List l;
			l = fe.getErrores();

			if (l.size() > 0) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				throw cfe;
			}

			if (fe.getHashErrores() != null  && !fe.getHashErrores().isEmpty()) {
				Hashtable ht = fe.getHashErrores();
				ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(fe);
				throw vpe;
			}
			
			if (fe.getCause()!=null) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				cfe.addError(fe.getCause().getMessage());
				throw cfe;
			}

			if (fe.getMessage() != null) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				cfe.addError(fe.getMessage());
				throw cfe;
			}
		}
		catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}



		// --------------------------------------------------
		// @ou
        // wrap ------------------------------------
		EvnResp_AdminCrearFolioSaveInfo local_Result;
		local_Result = new EvnResp_AdminCrearFolioSaveInfo( EvnResp_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESP_OK );

        // set results
		local_Result.setFolioEditado( folioTemporal );

       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
       // -----------------------------------------

	} // end-method:


    // @decorator
    private EventoRespuesta
    adminCrearFolioSaveInfo_ProcesarFetchFolio( Evn_AdminCrearFolioSaveInfo evento ) throws EventoException {



        // @in

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolioEditado();
        local_UsuarioSir = evento.getUsuarioSir();

        // @proc

        // consultar el folio por el identificador

        Folio folioTemporal = null;

        try {

          String local_FolioEditado_IdMatricula;
          String local_FolioEditado_IdZonaRegistral;

          local_FolioEditado_IdMatricula = local_Folio.getIdMatricula();
          local_FolioEditado_IdZonaRegistral = forseti.getZonaRegistral( local_FolioEditado_IdMatricula );

          FolioPk id        = new FolioPk();
          id.idMatricula     = local_FolioEditado_IdMatricula;
          folioTemporal      = forseti.getFolioByID( id, local_UsuarioSir );
          //TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
          if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
        	  FolioPk fid = new FolioPk();
        	  fid.idMatricula = local_FolioEditado_IdMatricula;
        	  folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(fid));
          }

        }
        catch( ForsetiException fe ) {
                List l;
                l = fe.getErrores();
                throw new FolioNoCreadoException(l);
        }
        catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e.getMessage(), e);
        }

        // REVISAR: Se necesita consultar aca el ultimo editado ?


        // --------------------------------------------------
        // @ou
	// --------------------------------------------------
        // wrap ------------------------------------
        EvnResp_AdminCrearFolioSaveInfo local_Result;
	local_Result = new EvnResp_AdminCrearFolioSaveInfo( EvnResp_AdminCrearFolioSaveInfo.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESP_OK );

        // set results
        local_Result.setFolioEditado( folioTemporal );


       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
       // -----------------------------------------

    } // end-method:



    // @decorator
	private EventoRespuesta
	adminCrearFolioSaveInfo_ProcesarFolio( EvnCrearFolio evento ) throws EventoException {

		if( null == evento ) {

			throw new EventoException( "Parametros Invalidos para procesar la accion de negocio" );

		} // if

		if( evento.isUpdating() ) {
			return adminCrearFolioSaveInfo_ProcesarFolio_Updating( evento );
		}

		return validarMatriculaCreacion( evento );



	} // end-method

    // @decorator
	private EventoRespuesta
	adminCrearFolioSaveInfo_ProcesarFolio_Updating( EvnCrearFolio evento ) throws EventoException {

		// unwrap-event

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolio();
        local_UsuarioSir = evento.getUsuarioSir();


		// 0: preprocess folio

/*
        local_Preprocess_Salvedades : {

          // obtener los datos anteriores de salvedad y
          // eliminarlos para poder insertar los nuevos y
          // no queden repetidos

          Folio     local_t0_Folio = null;
          Folio.ID  local_t0_FolioId = null;

          local_t0_FolioId = new Folio.ID();
          local_t0_FolioId.idMatricula = local_Folio.getIdMatricula();

          try {
            local_t0_Folio = forseti.getFolioByID( local_t0_FolioId, local_UsuarioSir );
          }
          catch (ForsetiException e) {
              throw new EventoException(e.getMessage(), e);
          }
          catch (Throwable e) {
              ExceptionPrinter printer = new ExceptionPrinter(e);
              Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
              throw new EventoException(e.getMessage(), e);
          } // try


          // recorrer las salvedades anteriores
          // List< SalvedadFolio >
          List local_t0_Folio_Salvedades = null;


          local_t0_Folio_Salvedades = local_t0_Folio.getSalvedades();

          if( null == local_t0_Folio_Salvedades ) {
            local_t0_Folio_Salvedades = new ArrayList();
          }

          SalvedadFolio local_t0_Folio_SalvedadesElement;
          for( Iterator local_t0_Folio_SalvedadesIterator = local_t0_Folio_Salvedades.iterator(); local_t0_Folio_SalvedadesIterator.hasNext(); ){
            local_t0_Folio_SalvedadesElement = (SalvedadFolio) local_t0_Folio_SalvedadesIterator.next();
            if( null != local_t0_Folio_SalvedadesElement.getIdSalvedad() ) {
              local_t0_Folio_SalvedadesElement.setToDelete( true );
            }
          } // for




        } // :local_Preprocess_Salvedades
*/
		// 1: lock folios

		// 2: update folio

		try {
			forseti.updateFolioCreacionDirecta( local_Folio, local_UsuarioSir );
		}
	    catch (ForsetiException e) {
	    	throw new EventoException(e.getMessage(), e);
		}
	    catch (Throwable e) {
	    	ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		} // try

		// 3: get folio

		Folio folioTemporal = null;
		long  folioTemporalAnotacionesNextOrden = 0L;

		try {

			FolioPk id        = new FolioPk();
			id.idMatricula     = local_Folio.getIdMatricula();
			folioTemporal      = forseti.getFolioByID( id, local_UsuarioSir );
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
	        	  folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
	        }
                        try {
			  folioTemporalAnotacionesNextOrden = forseti.getNextOrdenAnotacion( id );
                        }
                        catch( Exception e ) {
                          Log.getInstance().error(ANCrearFolio.class,"Errores al obtener el siguiente identificador de orden " + e.getMessage() );
                          e.printStackTrace( System.err );
                        }

		}
		catch( ForsetiException fe ) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		}
		catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

	    // wrap event

		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO_UPDATING_OK );
		eventoResp.setAnotacionesNextOrden( folioTemporalAnotacionesNextOrden );

		return eventoResp;


	} // end-method:

	/**
	 * Método que se encaga de asociar el nuevo folio al turno luego de su creación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta registrarCreacion(EvnCrearFolio evento) throws EventoException {

		EvnRespCrearFolio eventoResp = null;
		Usuario user = evento.getUsuarioSir();
		//Hashtable tabla = new Hashtable();

		Folio folioResp = crearFolio(evento);
		eventoResp = new EvnRespCrearFolio(folioResp, EvnRespCrearFolio.CREACION_FOLIO);

		return eventoResp;

	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnCrearFolio evento) throws EventoException {
	
		List anotacionCiudadanos = evento.getAnotacionCiudadanos();
		
		try {
			Iterator itt = anotacionCiudadanos.iterator();
			while(itt.hasNext()){
				AnotacionCiudadano anotaCiudadano = (AnotacionCiudadano)itt.next();
				Ciudadano ciu = anotaCiudadano.getCiudadano();
				CiudadanoTMP ciudTemp = null;
				try {
					ciudTemp = forseti.getCiudadanoTmpByDocumento(ciu.getTipoDoc(), ciu.getDocumento(), ciu.getIdCirculo());	
				} catch (Throwable ee) {}
				
				if (ciudTemp!=null) {
					if (ciudTemp.getNumeroRadicacion()!=null) {
						throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
					} else {
						throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
					}
				}
			}
			
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolio.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespCrearFolio evn = new EvnRespCrearFolio(EvnRespCrearFolio.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
	}



	/**
	 * Método que se encarga de la creación del nuevo folio
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private Folio crearFolio(EvnCrearFolio evento) throws EventoException {

		Folio folio = evento.getFolio();

		if (folio == null) {
			List l = new ArrayList();
			l.add("El folio asociado es inválido");
			throw new FolioNoCreadoException(l);
		}

		Folio auxFolio;

		//Se valida que toda la información del folio sea correcta.
		/*
		try {
			this.forseti.validarCrearFolio(folio, false);
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		*/
		//Si la información del folio es válida se crea el folio.
		Usuario usuarioSIR = evento.getUsuarioSir();

		try {
			//auxFolio = this.forseti.crearFolio(folio, usuarioSIR, false);
			this.forseti.hacerDefinitivoFolio(folio,usuarioSIR, null, false);
			this.forseti.desbloquearFolio(folio);
		} catch (ForsetiException fe) {
			List l = null;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		// Si existen datos de Antiguo Sistema, se asocia una solicitud al folio. La solicitud
		// correspondería al proceso de Antiguo Sistema.
		DatosAntiguoSistema datosAntiguoSistema = evento.getDatosAntiguoSistema();
		if(datosAntiguoSistema != null) {
			Proceso proceso = new Proceso();
			proceso.setIdProceso(Long.parseLong(CProceso.PROCESO_ANTIGUO_SISTEMA));

			datosAntiguoSistema.setFechaCreacion(new Date());

			Solicitud solicitudFolio = new SolicitudAntiguoSistema();
			solicitudFolio.setDatosAntiguoSistema(datosAntiguoSistema);
			solicitudFolio.setCirculo(evento.getCirculo());
			solicitudFolio.setProceso(proceso);
			solicitudFolio.setUsuario(evento.getUsuarioSir());

			SolicitudFolio solicitudHija = new SolicitudFolio();
			solicitudHija.setFolio(folio);
			solicitudHija.setSolicitud(solicitudFolio);

			solicitudFolio.addSolicitudFolio(solicitudHija);

			try {
				solicitudFolio = hermod.crearSolicitud(solicitudFolio);
			} catch(HermodException hermodException) {
				throw new FolioNoCreadoException(hermodException.getMessage(), hermodException);
			} catch (Throwable e) {
				ExceptionPrinter printer = new ExceptionPrinter(e);
				Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new FolioNoCreadoException(e.getMessage(), e);
			}
		}
		return folio;

	}




	/**
	 * Método que se encaga de validar el número de matrícula.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta validarMatriculaCreacion(EvnCrearFolio evento) throws EventoException {

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolio();
        local_UsuarioSir = evento.getUsuarioSir();


        FolioPk id        = new FolioPk();
		Folio folioTemporal = null;
		long  folioTemporalAnotacionesNextOrden = 0L;
		
		
		try {
			id.idMatricula     = local_Folio.getIdMatricula();

			/**Si el folio se encuentra en mat_no_gradaba se debe lanzar la excepcion*/
			
			String[] partes = local_Folio.getIdMatricula().split("-");
			String circulo = partes[0];
			String matricula = partes[1];
			if(forseti.matriculaNoGrabadaExistente(circulo, (Long.parseLong(matricula)))){
				ForsetiException fe= new ForsetiException();
				fe.addError("La matrícula se encuentra como no grabada");
				throw fe;
			}
			
			
			
			
			//Validaciones sobre los datos ingresados para la creación
			forseti.validarMatriculaCrearFolio( local_Folio );
			
			//Se válida que la matrícula que se quiere grabar no este en proceso de migración.
			Folio folioTemp = new Folio();
			folioTemp.setIdMatricula(local_Folio.getIdMatricula());
			
			List exceptionList= new ArrayList();
			if(hermod.isFolioSirMigTurnoFolioTramite(folioTemp)){
				exceptionList.add("Matrícula inválida. El folio se encuentra en trámite en el sistema FOLIO.");
			}
			
			if(hermod.isFolioSirMigTurnoFolio(folioTemp)){
				exceptionList.add("Matrícula inválida. El folio se encuentra en proceso de migración a SIR.");
			}
			
			if(exceptionList!=null && exceptionList.size()>0){
				FolioNoCreadoException exception = new FolioNoCreadoException(exceptionList);
				throw exception;
			}
			
			//Si todas las validaciones pasaron, se procede a crear el folio.
			folioTemporal = forseti.crearFolio( local_Folio, local_UsuarioSir, null, false);
			List matriculas = new ArrayList();
			matriculas.add(folioTemporal.getIdMatricula());
			//this.forseti.delegarBloqueoFolio(null, evento.getUsuarioSir(), folioId);
			this.forseti.bloquearFolios( matriculas, local_UsuarioSir, null );
		} catch (FolioNoCreadoException fe) {
			throw fe;
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			folioTemporalAnotacionesNextOrden = forseti.getNextOrdenAnotacion( id );
		} catch (Throwable e) {
			folioTemporalAnotacionesNextOrden = 1;
		}

		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.VALIDAR_MATRICULA_CREACION_FOLIO_OK);
		eventoResp.setAnotacionesNextOrden( folioTemporalAnotacionesNextOrden );
		return eventoResp;

	}

	/**
	 * Método que se encaga de agregar una anotación a la matrícula.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta agregarAnotacionMatricula(EvnCrearFolio evento) throws EventoException {
		Folio folioTemporal = null;
		List anotaciones = null;
		try {
			this.forseti.updateFolio(evento.getFolio(), evento.getUsuarioSir(), null, false);

			FolioPk id = new FolioPk();
			id.idMatricula = evento.getFolio().getIdMatricula();
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
			anotaciones = forseti.getAnotacionesTemporalesByRangoOrden(id,evento.getAnotacion().getOrden(),evento.getAnotacion().getOrden(), evento.getUsuarioSir());
		} catch (ForsetiException fe) {
			List l = new ArrayList();
			l.add(fe.getMessage());
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.AGREGAR_ANOTACION_OK);
		if(evento.isGenerarNextOrden()&&evento.getAnotacion()!=null){
			String norder = evento.getAnotacion().getOrden();
			int torder=Integer.parseInt(evento.getAnotacion().getOrden());
			torder++;
			norder=Integer.toString(torder);
			eventoResp.setNextOrden(norder);
		}
		Iterator iter = anotaciones.iterator();
		Anotacion anotacion = iter.hasNext()?(Anotacion)iter.next():null;
		eventoResp.setAnotacion(anotacion);
		eventoResp.setAnotacionesAgregadas(evento.getAnotacionesAgregadas());
		return eventoResp;

	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta grabarAnotacionesTemporal(EvnCrearFolio evento) throws EventoException {

		Folio folioTemporal;
		
		Usuario usuarioNeg = evento.getUsuarioSir();
		Folio nuevo=new Folio();
		FolioPk id = new FolioPk();
		id.idMatricula = evento.getFolio().getIdMatricula();

		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		//nuevo.setZonaRegistral(evento.getFolio().getZonaRegistral());
		

		//SE ACTUALIZA EL FOLIO
		try {
			List anotaciones = evento.getAnotacionesGuardar();
			Iterator it = anotaciones.iterator();
			while(it.hasNext()){
				Anotacion anota=(Anotacion)it.next();
				nuevo.addAnotacione(anota);
				forseti.updateAnotacionesCreacionDirecta(anota,usuarioNeg);
			}
			
			/*if (forseti.updateAnotacionesCreacionDirecta(nuevo,usuarioNeg, null, false)){
				id.idMatricula=evento.getFolio().getIdMatricula();
			}*/
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.AGREGAR_ANOTACION_OK);
		return eventoResp;


	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarAnotacionTemporal(EvnCrearFolio evento) throws EventoException {

		Folio folioTemporal;

		Folio nuevo=new Folio();
		FolioPk id = new FolioPk();
		id.idMatricula = evento.getFolio().getIdMatricula();
		
		Usuario usuarioNeg=evento.getUsuarioSir();

		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		Anotacion anotacion = evento.getAnotacion();
		nuevo.addAnotacione( anotacion );
		try {
			boolean resultOk = forseti.updateFolio( nuevo, usuarioNeg, null, false );
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
		}
		
		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.AGREGAR_ANOTACION_OK);
		return eventoResp;
	}
                   
                    
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta volverEdicion(EvnCrearFolio evento) throws EventoException {

		Folio folioTemporal;
		
		Usuario usuarioNeg = evento.getUsuarioSir();
		Folio nuevo=new Folio();
		FolioPk id = new FolioPk();
		id.idMatricula = evento.getFolio().getIdMatricula();

		//SE ACTUALIZA EL FOLIO
		try {
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(folioTemporal, EvnRespCrearFolio.AGREGAR_ANOTACION_OK);
		return eventoResp;


	}

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cancelarAnotacionTemporal(EvnCrearFolio evento) throws EventoException {

		Folio devuelve=null;
		List listaATemp=null;

		Usuario usuarioNeg = evento.getUsuarioSir();
		Folio nuevo=new Folio();
		Turno turno = null;


		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		Anotacion anotacion= evento.getAnotacion();
		nuevo.addAnotacione(anotacion);
		try {
			if(forseti.updateFolioCreacionDirecta(nuevo,usuarioNeg)){
			FolioPk id=new FolioPk();
			id.idMatricula=evento.getFolio().getIdMatricula();
			devuelve = forseti.getFolioByIDSinAnotaciones(id);
			listaATemp= forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, usuarioNeg);

			if(turno!=null){
				TurnoPk idTurno = new TurnoPk();
				idTurno.anio = turno.getAnio();
				idTurno.idCirculo = turno.getIdCirculo();
				idTurno.idProceso = turno.getIdProceso();
				idTurno.idTurno = turno.getIdTurno();

				turno = hermod.getTurno(idTurno);
			}
		}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolio.class,"No se pudo cancelar la anotacion:"+ep.toString());
			throw new CancelarAnotacionNoEfectuadaException("No se pudo cancelar la anotacion",e1);
		}


		//SE CONSULTA EL FOLIO PARA MOSTRAR LA INFORMACIÓN DEL MISMO
		try {
			if(nuevo!=null && nuevo.getIdMatricula()!=null){

				FolioPk id=new FolioPk();
				id.idMatricula=nuevo.getIdMatricula();

				nuevo = forseti.getFolioByIDSinAnotaciones(id,usuarioNeg);
				Folio folioDef = forseti.getFolioByIDSinAnotaciones(id);
				//forseti.getDatosDefinitivosDeDatosTemporales(id,usuarioNeg);

				boolean esMayorExtension = forseti.mayorExtensionFolio(nuevo.getIdMatricula());

				long numeroAnotaciones = forseti.getCountAnotacionesFolio(nuevo.getIdMatricula());

				List foliosHijos = forseti.getFoliosHijos(id,usuarioNeg);
				List foliosPadre = forseti.getFoliosPadre(id,usuarioNeg);

				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,usuarioNeg, true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,usuarioNeg,true);

				long numeroFalsaTradicion = 0;
				List falsaTradicion = null;
				List ordenFalsaTradicion = new ArrayList();
				List anotacionesInvalidas = null;
				List ordenAnotacionesInvalidas = new ArrayList();

				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNeg, true);
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());
					}
				}

				//CONSULTA ANOTACIONES INVALIDAS
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNeg);
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());
					}
				}

				List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNeg);
				List cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNeg);

				String linderoTemporal = "";
				if(nuevo!=null && nuevo.getLindero()!=null && !nuevo.getLindero().equals("")){
					if(folioDef!=null){
						if(folioDef.getLindero()!=null){
							linderoTemporal = nuevo.getLindero().substring(folioDef.getLindero().length(),nuevo.getLindero().length());
						}else{
							linderoTemporal = nuevo.getLindero();
						}
					}else{
						linderoTemporal = nuevo.getLindero();
					}
				}

				nuevo = forseti.getFolioByID(id,usuarioNeg);

				EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(nuevo, EvnRespCrearFolio.CANCELAR_ANOTACIONES_OK);
				eventoResp.setFoliosPadre(foliosPadre);
				eventoResp.setFoliosHijo(foliosHijos);
				eventoResp.setGravamenes(gravamenes);
				eventoResp.setMedidasCautelares(medidasCautelares);
				eventoResp.setFalsaTradicion(ordenFalsaTradicion);
				eventoResp.setAnotacionesInvalidas(anotacionesInvalidas);
				eventoResp.setSalvedadesAnotaciones(salvedadesAnotaciones);
				eventoResp.setCancelaciones(cancelaciones);
				eventoResp.setNumeroAnotaciones(numeroAnotaciones);
				eventoResp.setEsMayorExtension(esMayorExtension);
				eventoResp.setATemporales(listaATemp);
				eventoResp.setLinderoTemporal(linderoTemporal);
				return eventoResp;
			}
			throw new EventoException();
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}


			//return new EvnRespCalificacion(devuelve, listaATemp);
	}

	/**
	 * Este método hace el llamado a los servicios para consultar al ciudadano en anotación con el identificador que
	 * viene en el evento.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCiudadano</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCiudadano</CODE>
	 */
	private EventoRespuesta consultarCiudadanoAnotacion(EvnCiudadano evento) throws EventoException {

		EvnRespCiudadano evRespuesta = null;
		Ciudadano ciudadano = evento.getCiudadano();
		Ciudadano ciudadanoRta = null;
		boolean mostrarCiudadano = true;

		try {
			if (ciudadano!=null && ciudadano.getTipoDoc()!=null && ciudadano.getDocumento()!=null){
				ciudadanoRta = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
				if(ciudadanoRta!=null){
					CiudadanoPk idCiudadano = new CiudadanoPk();
					idCiudadano.idCiudadano = ciudadanoRta.getIdCiudadano();
					mostrarCiudadano = forseti.isCiudadanoInAnotacionDefinitiva(idCiudadano);
				}
			}

			if (ciudadanoRta!=null){
				evRespuesta = new EvnRespCiudadano(ciudadanoRta);
				evRespuesta.setCiudadanoEncontrado(true);
				evRespuesta.setMostrarCiudadano(mostrarCiudadano);
			} else {
				evRespuesta = new EvnRespCiudadano(ciudadano);
				evRespuesta.setCiudadanoEncontrado(false);
				evRespuesta.setMostrarCiudadano(false);
			}

		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}

		return evRespuesta;
	}
	
	private EventoRespuesta crearSegregacionEnglobe(EvnCrearFolio evento) throws EventoException {

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(evento.getFolio(), EvnRespCrearFolioOficio.AGREGAR_SEGREGACION_ENGLOBE_OK);
		
		try {
			
			Folio   param_Folio;
		    Usuario param_UsuarioSIR;

		    // datos basicos para procesamiento
		    param_Folio      = evento.getFolio();
		    param_UsuarioSIR = evento.getUsuarioSir();
		    
        	FolioPk id = new FolioPk();
	        id.idMatricula = param_Folio.getIdMatricula();

	        List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
	        List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

	        long numeroAnotaciones = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );
	        eventoResp.setFoliosHijo( foliosHijos );
	        eventoResp.setFoliosPadre( foliosPadre );
	        eventoResp.setNumeroAnotaciones( numeroAnotaciones );
        	return eventoResp;
        } catch (Throwable e) {
        	ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
        
		//return eventoResp;
	}
	
	private EventoRespuesta crearDerivacion(EvnCrearFolio evento) throws EventoException {
		
		Folio   param_Folio;
	    Usuario param_UsuarioSIR;

	    // datos basicos para procesamiento
	    param_Folio      = evento.getFolio();
	    param_UsuarioSIR = evento.getUsuarioSir();

	    if((param_Folio == null) || (param_Folio.getIdMatricula() == null)) {
	      throw new EventoException( "Debe especificarse un folio con un id-matricula" );
	    }

	    // id-folios sobre los cuales se va a eliminar la relacion
	    FolioPk padre_FolioID = null;
	    FolioPk hijo_FolioID = null;
	    
	    //	  parametros del evento para procesar la existencia de folios:
        Anotacion hijo = evento.getAnotacionHijo();
        Anotacion padre = evento.getAnotacionPadre();

       //se valida que este bloqueo
	   try {
	    	
	    	// 1. ver que ambos folios existan
	        hijo_FolioID = new FolioPk();
	        padre_FolioID = new FolioPk();

	        Folio padre_Folio;
	        Folio hijo_Folio;

	        padre_FolioID.idMatricula     = padre.getIdMatricula();
	        hijo_FolioID.idMatricula     = hijo.getIdMatricula();
	        
	        List matriculas = new ArrayList();
			matriculas.add(hijo.getIdMatricula());
			
			if (forseti.enTramiteFolio(hijo.getIdMatricula())){
				throw new ForsetiException( "El folio  " +  hijo_FolioID.idMatricula + " está en tramite" );
			}
			
			this.forseti.bloquearFolios( matriculas, param_UsuarioSIR, null );

	        padre_Folio = forseti.getFolioByIDSinAnotaciones( padre_FolioID,param_UsuarioSIR);
	        hijo_Folio = forseti.getFolioByIDSinAnotaciones( hijo_FolioID, param_UsuarioSIR );

	        if(padre_Folio == null) {
	            throw new ForsetiException( "El folio origen " +  padre_FolioID.idMatricula + " no existe" );
	        }
	        
	        if(hijo_Folio == null) {
	            throw new ForsetiException( "El folio destino " +  hijo_FolioID.idMatricula + " no existe" );
	        }

	    }
	    catch( ForsetiException e ) {
	       	List l;
			l = e.getErrores();

			if (l.size() > 0) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				throw cfe;
			}

			if (e.getHashErrores() != null  && !e.getHashErrores().isEmpty()) {
				Hashtable ht = e.getHashErrores();
				ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(e);
				throw vpe;
			}

			if (e.getMessage() != null) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				cfe.addError(e.getMessage());
				throw cfe;
			}
           
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }

	    Anotacion padre_FolioAnotacion = null;
	    Anotacion hijo_FolioAnotacion = null;

	    // no se hace busqueda; todo llega como parametro
	    // se debe tener en cuenta que el id que llega no es el verdadero id, sino el orden;

	    try {

	        // parametros del evento para procesar la existencia de folios:
	    	//si se manda nullo en usuario no valida si estan bloqueados
	        padre_FolioAnotacion = forseti.getAnotacionByOrden( padre_FolioID, padre.getIdAnotacion(), param_UsuarioSIR );
	        hijo_FolioAnotacion = forseti.getAnotacionByOrden( hijo_FolioID, hijo.getIdAnotacion(), param_UsuarioSIR );


	        if(padre_FolioAnotacion == null) {
	            throw new ForsetiException( "La anotacion " +  padre.getIdAnotacion() + " en el folio " + padre_FolioID.idMatricula + " no existe" );
	        }
	        if(hijo_FolioAnotacion == null) {
	            throw new ForsetiException( "La anotacion " +  hijo.getIdAnotacion() + " en el folio " + hijo_FolioID.idMatricula + " no existe" );
	        }


	    }
	    catch( ForsetiException e ) {
	    	ExceptionPrinter printer = new ExceptionPrinter(e);
		    Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }


	    FolioDerivado folioDerivado = null;

	    folioDerivado = new FolioDerivado();
	    folioDerivado.setIdAnotacion1(hijo_FolioAnotacion.getIdAnotacion());
	    folioDerivado.setIdMatricula1(hijo_FolioAnotacion.getIdMatricula());

	    folioDerivado.setIdAnotacion(padre_FolioAnotacion.getIdAnotacion());
	    folioDerivado.setIdMatricula(padre_FolioAnotacion.getIdMatricula());

	    try {

	    	AnotacionPk padre_Folio_AnotacionID;
	        AnotacionPk hijo_Folio_AnotacionID;

	        padre_Folio_AnotacionID = new AnotacionPk();
	        hijo_Folio_AnotacionID = new AnotacionPk();

	        padre_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion();
	        padre_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula();

	        hijo_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion1();
	        hijo_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula1();

	        boolean result;

	        result = forseti.asociarFoliosAdministrativa( padre_Folio_AnotacionID, hijo_Folio_AnotacionID, param_UsuarioSIR, 1 );

	    }
	    catch( ForsetiException e ) {
	        ExceptionPrinter ep =new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolio.class,"No se pudo crear la segregación:" +ep.toString());
			throw new CancelarAnotacionNoEfectuadaException("No se pudo crear la segregación",e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }

	    // la respuesta del procesamiento se devuelve en
	    EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(evento.getFolio(), EvnRespCrearFolioOficio.AGREGAR_DERIVACION_OK);
	
		
        try {
        	FolioPk id = new FolioPk();
	        id.idMatricula = param_Folio.getIdMatricula();

	        List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
	        List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

	        long numeroAnotaciones = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );
	        eventoResp.setFoliosHijo( foliosHijos );
	        eventoResp.setFoliosPadre( foliosPadre );
	        eventoResp.setNumeroAnotaciones( numeroAnotaciones );
        	return eventoResp;
        } catch (Throwable e) {
        	ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
        
        
	} // :foliosPadreHijo_addFolioPadre

	  
	private EventoRespuesta eliminarDerivacion(EvnCrearFolio evento) throws EventoException {

		Folio   param_Folio;
	    Usuario param_UsuarioSIR;

	    // datos basicos para procesamiento
	    param_Folio      = evento.getFolio();
	    param_UsuarioSIR = evento.getUsuarioSir();

	    if((param_Folio == null) || (param_Folio.getIdMatricula() == null)) {
	      throw new EventoException( "Debe especificarse un folio con un id-matricula" );
	    }

	    // id-folios sobre los cuales se va a eliminar la relacion
	    FolioPk padre_FolioID = null;
	    FolioPk hijo_FolioID = null;
	    
	    //	  parametros del evento para procesar la existencia de folios:
        Anotacion hijo = evento.getAnotacionHijo();
        Anotacion padre = evento.getAnotacionPadre();

       //se valida que este bloqueo
	   try {
	    	
	    	// 1. ver que ambos folios existan
	        hijo_FolioID = new FolioPk();
	        padre_FolioID = new FolioPk();

	        Folio padre_Folio;
	        Folio hijo_Folio;

	        padre_FolioID.idMatricula     = padre.getIdMatricula();
	        hijo_FolioID.idMatricula     = hijo.getIdMatricula();

	        padre_Folio = forseti.getFolioByIDSinAnotaciones( padre_FolioID, param_UsuarioSIR );
	        hijo_Folio = forseti.getFolioByIDSinAnotaciones( hijo_FolioID, param_UsuarioSIR );

	        if(padre_Folio == null) {
	            throw new ForsetiException( "El folio origen " +  padre_FolioID.idMatricula + " no existe" );
	        }
	        
	        if(hijo_Folio == null) {
	            throw new ForsetiException( "El folio destino " +  hijo_FolioID.idMatricula + " no existe" );
	        }
	        
	        FolioDerivado folioDerivado = null;
	        
	        folioDerivado = forseti.getFolioDerivadoEnlace( padre_FolioID, hijo_FolioID, param_UsuarioSIR);
	        
	        if( folioDerivado == null ) {
                throw new ForsetiException( "No se encontro relacion entre los folios "
                                          + "(" +  padre_FolioID.idMatricula +"," +  hijo_FolioID.idMatricula + ")"
                                          + " "
                                          + "(padre,hijo)" );
            }
	        
	        AnotacionPk padre_Folio_AnotacionID;
	        AnotacionPk hijo_Folio_AnotacionID;

	        padre_Folio_AnotacionID = new AnotacionPk();
	        hijo_Folio_AnotacionID = new AnotacionPk();

	        padre_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion();
	        padre_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula();

	        hijo_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion1();
	        hijo_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula1();
	        
	        boolean result = false;

	        result = forseti.desasociarFoliosAdministrativa( padre_Folio_AnotacionID, hijo_Folio_AnotacionID, param_UsuarioSIR, 1 );
	        
	        forseti.desbloquearFolio(hijo_Folio);

	    }
	    catch( ForsetiException e ) {
	        ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al eliminar la Relacion" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }
	    
	    //	  la respuesta del procesamiento se devuelve en
	    EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(evento.getFolio(), EvnRespCrearFolioOficio.ELIMINAR_DERIVACION_OK);
	
		
        try {
        	FolioPk id = new FolioPk();
	        id.idMatricula = param_Folio.getIdMatricula();

	        List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
	        List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

	        long numeroAnotaciones = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );
	        eventoResp.setFoliosHijo( foliosHijos );
	        eventoResp.setFoliosPadre( foliosPadre );
	        eventoResp.setNumeroAnotaciones( numeroAnotaciones );
        	return eventoResp;
        } catch (Throwable e) {
        	ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }

        
	    
	}
	
	private EventoRespuesta crearEnglobe(EvnCrearFolio evento) throws EventoException {
		
		Folio   param_Folio;
	    Usuario param_UsuarioSIR;

	    // datos basicos para procesamiento
	    param_Folio      = evento.getFolio();
	    param_UsuarioSIR = evento.getUsuarioSir();

	    if((param_Folio == null) || (param_Folio.getIdMatricula() == null)) {
	      throw new EventoException( "Debe especificarse un folio con un id-matricula" );
	    }

	    // id-folios sobre los cuales se va a eliminar la relacion
	    FolioPk padre_FolioID = null;
	    FolioPk hijo_FolioID = null;
	    
	    //	  parametros del evento para procesar la existencia de folios:
        Anotacion hijo = evento.getAnotacionHijo();
        Anotacion padre = evento.getAnotacionPadre();

       //se valida que este bloqueo
	   try {
	    	
	    	// 1. ver que ambos folios existan
	        hijo_FolioID = new FolioPk();
	        padre_FolioID = new FolioPk();

	        Folio padre_Folio;
	        Folio hijo_Folio;

	        padre_FolioID.idMatricula     = padre.getIdMatricula();
	        hijo_FolioID.idMatricula     = hijo.getIdMatricula();

	        List matriculas = new ArrayList();
			matriculas.add(padre.getIdMatricula());
			
			if (forseti.enTramiteFolio(padre.getIdMatricula())){
				throw new ForsetiException( "El folio  " +  padre_FolioID.idMatricula + " está en tramite" );
			}
			
			this.forseti.bloquearFolios( matriculas, param_UsuarioSIR, null );
			
	        padre_Folio = forseti.getFolioByIDSinAnotaciones( padre_FolioID,param_UsuarioSIR );
	        hijo_Folio = forseti.getFolioByIDSinAnotaciones( hijo_FolioID, param_UsuarioSIR );

	        if(padre_Folio == null) {
	            throw new ForsetiException( "El folio origen " +  padre_FolioID.idMatricula + " no existe" );
	        }
	        
	        if(hijo_Folio == null) {
	            throw new ForsetiException( "El folio destino " +  hijo_FolioID.idMatricula + " no existe" );
	        }

	    }
	    catch( ForsetiException e ) {
	    	List l;
			l = e.getErrores();

			if (l.size() > 0) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				throw cfe;
			}

			if (e.getHashErrores() != null && !e.getHashErrores().isEmpty()) {
				Hashtable ht = e.getHashErrores();
				ValidacionParametrosHTException vpe = new ValidacionParametrosHTException(e);
				throw vpe;
			}

			if (e.getMessage() != null) {
				NegocioCorreccionesCorreccionSimpleCollectorException cfe = new NegocioCorreccionesCorreccionSimpleCollectorException(l);
				cfe.addError(e.getMessage());
				throw cfe;
			}
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }

	    Anotacion padre_FolioAnotacion = null;
	    Anotacion hijo_FolioAnotacion = null;

	    // no se hace busqueda; todo llega como parametro
	    // se debe tener en cuenta que el id que llega no es el verdadero id, sino el orden;

	    try {

	        // parametros del evento para procesar la existencia de folios:
	    	//si se manda nullo en usuario no valida si estan bloqueados
	        padre_FolioAnotacion = forseti.getAnotacionByOrden( padre_FolioID, padre.getIdAnotacion(), param_UsuarioSIR );
	        hijo_FolioAnotacion = forseti.getAnotacionByOrden( hijo_FolioID, hijo.getIdAnotacion(), param_UsuarioSIR );


	        if(padre_FolioAnotacion == null) {
	            throw new ForsetiException( "La anotacion " +  padre.getIdAnotacion() + " en el folio " + padre_FolioID.idMatricula + " no existe" );
	        }
	        if(hijo_FolioAnotacion == null) {
	            throw new ForsetiException( "La anotacion " +  hijo.getIdAnotacion() + " en el folio " + hijo_FolioID.idMatricula + " no existe" );
	        }


	    }
	    catch( ForsetiException e ) {
	    	ExceptionPrinter printer = new ExceptionPrinter(e);
		    Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }


	    FolioDerivado folioDerivado = null;

	    folioDerivado = new FolioDerivado();
	    folioDerivado.setIdAnotacion1(hijo_FolioAnotacion.getIdAnotacion());
	    folioDerivado.setIdMatricula1(hijo_FolioAnotacion.getIdMatricula());

	    folioDerivado.setIdAnotacion(padre_FolioAnotacion.getIdAnotacion());
	    folioDerivado.setIdMatricula(padre_FolioAnotacion.getIdMatricula());

	    try {

	    	AnotacionPk padre_Folio_AnotacionID;
	        AnotacionPk hijo_Folio_AnotacionID;

	        padre_Folio_AnotacionID = new AnotacionPk();
	        hijo_Folio_AnotacionID = new AnotacionPk();

	        padre_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion();
	        padre_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula();

	        hijo_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion1();
	        hijo_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula1();

	        boolean result;

	        result = forseti.asociarFoliosAdministrativa( padre_Folio_AnotacionID, hijo_Folio_AnotacionID, param_UsuarioSIR, 2);

	    }
	    catch( ForsetiException e ) {
	        ExceptionPrinter printer = new ExceptionPrinter(e);
		    Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }

	    // la respuesta del procesamiento se devuelve en
	    EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(evento.getFolio(), EvnRespCrearFolioOficio.AGREGAR_ENGLOBE_OK);
	
		
        try {
        	FolioPk id = new FolioPk();
	        id.idMatricula = param_Folio.getIdMatricula();

	        List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
	        List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

	        long numeroAnotaciones = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );
	        eventoResp.setFoliosHijo( foliosHijos );
	        eventoResp.setFoliosPadre( foliosPadre );
	        eventoResp.setNumeroAnotaciones( numeroAnotaciones );
        	return eventoResp;
        } catch (Throwable e) {
        	ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
       
	}
	
	private EventoRespuesta eliminarEnglobe(EvnCrearFolio evento) throws EventoException {
		Folio   param_Folio;
	    Usuario param_UsuarioSIR;

	    // datos basicos para procesamiento
	    param_Folio      = evento.getFolio();
	    param_UsuarioSIR = evento.getUsuarioSir();

	    if((param_Folio == null) || (param_Folio.getIdMatricula() == null)) {
	      throw new EventoException( "Debe especificarse un folio con un id-matricula" );
	    }

	    // id-folios sobre los cuales se va a eliminar la relacion
	    FolioPk padre_FolioID = null;
	    FolioPk hijo_FolioID = null;
	    
	    //	  parametros del evento para procesar la existencia de folios:
        Anotacion hijo = evento.getAnotacionHijo();
        Anotacion padre = evento.getAnotacionPadre();

       //se valida que este bloqueo
	   try {
	    	
	    	// 1. ver que ambos folios existan
	        hijo_FolioID = new FolioPk();
	        padre_FolioID = new FolioPk();

	        Folio padre_Folio;
	        Folio hijo_Folio;

	        padre_FolioID.idMatricula     = padre.getIdMatricula();
	        hijo_FolioID.idMatricula     = hijo.getIdMatricula();

	        padre_Folio = forseti.getFolioByIDSinAnotaciones( padre_FolioID,param_UsuarioSIR );
	        hijo_Folio = forseti.getFolioByIDSinAnotaciones( hijo_FolioID, param_UsuarioSIR );

	        if(padre_Folio == null) {
	            throw new ForsetiException( "El folio origen " +  padre_FolioID.idMatricula + " no existe" );
	        }
	        
	        if(hijo_Folio == null) {
	            throw new ForsetiException( "El folio destino " +  hijo_FolioID.idMatricula + " no existe" );
	        }
	        
	        FolioDerivado folioDerivado = null;
	        
	        folioDerivado = forseti.getFolioDerivadoEnlace( padre_FolioID, hijo_FolioID,param_UsuarioSIR);
	        
	        if( folioDerivado == null ) {
                throw new ForsetiException( "No se encontro relacion entre los folios "
                                          + "(" +  padre_FolioID.idMatricula +"," +  hijo_FolioID.idMatricula + ")"
                                          + " "
                                          + "(padre,hijo)" );
            }
	        
	        AnotacionPk padre_Folio_AnotacionID;
	        AnotacionPk hijo_Folio_AnotacionID;

	        padre_Folio_AnotacionID = new AnotacionPk();
	        hijo_Folio_AnotacionID = new AnotacionPk();

	        padre_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion();
	        padre_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula();

	        hijo_Folio_AnotacionID.idAnotacion = folioDerivado.getIdAnotacion1();
	        hijo_Folio_AnotacionID.idMatricula = folioDerivado.getIdMatricula1();
	        
	        boolean result = false;

	        result = forseti.desasociarFoliosAdministrativa( padre_Folio_AnotacionID, hijo_Folio_AnotacionID, param_UsuarioSIR, 2 );
	        forseti.desbloquearFolio(padre_Folio);

	    }
	    catch( ForsetiException e ) {
	        ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al eliminar la Relacion" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }
	    
	    //	  la respuesta del procesamiento se devuelve en
	    EvnRespCrearFolio eventoResp = new EvnRespCrearFolio(evento.getFolio(), EvnRespCrearFolio.ELIMINAR_ENGLOBE_OK);
	
		
        try {
        	FolioPk id = new FolioPk();
	        id.idMatricula = param_Folio.getIdMatricula();

	        List foliosHijos = forseti.getFoliosHijos( id, param_UsuarioSIR );
	        List foliosPadre = forseti.getFoliosPadre( id, param_UsuarioSIR );

	        long numeroAnotaciones = forseti.getCountAnotacionesFolio( param_Folio.getIdMatricula() );
	        eventoResp.setFoliosHijo( foliosHijos );
	        eventoResp.setFoliosPadre( foliosPadre );
	        eventoResp.setNumeroAnotaciones( numeroAnotaciones );
        	return eventoResp;
        } catch (Throwable e) {
        	ExceptionPrinter printer = new ExceptionPrinter(e);
	        Log.getInstance().error(ANCrearFolio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }

	}
	
	private EventoRespuesta imprimirMatricula(EvnCrearFolio evento) throws EventoException{
		
		Folio folio = evento.getFolio();
		//CONSTANTES PARA LA IMPRESIÓN.
        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";

        //COLOCAR LOS PARAMETROS INICIALES
        Hashtable parametros = new Hashtable();

        //OBTENER INFORMACIÓN A PARTIR DEL TURNO
        String UID = evento.getCirculo().getIdCirculo();

        //IMPRIMIR LOS CERTIFICADOS SOLICITADOS
        try {

                //INGRESO DE INTENTOS DE IMPRESION
                String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                if (intentosImpresion != null)
                {
                    parametros.put(INTENTOS, intentosImpresion);
                }
                else
                {
                    intentosImpresion = CImpresion.DEFAULT_INTENTOS_IMPRESION;
                    parametros.put(INTENTOS, CImpresion.DEFAULT_INTENTOS_IMPRESION);
                }

                //INGRESO TIEMPO DE ESPERA IMPRESION
                String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_CERTIFICADOS_INDIVIDUALES);
                if (intentosImpresion != null)
                {
                    parametros.put(ESPERA, esperaImpresion);
                  
                }
                else
                {
                    esperaImpresion = CImpresion.DEFAULT_ESPERA_IMPRESION;
                    parametros.put(ESPERA, CImpresion.DEFAULT_ESPERA_IMPRESION);
                }

            if ((UID == null) || (UID.length() == 0) || (intentosImpresion == null) || (intentosImpresion.length() == 0) || (esperaImpresion == null) || (esperaImpresion.length() == 0)) {
                throw new EventoException("Se requiere especificar el circulo, el número de intentos y la espera.");
            }

            if (folio == null) {
                throw new EventoException("Se requiere especificar la huja de ruta a imprimir");
            }
            
            gov.sir.core.negocio.modelo.Usuario usuarioNeg = evento.getUsuarioSir();
            String fechaImpresion = this.getFechaImpresion();
            //obtener textos base de los separadores
			String tbase1 ="";
			String tbase2 = "";
			List variablesImprimibles =  hermod.getOPLookupCodes(COPLookupTypes.VARIABLES_IMPRIMIBLES);
			for(Iterator it= variablesImprimibles.iterator(); it.hasNext();){
				OPLookupCodes op = (OPLookupCodes) it.next();
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE1)){
					tbase1= op.getValor();
				}
				if(op.getCodigo().equals(COPLookupCodes.TEXTO_BASE2)){
					tbase2 = op.getValor();
				}
			}
                 folio = forseti.getFolioByID(folio.getIdMatricula());
                  if (folio.getAnotaciones() == null || folio.getAnotaciones().isEmpty()) {
                List anots = null;
                Folio currentFolio = folio;
                FolioPk fpk = new FolioPk();
                fpk.idMatricula = folio.getIdMatricula();
                anots = forseti.getAnotacionesFolioTMP(fpk);
                if(anots != null && !anots.isEmpty()){
                    currentFolio.setAnotaciones(anots);
                    folio = currentFolio;
                }
            }
            ImprimibleHojaDeRuta imprimibleHojaRuta = new ImprimibleHojaDeRuta(folio, null, usuarioNeg, fechaImpresion,CTipoImprimible.HOJA_RUTA,tbase1, tbase2);
			
			try{
				parametros = this.imprimir(imprimibleHojaRuta, parametros, UID);
			}catch(PrintJobsException exc){
				int idImprimible = exc.getIdImpresion();
				if(idImprimible!=0){
					throw new ImpresionException("Hubo problemas de comunicación al intentar realizar la impresión <SPAN class='botontextual'><FONT  size='2'><b>("+idImprimible+")</b></FONT></SPAN>, por favor ingrese este número en el aplicativo de impresión SIR, para realizar esta impresión.");
				}
			}				
        }catch(ImpresionException exc){
			throw exc;
        } catch (Throwable t2) {
            throw new EventoException("No se pudo imprimir");
        }
		
		return null;
	}
	protected String getFechaImpresion()
	{

		Calendar c = Calendar.getInstance();
		int dia, ano, hora;
		String min, seg, mes;

		dia = c.get(Calendar.DAY_OF_MONTH);
		mes = ImprimibleConstantes.MESES[c.get(Calendar.MONTH)]; //0-Based
		ano = c.get(Calendar.YEAR);

		hora = c.get(Calendar.HOUR_OF_DAY);
		if (hora > 12)
			hora -= 12;

		min = formato(c.get(Calendar.MINUTE));
		seg = formato(c.get(Calendar.SECOND));

		String fechaImp =
			"Impreso el "
				+ dia
				+ " de "
				+ mes
				+ " de "
				+ ano
				+ " a las "
				+ formato(hora)
				+ ":"
				+ min
				+ ":"
				+ seg
				+ " "
				+ DateFormatUtil.getAmPmString(c.get(Calendar.AM_PM));

		return fechaImp;
	}
	
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}
	
	private Hashtable imprimir(ImprimibleBase imprimible, Hashtable parametros, String UID)throws PrintJobsException {

        //CONSTANTES PARA LA IMPRESIÓN.
        String INTENTOS = "INTENTOS";
        String ESPERA = "ESPERA";

        Bundle b = new Bundle(imprimible);

        String numIntentos = (String) parametros.get(INTENTOS);
        String espera = (String) parametros.get(ESPERA);

        Integer intentosInt = new Integer(numIntentos);
        int intentos = intentosInt.intValue();
        Integer esperaInt = new Integer(espera);
        int esperado = esperaInt.intValue();

        //Ciclo que se ejecuta de acuerdo al valor recibido en intentos
        try {
            //se manda a imprimir el recibo por el identificador unico de usuario
            printJobs.enviar(UID, b, intentos, esperado);
        } catch (PrintJobsException pe) {
			throw pe;
		}catch (Throwable t) {
            t.printStackTrace();
        }

        return parametros;
    }
}
