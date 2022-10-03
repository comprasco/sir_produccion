package gov.sir.core.negocio.acciones.registro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.negocio.acciones.excepciones.CopiaAnotacionException;
import gov.sir.core.negocio.acciones.excepciones.CopiaAnotacionHTException;

import gov.sir.core.eventos.registro.EvnCopiaAnotacion;
import gov.sir.core.eventos.registro.EvnRespCopiaAnotacion;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.CopiaAnotacion;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.radicacionAnotacionComparator;
//import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;


/**
 * Clase encargada de la copia de una anotación a otros folios, en la capa de negocio. 
 * @author ppabon
*/
public class ANCopiaAnotacion extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANEnglobe.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *de los servicio que necesita.
	 */
	public ANCopiaAnotacion() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnCopiaAnotacion evento = (EvnCopiaAnotacion) ev;

		if (evento.getTipoEvento().equals(EvnCopiaAnotacion.COPIAR_ANOTACION)) {
			return copiarAnotacion(evento);
		} else if(evento.getTipoEvento().equals(EvnCopiaAnotacion.CANCELADA)) {
			return copiarCancelada(evento);
		} else if(evento.getTipoEvento().equals(EvnCopiaAnotacion.CORREGIR_CANCELADA)) {
			return corregirCancelada(evento);
		}
		return null;
	}

	/**
	 * Método para copiar una anotación, si es una anotación cancelador, se devuelve un evento para que el usuario
	 * ingrese la anotación que desea cancelarse. 
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta copiarAnotacion(Evento ev) throws EventoException {
		EvnCopiaAnotacion evento = (EvnCopiaAnotacion) ev;
		List copiadas = new ArrayList();
		
		CopiaAnotacion copiaAnotacion = evento.getCopiaAnotacion();
		Anotacion anotacionOrigen = copiaAnotacion.getAnotacionOrigen();
		Folio folioOrigen = copiaAnotacion.getFolioOrigen();
		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		boolean copiarComentario = copiaAnotacion.isCopiaComentario();
	 
		Usuario usuarioSIR = evento.getUsuarioSIR();
		Turno turno = evento.getTurno();
		List anotaciones = null;
	

		//SE CONSIGUE CUÁL ES EL FOLIO QUE TIENE LA ANOTACIÓN QUE DESEA COPIARSE
		Folio folioTemp = null;
		try {
				
			Folio folio = copiaAnotacion.getFolioOrigen();
			String zonaRegistral = forseti.getZonaRegistral(folio.getIdMatricula());
	
			FolioPk id = new FolioPk();
			id.idMatricula = folio.getIdMatricula();
			
			folioTemp = forseti.getFolioByID(id);
			
			if(folioTemp == null){
				folioTemp = forseti.getFolioByID(id, usuarioSIR);
			}

			//SE SACA DEL FOLIO LA ANOTACIÓN QUE DESEA COPIARSE
			//anotacion = forseti.getAnotacionByOrden(id, anotacionOrigen.getOrden(), usuarioSIR);
			anotaciones = forseti.getAnotacionesTemporalesByRangoOrden(id,String.valueOf(evento.getDesde()),String.valueOf(evento.getHasta()),usuarioSIR);
			
			//Aca se debe ordenar las anotaciones
			// aca deberia ordenar las anotaciones por la fecha de Radicacion
			Collections.sort(anotaciones, new radicacionAnotacionComparator());
		
			int cantidadAnotacion = (evento.getHasta() - evento.getDesde()) + 1;				
			if(anotaciones==null ||anotaciones.size()!= cantidadAnotacion){
				CopiaAnotacionException cae = new CopiaAnotacionException();
				cae.addError("El rango ingresado es inválido para anotaciones temporales, por favor ingrese una anotación de inicio y de fin correcto dónde existan anotaciones temporales.");
				throw cae;						 
			}
				
		} catch (ForsetiException e) {
			throw new CopiaAnotacionException(e.getMessage(), e);
		} catch (CopiaAnotacionException cae) {
			throw cae;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCopiaAnotacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		List anotacionesCancelacion = new ArrayList();
		
		Iterator itAnotaciones = anotaciones.iterator();
		while(itAnotaciones.hasNext()){
			Anotacion anotacion = (Anotacion) itAnotaciones.next();
			//SI LA ANOTACION ES CANCELADORA SE DEVUELVE UN MENSAJE A LAS CAPAS SUPERIORES PARA QUE ESTAS
			//SOLICITEN CUÁLES SON LAS ANOTACIONES A CANCELAR. SINO ES CANCELADORA SE COPIA LA ANOTACIÓN.
			if(anotacion.getTipoAnotacion()!=null && anotacion.getTipoAnotacion().getIdTipoAnotacion() !=null && anotacion.getTipoAnotacion().getIdTipoAnotacion().equals(CTipoAnotacion.CANCELACION)){
				copiaAnotacion.setAnotacionOrigen(anotacion);
				copiaAnotacion.setFolioOrigen(folioTemp);
				//return new EvnRespCopiaAnotacion(copiaAnotacion, EvnRespCopiaAnotacion.COPIAR_ANOTACION);	
				copiadas.add(copiaAnotacion);
				anotacionesCancelacion.add(anotacion.getOrden());
			}else{
				//SE OBTIENE EL ID DE LA ANOTACIÓN A COPIAR 
				AnotacionPk idAnotacion = new AnotacionPk();
				idAnotacion.idAnotacion = anotacion.getIdAnotacion();
				idAnotacion.idMatricula = anotacion.getIdMatricula();
				
				
				//SE CREA UNA LISTA CON LOS ID'S DE LOS FOLIOS A DÓNDE DESEA COPIARS LA ANOTACIÓN.
				List foliosID = new ArrayList();
				Iterator itFoliosDestino =  foliosACopiar.iterator();
				while(itFoliosDestino.hasNext()){
			
					Folio folio = (Folio)itFoliosDestino.next();
					FolioPk idFolio = new FolioPk();
					idFolio.idMatricula = folio.getIdMatricula();
					foliosID.add(idFolio);
				
				}
						
				try {				
					forseti.copiarAnotacion(idAnotacion, foliosID, usuarioSIR, copiarComentario);		
				} catch (ForsetiException e) {
					throw new CopiaAnotacionException(e.getErrores());
				} catch (Throwable e) {
					ExceptionPrinter printer = new ExceptionPrinter(e);
					Log.getInstance().error(ANCopiaAnotacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
					throw new EventoException(e.getMessage(), e);
				}
			
				copiaAnotacion.setAnotacionOrigen(anotacion);
				copiaAnotacion.setFolioOrigen(folioTemp);			
				//return new EvnRespCopiaAnotacion(copiaAnotacion, EvnRespCopiaAnotacion.COPIAR_ANOTACION);
				//copiadas.add(copiaAnotacion);			
			}
				
		}
		if(anotacionesCancelacion.size()>1){			
			
			String excVariasCancelacionesIni = " Las anotaciones que no son canceladoras han sido copiadas, sin embargo existen varias anotaciones canceladoras en el rango (";
			String excVariasCancelacionesFin = "  ) , por favor realice una copia para cada una especificando la anotación a cancelar en cada folio.";			
			
			String anotCanceladoras = "   ";
			Iterator it = anotacionesCancelacion.iterator();			
			while (it.hasNext()){
				anotCanceladoras = anotCanceladoras + (String)it.next() + ", ";				
			}
			anotCanceladoras = anotCanceladoras.substring(0, (anotCanceladoras.length()-2));
			
			excVariasCancelacionesFin = excVariasCancelacionesIni + anotCanceladoras + excVariasCancelacionesFin;
			CopiaAnotacionException cae = new CopiaAnotacionException();
			cae.addError(excVariasCancelacionesFin);
			throw cae;
		
		}
		
		
		return new EvnRespCopiaAnotacion(copiadas, EvnRespCopiaAnotacion.COPIAR_ANOTACION);
	}
	
	
	/**
 	 * Método para copiar una anotación canceladora.
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta copiarCancelada(Evento ev) throws EventoException {
		EvnCopiaAnotacion evento = (EvnCopiaAnotacion) ev;

		CopiaAnotacionException exception = new CopiaAnotacionException();
		CopiaAnotacion copiaAnotacion = evento.getCopiaAnotacion();
		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		boolean copiarComentario = copiaAnotacion.isCopiaComentario();		
		
		Usuario usuarioSIR = evento.getUsuarioSIR();
		Turno turno = evento.getTurno();

		//SE CREA UNA LISTA CON LOS ID'S DE LOS FOLIOS A DÓNDE DESEA COPIARSE LA ANOTACIÓN.
		Iterator itFoliosDestino =  foliosACopiar.iterator();
		Anotacion anotacionTemp = null;
		Anotacion anotacionCancelada = null;
		while(itFoliosDestino.hasNext()){
		
			Folio folio = (Folio)itFoliosDestino.next();
			
			FolioPk idFolio = new FolioPk();			
			idFolio.idMatricula = folio.getIdMatricula();
			
			anotacionCancelada = (Anotacion)folio.getAnotaciones().get(0);
			
			//SE SACA DEL FOLIO LA ANOTACIÓN QUE DESEA COPIARSE Y SE CONSULTA
			try{

				anotacionTemp = forseti.getAnotacionByOrden(idFolio, anotacionCancelada.getOrden(), usuarioSIR);
				folio.removeAnotacione(anotacionCancelada);
				if(anotacionTemp!=null){
					folio.addAnotacione(anotacionTemp);	
				}else{
					exception.addError("No se encontro la anotación  " + anotacionCancelada.getOrden() +" para la matrícula " + folio.getIdMatricula());
				}
													
			}catch(Throwable t){
				exception.addError("No se encontro la anotación  " + anotacionCancelada.getOrden() +" para la matrícula " + folio.getIdMatricula());
			}

			
		}	
		
		if(exception.getErrores().size()>0){
			throw exception;		
		}
			
		Anotacion anotacionOrigen = copiaAnotacion.getAnotacionOrigen();
		Folio folioOrigen = copiaAnotacion.getFolioOrigen();		

		//SE OBTIENE EL ID DEL FOLIO A COPIAR 
		FolioPk idFolioOrigen = new FolioPk();
		idFolioOrigen.idMatricula = folioOrigen.getIdMatricula();
		
		//SE OBTIENE EL ID DE LA ANOTACIÓN A COPIAR 
		AnotacionPk idAnotacion = new AnotacionPk();
		idAnotacion.idAnotacion = anotacionOrigen.getIdAnotacion();
		idAnotacion.idMatricula = anotacionOrigen.getIdMatricula();

					
		try {				
			forseti.copiarAnotacionCanceladora(idAnotacion, foliosACopiar, usuarioSIR, copiarComentario);		
		} catch (ForsetiException e) {
			throw new CopiaAnotacionHTException(e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCopiaAnotacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		return new EvnRespCopiaAnotacion(copiaAnotacion, EvnRespCopiaAnotacion.COPIAR_ANOTACION);			
	}
	
	
	/**
	 * Método para copiar una anotación canceladora.
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta corregirCancelada(Evento ev) throws EventoException {
		EvnCopiaAnotacion evento = (EvnCopiaAnotacion) ev;

		CopiaAnotacionException exception = new CopiaAnotacionException();
		CopiaAnotacion copiaAnotacion = evento.getCopiaAnotacion();
		List foliosACopiar = copiaAnotacion.getFoliosACopiar();
		List foliosACopiarOk = copiaAnotacion.getFoliosACopiarOk();
		boolean copiarComentario = copiaAnotacion.isCopiaComentario();				
		Usuario usuarioSIR = evento.getUsuarioSIR();
		Turno turno = evento.getTurno();

		//SE CREA UNA LISTA CON LOS ID'S DE LOS FOLIOS A DÓNDE DESEA COPIARSE LA ANOTACIÓN.
		Iterator itFoliosDestino =  foliosACopiar.iterator();
		Anotacion anotacionTemp = null;
		Anotacion anotacionCancelada = null;
		while(itFoliosDestino.hasNext()){
		
			Folio folio = (Folio)itFoliosDestino.next();
			
			FolioPk idFolio = new FolioPk();			
			idFolio.idMatricula = folio.getIdMatricula();
	
			
			anotacionCancelada = (Anotacion)folio.getAnotaciones().get(0);
			
			//SE SACA DEL FOLIO LA ANOTACIÓN QUE DESEA COPIARSE Y SE CONSULTA
			try{

				anotacionTemp = forseti.getAnotacionByOrden(idFolio, anotacionCancelada.getOrden(), usuarioSIR);
				folio.removeAnotacione(anotacionCancelada);
				if(anotacionTemp!=null){
					folio.addAnotacione(anotacionTemp);	
				}else{
					exception.addError("No se encontro la anotación  " + anotacionCancelada.getOrden() +" para la matrícula " + folio.getIdMatricula());
				}
													
			}catch(Throwable t){
				exception.addError("No se encontro la anotación  " + anotacionCancelada.getOrden() +" para la matrícula " + folio.getIdMatricula());
			}

			
		}	
		
		if(exception.getErrores().size()>0){
			throw exception;		
		}
			
		Anotacion anotacionOrigen = copiaAnotacion.getAnotacionOrigen();
		Folio folioOrigen = copiaAnotacion.getFolioOrigen();		

		//SE OBTIENE EL ID DEL FOLIO A COPIAR 
		FolioPk idFolioOrigen = new FolioPk();
		idFolioOrigen.idMatricula = folioOrigen.getIdMatricula();
		
		//SE OBTIENE EL ID DE LA ANOTACIÓN A COPIAR 
		AnotacionPk idAnotacion = new AnotacionPk();
		idAnotacion.idAnotacion = anotacionOrigen.getIdAnotacion();
		idAnotacion.idMatricula = anotacionOrigen.getIdMatricula();

					
		try {				
			forseti.copiarAnotacionCanceladora(idAnotacion, foliosACopiar, usuarioSIR, copiarComentario);
			forseti.copiarAnotacionCanceladora(idAnotacion, foliosACopiarOk, usuarioSIR, copiarComentario);
		} catch (ForsetiException e) {
			throw new CopiaAnotacionHTException(e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANCopiaAnotacion.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		return new EvnRespCopiaAnotacion(copiaAnotacion, EvnRespCopiaAnotacion.COPIAR_ANOTACION);			
	}	
			

}