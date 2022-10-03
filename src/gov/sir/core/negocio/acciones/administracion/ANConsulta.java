package gov.sir.core.negocio.acciones.administracion;

import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import gov.sir.core.eventos.administracion.EvnConsulta;
import gov.sir.core.eventos.administracion.EvnRespConsulta;
import gov.sir.core.negocio.acciones.excepciones.ANConsultaException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Busqueda;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.ResultadoFolio;
/* @autor          : JATENCIA 
 * @mantis         : 0014985 
 * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas  
 * @descripcion    : Se incluye el objeto traslado, para poder utilizar la función.
 */
import gov.sir.core.negocio.modelo.Traslado;
/* Fin del bloque*/
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;

import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.List;
import java.util.Vector;

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
			
			//2. GENERAR NUEVAS BUSQUEDAS
			if (evento.getTipoEvento().equals(EvnConsulta.NUEVA_BUSQUEDA)) { // SI SE UTILIZA
				return generarNuevaBusqueda(evento);
			}
			
			//4. CONSULTAR FOLIO
			if (evento.getTipoEvento().equals(EvnConsulta.FOLIO)) {
				return consultarFolio(evento);
			}
			
			return null;
			
			
		}catch (Throwable t) {
			throw new EventoException (t.getMessage(),t);
		}
		
	}
	
	//Depurado Enero 11 2006
	/**
	 * Método que adiciona una búsqueda a una solicitud de consultas ya existente.
	 * @param evento el evento recibido. Este evento debe ser del tipo <CODE>EvnConsulta</CODE>
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsulta</CODE>
	 * @throws ANConsultaException
	 */
	private EvnRespConsulta generarNuevaBusqueda(EvnConsulta evento) throws EventoException {
		
	
		//3. Atender la consulta (Servicio Forseti)
		List folios = new Vector();
		//List busquedas = evento.getBusqueda();
		
		Busqueda busqueda = null;
		
		Busqueda busquedaCreada = evento.getBusqueda(); //última búsqueda
		gov.sir.core.negocio.modelo.BusquedaPk bid = new gov.sir.core.negocio.modelo.BusquedaPk();
		bid.idBusqueda = busquedaCreada.getIdBusqueda();
		bid.idSolicitud = busquedaCreada.getIdSolicitud();
                
                System.out.println("BUSQUEDA CREADA ID "+busquedaCreada.getIdBusqueda()+" SOLICITUD "+busquedaCreada.getIdSolicitud());
		
		try {
			busqueda = forseti.atenderConsultaAdministracion(busquedaCreada);
			if (busqueda == null) {
				throw new EventoException("La consulta solicitada no pudo ser realizada", null);
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
		
		
		
		//4. Generar un evento de respuesta con el resultado de la consulta.
		EvnRespConsulta eventoRespuesta = null;
		
		eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.RESULTADO_CONSULTA);
		
		eventoRespuesta.setUltimaBusqueda(busqueda);
		
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
				if(folio.getAnotaciones()==null||folio.getAnotaciones().isEmpty()){
					FolioPk folioPk = new FolioPk();
					folioPk.idMatricula = folio.getIdMatricula();
					List anotaciones = forseti.getAnotacionesFolio(folioPk);
					folio.setAnotaciones(anotaciones);
				}
                                /* @autor          : JATENCIA 
                                 * @mantis         : 0014985 
                                 * @Requerimiento  : 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas  
                                 * @descripcion    : Llamado de la función que organiza y visualiza la información de traslado
                                 *                   del folio.
                                 */
                                Traslado tr = hermod.getFolioDestinoTraslado(folio.getIdMatricula());                             
				folios.add(folio);
                                folios.add(tr);
                                /* Fin del bloque */
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
		eventoRespuesta = new EvnRespConsulta(folios, EvnRespConsulta.VER_FOLIO);
		return eventoRespuesta;
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
	
}
