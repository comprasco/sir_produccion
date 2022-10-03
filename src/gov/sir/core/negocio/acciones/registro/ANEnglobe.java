package gov.sir.core.negocio.acciones.registro;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.registro.EvnRespEnglobe;
import gov.sir.core.eventos.registro.EvnEnglobe;
import gov.sir.core.eventos.registro.EvnRespSegregacion;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.EnglobeException;
import gov.sir.core.negocio.acciones.excepciones.EnglobeHTException;
import gov.sir.core.negocio.acciones.excepciones.SegregacionException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.jdogenie.CiudadanoTMP;
import gov.sir.core.negocio.modelo.util.CiudadanoComparatorVarios;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.negocio.modelo.util.radicacionAnotacionComparator;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.HermodException;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import gov.sir.print.interfaz.PrintJobsInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de la creación del folio para mediante el proceso de englobe,
 * proceso por el cuál a partir de un varios folios se deja uno definitivo que los agrupa
 * @author ppabon
*/
public class ANEnglobe extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia de Hermod
	 */
	private PrintJobsInterface printJobs;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANEnglobe.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *de los servicio que necesita.
	 */
	public ANEnglobe() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio Hermod", e);
		}

		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}

	}

	/* (non-Javadoc)
	 * @see org.auriga.smart.negocio.acciones.AccionNegocio#perform(org.auriga.smart.eventos.Evento)
	 */
	public EventoRespuesta perform(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;
		if (evento.getTipoEvento().equals(EvnEnglobe.SELECCIONAR_FOLIOS)) {
			return seleccionarFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnEnglobe.GUARDAR_WEB_ENGLOBE)) {
			return guardarWebEnglobe(evento);
		} else if (evento.getTipoEvento().equals(EvnEnglobe.CONSULTA_FOLIO)) {
			return consultarFolio(evento);
		} else if (evento.getTipoEvento().equals(EvnEnglobe.CONSULTA_ANOTACIONES_TEMPORALES)) {
			return consultarAnotacionesTemporales(evento);
		} else if (evento.getTipoEvento().equals(EvnEnglobe.ENGLOBAR_FOLIOS)) {
			return englobarFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnEnglobe.ELIMINAR_ENGLOBE)) {
			return eliminarWebEnglobe(evento);
		} else if(evento.getTipoEvento().equals(EvnEnglobe.CONSULTAR_ULTIMOS_PROPIETARIOS)) {
			return consultarUltimosPropietarios(evento);
		} else if(evento.getTipoEvento().equals(EvnEnglobe.AGREGAR_CIUDADANO_ANOTACION)) {
			return validarCiudadanos(evento);
		}

		return null;
	}


	/**
	 * Éste método guarda el objeto webEnglobe con los folios que harán parte del englobe 
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta seleccionarFolios(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;

		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();

		WebEnglobe webEnglobe = evento.getWebEnglobe();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		OficinaOrigen oficinaOrigen = null;
		
		
		try {
			
			if(webEnglobe.getIdWebSegeng()==null){
				
				SolicitudPk sid = new SolicitudPk();
				sid.idSolicitud = solicitud.getIdSolicitud();
				webEnglobe.setFechaCreacion(new Date());
				wid = hermod.crearWebSegEng(webEnglobe ,  sid);				
			} else{
				wid = new WebSegEngPk();
				wid.idSolicitud = webEnglobe.getIdSolicitud();
				wid.idWebSegeng = webEnglobe.getIdWebSegeng(); 
				hermod.actualizarWebSegEng(wid, webEnglobe);
			}
			
			webSegEng = hermod.consultarWebSegEng(wid);
			
			WebEnglobe webSeg = (WebEnglobe)webSegEng;
			if(webSeg!=null&&webSeg.getAnotacion()!=null){
				WebAnotacion webAnotacion = webSeg.getAnotacion();
				
				if(webAnotacion!=null&& webAnotacion.getDocumento()!=null && webAnotacion.getDocumento().getIdOficinaOrigen()!=null){
					OficinaOrigenPk oid = new OficinaOrigenPk();
					oid.idOficinaOrigen = webAnotacion.getDocumento().getIdOficinaOrigen();
                                           /*
                                         *  @author Carlos Torres
                                         *  @chage   se agrega validacion de version diferente
                                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                                         */
                                        oid.version = webAnotacion.getDocumento().getVersion();
					try{
						oficinaOrigen = forseti.getOficinaOrigen(oid);	  
					}catch(Throwable t){
					}
				}
			}
		   	
		}catch(HermodException t){
			throw new EnglobeException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al guardar los datos.",e);
		}

		EvnRespEnglobe evnResp = new EvnRespEnglobe(webSegEng , EvnRespEnglobe.GUARDAR_WEB_ENGLOBE);
		evnResp.setOficinaOrigen(oficinaOrigen);
		return evnResp;
	}

	private EventoRespuesta consultarFolio(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;


		WebEnglobe webEnglobe = evento.getWebEnglobe();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			
			wid = new WebSegEngPk();
			wid.idSolicitud = webEnglobe.getIdSolicitud();
			wid.idWebSegeng = webEnglobe.getIdWebSegeng(); 
			hermod.actualizarWebSegEng(wid, webEnglobe );
			
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new EnglobeException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al guardar los datos.",e);
		}


		Folio folio = evento.getFolio();
		Usuario usuarioSIR = evento.getUsuarioNeg(); 

		FolioPk id = new FolioPk();
		id.idMatricula = folio.getIdMatricula();

		try {
			folio = forseti.getFolioByID(id, usuarioSIR);
		} catch (Throwable e) {
			try {
				folio = forseti.getFolioByID(id);
			} catch (ForsetiException ex) {
				throw new EnglobeException(ex.getMessage(), ex);
			} catch (Throwable et) {
				ExceptionPrinter printer = new ExceptionPrinter(et);
				Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
				throw new EventoException(e.getMessage(), et);
			}
		}
		
		return new EvnRespEnglobe(folio, EvnRespEnglobe.CONSULTA_FOLIO);
	}

	/**
	 * Éste método guarda el objeto webEnglobe con los datos que hacen parte del englobe 
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta guardarWebEnglobe(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;

		Turno turno = evento.getTurno();
		Solicitud solicitud = turno.getSolicitud();

		WebEnglobe webEnglobe = evento.getWebEnglobe();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			
			wid = new WebSegEngPk();
			wid.idSolicitud = webEnglobe.getIdSolicitud();
			wid.idWebSegeng = webEnglobe.getIdWebSegeng();
			hermod.actualizarWebSegEng(wid, webEnglobe);
					
			webSegEng = hermod.consultarWebSegEng(wid);
			
		}catch(HermodException t){
			throw new EnglobeException("Se presento un error al guardar los datos.",t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al guardar los datos.",e);
		}

		return new EvnRespEnglobe(webSegEng , EvnRespEnglobe.GUARDAR_WEB_ENGLOBE);
	}

	private EventoRespuesta consultarAnotacionesTemporales(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;

		Folio folio = evento.getFolio();
		List anotaciones = null;
		try {
			FolioPk id = new FolioPk();
			id.idMatricula = folio.getIdMatricula();

			anotaciones = forseti.getAnotacionesTMPFolioToInsert(id, CCriterio.TODAS_LAS_ANOTACIONES, null, evento.getUsuarioNeg());
		} catch (ForsetiException e) {
			throw new EnglobeException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		return new EvnRespEnglobe(anotaciones, EvnRespEnglobe.CONSULTA_ANOTACIONES_TEMPORALES, folio);
	}

	/**
	 * @param ev
	 * @return
	 */
	private EventoRespuesta englobarFolios(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;

		//SE RECUPERA LA INFORMACION DEL EVENTO
		Folio folioCreado = null;
		Turno turno = evento.getTurno();
		Circulo circulo = evento.getCirculo();
		Usuario usuarioSIR = evento.getUsuarioNeg();
		List direccionesEnglobe= evento.getDireccionesEnglobe();

		WebEnglobe webEnglobe = evento.getWebEnglobe();
		List ejes = evento.getListaCompletaEjes();
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		TurnoPk oidTurno = new TurnoPk();
		
		oidTurno.anio = turno.getAnio();
		oidTurno.idCirculo = turno.getIdCirculo();
		oidTurno.idProceso = turno.getIdProceso();
		oidTurno.idTurno = turno.getIdTurno();
		
		if (webEnglobe == null) {
			throw new EnglobeException("No hay información para poder realizar el englobe");
		}		
		
		try {
			wid = new WebSegEngPk();
			wid.idSolicitud = webEnglobe.getIdSolicitud();
			wid.idWebSegeng = webEnglobe.getIdWebSegeng();
			hermod.actualizarWebSegEng(wid, webEnglobe);

			webSegEng = hermod.consultarWebSegEng(wid);

		} catch (HermodException t) {
			throw new EnglobeException("Se presento un error al guardar los datos.", t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al guardar los datos.", e);
		}		
		

		//SE RECUPERA LA LISTA DE FOLIOS QUE SE VAN A ENGLOBAR
		//List foliosFuente = englobe.getFoliosPadre();
		//Hashtable anotacionesHeredar = englobe.getAnotacionesHeredar();
		
		List foliosFuente = new ArrayList();
		Hashtable anotacionesHeredar = new Hashtable();
		List anotacionesToHeredar = new ArrayList();
		
		//SE RECUPERA EL BOOLEAN QUE DETERMINA SI SE DEBE COPIAR O NO EL COMENTARIO EN LAS ANOTACIONES A HEREDAR 
		boolean copiarComentarioHeredadas = true;
		
		//SE LE AGREGAN AL NUEVO FOLIO LAS ANOTACIONES QUE DEBEN HEREDARSE
		//Y
		//SE RECUPERA EL HASHTABLE CON LA LISTA DE ANOTACIONES QUE DEBEN HEREDARSE PARA CADA FOLIO
		if(webSegEng.getFoliosHeredados()!=null && webSegEng.getFoliosHeredados().size()>0){
			
			Iterator itFolios  =  webSegEng.getFoliosHeredados().iterator();
			
			while(itFolios.hasNext()){
				WebFolioHeredado webFolioHeredado = (WebFolioHeredado)itFolios.next();
				Folio folio = new Folio();
				folio.setIdMatricula(webFolioHeredado.getIdMatricula());
				foliosFuente.add(folio);
				copiarComentarioHeredadas = webFolioHeredado.isCopiaComentario();
				
				List anotaHeredar = new ArrayList();
				Iterator itAnotaciones = webFolioHeredado.getAnotacionesHeredadas().iterator();
				while (itAnotaciones.hasNext()) {
					WebAnotaHereda webAnotaHereda = (WebAnotaHereda)itAnotaciones.next();
					Anotacion anotacion = new Anotacion();
					anotacion.setIdMatricula(webFolioHeredado.getIdMatricula());
					anotacion.setIdAnotacion(webAnotaHereda.getIdAnotacion());

					anotacionesHeredar.put(webFolioHeredado.getIdMatricula() , anotacion);
					AnotacionPk anotacionId = new AnotacionPk();
					anotacionId.idMatricula = webFolioHeredado.getIdMatricula();
					anotacionId.idAnotacion = webAnotaHereda.getIdAnotacion();
					try {
						Anotacion anotacionToRadicacion = forseti.getAnotacion(anotacionId);
						if (anotacionToRadicacion != null) {
							anotacion.setFechaRadicacion(anotacionToRadicacion.getFechaRadicacion());
							anotacion.setOrden(anotacionToRadicacion.getOrden());
						} 
					} catch (Throwable  eAnota) {
						ExceptionPrinter printer = new ExceptionPrinter(eAnota);
						Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
						throw new EventoException(eAnota.getMessage(), eAnota);
					}
					anotaHeredar.add(anotacion);
					anotacionesToHeredar.add(anotacion);
				}	
				anotacionesHeredar.put(webFolioHeredado.getIdMatricula() , anotaHeredar);
			}
		}		
		
		//aca deberia ordenar las anotaciones por la fecha de Radicacion
		Collections.sort(anotacionesToHeredar, new radicacionAnotacionComparator());
		
		//SE RECUPERA LA ANOTACIÓN TEMPORAL QUE DARÁ ORIGEN AL ENGLOBE
		//Anotacion anotacion = englobe.getAnotacion();
		//SE RECUPERA LA ANOTACIÓN ORIGINAL
		WebAnotacion webAnotacion = webEnglobe.getAnotacion(); 
		WebDocumento webDocumento = webAnotacion.getDocumento();

		Documento documento = new Documento();
		if(webAnotacion.getIdDocumento()!=null && !webAnotacion.getIdDocumento().equals("")){
			documento.setIdDocumento(webAnotacion.getIdDocumento());
		}else{
			documento.setComentario(webDocumento.getComentario());
			documento.setFecha(webDocumento.getFecha());
			documento.setNumero(webDocumento.getNumero());
			documento.setOficinaInternacional(webDocumento.getOficinaInternacional());
			OficinaOrigen oficinaOrigen = new OficinaOrigen();
			oficinaOrigen.setIdOficinaOrigen(webDocumento.getIdOficinaOrigen());
                        /*
                         *  @author Carlos Torres
                         *  @chage   se agrega validacion de version diferente
                         *  @mantis 0013414: Acta - Requerimiento No 069_453_Código_Notaria_NC
                         */
                        oficinaOrigen.setVersion(webDocumento.getVersion());
			documento.setOficinaOrigen(oficinaOrigen);
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setIdTipoDocumento(webDocumento.getIdTipoDocumento());
			documento.setTipoDocumento(tipoDocumento);
		}
		
		NaturalezaJuridica naturaleza = new NaturalezaJuridica();
		naturaleza.setIdNaturalezaJuridica(webAnotacion.getIdNaturalezaJuridica());
                 /**
         * @author     : Carlos Mario Torres Urina
         * @casoMantis : 0012705.
         * @actaReq    : Acta - Requerimiento No 056_453_Modificiación_de_Naturaleza_Jurídica
         * @change     : Se asigna valor para la propiedad version
	 */
                naturaleza.setVersion(webAnotacion.getVersion());
                
		Anotacion anotacion = new Anotacion();
		anotacion.setDocumento(documento);
		anotacion.setComentario(webAnotacion.getComentario());
		anotacion.setValor(new Double(webAnotacion.getValor()).doubleValue());
                anotacion.setModalidad(webAnotacion.getModalidad());
		anotacion.setNumRadicacion(turno.getIdWorkflow());
		anotacion.setIdWorkflow(turno.getIdWorkflow());
		//TFS 3576: El número de radicación y la fecha de la anotación
		//para las correcciones, serán digitadas por el usuario
		if(turno.getSolicitud() instanceof SolicitudCorreccion){
			anotacion.setNumRadicacion(webAnotacion.getNumeroRadicacion());
			anotacion.setFechaRadicacion(webAnotacion.getFechaRadicacion());
                        /*
                         * @author      :   Julio Alcázar Rivas
                         * @change      :   Se agrega la informacion de la salvedad a la anotacion
                         * Caso Mantis  :   04131
                         */
                        SalvedadAnotacion local_SalvedadAnotacion = new SalvedadAnotacion();
                        String salvedadDescripcion = evento.getSalvedadDescripcion();
                        String salvedadId = evento.getSalvedadId();
                        local_SalvedadAnotacion.setDescripcion(salvedadDescripcion);
                        local_SalvedadAnotacion.setIdSalvedad(salvedadId);
                        local_SalvedadAnotacion.setIdMatricula(webEnglobe.getIdMatriculaUbicacion());
                        Folio folio = null;
                        try {
                            folio = forseti.getFolioByID(webEnglobe.getIdMatriculaUbicacion());
                            local_SalvedadAnotacion.setIdAnotacion( String.valueOf(folio.getLastIdAnotacion()+1) );
                        } catch (Throwable ex) {
                            Logger.getLogger(ANEnglobe.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        local_SalvedadAnotacion.setNumRadicacion(turno.getIdWorkflow());

                        List local_SalvedadList = new ArrayList();
                        local_SalvedadList.add( local_SalvedadAnotacion );
                        anotacion.setSalvedades( local_SalvedadList );

		}else{
			anotacion.setNumRadicacion(turno.getIdWorkflow());
			anotacion.setIdWorkflow(turno.getIdWorkflow());
			anotacion.setFechaRadicacion(turno.getFechaInicio());
		}	
		anotacion.setTemporal(true);
		anotacion.setNaturalezaJuridica(naturaleza);
		//anotacion.setOrden(sOrden);
		
		List webCiudadanos = webAnotacion.getCiudadanos();
		Iterator itCiudadanos = webCiudadanos.iterator();
		while(itCiudadanos.hasNext()){
			WebCiudadano webCiudadano = (WebCiudadano)itCiudadanos.next();
			AnotacionCiudadano anotacionCiudadano = new AnotacionCiudadano();
			Ciudadano ciudadano = new Ciudadano();
                        ciudadano.setTipoPersona(webCiudadano.getTipoPersona());
                        ciudadano.setSexo(webCiudadano.getSexo());
			ciudadano.setApellido1(webCiudadano.getApellido1());
			ciudadano.setApellido2(webCiudadano.getApellido2());
			ciudadano.setTipoDoc(webCiudadano.getTipoDocumento());
			ciudadano.setDocumento(webCiudadano.getNumDocumento());
			ciudadano.setNombre(webCiudadano.getNombre());
			ciudadano.setSolicitante(false);
			ciudadano.setNombre(webCiudadano.getNombre());
			ciudadano.setIdCirculo(circulo.getIdCirculo());
			anotacionCiudadano.setCiudadano(ciudadano);
			anotacionCiudadano.setParticipacion(webCiudadano.getPorcentaje());
			anotacionCiudadano.setRolPersona(webCiudadano.getTipoIntervencion());
			anotacionCiudadano.setMarcaPropietario(new Integer(webCiudadano.getPropietario()).intValue());
			anotacion.addAnotacionesCiudadano(anotacionCiudadano);
		}		

		
		//SE RECUPERA EL FOLIO QUE TIENE LA INFORMACIÓN DE LA UBICACIÓN DEL NUEVO FOLIO Y DE SU UBICACIÓN
		//Folio folioUbicacion = englobe.getFolioUbicacion();
		Folio folioUbicacion = new Folio();
		folioUbicacion.setIdMatricula(webEnglobe.getIdMatriculaUbicacion());
		
		//SE RECUPERA EL FOLIO DERIVADO CON LA INFORMACIÓN DEL LOTE PARA EL NUEVO FOLIO
		//FolioDerivado foliosDerivado = englobe.getFolioDerivado();
		FolioDerivado folioDerivado = new FolioDerivado();
		WebFolioNuevo webFolioNuevo = webEnglobe.getFolioNuevo();
                folioDerivado.setHectareas(webFolioNuevo.getHectareas());
                folioDerivado.setMetros(webFolioNuevo.getMetros());
                folioDerivado.setCentimetros(webFolioNuevo.getCentimetros());
		folioDerivado.setArea(webFolioNuevo.getArea());
		folioDerivado.setDescripcion(webFolioNuevo.getDescripcion());
		folioDerivado.setLote(webFolioNuevo.getNombre());
		
		//SE RECUPERA EL BOOLEAN QUE DETERMINA SI SE DEBE COPIAR O NO EL COMENTARIO DE LA ANOTACIÓN ORIGEN EN 
		//LAS ANOTACIONES DE LOS NUEVOS FOLIOS
		//boolean copiarComentarioEnglobe = englobe.isCopiaComentarioEnglobadas();		
		long copiaAnotacionEnglobadas  = webAnotacion.getCopiaComentario();
		boolean copiarComentarioEnglobe = false;
		if(copiaAnotacionEnglobadas==1){
			copiarComentarioEnglobe = true;
		}
		

		//SE CREA UN TURNOFOLIO Y SE LE COLOCA EN EL FOLIO
		TurnoFolio tFolio = new TurnoFolio();
		tFolio.setTurno(evento.getTurno());
		folioUbicacion.addTurnosFolio(tFolio);		
		if(webFolioNuevo.getDirecciones()!=null)
			folioUbicacion.setDirecciones(direccionesEnglobe);
		//SE COLOCAN LAS ANOTACIONES CON GRAVAMENES Y MEDIDAS CAUTELARES EN AQUELLOS FOLIOS 
		//DONDE EL USUARIO NO LAS COLOCÓ EN LA CAPA DE PRESENTACIÓN.
		//SE RECUPERA LA SOLICITUD-FOLIO CON ESE ID DE MATRÍCULA PARA CREAR UN FOLIO CON LOS ID'S DEL FOLIO COMPLETOS.
		//DESCOMENTAREAR LAS SIGUIENTES LINES SI SE QUIERE HABILITAR LA FUNCIONALIDAD DESCRITA ANTERIORMENTE.
		/*Folio folioTemp = null;
		List anotacionesTemp = new ArrayList();
		List anotacionesGravamenesTemp = new ArrayList(); 
		
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();
		Iterator it = foliosFuente.iterator();

		
		while(it.hasNext()){
			anotacionesGravamenesTemp = new ArrayList();
			anotacionesTemp = new ArrayList();  
			
			Folio folioAHeredar = (Folio)it.next();
			
			Object obj = anotacionesHeredar.get(folioAHeredar.getIdMatricula());
			
			if(obj==null){
				
				try {
					Folio.ID id = new Folio.ID();
					id.idMatricula = folioAHeredar.getIdMatricula();
					id.idZonaRegistral = folioAHeredar.getIdZonaRegistral();
					
					folioTemp = forseti.getFolioByID(id);
					
					if(folioTemp == null){
						folioTemp = forseti.getFolioByID(id, usuarioSIR);
					}
					
				} catch (ForsetiException e) {
					throw new EnglobeException(e.getMessage(), e);
				} catch (Throwable e) {
					ExceptionPrinter printer = new ExceptionPrinter(e);
					Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
					throw new EventoException(e.getMessage(), e);
				}
				
				anotacionesTemp = folioTemp.getAnotaciones();
				if(anotacionesTemp!=null){
					Iterator itAnotacion = anotacionesTemp.iterator();
					while(itAnotacion.hasNext()){
						Anotacion anotacionTemp = (Anotacion)itAnotacion.next();
						if(anotacionTemp.getNaturalezaJuridica() != null && anotacionTemp.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.GRAVAMEN) || anotacionTemp.getNaturalezaJuridica().getGrupoNaturalezaJuridica().getIdGrupoNatJuridica().equals(CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR)){
							anotacionesGravamenesTemp.add(anotacionTemp);
						}
					}	
				}
				
				anotacionesHeredar.put(folioAHeredar.getIdMatricula(),anotacionesGravamenesTemp);		
			} 
		}*/
	
		try {
			folioCreado = forseti.englobarFolio(foliosFuente,folioUbicacion,usuarioSIR,anotacionesToHeredar,anotacion, folioDerivado, copiarComentarioEnglobe, copiarComentarioHeredadas, oidTurno);
		} catch (ForsetiException e) {
			
			if(e.getHashErrores()!=null && e.getHashErrores().size()>0){
				throw new EnglobeHTException(e.getHashErrores());
			}
			
			throw new EnglobeException(e.getMessage(), e);
			
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		} 
		
		//SE ELIMINAN LOS DATOS TEMPORALES DE WEBSEGREGACION
		try {
			hermod.eliminarWebSegEng(wid);
		} catch (HermodException t) {
			throw new EnglobeException("Se presento un error al eliminar los datos.", t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al eliminar los datos.", e);
		}		
		
		//SE ASOCIAN LAS NUEVAS MATRÍCULAS AL TURNO DE REGISTRO
		try {
			TurnoPk idTurno = new TurnoPk();
			idTurno.anio = turno.getAnio();
			idTurno.idCirculo = turno.getIdCirculo();
			idTurno.idProceso = turno.getIdProceso();
			idTurno.idTurno = turno.getIdTurno();
			
			hermod.addFolioToTurno(   folioCreado.getIdMatricula(), idTurno, evento.getUsuarioNeg() );
			turno = hermod.getTurno(idTurno);
			/**
			 * @author David Panesso
			 * @change 1253.CALIFICACION.FOLIOS.CERRADOS
			 * Se toma el estado del folio para manejar la excepción null para que guarde si es el folio está activo o cerrado.
			 **/
			try {
				Solicitud sol = turno.getSolicitud();
				List folios = sol.getSolicitudFolios();
				Iterator ifol = folios.iterator();
				while (ifol.hasNext()) {
					SolicitudFolio sfol = (SolicitudFolio) ifol.next();
					Folio fol = sfol.getFolio();
					FolioPk fid = new FolioPk();
					fid.idMatricula = fol.getIdMatricula();
					EstadoFolio est = new EstadoFolio();
					if (forseti.cerradoFolio(fid.idMatricula, null)) {
						est.setIdEstado(CEstadoFolio.CERRADO);
					} else {
						est.setIdEstado(CEstadoFolio.ACTIVO);
					}
					fol.setEstado(est);
					sfol.setFolio(fol);
				}
			} catch (Throwable e1) {
				e1.printStackTrace();
				throw new EventoException("No se pudo realizar la validacion. Fallo en el servicio Forseti", e1);
			}
		} catch (HermodException e) {
			throw new EnglobeException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANEnglobe.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
		
		return new EvnRespEnglobe( folioCreado, EvnRespEnglobe.ENGLOBAR_FOLIOS, turno);
	}
	
	/**
	 * Éste método elimina el objeto webEnglobe con los datos que hacen parte del englobe 
	 * @param ev
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta eliminarWebEnglobe(Evento ev) throws EventoException {
		EvnEnglobe evento = (EvnEnglobe) ev;

		WebEnglobe webEnglobe = evento.getWebEnglobe();		
		WebSegEng webSegEng = null;
		WebSegEngPk wid = null;
		
		try {
			if(webEnglobe!=null){
				wid = new WebSegEngPk();
				wid.idSolicitud = webEnglobe.getIdSolicitud();
				wid.idWebSegeng = webEnglobe.getIdWebSegeng();
				hermod.eliminarWebSegEng(wid); 
			}
		}catch(HermodException t){
			throw new EnglobeException("Se presento un error al eliminar los datos del englobe.",t);
		} catch (Throwable e) {
			throw new EnglobeException("Se presento un error al eliminar los datos del englobe.",e);
		}

		return new EvnRespEnglobe(webSegEng , EvnRespEnglobe.GUARDAR_WEB_ENGLOBE);
	}	
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta consultarUltimosPropietarios(EvnEnglobe evento) throws EventoException {
	
		Usuario usuarioNeg = evento.getUsuarioNeg();
		Folio folio = evento.getFolio();
		List listaUltimosPropietarios = null;
		List listaFoliosEnglobados = evento.getFoliosEnglobados();
		List listaUltimosPropietariosfinal = new ArrayList();
		
		for (Iterator iter = listaFoliosEnglobados.iterator(); iter.hasNext();) {
			WebFolioHeredado f = (WebFolioHeredado) iter.next();
			try {
				listaUltimosPropietarios = forseti.getCiudadanoUltimosFolio(f.getIdMatricula());
			} catch (Throwable e1) {
				ExceptionPrinter ep=new ExceptionPrinter(e1);
				Log.getInstance().error(ANEnglobe.class,"No se pudieron grabar las anotaciones temporales:"+ep.toString());
				throw new SegregacionException("No se pudieron grabar las anotaciones temporales", e1);

			}
			
			Map mapaPropietarios = new HashMap();
			
			for (int i = 0; i < listaUltimosPropietarios.size(); i++ ) {
				AnotacionCiudadano anotacionciu = (AnotacionCiudadano)listaUltimosPropietarios.get(i);
			    Ciudadano ciu = anotacionciu.getCiudadano();    
			    String key = ciu.getTipoDoc() + "-" + ciu.getDocumento();
			    if (!mapaPropietarios.containsKey(key)){
			    	mapaPropietarios.put(key,anotacionciu);	
			    }		    
			}
			
			Collection col = (Collection)mapaPropietarios.values();
				
			for (Iterator iterr = col.iterator(); iterr.hasNext();) {
				listaUltimosPropietariosfinal.add((AnotacionCiudadano) iterr.next());
			}
			
			//listaUltimosPropietarios = (List) mapaPropietarios.values();
			Collections.sort(listaUltimosPropietariosfinal, new CiudadanoComparatorVarios());
			Collections.reverse(listaUltimosPropietariosfinal);
		}
		
		
		
		EvnRespEnglobe evn = new EvnRespEnglobe("",EvnRespSegregacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setTipoEvento(EvnRespSegregacion.CONSULTAR_PROPIETARIOS_FOLIO);
		evn.setPropietariosFolios(listaUltimosPropietariosfinal);
		return evn;
	}
	
	/**
	 * @param evento
	 * @return
	 */
	private EventoRespuesta validarCiudadanos(EvnEnglobe evento) throws EventoException {
	
		List anotacionCiudadanos = evento.getAnotacionCiudadanos();
		Turno turno = evento.getTurno();
		
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
					if (turno!=null) {
						if (turno.getIdProceso() == Long.parseLong(CProceso.PROCESO_CORRECCIONES) ) {
							if (ciudTemp.getNumeroRadicacion()!=null && !ciudTemp.getNumeroRadicacion().equals(turno.getIdWorkflow())) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} 
						} else {
							if (ciudTemp.getNumeroRadicacion()!=null) {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con el turno de Correciones en Trámite: " + ciudTemp.getNumeroRadicacion());
							} else {
								throw new EventoException("El Ciudadano " + ciudTemp.getTipoDocumento() + " - " + ciudTemp.getDocumento() + " , está relacionado con un turno de Correciones en Trámite.");	
							}
						}
					}
				}
				
			}
			
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANEnglobe.class,"No se pudieron agregar los ciudadanos: "+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron agregar los ciudadanos",e1);

		}
		
		EvnRespEnglobe evn = new EvnRespEnglobe(EvnRespEnglobe.VOLVER_AGREGAR_CIUDADANOS);
		evn.setTipoEvento(EvnRespSegregacion.VOLVER_AGREGAR_CIUDADANOS);
		evn.setListaCompletaCiudadanos(evento.getListaCompletaCiudadanos());
		return evn;
	}

}
