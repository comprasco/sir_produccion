package gov.sir.core.negocio.acciones.registro;

import gov.sir.core.eventos.registro.EvnAprobarAjuste;
import gov.sir.core.eventos.registro.EvnRespAprobarAjuste;
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
import gov.sir.core.negocio.modelo.constantes.CRespuesta;
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
public class ANAprobarAjuste extends SoporteAccionNegocio {
	private ServiceLocator service = null;
	/**
	 * 
	 */
	public ANAprobarAjuste() {
		service = ServiceLocator.getInstancia();
	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnAprobarAjuste evento = (EvnAprobarAjuste) ev;
		
		if (evento.getTipoEvento().equals(EvnAprobarAjuste.AVANZAR)){
			return avanzarNuevo(evento);
		
		} else if (evento.getTipoEvento().equals(EvnAprobarAjuste.CONSULTA)){
			return consultaTemporal(evento);
		} 
		return null;
	}

	
	

	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultaTemporal(EvnAprobarAjuste evento) throws EventoException{
		Folio folio = evento.getFolio();
		FolioPk id=new FolioPk();
		id.idMatricula=folio.getIdMatricula();
		
		Usuario u = evento.getUsuarioNeg();
		ForsetiServiceInterface forseti=null;
		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANAprobarAjuste.class,"No se encontró el servicio forseti:"+ep.toString());
			throw new ServicioNoEncontradoException("No se encontró el servicio forseti:"+ep.toString(),e);
		}
	
		try {
			if(folio!=null && folio.getIdMatricula()!=null){
				folio = forseti.getFolioByIDSinAnotaciones(id, null);
                boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
                
				long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());
				
				List foliosHijos = forseti.getFoliosHijos(id, null);
				List foliosPadre = forseti.getFoliosPadre(id, null);
                                
                                String idMatricula = folio.getIdMatricula();
                                List historialAreas = forseti.getHistorialArea(idMatricula);
				
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
				
				return new EvnRespAprobarAjuste(historialAreas, folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas, numeroAnotaciones, esMayorExtension);
			}			
			else{
				throw new EventoException();
			}
		} catch (Throwable e){
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANAprobarAjuste.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(),e);
		}
	}



	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta tomarTurno(EvnAprobarAjuste evento) throws EventoException {
		ForsetiServiceInterface forseti=null;
		try {
			forseti= (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANAprobarAjuste.class,"No se encontró el servicio forseti:"+ep.toString());
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
				Log.getInstance().error(ANAprobarAjuste.class,mensaje+ep.toString());
				throw new TomarTurnoCalificacionHTException(e1);
			}else{
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANAprobarAjuste.class,"No fué posible tomar el turno:"+ep.toString());
				throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
			}
			
			
		}catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAprobarAjuste.class,"No fue posible tomar el turno:"+ep.toString());
			throw new TomarTurnoRegistroException("No fué posible tomar el turno:"+e1.getMessage(),e1);
		}
	return null;
	}

		

		
		
	
		
		
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzar(EvnAprobarAjuste evento) throws EventoException,
	RevisionCalificacionException{
		HermodServiceInterface hermod=null;
		Turno turno=evento.getTurno();
		Fase fase=evento.getFase();
		String respuesta="";
		Hashtable tabla=new Hashtable();
		Usuario u=evento.getUsuarioNeg();
		
		String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );
		
        tabla.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANAprobarAjuste.class,"No se encontró el servicio hermod:"+ep.toString());
		}
		
		

		
		
		try{
			hermod.updateAjusteInTurnoRegistro(turno, false);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAprobarAjuste.class,"No se pudo hacer el update al turno:"+ep.toString());
			throw new RevisionCalificacionException("", e1);
		}
	
	    //Configurar los parametros de impresión del Certificado de calificacion
		if(evento.getRespuestaWf().equals(EvnAprobarAjuste.WF_NEGAR)){
			
			//Ver la ultima respuesta dada para definir la nueva respuesta a dar
			try{
				String ultimaRespuesta=turno.getUltimaRespuesta();
				if(ultimaRespuesta.equals(CRespuesta.AJUSTAR_CALIFICACION)){
					respuesta=CRespuesta.NEGAR_REVISION;
				}else if(ultimaRespuesta.equals(CRespuesta.AJUSTAR_MESA_CONTROL)){
					respuesta=CRespuesta.NEGAR_MESA;
				}else if(ultimaRespuesta.equals(CRespuesta.AJUSTAR)){
					respuesta=CRespuesta.NEGAR_FIRMA;
				}else{
					throw new Exception("Ultima respuesta invalida");
				}
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANAprobarAjuste.class,"No se pudo avanzar el turno:"+ep.toString());
				throw new RevisionCalificacionException("", e1);
			}
		}else{
			respuesta=EvnAprobarAjuste.WF_CONFIRMAR;
		}
		tabla.put(Processor.RESULT,respuesta);
	
	
		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, respuesta);
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}	
		
		return null;
	}

	
	
	
	
	
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta avanzarNuevo (EvnAprobarAjuste evento) throws EventoException,
																RevisionCalificacionException{
		HermodServiceInterface hermod=null;
		Turno turno=evento.getTurno();
		Fase fase=evento.getFase();
		String respuesta="";
		Hashtable tabla=new Hashtable();
		Usuario u=evento.getUsuarioNeg();
		
		String USUARIO_INICIADOR = ( null != evento.getUsuarioNeg() )?(""+evento.getUsuarioNeg().getUsername()):("");
        if( "".equals( USUARIO_INICIADOR ) || null == USUARIO_INICIADOR )
            throw new TurnoNoAvanzadoException( "El usuario no se ha regostrado en la capa web." + this.getClass().getName() );
		
        tabla.put(CInfoUsuario.USUARIO_SIR, USUARIO_INICIADOR);
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
		} catch (ServiceLocatorException e) {
			ExceptionPrinter ep=new ExceptionPrinter(e);
			Log.getInstance().error(ANAprobarAjuste.class,"No se encontró el servicio hermod:"+ep.toString());
		}
		
		

		
		
		try{
			hermod.updateAjusteInTurnoRegistro(turno, false);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANAprobarAjuste.class,"No se pudo hacer el update al turno:"+ep.toString());
			throw new RevisionCalificacionException("", e1);
		}
	
	    //Configurar los parametros de impresión del Certificado de calificacion
		if(evento.getRespuestaWf().equals(EvnAprobarAjuste.WF_NEGAR)){
			
			//Ver la ultima respuesta dada para definir la nueva respuesta a dar
			try{
				String ultimaRespuesta=turno.getUltimaRespuesta();
				if(ultimaRespuesta.equals(CRespuesta.AJUSTAR_CALIFICACION)){
					respuesta=CRespuesta.NEGAR_REVISION;
				}else if(ultimaRespuesta.equals(CRespuesta.AJUSTAR_MESA_CONTROL)){
					respuesta=CRespuesta.NEGAR_MESA;
				}else if(ultimaRespuesta.equals(CRespuesta.AJUSTAR)){
					respuesta=CRespuesta.NEGAR_FIRMA;
				}else{
					throw new Exception("Ultima respuesta invalida");
				}
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANAprobarAjuste.class,"No se pudo avanzar el turno:"+ep.toString());
				throw new RevisionCalificacionException("", e1);
			}
		}else{
			respuesta=EvnAprobarAjuste.WF_CONFIRMAR;
		}
		tabla.put(Processor.RESULT,respuesta);
	
	
		// 3. Se valida que no sea necesario exigir una nota informativa para permitir el avance
		//del turno desde esta fase. 
		try {
				 Hashtable tablaAvance = new Hashtable(2);
				 tablaAvance.put(Processor.RESULT, respuesta);
				 tablaAvance.put(CInfoUsuario.USUARIO_SIR, evento.getUsuarioNeg().getUsername());
				 hermod.validarNotaInformativaAvanceTurno(evento.getFase(),tablaAvance,turno);
		}
		catch(Throwable t)
		{
			  throw new TurnoNoAvanzadoException("No es posible avanzar el turno. Se requiere una nota informativa para avanzar desde esta fase");
		}	
		
		
		/*****************************************/
		/*        ELIMINAR SASS                  */
		/*****************************************/
		
		
		Fase faseAvance = evento.getFase();    
	    Hashtable parametrosAvance = new Hashtable();
	  	
	    //Devolver el turno a mesa de control.
	    String respuestaWF = evento.getRespuestaWf();
	    parametrosAvance.put(Processor.RESULT,respuesta);
	    
	    
		             
		try 
		{
			
			if (respuestaWF.equals(EvnAprobarAjuste.WF_CONFIRMAR)){
				hermod.avanzarTurnoNuevoPop(turno,faseAvance,parametrosAvance,u);
			}
			
			else
			{
				hermod.avanzarTurnoNuevoNormal(turno,faseAvance,parametrosAvance,u);
			}
			
		
		}
	    catch (Throwable exception) {
		    	 throw new EventoException ("Error avanzando el turno en el servicio hermod.",exception);
	    }
		
		
		
		return null;
	}

	
	
	
	
	
	
}