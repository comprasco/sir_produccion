
package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnRespRevision;
import gov.sir.core.eventos.registro.EvnRevision;
import gov.sir.core.negocio.acciones.excepciones.RevisionCalificacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoCalificacionHTException;
import gov.sir.core.negocio.acciones.excepciones.TomarTurnoRegistroException;
import gov.sir.core.negocio.acciones.excepciones.TurnoNoAvanzadoException;
import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CInfoUsuario;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.hermod.workflow.Processor;

import java.util.ArrayList;
import java.util.Enumeration;
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
 * @author jfrias,mmunoz
*/
public class ANRevision extends SoporteAccionNegocio {
	private ServiceLocator service = null;
	
	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;	
	
	/**
	 * 
	 */
	public ANRevision()  throws EventoException {
		service = ServiceLocator.getInstancia();
		
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod");
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}		
		
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnRevision evento = (EvnRevision) ev;
		
		if (evento.getTipoEvento().equals(EvnRevision.AVANZAR)){
			return avanzar(evento);
		/*} else if (evento.getTipoEvento().equals(EvnRevision.TOMAR_TURNO)){
			return tomarTurno(evento);*/
		} else if (evento.getTipoEvento().equals(EvnRevision.CONSULTA)){
			return consultaTemporal(evento);
		} 
		return null;
	}

	
	

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultaTemporal(EvnRevision evento) throws EventoException{
		Folio folio = evento.getFolio();
		FolioPk id=new FolioPk();
		id.idMatricula=folio.getIdMatricula();
		
		Usuario u = evento.getUsuarioNeg();
		ForsetiServiceInterface forseti=null;
		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se encontró el servicio forseti:"+ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:"+ep.toString(),e);
		}
	
		try {
			if(folio!=null && folio.getIdMatricula()!=null){
				folio = forseti.getFolioByIDSinAnotaciones(id, null);
                boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
                
				long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());
				
				List foliosHijos = forseti.getFoliosHijos(id,null);
				List foliosPadre = forseti.getFoliosPadre(id,null);
				
				long numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN, null);
				List gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes, null, true);

				long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, null);
				List medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares , null, true);
				
				long numeroFalsaTradicion = 0;
				List falsaTradicion = null;
				List ordenFalsaTradicion = new ArrayList();
				List anotacionesInvalidas = null;
				List ordenAnotacionesInvalidas = new ArrayList();		
				
				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, u, true);
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());	
					}
				}		
				
				//CONSULTA ANOTACIONES INVALIDAS
				anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, u);
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());	
					}
				}					
				
				return new EvnRespRevision(folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas,  numeroAnotaciones, esMayorExtension);
			}			
			else{
				throw new EventoException();
			}
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
	}



	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta tomarTurno(EvnRevision evento) throws EventoException {
		ForsetiServiceInterface forseti=null;
		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANRevision.class,"No se encontró el servicio forseti:"+ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:"+ep.toString(),e);
		}
		Usuario usuarioNeg=evento.getUsuarioNeg();
		Turno turno = evento.getTurno();
		SolicitudRegistro solicitud = (SolicitudRegistro)turno.getSolicitud();
		List folios= solicitud.getSolicitudFolios();
		List matriculas=new ArrayList();
		Iterator it=folios.iterator();
		while(it.hasNext()){
				SolicitudFolio solF=(SolicitudFolio)it.next();
				matriculas.add(solF.getFolio().getIdMatricula());
		}
		try {
			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();
			forseti.validarPrincipioPrioridadCorreccion(turnoID);
			forseti.validarPrincipioPrioridadCalificacion(turnoID, usuarioNeg);				
			forseti.delegarBloqueoFolios(turnoID,usuarioNeg);
		} catch (ForsetiException e1) {
			Hashtable errores=e1.getHashErrores();
			if (errores!=null && !errores.isEmpty()){
				String mensaje="No fue posible bloquear las matrículas:\n";
				Enumeration enumeration=errores.keys();
				while(enumeration.hasMoreElements()){
					String matricula=(String)enumeration.nextElement();
					String razon = (String)errores.get(matricula);
					mensaje.concat(matricula+" : "+razon+"\n");
				}	
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANRevision.class,mensaje+ep.toString());
				throw new TomarTurnoCalificacionHTException(e1);
			}else{
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANRevision.class,"No fué posible tomar el turno:"+ep.toString());
				throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
			}
			
			
		}catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANRevision.class,"No fue posible tomar el turno:"+ep.toString());
			throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
		}
	return null;
	}

		

		
		
	
		
		
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzar(EvnRevision evento) throws EventoException,
																RevisionCalificacionException{
		Turno turno=evento.getTurno();
		Fase fase=evento.getFase();
		Hashtable tabla=new Hashtable();
		Usuario u=evento.getUsuarioNeg();
		tabla.put(Processor.RESULT,evento.getRespuestaWf());
		String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
	    if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
	         throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );
		
		
			 // 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
			 //del turno desde esta fase. 
			 try {
					  Hashtable tablaAvance = new Hashtable(2);
					  tablaAvance.put(Processor.RESULT, evento.getRespuestaWf());
					  tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
					  hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
			 }
			 catch(Throwable t)
			 {
			 		t.printStackTrace();
				   throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
			 }
	
	    tabla.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
	
	    //Configurar los parametros de impresión del Certificado de calificacion
		
		tabla.put(Processor.RESULT,evento.getRespuestaWf());
	
		return null;
	}

	
	
	
	
	
}