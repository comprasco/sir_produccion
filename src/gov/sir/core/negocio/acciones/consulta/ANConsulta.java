package gov.sir.core.negocio.acciones.consulta;

import co.com.iridium.generalSIR.negocio.validaciones.TrasladoSIR;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.consulta.EvnConsulta;
import gov.sir.core.eventos.consulta.EvnRespConsulta;
import gov.sir.core.negocio.acciones.excepciones.ANConsultaException;
import gov.sir.core.negocio.acciones.excepciones.ActualizacionBusquedaException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaCalificacionFolioException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaCorreccionFolioException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaFolioEspecializadoException;
import gov.sir.core.negocio.acciones.excepciones.CreacionBusquedaException;
import gov.sir.core.negocio.acciones.excepciones.ErrorImpresionException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ImpresionNoEfectuadaException;
import gov.sir.core.negocio.acciones.excepciones.ListarNoEfectuadoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.SolicitudConsultaNoCreadaException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFirmaRegistrador;
import gov.sir.core.negocio.modelo.constantes.CHermod;
import gov.sir.core.negocio.modelo.constantes.CImpresion;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CTipoImprimible;
import gov.sir.core.negocio.modelo.constantes.CValidacion;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionCiudadano;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.FirmaRegistrador;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.TipoConsulta;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.Validacion;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConstanciaNoPropiedad;
import gov.sir.core.negocio.modelo.imprimibles.ImprimibleConsulta;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleBase;
import gov.sir.core.negocio.modelo.imprimibles.base.ImprimibleConstantes;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.imprimibles.base.UIUtils;
import gov.sir.core.util.DateFormatUtil;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

import gov.sir.print.common.Bundle;
import gov.sir.print.interfaz.PrintJobsInterface;
import gov.sir.print.server.PrintJobsException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.auriga.core.modelo.transferObjects.Estacion;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;


  /**
   * Esta accion de negocio es responsable de ejecutar la lógica
   * necesaria para dar respuesta a los diferentes requerimientos del proceso
   * de consultas.
   */
   public class ANConsulta extends SoporteAccionNegocio {

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

    //Depurado Enero 11 2006
	/**
	* Constructor encargado de instanciar los servicios utilizados por la
	* acción de Negocio
	* @throws EventoException
	*/
	public ANConsulta() throws EventoException {

		super();
		service = ServiceLocator.getInstancia();


		try {

			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			printJobs = (PrintJobsInterface) service.getServicio("gov.sir.print");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando servicios.");
		}

        //No se encontró el servicio HERMOD.
		if (hermod == null) {
			throw new ServicioNoEncontradoException("EL Servicio HERMOD no pudo ser instanciado");
		}

		//No se encontró el servicio FORSETI.
		if (forseti == null) {
			throw new ServicioNoEncontradoException("El Servicio FORSETI no pudo ser instanciado");
		}

		//No se encontró el servicio PRINTJOBS
        if (printJobs == null) {
            throw new ServicioNoEncontradoException("Servicio PrintJobs no encontrado");
        }
    }



	//Depurado Enero 11 2006
	/**
	 * Realiza el llamado al método necesario para realizar la lógica de los diferentes requerimientos
	 * del proceso de consultas, de acuerdo con el tipo de evento recibido como parámetro.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @throws EventoException cuando ocurre un problema que no se pueda manejar
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @see gov.sir.core.eventos.comun.EvnConsulta
	 * @see gov.sir.core.eventos.comun.EvnRespConsulta
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {


		try{

			EvnConsulta evento = (EvnConsulta) e;

			//1. CREAR UNA SOLICITUD DE CONSULTAS
			if (evento.getTipoEvento().equals(EvnConsulta.GENERAR_CONSULTA)) {
				return generarSolicitud(evento);
			}

			//2. GENERAR NUEVAS BUSQUEDAS
			else if (evento.getTipoEvento().equals(EvnConsulta.NUEVA_BUSQUEDA)) {
				return generarNuevaBusqueda(evento);
			}

			else if (evento.getTipoEvento().equals(EvnConsulta.NUEVA_BUSQUEDA_CALIFICACION)) {
				return generarNuevaBusqueda(evento);
			}

			//3. GENERAR UN NUEVO INTENTO SIMPLE.
			else if (evento.getTipoEvento().equals(EvnConsulta.INTENTO_SIMPLE)) {
				return generarNuevoIntento (evento);
			}

			else if (evento.getTipoEvento().equals(EvnConsulta.INTENTO_SIMPLE_CALIFICACION)) {
				return generarNuevoIntento(evento);
			}

			//4. CONSULTAR FOLIO
			else if (evento.getTipoEvento().equals(EvnConsulta.FOLIO)) {
				return consultarFolio(evento);
			}

			else if (evento.getTipoEvento().equals(EvnConsulta.FOLIO_CALIFICACION)) {
				return consultarFolio(evento);
			}

			else if (evento.getTipoEvento().equals(EvnConsulta.FOLIO_CORRECCION)) {
				return consultarFolio(evento);
			}

			//5. VALIDAR EXISTENCIA FOLIO
			else if (evento.getTipoEvento().equals(EvnConsulta.VALIDA_FOLIO)) {
				return validarFolio(evento);
			}

			//6. CONSULTAR FOLIOS CALIFICACION
			else if (evento.getTipoEvento().equals(EvnConsulta.CONSULTA_CALIFICACION_FOLIO)) {
				return consultarFolioCalificacion(evento);
			}

			//7. CONSULTAR FOLIOS CALIFICACION
			else if (evento.getTipoEvento().equals(EvnConsulta.CONSULTA_TRASLADO)) {
				return consultarFolio(evento);
			}

			//8. ADICIONAR SOLICITUDES DE FOLIOS
			else if (evento.getTipoEvento().equals(EvnConsulta.ADICIONAR_SOLICITUD_FOLIO)) {
				return adicionarSolicitudesFolio(evento);
			}

			//9. IMPRIMIR RESULTADOS CONSULTA
			else if (evento.getTipoEvento().equals(EvnConsulta.IMPRIMIR)) {
				return imprimir(evento);
			}

			//10. AVANZAR TURNO
			else if (evento.getTipoEvento().equals(EvnConsulta.AVANZAR_TURNO)) {
				return avanzaTurno(evento);
			}

			//11. ADICIONAR CIUDADANO A SOLICITUD
			else if (evento.getTipoEvento().equals(EvnConsulta.ADICIONAR_CIUDADANO_A_SOLICITUD_COMPLEJA)) {
				return adicionaCiudadanoASolicitud(evento);
			}

			//12. CONSULTAR DETALLES DE FOLIO EN TURNO
			else if (evento.getTipoEvento().equals(EvnConsulta.CONSULTAR_FOLIO)) {
				return consultarDetalleTurno(evento);
			}

			return null;


		}
                /**
                 * @author      :  Julio Alcazar
                 * @change      :  Revision: manejo de la Excepcion.
                 * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                 */
                catch (CreacionBusquedaException t) {
			throw  t;
		}catch (Throwable t) {
		     throw new EventoException (t.getMessage(),t);
	    }

	}





	//Depurado Enero 11 2006
	/**
	 * Manejo del evento para generar Solicitudes de Consulta.
	 * El método invoca los servicios de Hermod encargados de la creación de solicitudes de Consulta
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 * Se genera la excepción cuando:
	 * 1. No fue posible crear la solicitud de consultas (Fallo servicio HERMOD).
	 */
	private EvnRespConsulta generarSolicitud(EvnConsulta evento) throws EventoException {

		SolicitudConsulta solicitud = null;
		Busqueda busqueda = evento.getBusqueda();

		//Crear la solicitud de Consulta (Servicio hermod)
		try {
			solicitud = (SolicitudConsulta) hermod.crearSolicitud(evento.getSolicitudConsulta());

			//No fue posible crear la solicitud de consultas.
			if (solicitud == null) {
				throw new SolicitudConsultaNoCreadaException(
					"Fallo en el servicio de creación de la solicitud de consultas",null);
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

        //Se genera un evento de respuesta con la solicitud de consulta generada.
		EvnRespConsulta eventoRespuesta =
			new EvnRespConsulta(new Vector(), EvnRespConsulta.SOLICITUD_CONSULTA_GENERADA);
		eventoRespuesta.setSolicitudConsulta(solicitud);

		return eventoRespuesta;
	}




	//Depurado Enero 11 2006
	/**
	* Método que adiciona una búsqueda a una solicitud de consultas ya existente.
	* @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	* @throws ANConsultaException
	*/
	private EvnRespConsulta generarNuevaBusqueda(EvnConsulta evento) throws EventoException {

		//1. Obtención de parámetros desde el evento.
		SolicitudConsulta solicitud = null;
		evento.getBusqueda().setSolicitud(evento.getSolicitudConsulta());


		//2. Adicionar la búsqueda a la solicitud de consultas (Servicio Hermod)
		try {
			solicitud = (SolicitudConsulta) hermod.addBusquedaToSolicitudConsulta(
					     evento.getSolicitudConsulta(),	evento.getBusqueda());

                        /**
                        * @author      :  Julio Alcazar
                        * @change      :  Revision: Validacion bloqueo por traslado.
                        * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                        */
                        TrasladoSIR trasladoSIR = new TrasladoSIR();
                        if (trasladoSIR.isTrasladoSinConf(evento.getBusqueda().getMatricula())) {
                             throw new CreacionBusquedaException("El folio se encuentra bloqueado");
                        }
			//Fallo en el servicio de hermod
			if (solicitud == null) {
				throw new CreacionBusquedaException("Fallo en el servicio que adiciona búsquedas +" +
					                                 " a solicitudes de consultas", null);
			}
		} /**
                  * @author      :  Julio Alcazar
                  * @change      :  Revision: manejo de la Excepcion.
                        * Caso Mantis  :  0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                  */
                catch (CreacionBusquedaException e) {
			throw  e;
		}
                catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}



		//3. Atender la consulta (Servicio Forseti)
		List folios = new Vector();
		List busquedas = solicitud.getBusquedas();

		Busqueda busqueda = null;

		if (busquedas.size() > 0) {
			Busqueda busquedaCreada = obtenerUltimaBusqueda(busquedas); //última búsqueda
			gov.sir.core.negocio.modelo.BusquedaPk bid = new gov.sir.core.negocio.modelo.BusquedaPk();
			bid.idBusqueda = busquedaCreada.getIdBusqueda();
			bid.idSolicitud = busquedaCreada.getIdSolicitud();

			try {
				busqueda = forseti.atenderConsulta(bid);
				if (busqueda == null) {
					throw new EventoException("La consulta solicitada no pudo ser realizada", null);
				}
				
				//SI LA CONSULTA ERA POR ID_MATRICULA Y NO SE ENCONTRARON REGISTROS ASOCIADOS
				//SE INTENTA VERIFICAR SI EL FOLIO ASOCIADO ESTA EN TRAMITE.
				if(busqueda.getResultadosFolios().size()==0 && busqueda.getMatricula()!=null){
					
					Folio folioTemp = new Folio();
					folioTemp.setIdMatricula(busqueda.getMatricula());
					
					if(hermod.isFolioSirMigTurnoFolioTramite(folioTemp)){
						throw new EventoException("El folio se encuentra en trámite en el sistema FOLIO.");		
					}
					
					if(hermod.isFolioSirMigTurnoFolio(folioTemp)){
						throw new EventoException("El folio aún se encuentra sin migrar a SIR.");		
					}
					
				}
				
                                /**
                                * @autor Edgar Lora
                                * @mantis 11987
                                */
				List resultadoFolios = busqueda.getResultadosFolios();
                                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                                for(int i = 0; i < resultadoFolios.size(); i = i + 1){
                                    ResultadoFolio folio = (ResultadoFolio) resultadoFolios.get(i);
                                    if(!validacionesSIR.isEstadoFolioBloqueado(folio.getIdMatricula())){
                                        folios.add(folio);
                                    }
                                }
			} catch (HermodException e) {
				Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			} catch (Throwable e) {
				Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
				throw new EventoException(e.getMessage(), e);
			}
		}


		//4. Generar un evento de respuesta con el resultado de la consulta.
		EvnRespConsulta eventoRespuesta = null;

		//BUSQUEDAS EN EL PROCESO DE CALIFICACION.
		if (evento.getTipoEvento().equals(EvnConsulta.NUEVA_BUSQUEDA_CALIFICACION)) {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.RESULTADO_CONSULTA_CALIFICACION);
		}

		//BUSQUEDAS EN PROCESOS DIFERENTES A CALIFICACION.
		else {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.RESULTADO_CONSULTA);
		}

		eventoRespuesta.setSolicitudConsulta(solicitud);
		eventoRespuesta.setUltimaBusqueda(busqueda);

		return eventoRespuesta;
	}



	//Depurado Enero 11 2006
	/**
	 * Método que obtiene la última búsqueda dentro de una lista de búsquedas, utilizado
	 * la fecha de creación como parámetro de ordenamiento
	 * @param busquedas Listado de objetos de tipo <code>Busqueda</code> del cual se desea
	 * obtener la última <code>Busqueda</code>
	 * @return Ultima <code>Busqueda</code> obtenida de una lista de objetos de tipo <code>Busqueda</code>
	 * utilizando la fecha de creación como parámetro de ordenamiento.
	 */
	private Busqueda obtenerUltimaBusqueda(List busquedas) {
		Busqueda rtn = null;
		long max = -1;

		for (int i = busquedas.size() - 1; i > -1 ; i--) {
			Busqueda element = (Busqueda) busquedas.get(i);
			if(element.getFechaCreacion().getTime() > max){
				max = element.getFechaCreacion().getTime();
				rtn = element;
			}
		}
		return rtn;
	}





	//Depurado Enero 11 2006
	/**
	 * Método que genera un nuevo intento de consulta en el sistema actualizando las búsquedas
	 * en la solicitud de consulta y luego ejecutando la consulta.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta generarNuevoIntento(EvnConsulta evento) throws EventoException {


		//1. Actualizar la búsqueda en la solicitud de consulta. (Servicio Hermod)
		SolicitudConsulta solicitud = null;
		Busqueda busqueda = evento.getBusqueda();

		try {
			solicitud = (SolicitudConsulta) hermod.updateBusquedaInSolicitudConsulta(
					                        evento.getSolicitudConsulta(), busqueda);
			if (solicitud == null) {
				throw new ActualizacionBusquedaException(
					"No se pudo generar un nuevo intento para la búsqueda. Fallo en el servicio que" +
					"actualiza búsquedas en las solicitudes de consultas", 	null);
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}



		//2. Atender la búsqueda. (Servicio Forseti)
		List folios = new Vector();
		gov.sir.core.negocio.modelo.BusquedaPk bid = new gov.sir.core.negocio.modelo.BusquedaPk();
		bid.idBusqueda = busqueda.getIdBusqueda();
		bid.idSolicitud = busqueda.getIdSolicitud();

		Busqueda busquedaActualizada = null;

		try {
			busquedaActualizada = forseti.atenderConsulta(bid);

			if (busquedaActualizada == null) {
				throw new ListarNoEfectuadoException("La consulta solicitada no pudo ser realizada", null);
			}
			
			//SI LA CONSULTA ERA POR ID_MATRICULA Y NO SE ENCONTRARON REGISTROS ASOCIADOS
			//SE INTENTA VERIFICAR SI EL FOLIO ASOCIADO ESTA EN TRAMITE.
			if(busquedaActualizada.getResultadosFolios().size()==0 && busquedaActualizada.getMatricula()!=null){
				
				Folio folioTemp = new Folio();
				folioTemp.setIdMatricula(busquedaActualizada.getMatricula());
				
				if(hermod.isFolioSirMigTurnoFolioTramite(folioTemp)){
					throw new EventoException("El folio se encuentra en trámite en el sistema FOLIO.");		
				}
				
				if(hermod.isFolioSirMigTurnoFolio(folioTemp)){
					throw new EventoException("El folio aún se encuentra sin migrar a SIR.");		
				}
				
			}
			/**
                        * @autor Edgar Lora
                        * @mantis 11987
                        */
			List foliosResultado = busquedaActualizada.getResultadosFolios();
                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                        for(int i = 0; i < foliosResultado.size(); i = i + 1){
                            ResultadoFolio folio = (ResultadoFolio) foliosResultado.get(i);
                            if(!validacionesSIR.isEstadoFolioBloqueado(folio.getIdMatricula())){
                                folios.add(folio);
                            }
                        }
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}


        //3. Generar un evento de respuesta con los resultados obtenidos en la consulta.
		EvnRespConsulta eventoRespuesta = null;

		//BUSQUEDAS EN EL PROCESO DE CALIFICACION.
		if (evento.getTipoEvento().equals(EvnConsulta.INTENTO_SIMPLE_CALIFICACION)) {
			eventoRespuesta =
				new EvnRespConsulta(folios, EvnRespConsulta.RESULTADO_CONSULTA_CALIFICACION);
		}

		//BUSQUEDAS EN PROCESOS DIFERENTES A CALIFICACION.
		else {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.RESULTADO_CONSULTA);
		}

		eventoRespuesta.setSolicitudConsulta(solicitud);
		eventoRespuesta.setUltimaBusqueda(busquedaActualizada);

		return eventoRespuesta;
	}




	//Depurado Enero 11 2006
	/**
	 * Adiciona objetos <code>SolicitudFolio</code> a una <code>SolicitudConsulta</code>
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta adicionarSolicitudesFolio(EvnConsulta evento) throws EventoException {
		List solicitudesFolioPendientes = evento.getSolicitudesFolio();

		SolicitudConsulta solicitud = null;
		SolicitudFolio solFolio = null;
		Iterator iter = null;
		Busqueda busqueda = evento.getBusqueda();


		//1. Agrega solicitudes de folio a una solicitud de consultas. (Servicio Hermod)
		try {
			Busqueda ultimaBusqueda=obtenerUltimaBusqueda(evento.getSolicitudConsulta().getBusquedas());
			for (iter = solicitudesFolioPendientes.iterator(); iter.hasNext();) {
				solFolio = (SolicitudFolio) iter.next();
				solFolio.setIdBusquedaConsulta(ultimaBusqueda.getIdBusqueda());
				solicitud = (SolicitudConsulta) hermod.addFolioToSolicitudConsulta(
						                        evento.getSolicitudConsulta(), solFolio);

			}

			if (solicitud == null) {
				throw new ActualizacionBusquedaException(
					"Fallo en el servicio que asocia solicitudes de folios con solicitudes de consultas",null);
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespConsulta eventoRespuesta = new EvnRespConsulta(EvnRespConsulta.SOLICITUDES_FOLIO_ADICIONADAS);


                //TODO: INVESTIGAR E IDENTIFICAR QUE HACE EL SIGUIENTE SEGMENTO DE CODIGO Y DOCUMENTARLO.
                ////////
                try {
                    for (int i = 0; i < solicitud.getSolicitudFolios().size(); i++) {
                        Folio folio = ((SolicitudFolio) solicitud.getSolicitudFolios().get(i)).
                                      getFolio();
                        if (folio.getDirecciones().size() == 0) {
                            FolioPk fid = new FolioPk();
                            fid.idMatricula = folio.getIdMatricula();
                            folio.addDireccione(forseti.getUltimaDireccion(fid));
                        }
                    }
                } catch (Throwable e) {
                    Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
                    throw new EventoException(e.getMessage(), e);
                }
                ///////


		eventoRespuesta.setSolicitudConsulta(solicitud);

		return eventoRespuesta;
	}




	/**
	 * Método encargado de consultar un folio específico en el sistema.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * Contiene
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta consultarFolio(EvnConsulta evento) throws EventoException {
		List folios = new Vector();
		try {
			Folio folio = forseti.getFolioByMatricula(evento.getFolio().getIdMatricula());
			if (folio != null) {
				folios.add(folio);
			} else {
				throw new FolioNoEncontradoException("No fue posible obtener el folio", null);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespConsulta eventoRespuesta = null;
		if (evento.getTipoEvento().equals(EvnConsulta.FOLIO_CALIFICACION)) {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.VER_FOLIO_CALIFICACION);
//ok sin anotaciones
		} else if (evento.getTipoEvento().equals(EvnConsulta.FOLIO_CORRECCION)) {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.VER_FOLIO_CORRECCION);
//ok sin anotaciones
		} else if (evento.getTipoEvento().equals(EvnConsulta.CONSULTA_TRASLADO)) {

			if (!((Folio) folios.get(0)).getEstado().getIdEstado().equals(CEstadoFolio.ACTIVO)) {
				throw new EventoException("El folio no se encuentra activo");
			}
			if (((Folio) folios.get(0)).getTurnosFolios().size() > 0) {
				throw new EventoException("Existen turnos asociados al folio");
			}

            String idCirculo = evento.getCirculo().getIdCirculo();
            if (!(((Folio) folios.get(0)).getZonaRegistral().getCirculo().getIdCirculo().equals(idCirculo))) {
                throw new EventoException("El folio no pertenece al círculo registral actual");
            }

            // El folio no puede estar en trámite, ni cerrado, ni bloqueado
            List validaciones = new Vector();
            Validacion validacion = new Validacion();
            validacion.setIdValidacion(CValidacion.FOLIO_ANULADO);
            validaciones.add(validacion);
            validacion = new Validacion();
            validacion.setIdValidacion(CValidacion.FOLIO_BLOQUEADO);
            validaciones.add(validacion);
            validacion = new Validacion();
            validacion.setIdValidacion(CValidacion.FOLIO_CERRADO);
            validaciones.add(validacion);
            validacion = new Validacion();
            validacion.setIdValidacion(CValidacion.FOLIO_EN_TRAMITE);
            validaciones.add(validacion);
            validacion = new Validacion();
            validacion.setIdValidacion(CValidacion.FOLIO_TRASLADADO);
            validaciones.add(validacion);

            List matriculas = new Vector();
            for(Iterator iteradorFolios = folios.iterator(); iteradorFolios.hasNext();) {
                Folio folioActual = (Folio)iteradorFolios.next();
                matriculas.add(folioActual.getIdMatricula());
            }

            boolean esValido = false;

            try {
                esValido = forseti.validarMatriculas(validaciones, matriculas);
            } catch (ForsetiException fe) {
                String matricula = ((Folio)folios.get(0)).getIdMatricula();
                Map hashErrores = fe.getHashErrores();
                List errores = (List)hashErrores.get(matricula);
                StringBuffer mensaje = new StringBuffer();

                mensaje.append("Para la matricula " + matricula + " se encontraron los siguientes errores:\n\r");

                for(Iterator iteradorErrores = errores.iterator(); iteradorErrores.hasNext();) {
                    mensaje.append(iteradorErrores.next());
                    if(iteradorErrores.hasNext())
                        mensaje.append("; ");
                }

                throw new EventoException(mensaje.toString(), fe);
            } catch (Throwable e) {
                throw new EventoException(e.getMessage(), e);
            }

            if(!esValido)
                throw new EventoException("El folio especificado no puede ser trasladado");
            
            try {
            	Folio folio = (Folio)folios.get(0);
            	List anotaciones = folio.getAnotaciones();
            	if (anotaciones == null || anotaciones.isEmpty())
            	{
            		FolioPk fpk = new FolioPk();
            		fpk.idMatricula = folio.getIdMatricula();
            		anotaciones = forseti.getAnotacionesFolio(fpk, CCriterio.TODAS_LAS_ANOTACIONES, null, 0, 1, false);
            		folio.setAnotaciones(anotaciones);
            	}
            } catch (Throwable fe)
            {
            	throw new EventoException(fe.getMessage(), fe);
            }

			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.CONSULTA_TRASLADO_OK);
// necesita la primera anotacion
		} else {
			eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.VER_FOLIO);
                        // BUG 5778
                        FolioPk fid = new FolioPk();
                        fid.idMatricula = ((Folio)folios.get(0)).getIdMatricula();
                        try {
                        	Folio folio = ((Folio)folios.get(0));
                                /**
                                * @author     : Carlos Torres
                                * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                */
                                Traslado tr = hermod.getFolioDestinoTraslado(folio.getIdMatricula());
                                folios.add(tr);
                        	List anotaciones = folio.getAnotaciones();
                        	if (anotaciones == null || anotaciones.isEmpty())
                        	{
                        		anotaciones = forseti.getAnotacionesFolio(fid);
                        		folio.setAnotaciones(anotaciones);
                        	}
                          eventoRespuesta.setFoliosHijo(forseti.getFolioHijosEnAnotacionesConDireccion(
                              fid));
                          eventoRespuesta.setFoliosPadre(forseti.getFoliosPadre(fid));
                        } catch (Throwable e) {
                          throw new EventoException(e.getMessage(), e);
                        }
		}
		return eventoRespuesta;
	}

	/**
	 * Valida la existencia de una Matrícula en el sistema
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta validarFolio(EvnConsulta evento) throws EventoException {
		SolicitudConsulta solicitud = null;
		SolicitudFolio solFolio = null;

		try {
			Folio folio = forseti.getFolioByMatricula(evento.getMatriculaInmobiliaria());
//ok sin anotaciones
			if (folio == null) {
				//throw new FolioNoEncontradoException("No fue posible obtener el folio");
				solicitud = evento.getSolicitudConsulta();
			} else {
				solFolio = new SolicitudFolio();
				solFolio.setFolio(folio);
				solicitud =
					(SolicitudConsulta) hermod.addFolioToSolicitudConsulta(
						evento.getSolicitudConsulta(),
						solFolio);
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespConsulta eventoRespuesta = null;
		eventoRespuesta = new EvnRespConsulta(EvnRespConsulta.VALIDACION_FOLIO_OK);
		eventoRespuesta.setSolicitudConsulta(solicitud);
		return eventoRespuesta;
	}

	/**
	 * Consulta de folios utilizada en el proceso de calificación.
	 * (Valida si el folio se encuentra bloqueado por otras fases o en estados inválidos
	 * para avanzar el proceso.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta consultarFolioCalificacion(EvnConsulta evento) throws EventoException {
		List folios = new Vector();
		Fase fase = evento.getFase();
		
		List foliosDerivadoPadre = null;
		List foliosDerivadoHijo = null;
		
		if (fase == null) {
			fase = new Fase();
		}
		
		
		try {
			FolioPk folioID = new FolioPk();
			folioID.idMatricula = evento.getFolio().getIdMatricula();

			Folio folio = null;
			try {
				folio = forseti.getFolioByIDSinAnotaciones(folioID, evento.getUsuarioNeg());
			} catch (ForsetiException e) {
				folio = forseti.getFolioByIDSinAnotaciones(folioID, null);
			}
			
			//Se obtiene la lista de los folios Padres de un Folio.
			foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(folioID);
			foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(folioID);

			if (folio != null) {
				folios.add(folio);
			} else {
				if (fase.getID().equals(CFase.CAL_CALIFICACION)) {
					throw new ConsultaCalificacionFolioException(
						"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
				} else if (fase.getID().equals(CFase.COR_CORRECCION)) {
					throw new ConsultaCorreccionFolioException(
						"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
				} else if (fase.getID().equals(CFase.COR_USUARIO_ESPECIALIZADO)) {
					throw new ConsultaFolioEspecializadoException(
						"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
				} else {
					throw new EventoException(
						"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
				}
			}

		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			if (fase.getID().equals(CFase.CAL_CALIFICACION)) {
				throw new ConsultaCalificacionFolioException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else if (fase.getID().equals(CFase.COR_CORRECCION)) {
				throw new ConsultaCorreccionFolioException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else if (fase.getID().equals(CFase.COR_USUARIO_ESPECIALIZADO)) {
				throw new ConsultaFolioEspecializadoException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else {
				throw new EventoException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
			}
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			if (fase.getID().equals(CFase.CAL_CALIFICACION)) {
				throw new ConsultaCalificacionFolioException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else if (fase.getID().equals(CFase.COR_CORRECCION)) {
				throw new ConsultaCorreccionFolioException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else if (fase.getID().equals(CFase.COR_USUARIO_ESPECIALIZADO)) {
				throw new ConsultaFolioEspecializadoException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
					e);
			} else if (fase.getID().equals(CFase.COR_CORRECCION_SIMPLE)) {
				throw new ConsultaFolioEspecializadoException(
						"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula(),
						e);
			} else {
				throw new EventoException(
					"No fue posible obtener el folio :" + evento.getFolio().getIdMatricula());
			}
		}

		if (fase != null && fase.getID().equals(CFase.COR_CORRECCION_SIMPLE))
		{
			Turno turno = evento.getTurno();
			Solicitud solicitud = null;
			solicitud = turno.getSolicitud();
				
			
			if (solicitud != null)
			{
				List solFolios = solicitud.getSolicitudFolios();
				
				// se intenta obtener el listado de folios
				if (solFolios == null || solFolios.isEmpty())
				{
					try {
						solicitud = hermod.getSolicitudById(turno.getSolicitud().getIdSolicitud());
					} catch (Throwable e) {
						throw new ConsultaCorreccionFolioException(
								"Error consultando la solicitud");
					}
					if (solicitud != null)
					{
						solFolios = solicitud.getSolicitudFolios();
						if (solFolios == null || solFolios.isEmpty())
						{
							throw new ConsultaCorreccionFolioException(
							"Error consultando la solicitud");
						}
					} else {
						throw new ConsultaCorreccionFolioException(
							"Error consultando la solicitud");
					}
				}
				
				boolean existeMatInTurno = false;
				for (Iterator solFolItera = solFolios.iterator();
						solFolItera.hasNext();)
				{
					SolicitudFolio solFolio = (SolicitudFolio)solFolItera.next();
					if (solFolio.getIdMatricula().equals(evento.getFolio().getIdMatricula()))
					{
						existeMatInTurno = true;
						break;
					}
				}
				
				if (!existeMatInTurno)
				{
					throw new ConsultaCorreccionFolioException(
						"La matricula " + evento.getFolio().getIdMatricula() +  " no está asocia al turno");
				}
			}
		}
		
		EvnRespConsulta eventoRespuesta = null;
		eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.CONSULTA_FOLIO_CALIFICACION);
		eventoRespuesta.setFoliosDerivadoPadre(foliosDerivadoPadre);
		eventoRespuesta.setFoliosDerivadoHijo(foliosDerivadoHijo);
		return eventoRespuesta;
	}




	/**
	 * Este metodo hace el llamado al negocio para que imprima un certificado
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCertificado</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCertificado</CODE>
	 */
	private EvnRespConsulta imprimir(EvnConsulta evento)
		throws ServicioNoEncontradoException, ImpresionNoEfectuadaException, EventoException {

		Hashtable tabla = new Hashtable();
		Turno turno = evento.getTurno();
		TurnoPk tid = new TurnoPk();
		tid.anio = turno.getAnio();
		tid.idCirculo = turno.getIdCirculo();
		tid.idProceso = turno.getIdProceso();
		tid.idTurno = turno.getIdTurno();

		Fase fase = evento.getFase();
		Estacion estacion = evento.getEstacion();
		int idImprimible = 0;

		try {
			Turno turnoAux = hermod.getTurno(tid);
			tabla.put("ESTACION", estacion.getEstacionId());
			tabla.put(Processor.RESULT, "CONFIRMAR");

			SolicitudConsulta solicitudConsulta = (SolicitudConsulta) turno.getSolicitud();
			solicitudConsulta = (SolicitudConsulta) hermod.getSolicitudById(solicitudConsulta.getIdSolicitud());

			String fechaImpresion = this.getFechaImpresion();
			if (solicitudConsulta.getTipoConsulta().getNombre().equals(TipoConsulta.CONSTANCIA)){
				ImprimibleConstanciaNoPropiedad iNop = new ImprimibleConstanciaNoPropiedad(turnoAux,solicitudConsulta,CTipoImprimible.CONSULTA);
				iNop.setUsuario(evento.getUsuario().getUsuarioId());
				
				iNop.setCiudadano(evento.getCiudadano());
				
				iNop.setEsPropietario(esPropietario(turno, evento.getCiudadano()));
				
				String cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_PRINCIPAL;

				FirmaRegistrador firmaRegistrador = null;

				String sNombre = "";
				String archivo = "";
				String cargoToPrint = "";
				String rutaFisica = null;

			    try
			    {
			    	CirculoPk cid = new CirculoPk();
			    	
			    	if (turno.isNacional()&& solicitudConsulta.getSolicitudFolios()!=null && solicitudConsulta.getSolicitudFolios().size()>0)
			    	{
			    		cid.idCirculo = ((SolicitudFolio)solicitudConsulta.getSolicitudFolios().get(0)).getFolio().getCirculo(); 
			    	}else{
			    		cid.idCirculo = turnoAux.getIdCirculo();
			    	}
					firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);

					if(firmaRegistrador==null){
						cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_DELEGADO;
						firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
					}
					if(firmaRegistrador==null){
						cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL;
						firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
					}
					if(firmaRegistrador==null){
						cargo =  CFirmaRegistrador.CARGO_REGISTRADOR_SECCIONAL_DELEGADO;
						firmaRegistrador =  forseti.getFirmaRegistradorActiva(cid,cargo);
					}

					rutaFisica =  hermod.getPathFirmasRegistradores();

					sNombre = firmaRegistrador.getNombreRegistrador();
					archivo = firmaRegistrador.getIdArchivo();
					
					if(turno.isNacional()){
						if(firmaRegistrador==null || archivo.toUpperCase().equals("SINFIRMA.GIF") || archivo.toUpperCase().equals("SINFIRMA.JPG")
								|| archivo==null || archivo.equals("")){
							throw new Exception("No se permite la impresion de constancia de no propiedad de orden nacional sin firma");
						}
					}
					
					//Se recupera el verdadero cargo para definir si es ENCARGADO o
					//no lo es.
					cargo = firmaRegistrador.getCargoRegistrador();

					//Se saca el valor del cargo para imprimirlo en el certificado
					List cargos = hermod.getOPLookupCodes(COPLookupTypes.CARGOS_REGISTRADOR);

					cargoToPrint = "";
					OPLookupCodes lookUp;
					for(Iterator it = cargos.iterator(); it.hasNext();){
						lookUp = (OPLookupCodes) it.next();
						if(lookUp.getCodigo().equals(cargo)){
							cargoToPrint = lookUp.getValor();
						}
					}
			    }
			    catch(Exception e)
			    {
			    	throw e;
			    }
			    catch(Throwable t)
			    {
			    	t.printStackTrace();
			    }

				if (sNombre==null)
				  sNombre="";


				iNop.setCargoRegistrador(cargoToPrint);
				iNop.setNombreRegistrador(sNombre);


				if(rutaFisica!=null)
				{
					BufferedImage imagenFirmaRegistrador=getImage(rutaFisica,archivo);

					byte pixeles[]=null;
					try
					{
						pixeles = UIUtils.toJpeg(imagenFirmaRegistrador);
					}
					catch (Throwable e1) {

						e1.printStackTrace();
					}
					iNop.setPixelesImagenFirmaRegistrador(pixeles);
				}
				try{
		            idImprimible = this.imprimir(iNop, evento.getUID());
				}catch (PrintJobsException pe) {
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + pe.getMessage(),turno.getIdWorkflow());
				} catch(Exception e){
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + e.getMessage(),turno.getIdWorkflow());
				}
			}else{
				ImprimibleConsulta iRec = new ImprimibleConsulta(turnoAux, fechaImpresion,CTipoImprimible.CONSULTA);
				if(turnoAux.isNacional()){
					iRec.setIdUsuario(evento.getUsuario().getUsuarioId());
					iRec.setCirculo(evento.getCirculo());
				}			
				iRec.setTamanoCarta(false);

	                        // Bug 0003385: manejar impresion opcional en el
	                        // caso de consultas exentas: Imprimible Resultado Consultas Exentas

	                        boolean enabled_SolicitudConsultasTipoConsultaExenta;
	                        boolean enabled_SolicitudConsultasTipoConsultaExentaPrint;
	                        enabled_SolicitudConsultasTipoConsultaExenta      = evento.isEnabled_SolicitudConsultasTipoConsultaExenta();
	                        enabled_SolicitudConsultasTipoConsultaExentaPrint = evento.isEnabled_SolicitudConsultasTipoConsultaExentaPrint();

	                        boolean printEnabled = false;

	                        // en el caso de consultas exentas solo se imprime
	                        // si seleccionaron la opcion

	                        if( !enabled_SolicitudConsultasTipoConsultaExenta ) {
	                           printEnabled = true;
	                        }
	                        else {
	                           if( enabled_SolicitudConsultasTipoConsultaExentaPrint ) {
	                              printEnabled = true;
	                           } // if
	                        } // if


	                        // print block
				try{
	              if( printEnabled ) {
	                idImprimible = this.imprimir(iRec, evento.getUID());
	              }
				}
				catch (PrintJobsException pe) {
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + pe.getMessage(),turno.getIdWorkflow());
				} catch(Exception e){
					throw new ErrorImpresionException("No se pudo imprimir el resultado de la consulta: " + e.getMessage(),turno.getIdWorkflow());
				}
			}

			//hermod.avanzarTurno(turno, fase, tabla,CAvanzarTurno.AVANZAR_NORMAL);
			hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNeg());
			if (((SolicitudConsulta)turno.getSolicitud()).getTipoConsulta().getIdTipoConsulta().equals(TipoConsulta.TIPO_SIMPLE)){
				fase.setID(CFase.CON_ENTREGAR_SIMPLE);
				turno.setIdFase(CFase.CON_ENTREGAR_SIMPLE);
				hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNeg());	
			}

		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		return new EvnRespConsulta(EvnRespConsulta.IMPRESION_OK,idImprimible);
	}


	/**
	 * @param imprimible
	 * @param uid
	 */
	private int imprimir(ImprimibleBase imprimible, String uid) throws EventoException ,PrintJobsException{


		//OBTENER LOS PARAMETROS DE IMPRESION DESDE LA BASE DE DATOS.
		int maxIntentos;
		int espera;


		//INGRESO DE INTENTOS DE IMPRESION
		try
		{

			String intentosImpresion = hermod.getNumeroIntentosImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer intentos = new Integer (intentosImpresion);
				 maxIntentos = intentos.intValue();
			}
			else
			{
				 Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
				 maxIntentos = intentosDefault.intValue();
			}

			//INGRESO TIEMPO DE ESPERA IMPRESION
			String esperaImpresion = hermod.getTiempoEsperaImpresion(CImpresion.IMPRIMIR_RECIBO);
			if (intentosImpresion != null)
			{
				 Integer esperaInt = new Integer(esperaImpresion);
				 espera = esperaInt.intValue();
			}
			else
			{
				Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
				espera = esperaDefault.intValue();
			}
		}
		catch (Throwable t)
		{
			Integer intentosDefault = new Integer (CImpresion.DEFAULT_INTENTOS_IMPRESION);
			maxIntentos = intentosDefault.intValue();

			Integer esperaDefault = new Integer (CImpresion.DEFAULT_ESPERA_IMPRESION);
			espera = esperaDefault.intValue();


		}


		Bundle bundle = new Bundle(imprimible);


		int idImprimible = 0;
		try {
			//se manda a imprimir el recibo por el identificador unico de usuario
			printJobs.enviar(uid, bundle, maxIntentos, espera);
		}catch (PrintJobsException t) {
			idImprimible = t.getIdImpresion();
			if(idImprimible == 0){
				throw new EventoException("Se genero una excepción al imprimir la Nota. " + t.getMessage());
			}
		} catch (Throwable t) {
			t.printStackTrace();
			//throw new PrintJobsException(t.getMessage());
		}

		return idImprimible;

	}



	/**
	 * Avanza un turno según el tipo de respuesta que viene especificada en el evento
	 * (Puede ser confirmar, negar.
	 * @param evento
	 * @return
	 * @throws ServicioNoEncontradoException
	 * @throws ImpresionNoEfectuadaException
	 * @throws EventoException
	 */
	private EvnRespConsulta avanzaTurno(EvnConsulta evento)
		throws ServicioNoEncontradoException, ImpresionNoEfectuadaException, EventoException {

		Turno turno = evento.getTurno();
		Fase fase = evento.getFase();
		Estacion estacion = evento.getEstacion();

		Hashtable tabla = new Hashtable();

		try {
			tabla.put(Processor.RESULT, evento.getRespuestaWF());
			hermod.avanzarTurnoNuevoNormal(turno,fase,tabla,evento.getUsuarioNeg());
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		return null;
	}

	/**
	 * Método encargado de adicionar un Ciudadano a una Solicitud de consulta.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta adicionaCiudadanoASolicitud(EvnConsulta evento) throws EventoException {

		SolicitudConsulta solicitud = null;
		try {
			SolicitudConsulta sol = evento.getSolicitudConsulta();
			int numMax = sol.getNumeroMaximoBusquedas();
			hermod.setNumMaxBusquedasToSolicitudConsulta(sol, numMax);
			Ciudadano ciudadano = evento.getCiudadano();
			if (ciudadano != null) {
				solicitud = (SolicitudConsulta) hermod.addCiudadanoToSolicitudConsulta(sol, ciudadano);
			} else {
				solicitud = sol;
			}

			if (solicitud == null) {
				throw new FolioNoEncontradoException("No fue posible obtener el folio", null);
			}
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespConsulta eventoRespuesta = new EvnRespConsulta(EvnRespConsulta.CIUDADANO_ADICIONADO_OK);
		eventoRespuesta.setSolicitudConsulta(solicitud);
		return eventoRespuesta;
	}

	/**
	 * Método encargado de consultar los detalles del folio en el turno
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta consultarDetalleTurno(EvnConsulta evento) throws EventoException {

		Folio folio=evento.getFolio();
		Usuario usuarioBloqueo=null;
		Turno turnoTramite=null;
		Turno turnoDeuda=null;
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioNeg();
		try {
			FolioPk id=new FolioPk();
			id.idMatricula=folio.getIdMatricula();
			folio = forseti.getFolioByIDSinAnotaciones(id);

			//obtener informacion tramite

			turnoTramite=forseti.getTurnoTramiteFolio(id.idMatricula);

			//obtener informacion bloqueo

			usuarioBloqueo=forseti.getBloqueoFolio(id);

			//obtener informacion de deuda de dineros

			turnoDeuda=forseti.getTurnoDeudaFolio(id);

			//aqui se cosultara la demas informacion que se necesite.
		} catch (HermodException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class,e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}

		EvnRespConsulta eventoRespuesta = new EvnRespConsulta(folio, turnoTramite, usuarioBloqueo, turnoDeuda, EvnRespConsulta.CONSULTAR_FOLIO);
		return eventoRespuesta;
	}

	/**
	 * Metodo que retorna la cadena con la fecha actual de impresión.
	 * @return
	 */
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

	/**
	 * Metodo que retorna un numero con un "0" antes en caso de ser menor
	 * que 10.
	 * @param i el numero.
	 * @return
	 */
	protected String formato(int i) {
		if (i < 10) {
			return "0" + (new Integer(i)).toString();
		}
		return (new Integer(i)).toString();
	}
	
	public static BufferedImage getImage(String path, String nombreArchivo)
	{
		String nombreCompleto = getNombreCompleto(path,nombreArchivo);
		BufferedImage buf = null;

		try
		{
			File file = new File(nombreCompleto);
			buf = ImageIO.read(file);
		}
		catch (IOException e)
		{
			Log.getInstance().error(ANConsulta.class, e);
			Log.getInstance().error(ANConsulta.class, "Error imprimiendo el gráfico en la ruta: "+nombreCompleto);
		}

		return buf;
	}
	
	public static String getNombreCompleto(String path, String nombreArchivo)
	{

		String nombreCompleto=null;

		if (!path.trim().equals(CHermod.CADENA_VACIA))
		  nombreCompleto = path + nombreArchivo;
		else
		  nombreCompleto = nombreArchivo;

	  return nombreCompleto;
	}
	
	private boolean esPropietario(Turno turno, Ciudadano ciud) throws EventoException{
		List solfolios = turno.getSolicitud().getSolicitudFolios();
		try{
			Folio folio;
			Iterator iter = solfolios.iterator();
			while(iter.hasNext()){
				String matricula = (String)((SolicitudFolio)iter.next()).getIdMatricula();
				FolioPk fid = new FolioPk();
				fid.idMatricula = matricula;
				
				List anotaciones = forseti.getAnotacionesFolio(fid);
				Iterator it = anotaciones.iterator();
				while (it.hasNext()){
					List anotaCiudadanos = ((Anotacion)it.next()).getAnotacionesCiudadanos();
					for(int i=0; i<anotaCiudadanos.size();i++){
						AnotacionCiudadano aCiudadano = (AnotacionCiudadano)anotaCiudadanos.get(i);
						if (estaCiudadano(aCiudadano,ciud)){
							if(forseti.isUltimoPropietario(aCiudadano)){
								return true;
							}
						}
					}
				}
			}
		} catch (ForsetiException e) {
			Log.getInstance().error(ANConsulta.class, e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			Log.getInstance().error(ANConsulta.class, e.getMessage(), e);
			throw new EventoException(e.getMessage(), e);
		}
		return false;
	}
	
	private boolean estaCiudadano(AnotacionCiudadano aCiud, Ciudadano ciud){
		Ciudadano aux = aCiud.getCiudadano();

        /**
         *@author: Guillermo Cabrera.
         * @change: Se adiciona la condición aux.getApellido1()!=null en cada if para evitar que se haga el equals()
         * cuando el apellido1 tenga valor nulo. ya que al realizar esta comparación se presenta un nullpointerexception.
         * CASO MANTIS 2818
         */
		if(aux.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_SECUENCIA) && aux.getApellido1()!=null && aux.getApellido1().equals(ciud.getApellido1()))
			return true;
		
		if(aux.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_NIT) && aux.getApellido1()!=null && aux.getApellido1().equals(ciud.getApellido1()))
			return true;
		
		
		if(aux.getTipoDoc().equals(ciud.getTipoDoc()) && aux.getDocumento().equals(ciud.getDocumento())
				&& aux.getNombre().equals(ciud.getNombre()) && aux.getApellido1()!=null && aux.getApellido1().equals(ciud.getApellido1()))
			return true;
		
		if(aux.getApellido1()!=null && aux.getApellido1().equals(ciud.getApellido1())
				&& aux.getNombre().equals(ciud.getNombre()))
			return true;
		
		return false;
	}
}
