package gov.sir.core.negocio.acciones.comun;


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
import gov.sir.core.eventos.comun.EvnPaginadorAnotaciones;
import gov.sir.core.eventos.comun.EvnRespPaginadorAnotaciones;
import gov.sir.core.negocio.acciones.excepciones.ANPaginadorAnotacionesException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.Anotacion;
//import gov.sir.core.negocio.modelo.AnotacionesFolio;
import gov.sir.core.negocio.modelo.Cancelacion;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
/**
 * @author Cesar Ramírez
 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
 **/
import gov.sir.core.negocio.modelo.constantes.CRol;
/**
 * @autor:Edgar Lora
 * @mantis: 0011599
 * @requerimiento: 085_151
 */
import gov.sir.core.negocio.modelo.util.ComparadorAnotaciones;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;

/**
 * Esta acción de negocio se encarga de consultar a los servicios un rango de anotaciones dependiendo de
 * algunos criterios de busqueda.
 * @author ppabon
 */
public class ANPaginadorAnotaciones extends SoporteAccionNegocio {

	/**
	 * Instancia de forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 *Constructor de la clase ANPaginadorAnotaciones
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANPaginadorAnotaciones() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();
		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");

			if (hermod == null) {
				throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod",e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		try {
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

			if (forseti == null) {
				throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
			}
		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio forseti",e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
	}

	/**
	 * Recibe un evento para escoger entregar una lista de anotaciones
	 * de acuerdo a unos criterios predeterminados.
	 * @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnPaginadorAnotaciones</CODE>
	 * @throws EventoException cuando ocurre un problema que no se pueda manejar.
	 * @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespPaginadorAnotaciones</CODE>
	 * @see gov.sir.core.eventos.comun.EvnPaginadorAnotaciones
	 * @see gov.sir.core.eventos.comun.EvnRespPaginadorAnotaciones
	 */
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnPaginadorAnotaciones evento = (EvnPaginadorAnotaciones) e;

		if (evento == null || evento.getTipoEvento() == null) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES)) {
			return consultarAnotaciones(evento);
		}
		if (evento.getTipoEvento().equals(EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO)) {
			return consultarAnotacionesFolio(evento);
		}
		if (evento.getTipoEvento().equals(EvnPaginadorAnotaciones.CARGAR_PAGINA)) {
			return cargarAnotacionesPaginador(evento);
		}

		return null;
	}

	/**
	 * Coloca en el session 2 listas una con las anotaciones definitivas del folio y otra con las anotaciones temporales de este.
	 * @param evento
	 * @return 
	 */
	private EventoRespuesta consultarAnotaciones(EvnPaginadorAnotaciones evento) throws EventoException {

		FolioPk folioID = evento.getFolio();
		String criterio = evento.getCriterio();
		String valorCriterio = evento.getValorCriterio();
		int inicio = evento.getInicio();
		int cantidad = evento.getCantidad();

		long cantidadRegistros = 0;
		List anotaciones = null;

		try {
			cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		}

		try {
			anotaciones = forseti.getAnotacionesFolio(folioID, criterio, valorCriterio, inicio, cantidad,false);
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		}

                
		return new EvnRespPaginadorAnotaciones(anotaciones, cantidadRegistros, EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES, evento.getNombrePaginador(), evento.getNombreResultado());		
	}
	
	/**
	 * Coloca en el session 2 listas una con las anotaciones definitivas del folio y otra con las anotaciones temporales de este.
	 * @param evento
	 * @return 
	 */
	private EventoRespuesta consultarAnotacionesFolio(EvnPaginadorAnotaciones evento) throws EventoException {

		if(evento.isConsultarAnotacionesDefinitivas()){
			return consultarAnotacionesDefinitivasFolio(evento);
		}

		FolioPk folioID = evento.getFolio();
		String criterio = CCriterio.TODAS_LAS_ANOTACIONES;
		String valorCriterio = null;
		int inicio = evento.getInicio();
		int cantidad = evento.getCantidad();
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNeg();

		long numeroAnotacionesDefinitivas = 0;
		long cantidadRegistros = 0;
		List anotacionesDefinitivas = null;
		List anotacionesDefinitivasOrdenadasPorOrdenLPAD = null;
		//TODO Lista no utilizada.
        //List anotacionesTemporales = null;
		List foliosDerivadoPadre = null;
		List foliosDerivadoHijos = null;
                List historialAreas = null;
		
		boolean consulta = evento.getConsulta();
		
		boolean actualizarDeltas = evento.isGetAnotacionesConDeltadas();

		try {
			if(consulta){
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
			}else{
				try{
					cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
					numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				}catch(ForsetiException e1){
					try{
						cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
						numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
					} catch (ForsetiException e) {
						ExceptionPrinter ep = new ExceptionPrinter(e1);
						ExceptionPrinter ep2 = new ExceptionPrinter(e);
						Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString() + ep2.toString(), e);
						throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
					}
				}
			}
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		}
		//falta por arreglar el servicio de forseti.
		try {
			if(!consulta){
				try{
					anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, usuarioNeg,false);
				}catch (ForsetiException e1) {
					try{
					anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, null,false);
					}catch (ForsetiException e) {
						ExceptionPrinter ep = new ExceptionPrinter(e1);
						ExceptionPrinter ep2 = new ExceptionPrinter(e);
						Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString()+ ep2.toString(), e);
						throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
					}
				} 
			}else{
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, null,false);
			}
            
			Log.getInstance().debug(ANPaginadorAnotaciones.class,"ANOTACIONES CARGADAS!!!! tamaño = "+ anotacionesDefinitivas.size());
			//Aca se deben arreglar por orden LPAD
			try {
				//ordenar las anotaciones por rol
				anotacionesDefinitivasOrdenadasPorOrdenLPAD = new ArrayList(anotacionesDefinitivas); 
                                /**
                                * @autor:Edgar Lora
                                * @mantis: 0011599
                                * @requerimiento: 085_151
                                */
				Collections.sort(anotacionesDefinitivasOrdenadasPorOrdenLPAD, new ComparadorAnotaciones());
                
				// 	Se consulta el objeto FolioDerivado
				foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(folioID);
				foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(folioID);
                                String idMatricula = evento.getFolio().idMatricula;
                                historialAreas = forseti.getHistorialArea(idMatricula);
                                
	        } catch (ClassCastException e) {
	            Log.getInstance().error(ANPaginadorAnotaciones.class,"No fue posible ordenar las anotaciones");
	            Log.getInstance().error(ANPaginadorAnotaciones.class,e);
	        }

			
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		}
		
		//Este metodo sirve para actualizar los orden de las anotaciones temporales con las definitivas
		//if (evento.isGetAnotacionesConDeltadas()){
		if (actualizarDeltas){
			Iterator itAnotacion = anotacionesDefinitivasOrdenadasPorOrdenLPAD.iterator(); //aqui se pide en vez folio.getAnotaciones la anotaciones definitivas
			try{
				while(itAnotacion.hasNext()){
					Anotacion anotacion = (Anotacion)itAnotacion.next();
					if (anotacion.getAnotacionesCancelacions()!=null){
						Iterator itCanceladas = anotacion.getAnotacionesCancelacions().iterator();
						while(itCanceladas.hasNext()){
							Cancelacion cancel = (Cancelacion) itCanceladas.next();
							String idAnotacion1 = cancel.getIdAnotacion1();
							Anotacion anotacionCancelada = getAnotacion(idAnotacion1, anotacionesDefinitivasOrdenadasPorOrdenLPAD);
							if (anotacionCancelada != null) {
								cancel.setCancelada(anotacionCancelada);
							}
						}
					}


				}
			} catch(Exception eAnotacion){
				eAnotacion.printStackTrace();
			}
			
		}
		
		EvnRespPaginadorAnotaciones ev= new EvnRespPaginadorAnotaciones(anotacionesDefinitivasOrdenadasPorOrdenLPAD, null, cantidadRegistros, EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, evento.getNombrePaginador(), evento.getNombreResultado());		
		ev.setPaginaInicial(evento.getPaginaInicial());
                ev.setHistorialAreas(historialAreas);
		ev.setNumeroAnotacionesDefinitivas(numeroAnotacionesDefinitivas);
		ev.setFoliosDerivadoPadre(foliosDerivadoPadre);
		ev.setFoliosDerivadoHijo(foliosDerivadoHijos);
		return ev;
	}
	
	private Anotacion getAnotacion(String idAnotacion, List anotacionesDefinitivasOrdenadasPorOrdenLPAD){
		Iterator itAnotacion = anotacionesDefinitivasOrdenadasPorOrdenLPAD.iterator(); //aqui se pide en vez folio.getAnotaciones la anotaciones definitivas
		try{
			while(itAnotacion.hasNext()){
				Anotacion anotacion = (Anotacion)itAnotacion.next();
				if (anotacion.getIdAnotacion().equals(idAnotacion)){
					return anotacion;
				}
			}
		}catch(Exception eAnotacion){
			eAnotacion.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	/**
	 * Método que consulta las anotaciones deifinitivas excepto si el usuario es el dueño del bloqueo 
	 * caso en el cuál se consultan también las anotaciones temporales.
	 * @param evento
	 * @return 
	 */
	private EventoRespuesta consultarAnotacionesDefinitivasFolio(EvnPaginadorAnotaciones evento) throws EventoException {

		//SE VALIDA QUE LLEGUE EL USUARIO QUE ESTA INTENTANDO REALIZAR LA CONSULTA
		Usuario usuarioNegocio = evento.getUsuarioNeg();
		if(usuarioNegocio == null){
			throw new EventoException("No se encuentra el usuario para determinar el bloqueo de los folios.");
		}


		FolioPk folioID = evento.getFolio();
		/**
		 * @author Cesar Ramírez
		 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
		 **/
		String rolID = evento.getRolID();
		String criterio = CCriterio.TODAS_LAS_ANOTACIONES;		
		String valorCriterio = null;
		int inicio = evento.getInicio();
		int cantidad = evento.getCantidad();
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNeg();
		Usuario usuarioBloqueo = null;

		long numeroAnotacionesDefinitivas = 0;
		long cantidadRegistros = 0;
		List anotacionesDefinitivas = null;
        //TODO Lista no utilizada.
		//List anotacionesTemporales = null;
		
		try {
			
			boolean usuarioTieneBloqueo = false;			
			FolioPk id = evento.getFolio();
			
			//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
			usuarioBloqueo = forseti.getBloqueoFolio(id);			
			
			//SE VERIFICA QUE EL USUARIO QUE QUIERE HACER LA CONSULTA ES EL DUEÑO DEL BLOQUEO, PARA CARGARLE TAMBIÉN 
			//LAS COSAS DEFINITIVAS, SINO ÚNICAMENTE SE CARGAN LAS COSAS TEMPORALES.
			if(usuarioBloqueo!=null && usuarioNegocio!=null && usuarioBloqueo.getUsername().equals(usuarioNegocio.getUsername())){
				usuarioTieneBloqueo = true;				
			/**
			 * @author Cesar Ramírez
			 * @change: 1480.REGISTRADOR.VERIFICAR.INFORMACION.TEMPORAL.CALIFICACION
			 * Si se está consultando las anotaciones 
			 **/
			} else if(rolID != null && rolID.equals(CRol.SIR_ROL_REGISTRADOR)) {
				usuarioTieneBloqueo = true;
				usuarioNeg = null;
			}
			
			
			if(usuarioTieneBloqueo){
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, usuarioNeg, false);
			}else if (!evento.isConsultarAnotacionesFolioTemporal()){
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, false);				
			}else if (evento.isConsultarAnotacionesFolioTemporal())
			{
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, null, false); 
			}
			

		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		}

		//TODO
		// if que valida sí el usuario digitó la anotación desde la cual quiere visualizar.
        // Si el usuario digitó el número de anotación entonces crea el evento con el número de anotaciones a visualizar.
        // en caso contrario crea el evento cómo se hacia anteriormente (todas las anotaciones)
        
        EvnRespPaginadorAnotaciones ev;

        // Modificado por OSBERT LINERO - Iridium Telecomunicaciones e informática Ltda.
        // Cambio para asolucionar requerimiento 117 - Incidencia Mantis 02399.
        // Se cambió la variable estática por atributos en sesion y en el evento EvnPaginadorAnotaciones.

        //if(AnotacionesFolio.getNumAnotacionesTotalV()>0){
        if(evento.getNumAnotcionesTotalV()>0){
            //ev= new EvnRespPaginadorAnotaciones(anotacionesDefinitivas, null, AnotacionesFolio.getNumAnotacionesTotalV()-AnotacionesFolio.getNumAnotacionesFolio(), EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, evento.getNombrePaginador(), evento.getNombreResultado());
            ev= new EvnRespPaginadorAnotaciones(anotacionesDefinitivas, null, evento.getNumAnotcionesTotalV()-evento.getAnotcionesDesdeV(), EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, evento.getNombrePaginador(), evento.getNombreResultado());
        }else{
            ev= new EvnRespPaginadorAnotaciones(anotacionesDefinitivas, null, cantidadRegistros, EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, evento.getNombrePaginador(), evento.getNombreResultado());
        }
		ev.setPaginaInicial(evento.getPaginaInicial());
		ev.setNumeroAnotacionesDefinitivas(numeroAnotacionesDefinitivas);
		return ev;
	}	
	
	
	
	/**
	 * Coloca en el session 2 listas una con las anotaciones definitivas del folio y otra con las anotaciones temporales de este.
	 * @param evento
	 * @return 
	 */
	private EventoRespuesta cargarAnotacionesPaginador(EvnPaginadorAnotaciones evento) throws EventoException {

		if(evento.isConsultarAnotacionesDefinitivas()){
			return cargarAnotacionesDefinitivasPaginador(evento);
		}

		FolioPk folioID = evento.getFolio();
		String criterio = CCriterio.TODAS_LAS_ANOTACIONES;
		String valorCriterio = null;
		int inicio = evento.getInicio();
		int cantidad = evento.getCantidad();
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNeg();

		long numeroAnotacionesDefinitivas = 0;
		long cantidadRegistros = 0;
		List anotacionesDefinitivas = null;
		//TODO Lista no utilizada.
        //List anotacionesTemporales = null;
		
		boolean consulta = evento.getConsulta();

		try {
			if(consulta){
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
			}else{
				try{
					cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
					numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				}catch(ForsetiException e1){
					try{
						cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, null);
						numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
					} catch (ForsetiException e) {
						ExceptionPrinter ep = new ExceptionPrinter(e1);
						ExceptionPrinter ep2 = new ExceptionPrinter(e);
						Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString() + ep2.toString(), e);
						throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
					}
				}
			}
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta del número de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta del número de anotaciones:", e);
		}
		//falta por arreglar el servicio de forseti.
		try {
			if(!consulta){
				try{
					anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, usuarioNeg,false);
				}catch (ForsetiException e1) {
					try{
					anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, null,false);
					}catch (ForsetiException e) {
						ExceptionPrinter ep = new ExceptionPrinter(e1);
						ExceptionPrinter ep2 = new ExceptionPrinter(e);
						Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString()+ ep2.toString(), e);
						throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
					}
				} 
			}else{
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, null,false);
			}
			Log.getInstance().debug(ANPaginadorAnotaciones.class,"ANOTACIONES CARGADAS!!!! tamaño = "+ anotacionesDefinitivas.size());
		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		}
		
	

		EvnRespPaginadorAnotaciones ev = new EvnRespPaginadorAnotaciones(anotacionesDefinitivas, null, cantidadRegistros, EvnRespPaginadorAnotaciones.CARGAR_PAGINA, evento.getNombrePaginador(), evento.getNombreResultado());		
		ev.setNumeroAnotacionesDefinitivas(numeroAnotacionesDefinitivas);
		return ev;
	}
	
	
	/**
	 * Coloca en el session 2 listas una con las anotaciones definitivas del folio y otra con las anotaciones temporales de este,
	 * si el usuario tiene el bloqueo muestra también las anotaciones temporales sino muestra todas.
	 * @param evento
	 * @return 
	 */
	private EventoRespuesta cargarAnotacionesDefinitivasPaginador(EvnPaginadorAnotaciones evento) throws EventoException {


		//SE VALIDA QUE LLEGUE EL USUARIO QUE ESTA INTENTANDO REALIZAR LA CONSULTA
		Usuario usuarioNegocio = evento.getUsuarioNeg();
		if(usuarioNegocio == null){
			throw new EventoException("No se encuentra el usuario para determinar el bloqueo de los folios.");
		}

		FolioPk folioID = evento.getFolio();
		String criterio = CCriterio.TODAS_LAS_ANOTACIONES;
		String valorCriterio = null;
		int inicio = evento.getInicio();
		int cantidad = evento.getCantidad();
		gov.sir.core.negocio.modelo.Usuario usuarioNeg=evento.getUsuarioNeg();

		long numeroAnotacionesDefinitivas = 0;
		long cantidadRegistros = 0;
		List anotacionesDefinitivas = null;
		//TODO Lista no utilizada.
        //List anotacionesTemporales = null;
		Usuario usuarioBloqueo = null;
		
		try {
			
			boolean usuarioTieneBloqueo = false;			
			FolioPk id = evento.getFolio();
			
			//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
			usuarioBloqueo = forseti.getBloqueoFolio(id);			
			
			//SE VERIFICA QUE EL USUARIO QUE QUIERE HACER LA CONSULTA ES EL DUEÑO DEL BLOQUEO, PARA CARGARLE TAMBIÉN 
			//LAS COSAS DEFINITIVAS, SINO ÚNICAMENTE SE CARGAN LAS COSAS TEMPORALES.
			if(usuarioBloqueo!=null && usuarioNegocio!=null && usuarioBloqueo.getUsername().equals(usuarioNegocio.getUsername())){
				usuarioTieneBloqueo = true;				
			}

			if(usuarioTieneBloqueo){
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio, usuarioNeg);
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, usuarioNeg, false);				
			}else{
				cantidadRegistros = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				numeroAnotacionesDefinitivas = forseti.getCountAnotacionesFolio(folioID, criterio, valorCriterio);
				anotacionesDefinitivas = forseti.getAnotacionesFolio(folioID, CCriterio.TODAS_LAS_ANOTACIONES, null, inicio, cantidad, false);				
			}

		} catch (ForsetiException e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		} catch (Throwable e) {
			ExceptionPrinter ep = new ExceptionPrinter(e);
			Log.getInstance().error(ANPaginadorAnotaciones.class,"No se pudo realizar la consulta de anotaciones:" + ep.toString(), e);
			throw new ANPaginadorAnotacionesException("No se pudo realizar la consulta de anotaciones:", e);
		}
		
	

		EvnRespPaginadorAnotaciones ev = new EvnRespPaginadorAnotaciones(anotacionesDefinitivas, null, cantidadRegistros, EvnRespPaginadorAnotaciones.CARGAR_PAGINA, evento.getNombrePaginador(), evento.getNombreResultado());		
		ev.setNumeroAnotacionesDefinitivas(numeroAnotacionesDefinitivas);
		return ev;
	}	
	

}
