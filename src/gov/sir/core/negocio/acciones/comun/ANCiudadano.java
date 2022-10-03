package gov.sir.core.negocio.acciones.comun;

import gov.sir.core.eventos.comun.EvnCiudadano;
import gov.sir.core.eventos.comun.EvnRespCiudadano;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosHTException;
import gov.sir.core.negocio.modelo.Ciudadano;
import gov.sir.core.negocio.modelo.CiudadanoPk;
import gov.sir.core.negocio.modelo.constantes.CCiudadano;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

/**
 * @author eacosta, ppabon
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnCiudadano</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespCiudadano</CODE> con
 * la consulta del ciudadano que se desea consultar.
 */
public class ANCiudadano extends SoporteAccionNegocio {
	
	
	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;
	
	public ANCiudadano() throws EventoException{
		super();
		service = ServiceLocator.getInstancia();

		try {
			forseti = (ForsetiServiceInterface) service.getServicio(
					"gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException(
					"Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException(
				"Error instanciando el servicio forseti");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException(
				"Servicio forseti no encontrado");
		}
	}

	/**
	* Recibe un evento de <code>EvnCiudadano</code> y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCiudadano</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCiudadano</CODE>
	* @see gov.sir.core.eventos.comun.EvnLiquidacion
	* @see gov.sir.core.eventos.comun.EvnRespLiquidacion
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnCiudadano evento = (EvnCiudadano) e;

		if (evento==null || evento.getTipoEvento()==null){
			return null;
		}
		
		if (evento.getTipoEvento().equals(EvnCiudadano.CONSULTAR)) {
			return consultar(evento);
		} 
		else if (evento.getTipoEvento().equals(EvnCiudadano.CONSULTAR_CIUDADANO_ANOTACION)) {
			return consultarCiudadanoAnotacion(evento);
		}                      /*
        * @author : CTORRES
        * @change : condicion para realizar consulta de testador
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                else if (evento.getTipoEvento().equals(EvnCiudadano.CONSULTAR_TESTADOR)) {
			return consultarTestador(evento);
		}
		
		return null;
	}
	
	/**
	 * Este método hace el llamado a los servicios para consultar al ciudadano con el identificador que
	 * viene en el evento.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCiudadano</CODE> 
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCiudadano</CODE>
	 */
	private EventoRespuesta consultar(EvnCiudadano evento) throws EventoException {
		
		EvnRespCiudadano evRespuesta = null;
		Ciudadano ciudadano = evento.getCiudadano();
		Ciudadano ciudadanoRta = null;
		boolean mostrarCiudadano = true;
			
		try {
			if (ciudadano!=null && ciudadano.getTipoDoc()!=null && ciudadano.getDocumento()!=null){
				ciudadanoRta = forseti.getCiudadanoByDocumentoSolicitante(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
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
			Log.getInstance().error(ANCiudadano.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}

		return evRespuesta;
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
		CiudadanoTMP ciudadanoTmp = null;
 		boolean mostrarCiudadano = true;
		boolean isRegistro = false;
		boolean isCorreciones = false;
		boolean isAdministrativa = false;
		
			
		try {
			if (ciudadano!=null && ciudadano.getTipoDoc()!=null && ciudadano.getDocumento()!=null){
				ciudadanoRta = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
				
				
				if(ciudadanoRta!=null){
					CiudadanoPk idCiudadano = new CiudadanoPk();
					idCiudadano.idCiudadano = ciudadanoRta.getIdCiudadano();
					mostrarCiudadano = forseti.isCiudadanoInAnotacionDefinitiva(idCiudadano);
                                        
                                        if(ciudadanoRta.getTipoDoc().equals(CCiudadano.TIPO_DOC_ID_NIT)){
                                            String razonSocial = (ciudadanoRta.getApellido1()!=null?ciudadanoRta.getApellido1():ciudadanoRta.getNombre());
                                            ciudadanoRta.setNombre(razonSocial);
                                            ciudadanoRta.setApellido1(null);
                                        }
				}
				
				isRegistro = evento.isRegistro();
				isCorreciones = evento.isCorrecciones();
				isAdministrativa = evento.isAdministrativa();
				
				// Si el turno es de correciones lo que se hace es colocar el temporal si existe
				if (isCorreciones) {
					ciudadanoTmp = forseti.getCiudadanoTmpByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
					//TFS 4570, se retorna el ciudadano temporal si este fue creado con el turno
					//si se consulta con otro turno, se retornará el definitivo
					if(ciudadanoTmp!=null && evento.getTurno()!=null){
						if(ciudadanoTmp.getNumeroRadicacion().
								equals(evento.getTurno().getIdWorkflow())){
							if(ciudadanoRta == null){
								ciudadanoRta = new Ciudadano();
							}
							ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
							ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
							ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
                                                        ciudadanoRta.setSexo(ciudadanoTmp.getSexo());
							ciudadanoRta.setNombre(ciudadanoTmp.getNombre());
						}
					}else if (ciudadanoTmp != null && ciudadanoRta!=null) {
						ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
						ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
						ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
						ciudadanoRta.setNombre(ciudadanoTmp.getNombre());					
                                                ciudadanoRta.setSexo(ciudadanoTmp.getSexo());
					} else {
						if (ciudadanoRta == null && ciudadanoTmp != null) {
							ciudadanoRta = new Ciudadano();
							ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
							ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
							ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
							ciudadanoRta.setNombre(ciudadanoTmp.getNombre());
                                                        ciudadanoRta.setSexo(ciudadanoTmp.getSexo());
						}
					}
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
			Log.getInstance().error(ANCiudadano.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}

		return evRespuesta;
	}
                              /*
        * @author : CTORRES
        * @change : Nuevo metodo consultarTestador
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        /**
	 * Este método hace el llamado a los servicios para consultar al ciudadano en testamento con el identificador que
	 * viene en el evento.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnCiudadano</CODE> 
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespCiudadano</CODE>
	 */
	private EventoRespuesta consultarTestador(EvnCiudadano evento) throws EventoException {
		
		EvnRespCiudadano evRespuesta = null;
		Ciudadano ciudadano = evento.getCiudadano();
		Ciudadano ciudadanoRta = null;
		CiudadanoTMP ciudadanoTmp = null;
		boolean mostrarCiudadano = true;
		boolean isRegistro = false;
		boolean isCorreciones = false;
		boolean isAdministrativa = false;
                boolean testadorEncontrador = false;
                boolean mostrarTestador = false;
		
			
		try {
			if (ciudadano!=null && ciudadano.getTipoDoc()!=null && ciudadano.getDocumento()!=null){
				ciudadanoRta = forseti.getCiudadanoByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
				
				
				if(ciudadanoRta!=null){
					CiudadanoPk idCiudadano = new CiudadanoPk();
					idCiudadano.idCiudadano = ciudadanoRta.getIdCiudadano();
					mostrarCiudadano = forseti.isCiudadanoInAnotacionDefinitiva(idCiudadano);
				}
				
				isRegistro = evento.isRegistro();
				isCorreciones = evento.isCorrecciones();
				isAdministrativa = evento.isAdministrativa();
				
				// Si el turno es de correciones lo que se hace es colocar el temporal si existe
				if (isCorreciones) {
					ciudadanoTmp = forseti.getCiudadanoTmpByDocumento(ciudadano.getTipoDoc(), ciudadano.getDocumento(), ciudadano.getIdCirculo());
					//TFS 4570, se retorna el ciudadano temporal si este fue creado con el turno
					//si se consulta con otro turno, se retornará el definitivo
					if(ciudadanoTmp!=null && evento.getTurno()!=null){
						if(ciudadanoTmp.getNumeroRadicacion().
								equals(evento.getTurno().getIdWorkflow())){
							if(ciudadanoRta == null){
								ciudadanoRta = new Ciudadano();
							}
							ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
							ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
							ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
							ciudadanoRta.setNombre(ciudadanoTmp.getNombre());
                                                        
						}
					}else if (ciudadanoTmp != null && ciudadanoRta!=null) {
						ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
						ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
						ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
						ciudadanoRta.setNombre(ciudadanoTmp.getNombre());					
					} else {
						if (ciudadanoRta == null && ciudadanoTmp != null) {
							ciudadanoRta = new Ciudadano();
							ciudadanoRta.setIdCiudadano(ciudadanoTmp.getIdCiudadanoTmp());
							ciudadanoRta.setApellido1(ciudadanoTmp.getApellido1());
							ciudadanoRta.setApellido2(ciudadanoTmp.getApellido2());
							ciudadanoRta.setNombre(ciudadanoTmp.getNombre());	
						}
					}
				}
					
				
			} 
			
			if (ciudadanoRta!=null){
				evRespuesta = new EvnRespCiudadano(ciudadanoRta);
				evRespuesta.setCiudadanoEncontrado(true);
				evRespuesta.setMostrarCiudadano(mostrarCiudadano);
                                evRespuesta.setTipoEvento("CONSULTAR_TESTADOR_OK");
       			} else {
				evRespuesta = new EvnRespCiudadano(ciudadano);
				evRespuesta.setCiudadanoEncontrado(false);
				evRespuesta.setMostrarCiudadano(false);
                                evRespuesta.setTipoEvento("CONSULTAR_TESTADOR_OK");
			}

		} catch (ForsetiException e) {
			throw new ValidacionParametrosHTException(e);
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCiudadano.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage());
		}

		return evRespuesta;
	}
        
}
