package gov.sir.core.negocio.acciones.administracion;

import gov.sir.core.eventos.administracion.EvnCrearFolioOficio;
import gov.sir.core.eventos.administracion.EvnRespCrearFolioOficio;
import gov.sir.core.eventos.administracion.EvnResp_AdminCrearFolioSaveInfoOficio;
import gov.sir.core.eventos.administracion.Evn_AdminCrearFolioSaveInfoOficio;
import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.CancelarAnotacionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCreadoException;
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
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudAntiguoSistema;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import java.util.ArrayList;
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
public class ANCrearFolioOficio extends SoporteAccionNegocio {
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

    public ANCrearFolioOficio() throws EventoException{
        super();
        service = ServiceLocator.getInstancia();
        try {
            hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
            forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
        } catch (ServiceLocatorException e) {
            throw new ServicioNoEncontradoException("Error instanciando el servicio", e);
        }

        if (hermod == null) {
            throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
        }

        if (forseti == null) {
            throw new ServicioNoEncontradoException("Servicio Forseti no encontrado");
        }

    }

    /* (non-Javadoc)
     * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
     */
    public EventoRespuesta perform(Evento ev) throws EventoException {
        if (ev instanceof EvnCrearFolioOficio){
			EvnCrearFolioOficio evento=(EvnCrearFolioOficio)ev;

			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.CREACION_FOLIO)){
				return registrarCreacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.VALIDAR_MATRICULA_CREACION_FOLIO)){
				return adminCrearFolioSaveInfo_ProcesarFolio(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.AGREGAR_ANOTACION)){
				return agregarAnotacionMatricula(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.GRABAR_ANOTACIONES_TEMPORALMENTE)){
				return grabarAnotacionesTemporal(evento);
			} 
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.ELIMINAR_ANOTACION)){
				return eliminarAnotacionTemporal(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.VOLVER_EDICION)){
				return volverEdicion(evento);
			} 
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.CANCELAR_ANOTACIONES)){
				return cancelarAnotacionTemporal(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.CREAR_SEGREGACION_ENGLOBE)){
				return crearSegregacionEnglobe(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.CREAR_DERIVACION)){
				return crearDerivacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.ELIMINAR_DERIVACION)){
				return eliminarDerivacion(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.CREAR_ENGLOBE)){
				return crearEnglobe(evento);
			}
			if (evento.getTipoEvento().equals(EvnCrearFolioOficio.ELIMINAR_ENGLOBE)){
				return eliminarEnglobe(evento);
			}
			if(evento.getTipoEvento().equals(EvnCrearFolioOficio.AGREGAR_CIUDADANO_ANOTACION)) {
				return validarCiudadanos(evento);
			}
			if(evento.getTipoEvento().equals(EvnCrearFolioOficio.ELIMINAR_FOLIO)) {
				return eliminarFolioTemporal(evento);
			}
        }


        if( ev instanceof Evn_AdminCrearFolioSaveInfoOficio ) {
        	Evn_AdminCrearFolioSaveInfoOficio evento;
        	evento = (Evn_AdminCrearFolioSaveInfoOficio)ev;

        	String local_TipoEvento;
        	local_TipoEvento = ev.getTipoEvento();

        	if( Evn_AdminCrearFolioSaveInfoOficio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENT.equals( local_TipoEvento ) ) {
        		return adminCrearFolioSaveInfo_ProcesarFetchFolio( evento );
        	} // if
        	if( Evn_AdminCrearFolioSaveInfoOficio.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENT.equals( local_TipoEvento ) ) {
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


    private EventoRespuesta eliminarFolioTemporal(EvnCrearFolioOficio evento) throws EventoException {
		Folio folio = evento.getFolio();
		
		try{
			forseti.eliminarFolioCreacionDirecta(folio, evento.getUsuarioSir());
		}catch( ForsetiException fe ) {
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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return null;
	}

	// @decorator
	private EventoRespuesta
	adminCrearFolioSaveInfo_ProcesarLoadFolioById( Evn_AdminCrearFolioSaveInfoOficio evento ) throws EventoException {

		// @in

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolioEditado();
        local_UsuarioSir = evento.getUsuarioSir();

		// @proc

        // consultar el folio por el identificador

		Folio folioTemporal = null;
		String local_FolioEditado_IdMatricula;

		try {
          	local_FolioEditado_IdMatricula = local_Folio.getIdMatricula();

          	FolioPk id        = new FolioPk();
			id.idMatricula     = local_FolioEditado_IdMatricula;
			folioTemporal      = forseti.loadFolioByID(id, local_UsuarioSir );
			List anotaciones = folioTemporal.getAnotaciones();
			if ( anotaciones == null || anotaciones.isEmpty())
			{
				anotaciones = forseti.getAnotacionesFolioTMP(id);
				folioTemporal.setAnotaciones(anotaciones);
			}
			
			if (!(folioTemporal.getDirecto() == CFolio.FOLIO_CREADO_ADMINISTRATIVA_CREACION)) {
				throw new ForsetiException("El folio no fue creado por Pantalla Administrativa de Creación Directa");
			}
			
			// Se tiene que validar si es temporal y esta asociado a un turno de Certificado
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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		


		// --------------------------------------------------
		// @ou
        // wrap ------------------------------------
		EvnResp_AdminCrearFolioSaveInfoOficio local_Result;
		local_Result = new EvnResp_AdminCrearFolioSaveInfoOficio( EvnResp_AdminCrearFolioSaveInfoOficio.ADMINCREARFOLIO_SAVEINFO_STEP0_FIND__EVENTRESP_OK );

        // set results
		local_Result.setFolioEditado( folioTemporal );

       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
       // -----------------------------------------

	} // end-method:


    // @decorator
    private EventoRespuesta
    adminCrearFolioSaveInfo_ProcesarFetchFolio( Evn_AdminCrearFolioSaveInfoOficio evento ) throws EventoException {



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
        	  folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
          }
        }
        catch( ForsetiException fe ) {
                List l;
                l = fe.getErrores();
                throw new FolioNoCreadoException(l);
        }
        catch (Throwable e) {
                ExceptionPrinter printer = new ExceptionPrinter(e);
                Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
                throw new EventoException(e.getMessage(), e);
        }

        // REVISAR: Se necesita consultar aca el ultimo editado ?


        // --------------------------------------------------
        // @ou
	// --------------------------------------------------
        // wrap ------------------------------------
        EvnResp_AdminCrearFolioSaveInfoOficio local_Result;
	local_Result = new EvnResp_AdminCrearFolioSaveInfoOficio( EvnResp_AdminCrearFolioSaveInfoOficio.ADMINCREARFOLIO_SAVEINFO_STEP2_BACK__EVENTRESP_OK );

        // set results
        local_Result.setFolioEditado( folioTemporal );


       // -----------------------------------------
       // send-message ---------------------------------
       return local_Result;
       // -----------------------------------------

    } // end-method:



    // @decorator
	private EventoRespuesta
	adminCrearFolioSaveInfo_ProcesarFolio( EvnCrearFolioOficio evento ) throws EventoException {

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
	adminCrearFolioSaveInfo_ProcesarFolio_Updating( EvnCrearFolioOficio evento ) throws EventoException {

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
              Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		} // try

		// 3: get folio

		Folio folioTemporal = null;
		long  folioTemporalAnotacionesNextOrden = 0L;

		try {

			FolioPk id        = new FolioPk();
			id.idMatricula     = local_Folio.getIdMatricula();
			folioTemporal      = forseti.getFolioByID( id, local_UsuarioSir );
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
				folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
			}
                        try {
			  folioTemporalAnotacionesNextOrden = forseti.getNextOrdenAnotacion( id );
                        }
                        catch( Exception e ) {
                          Log.getInstance().error(ANCrearFolioOficio.class,"Errores al obtener el siguiente identificador de orden " + e.getMessage() );
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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

	    // wrap event

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.VALIDAR_MATRICULA_CREACION_FOLIO_UPDATING_OK );
		eventoResp.setAnotacionesNextOrden( folioTemporalAnotacionesNextOrden );

		return eventoResp;


	} // end-method:

	/**
	 * Método que se encaga de asociar el nuevo folio al turno luego de su creación.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta registrarCreacion(EvnCrearFolioOficio evento) throws EventoException {

		EvnRespCrearFolioOficio eventoResp = null;
		Usuario user = evento.getUsuarioSir();
		//Hashtable tabla = new Hashtable();

		Folio folioResp = crearFolio(evento);
		eventoResp = new EvnRespCrearFolioOficio(folioResp, EvnRespCrearFolioOficio.CREACION_FOLIO);

		return eventoResp;

	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnCrearFolioOficio evento) throws EventoException {
	
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
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespCrearFolioOficio evn = new EvnRespCrearFolioOficio(EvnRespCrearFolioOficio.VOLVER_AGREGAR_CIUDADANOS);
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
	private Folio crearFolio(EvnCrearFolioOficio evento) throws EventoException {

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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		*/
		//Si la información del folio es válida se crea el folio.
		Usuario usuarioSIR = evento.getUsuarioSir();
		
			

		try {
			
			FolioPk idfolio = new FolioPk();
			idfolio.idMatricula = folio.getIdMatricula();

			
			//se consulta para volver definitivos los folios padres
			List foliosPadre = forseti.getFoliosPadre(idfolio,usuarioSIR);
			//	se consulta para volver definitivos los folios hijo
			List foliosHijos = forseti.getFoliosHijos(idfolio,usuarioSIR);
			
			Iterator ip = foliosPadre.iterator();
			while(ip.hasNext()){
				Folio folioId = (Folio)ip.next();
				this.forseti.hacerDefinitivoFolio(folioId,usuarioSIR, null, false);
				this.forseti.desbloquearFolio(folioId);
			}
			
			//se debe hacer definitivo el folio que se esta tratando de volver definitivo			
			//auxFolio = this.forseti.crearFolio(folio, usuarioSIR, false);
			this.forseti.hacerDefinitivoFolio(folio,usuarioSIR, null, false);
			this.forseti.desbloquearFolio(folio);
			
			Iterator ipp = foliosHijos.iterator();
			while(ipp.hasNext()){
				Folio folioId = (Folio)ipp.next();
				this.forseti.hacerDefinitivoFolio(folioId,usuarioSIR, null, false);
				this.forseti.desbloquearFolio(folioId);
			}
			
		} catch (ForsetiException fe) {
			List l = null;
			l = fe.getErrores();
			if (l.size() > 0) {
				throw new FolioNoCreadoException(l);
			} else {
				ExceptionPrinter printer = new ExceptionPrinter(fe);
				throw new EventoException(fe.getMessage(), fe);
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
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
				Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
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
	private EventoRespuesta validarMatriculaCreacion(EvnCrearFolioOficio evento) throws EventoException {

        gov.sir.core.negocio.modelo.Folio local_Folio;
        gov.sir.core.negocio.modelo.Usuario local_UsuarioSir;

        local_Folio      = evento.getFolio();
        local_UsuarioSir = evento.getUsuarioSir();


        FolioPk id        = new FolioPk();
		Folio folioTemporal = null;
		long  folioTemporalAnotacionesNextOrden = 0L;

		try {
			id.idMatricula     = local_Folio.getIdMatricula();

			forseti.validarMatriculaCrearFolio( local_Folio );
			folioTemporal = forseti.crearFolio( local_Folio, local_UsuarioSir, null, false);
			List matriculas = new ArrayList();
			matriculas.add(folioTemporal.getIdMatricula());
			//this.forseti.delegarBloqueoFolio(null, evento.getUsuarioSir(), folioId);
			this.forseti.bloquearFolios( matriculas, local_UsuarioSir, null );
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		try {
			folioTemporalAnotacionesNextOrden = forseti.getNextOrdenAnotacion( id );
		} catch (Throwable e) {
			folioTemporalAnotacionesNextOrden = 1;
		}

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.VALIDAR_MATRICULA_CREACION_FOLIO_OK);
		eventoResp.setAnotacionesNextOrden( folioTemporalAnotacionesNextOrden );
		return eventoResp;

	}

	/**
	 * Método que se encaga de agregar una anotación a la matrícula.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta agregarAnotacionMatricula(EvnCrearFolioOficio evento) throws EventoException {
		Folio folioTemporal = null;
		List anotaciones = null;
		try {
			this.forseti.updateFolio(evento.getFolio(), evento.getUsuarioSir(), null, false);

			FolioPk id = new FolioPk();
			id.idMatricula = evento.getFolio().getIdMatricula();
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
				folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
			}
			anotaciones = forseti.getAnotacionesTemporalesByRangoOrden(id,evento.getAnotacion().getOrden(),evento.getAnotacion().getOrden(), evento.getUsuarioSir());
		} catch (ForsetiException fe) {
			List l = new ArrayList();
			l.add(fe.getMessage());
			throw new FolioNoCreadoException(l);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.AGREGAR_ANOTACION_OK);
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
	private EventoRespuesta grabarAnotacionesTemporal(EvnCrearFolioOficio evento) throws EventoException {

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
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
				folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.AGREGAR_ANOTACION_OK);
		return eventoResp;


	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta eliminarAnotacionTemporal(EvnCrearFolioOficio evento) throws EventoException {

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
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
				folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
		}
		
		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.AGREGAR_ANOTACION_OK);
		return eventoResp;
	}
                   
                    
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta volverEdicion(EvnCrearFolioOficio evento) throws EventoException {

		Folio folioTemporal;
		
		Usuario usuarioNeg = evento.getUsuarioSir();
		Folio nuevo=new Folio();
		FolioPk id = new FolioPk();
		id.idMatricula = evento.getFolio().getIdMatricula();

		//SE ACTUALIZA EL FOLIO
		try {
			folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
				folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);

		}

		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.AGREGAR_ANOTACION_OK);
		return eventoResp;


	}
	
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta cancelarAnotacionTemporal(EvnCrearFolioOficio evento) throws EventoException {

		Folio folioTemporal = null;
		
		Usuario usuarioNeg = evento.getUsuarioSir();
		Folio nuevo=new Folio();
		
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		Anotacion anotacion= evento.getAnotacion();
		nuevo.addAnotacione(anotacion);
		try {
			if(forseti.updateFolioCreacionDirecta(nuevo,usuarioNeg)){
				FolioPk id=new FolioPk();
				id.idMatricula=evento.getFolio().getIdMatricula();
				folioTemporal = this.forseti.getFolioByID(id, evento.getUsuarioSir());
				//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
				if(folioTemporal.getAnotaciones()==null || folioTemporal.getAnotaciones().isEmpty()){
					folioTemporal.setAnotaciones(forseti.getAnotacionesFolioTMP(id));
				}
			}
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudo cancelar la anotacion:"+ep.toString());
			throw new CancelarAnotacionNoEfectuadaException("No se pudo cancelar la anotacion",e1);
		}


		EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(folioTemporal, EvnRespCrearFolioOficio.AGREGAR_ANOTACION_OK);
		return eventoResp;
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
			Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}

		return evRespuesta;
	}
	
	private EventoRespuesta crearSegregacionEnglobe(EvnCrearFolioOficio evento) throws EventoException {

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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
        
		//return eventoResp;
	}
	
	private EventoRespuesta crearDerivacion(EvnCrearFolioOficio evento) throws EventoException {
		
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
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
		    Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
			Log.getInstance().error(ANCrearFolioOficio.class,"No se pudo crear la segregación:" +ep.toString());
			throw new CancelarAnotacionNoEfectuadaException("No se pudo crear la segregación",e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
        
        
	} // :foliosPadreHijo_addFolioPadre

	  
	private EventoRespuesta eliminarDerivacion(EvnCrearFolioOficio evento) throws EventoException {

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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al eliminar la Relacion" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }

        
	    
	}
	
	private EventoRespuesta crearEnglobe(EvnCrearFolioOficio evento) throws EventoException {
		
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
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
		    Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
		    Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
		    throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
       
	}
	
	private EventoRespuesta eliminarEnglobe(EvnCrearFolioOficio evento) throws EventoException {
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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al eliminar la Relacion" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }
	    catch (Throwable e) {
	       ExceptionPrinter printer = new ExceptionPrinter(e);
	       Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepcion inesperada guardando cambios del folio" + printer.toString() );
	       throw new EventoException(e.getMessage(), e);
	    }
	    
	    //	  la respuesta del procesamiento se devuelve en
	    EvnRespCrearFolioOficio eventoResp = new EvnRespCrearFolioOficio(evento.getFolio(), EvnRespCrearFolioOficio.ELIMINAR_ENGLOBE_OK);
	
		
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
	        Log.getInstance().error(ANCrearFolioOficio.class,"Ha ocurrido una excepción al consultar los datos del folio" + printer.toString() );
	        throw new EventoException(e.getMessage(), e);
	    }

	}
}
