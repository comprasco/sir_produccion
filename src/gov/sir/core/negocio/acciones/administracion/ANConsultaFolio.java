package gov.sir.core.negocio.acciones.administracion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gov.sir.core.eventos.administracion.EvnConsultaFolio;
import gov.sir.core.eventos.administracion.EvnRespConsultaFolio;
import gov.sir.core.negocio.acciones.excepciones.AnularFolioException;
import gov.sir.core.negocio.acciones.excepciones.ConsultaFolioAdministracionException;
import gov.sir.core.negocio.acciones.excepciones.FolioNoCerradoException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;

import gov.sir.core.negocio.modelo.Anotacion;
import gov.sir.core.negocio.modelo.AnotacionPk;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.CirculoPk;
import gov.sir.core.negocio.modelo.EstadoFolio;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.OPLookupCodes;
import gov.sir.core.negocio.modelo.SalvedadAnotacion;
import gov.sir.core.negocio.modelo.Traslado;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CCriterio;
import gov.sir.core.negocio.modelo.constantes.CEstadoFolio;
import gov.sir.core.negocio.modelo.constantes.CGrupoNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.COPLookupTypes;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CProceso;

import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMP;
import gov.sir.core.negocio.modelo.jdogenie.FolioDatosTMPPk;
import gov.sir.core.negocio.modelo.util.Log;

import gov.sir.fenrir.interfaz.FenrirServiceInterface;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import org.auriga.core.modelo.transferObjects.Rol;

import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;

import org.auriga.util.ExceptionPrinter;

/**
 * Esta acción de negocio es responsable de recibir los eventos de tipo
 * <CODE>EvnConsultaFolio</CODE> y generar eventos de respuesta del tipo <CODE>EvnRespConsultaFolio</CODE>
 * Esta acción de negocio se encarga de atender todas las solicitudes relacionadas con las consultas 
 * de los datos del folio.
 * @author ppabon
 */
public class ANConsultaFolio extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;
	
	/** Instancia de la interfaz de Hermod */
	private HermodServiceInterface hermod;

	/** Instancia de la intefaz de Fenrir  */
	private FenrirServiceInterface fenrir;	

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANConsultaFolio.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *del servicio que necesita
	 */
	public ANConsultaFolio() throws EventoException {
		super();
		service = ServiceLocator.getInstancia();

		try {

			forseti = (ForsetiServiceInterface) service.getServicio("gov.sir.forseti");
			hermod = (HermodServiceInterface) service.getServicio("gov.sir.hermod");
			fenrir = (FenrirServiceInterface) service.getServicio("gov.sir.fenrir");

		} catch (ServiceLocatorException e) {
			throw new ServicioNoEncontradoException("Error instanciando el servicio.", e);
		}

		if (forseti == null) {
			throw new ServicioNoEncontradoException("Servicio forseti no encontrado");
		}
		if (hermod == null) {
			throw new ServicioNoEncontradoException("Servicio Hermod no encontrado");
		}
		if (fenrir == null) {
			throw new ServicioNoEncontradoException("Servicio fenrir no encontrado");
		}	

	}

	/**
	* Recibe un evento de consulta de folios y devuelve un evento de respuesta
	* @param e el evento recibido. Este evento debe ser del tipo <CODE>EvnConsultaFolio</CODE>
	* @throws EventoException cuando ocurre un problema que no se pueda manejar
	* @return el evento de respuesta. Este evento debe ser del tipo <CODE>EvnRespConsultaFolio</CODE>
	* @see gov.sir.core.eventos.comun.EvnConsultaFolio
	* @see gov.sir.core.eventos.comun.EvnRespConsultaFolio
	*/
	public EventoRespuesta perform(Evento e) throws EventoException {
		EvnConsultaFolio evento = (EvnConsultaFolio) e;

		if ((evento == null) || (evento.getTipoEvento() == null)) {
			return null;
		}

		if (evento.getTipoEvento().equals(EvnConsultaFolio.CONSULTAR_FOLIO)) {
			return consultaFolio(evento);
		}else if (evento.getTipoEvento().equals(EvnConsultaFolio.ANULAR_FOLIO)) {
			return anularFolio(evento);
		}else if (evento.getTipoEvento().equals(EvnConsultaFolio.CONSULTAR_FOLIO_REABRIR)) {
			EvnRespConsultaFolio eventoRTA = (EvnRespConsultaFolio)consultaFolioBasico(evento);
			if(!eventoRTA.getFolio().getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
					EvnRespConsultaFolio eventoRTATemp = (EvnRespConsultaFolio)consultaFolioBasicoTemporal(evento);
					FolioDatosTMP folioTMP = null;
					try{
						folioTMP = (FolioDatosTMP)eventoRTATemp.getPayload();
					}
					catch (Throwable e1) {
						ExceptionPrinter printer = new ExceptionPrinter(e1);
					}
					if(folioTMP == null || eventoRTA == null)
						throw new FolioNoCerradoException("El folio con número de matrícula "+eventoRTA.getFolio().getIdMatricula()+" NO se encuentra cerrado.");
					else{ 
						if(folioTMP.getEstado() != null){
							if (!folioTMP.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
									throw new FolioNoCerradoException("El folio con número de matrícula "+eventoRTA.getFolio().getIdMatricula()+" NO se encuentra cerrado.");		
							}
						} else {
							throw new FolioNoCerradoException("El folio con número de matrícula "+eventoRTA.getFolio().getIdMatricula()+" NO se encuentra cerrado.");
						}
						EstadoFolio estado= new EstadoFolio();
						estado.setIdEstado(folioTMP.getEstado().getIdEstado());
						estado.setNombre(folioTMP.getEstado().getNombre());
						estado.setComentario(folioTMP.getComentarioCambioEstado());
						eventoRTA.getFolio().setEstado(estado);						
					}
			}
			return eventoRTA;
		} else if (evento.getTipoEvento().equals(EvnConsultaFolio.DESBLOQUEAR_FOLIO_RESTITUCION)) {
			return desbloquearFolioRestitucion(evento);
		}


		return null;
	}
	
	/**
	 * Método que anula un folio.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta anularFolio(EvnConsultaFolio evento) throws EventoException {
		
		Usuario usuNeg = evento.getUsuarioSIR();
		
		Folio fol = evento.getFolio();
		FolioPk fid = new FolioPk();
		fid.idMatricula = fol.getIdMatricula();
		
		String razonAnulacion = evento.getRazonAnulacion();
		


		try {
			
			
			EstadoFolio estado = new EstadoFolio();
			estado.setIdEstado(CEstadoFolio.ANULADO);
			estado.setComentario(razonAnulacion);
			fol.setEstado(estado);
			forseti.updateFolio(fol, usuNeg, null, false);
			
			fol = forseti.getFolioByID(fid, usuNeg);
			//TFS 5362: SI SE NECESITAN LAS ANOTACIONES , SE CARGAN POR APARTE
			if(fol.getAnotaciones()==null || fol.getAnotaciones().isEmpty()){
				fol.setAnotaciones(forseti.getAnotacionesFolioTMP(fid));
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new AnularFolioException(e.getMessage(), e);
		}
		
		
		return new EvnRespConsultaFolio(fol, EvnRespConsultaFolio.ANULAR_FOLIO);
	}
	
	/**
	 * Método que anula un folio.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta desbloquearFolioRestitucion(EvnConsultaFolio evento) throws EventoException {
		
		Usuario usuNeg = evento.getUsuarioSIR();
		Folio folio = null;
		List turnosAsociados = null;
		boolean hasInformacionTemporal = false;
		
		String idMatricula = evento.getMatricula();
		FolioPk idFolio = new FolioPk();
		idFolio.idMatricula = idMatricula;
		
		
		try {
			
			folio = forseti.getFolioByMatricula(idMatricula);
			
			// Se verifica si el folio tiene información temporal
			hasInformacionTemporal = forseti.hasDatosTemporalesFolio(idFolio);
			
			if (hasInformacionTemporal) {
				throw new AnularFolioException("El folio " + idMatricula + " tiene información Temporal, No se puede desbloquear");
			}
			
			//	Obtener turnos asociados a la matricula y mirar cual esta en tramite.
			turnosAsociados = hermod.getTurnosByMatricula(idMatricula);
			
		    Iterator itTurnosAsociados = turnosAsociados.iterator();
			while (itTurnosAsociados.hasNext()) {
				Turno turno  = (Turno) itTurnosAsociados.next();
				String faseTurno = "";
				if (turno.getFechaFin() == null) {
					faseTurno = turno.getIdFase();
					//verificar que no este en mayor valor
					if (faseTurno.equals(CFase.PMY_CUSTODIA) ||
							faseTurno.equals(CFase.PMY_NOTIFICAR_CIUDADANO) ||
							faseTurno.equals(CFase.PMY_REGISTRAR) ||
							faseTurno.equals(CFase.PMY_NOTIFICAR_FUNCIONARIO)) {
						throw new AnularFolioException("El folio " + idMatricula + " tiene el turno " + turno.getIdWorkflow() + " en tramite en Pago Mayor Valor");
					} 
					
					if (String.valueOf(turno.getIdProceso()).equals(CProceso.PROCESO_DEVOLUCIONES)) {
						throw new AnularFolioException("El folio " + idMatricula + " tiene el turno " + turno.getIdWorkflow() + " en tramite de Devoluciones");
					}
				}
			}
			
			boolean desbloqueadoFolio = forseti.desbloquearFolio(folio);
			
		
		} catch (AnularFolioException ee) {
			throw ee;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al desbloquear folio " + printer.toString());
			throw new AnularFolioException(e.getMessage(), e);
		}
			
		EvnRespConsultaFolio resuesta = new EvnRespConsultaFolio(folio, EvnRespConsultaFolio.DESBLOQUEO_FOLIO_RESTIRUCION_OK);
		return resuesta; 
	}

	/**
	 * Método que permite consultar un folio.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultaFolio(EvnConsultaFolio evento) throws EventoException {
		
		if(evento.isConsultarDefinitivo()){
			return consultaFolioDefinitivo(evento);
		}

		String matricula = evento.getMatricula();
		Circulo circulo = evento.getCirculo();
		org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();
                /**
                *@Autor: Edgar Lora
                *@Mantis: 11599
                *@Requerimiento: 085_151
                */
                Usuario usuarioNeg = evento.getUsuarioSIR();

		//VARIBLES DE RETORNO 		
		Folio folio = null;
                List historialAreas = null;
		List foliosHijos = null;
		List foliosPadre = null;
		long numeroGravamenes = 0;
		List gravamenes = null;
		long numeroMedidasCautelares = 0;
		List medidasCautelares = null;
		long numeroFalsaTradicion = 0;
		List falsaTradicion = null;
		List ordenFalsaTradicion = new ArrayList();
		List anotacionesInvalidas = null;
		List ordenAnotacionesInvalidas = new ArrayList();
		List salvedadesAnotaciones = null;
		List cancelaciones = null;
		Usuario usuarioBloqueo = null;
		Turno turnoTramite = null;
		Turno turnoDeuda = null;
		List anotacionesPatrimonioFamiliar = null;
		long numanotacionesPatrimonioFamiliar = 0;
		List anotacionesAfectacionVivienda = null;
		long munanotacionesAfectacionVivienda = 0;
		List anotacionesAfectacionVivienda2 = null;
		long munanotacionesAfectacionVivienda2 = 0;
		List turnosFolioTramite = null;
                /**
                *@Autor: Edgar Lora
                *@Mantis: 11599
                *@Requerimiento: 085_151
                */
                List foliosDerivadoPadre = null;
                List foliosDerivadoHijo = null;
                
		try {
			if (matricula != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = matricula;
				boolean puedeConsultarTurno = false;

				//SI SE QUIERE CONSULTAR UN TURNO DE OTRO CIRCULO 
				if (circulo != null && !matricula.startsWith(circulo.getIdCirculo())) {

					//SE DEBE VERIFICAR QUE TENGA EL ROL QUE PUEDE HACERLO.
					//SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
					List rolesValidos = hermod.getOPLookupCodes(COPLookupTypes.ROLES_CONSULTA_FOLIOS);

					//SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
					long idUsuario = fenrir.darIdUsuario(user.getUsuarioId());
					List roles = fenrir.darRolUsuario(idUsuario);
					List rolesString = new ArrayList();

					Iterator itRoles = roles.iterator();
					while (itRoles.hasNext()) {
						Rol rol = (Rol) itRoles.next();
						rolesString.add(rol.getRolId());
					}

					Iterator it = rolesValidos.iterator();
					while (it.hasNext()) {
						OPLookupCodes code = (OPLookupCodes) it.next();
						if (rolesString.contains(code.getValor())) {
							puedeConsultarTurno = true;
							break;
						}
					}

					if (!puedeConsultarTurno) {
						throw new ConsultaFolioAdministracionException("No puede consultar folios de otros circulos.");
					}

				} else {
					//SI EL TURNO QUE SE QUIERE CONSULTAR ES DEL MISMO CIRCULO, NO HAY NINGÚN PROBLEMA.
					puedeConsultarTurno = true;
				}

				//Consulta si el folio esta asociado a un turno activo que se encuentre en calificación

				boolean estaEnCalificacion = forseti.isFolioInTurnoCalificacion(id);

				//CONSULTA DEL FOLIO
				if (estaEnCalificacion) {
					folio = forseti.getFolioByIDSinAnotaciones(id, null);
				} else {
					folio = forseti.getFolioByIDSinAnotaciones(id, null);
				}
				

				//CONSULTA DE FOLIO DE MAYOR EXTENSIÓN? Y NÚMERO DE ANOTACIONES DEL FOLIO
				boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
				long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());

				//CONSULTA DE LOS FOLIOS HIJOS
				if (estaEnCalificacion) {
					foliosHijos = forseti.getFoliosHijos(id, null);
				} else {
					foliosHijos = forseti.getFoliosHijos(id, null);
				}

				//CONSULTA DE LOS FOLIOS PADRE
				if (estaEnCalificacion) {
					foliosPadre = forseti.getFoliosPadre(id, null);
				} else {
					foliosPadre = forseti.getFoliosPadre(id, null);
				}

				//CONSULTA DE GRAVAMENES
				numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
				if (estaEnCalificacion) {
					gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, null, true);
				} else {
					gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, null, true);
				}

				//CONSULTA DE MEDIDAS CAUTELARES
				numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				if (estaEnCalificacion) {
					medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, null, true);
				} else {
					medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, null, true);
				}
				
				//CONSULTA ANOTACIONES DE FALSE TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				if (estaEnCalificacion) {
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);
				} else {
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);
				}
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());	
					}
				}
				
				//CONSULTA ANOTACIONES INVALIDAS
				if (estaEnCalificacion) {
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id);
				} else {
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id);
				}
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());	
					}
				}	
				
				//CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
//				 CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
				

				//CONSULTA DE SALVEDADES
				if (estaEnCalificacion) {
					salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, null);
				} else {
					salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, null);
				}
				

				//CONSULTA DE CANCELACIONES
				if (estaEnCalificacion) {
					cancelaciones = forseti.getAnotacionesConCancelaciones(id, null);
				} else {
					cancelaciones = forseti.getAnotacionesConCancelaciones(id, null);
				}

				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE EN TRAMITE UN FOLIO
				turnoTramite = forseti.getTurnoTramiteFolio(id.idMatricula);

				//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
				usuarioBloqueo = forseti.getBloqueoFolio(id);

				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE DEUDAS PENDIENTES, REFERENTES A UN FOLIO			
				turnoDeuda = forseti.getTurnoDeudaFolio(id);
				
				//OBTENER TURNOS EN TRAMITE PARA EL FOLIO EN EL SISTEMA FOLIO
				if(matricula.split("-").length < 3){
					Folio folio2 = new Folio();
					folio2.setIdMatricula(matricula);
					turnosFolioTramite = hermod.getTurnosFolioTramite(folio2);
				}else{
					turnosFolioTramite = new ArrayList();
				}
                                
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                boolean usuarioTieneBloqueo = false;
                                if(usuarioBloqueo!=null && usuarioNeg!=null && usuarioBloqueo.getUsername().equals(usuarioNeg.getUsername())){
                                        usuarioTieneBloqueo = true;				
                                }

                                if (usuarioTieneBloqueo) {
                                        foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id, usuarioNeg);
                                } else {
                                        foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id);
                                }

                                if (usuarioTieneBloqueo) {
                                        foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(id, usuarioNeg);
                                } else {
                                        foliosDerivadoHijo = forseti.getFoliosDerivadoHijos(id);
                                }
                                
                                /**
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                Traslado trasladoOrigen =  hermod.getFolioOrigenTraslado(matricula);
                                Traslado trasladoDestino =  hermod.getFolioDestinoTraslado(matricula);
                                 Circulo circuloorigen = null;
                                 Circulo circulodestino = null;
                                if(trasladoOrigen != null){
                                    CirculoPk circuloPk = null;
                                    if(!trasladoOrigen.getFolioDestino().substring(0, 3).equals("FOL")){
                                        circuloPk = new CirculoPk(trasladoOrigen.getFolioDestino().substring(0, 3));
                                    }else{
                                        String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                                        circuloPk = new CirculoPk(idCirculo);
                                    }
                                    circulodestino = forseti.getCirculo(circuloPk);
                                }                                 
                                if(trasladoDestino != null){
                                    String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                                    CirculoPk circuloPk = new CirculoPk(idCirculo);
                                    circuloorigen = forseti.getCirculo(circuloPk);
                                }
                                
                                String idMatricula = folio.getIdMatricula();
                                historialAreas = forseti.getHistorialArea(idMatricula);
				//return new EvnRespConsultaFolio(folio, null, null, null, null, null, null, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO);
				EvnRespConsultaFolio resp = new EvnRespConsultaFolio(historialAreas, folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion , ordenAnotacionesInvalidas, salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO, turnoDeuda, turnoTramite, usuarioBloqueo);
                                resp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				resp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
                                /**
                                *@Autor: Edgar Lora
                                *@Mantis: 11599
                                *@Requerimiento: 085_151
                                */
                                resp.setFoliosDerivadoPadre(foliosDerivadoPadre);
                                resp.setFoliosDerivadoHijo(foliosDerivadoHijo);
				resp.setTurnosFolioTramite(turnosFolioTramite);                             
                                /**
                                 * @author     : Carlos Torres
                                 * Caso Mantis : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                 */
                                resp.setTrasladoDestino(trasladoDestino);
                                resp.setTrasladoOrigen(trasladoOrigen);
                                resp.setCirculoDestino(circulodestino);
                                resp.setCirculoOrigen(circuloorigen);
                                
				return resp;
			} else {
				throw new EventoException("Se necesita saber la matrícula a consultar");
			}
		} catch (ConsultaFolioAdministracionException e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw e;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Método que permite consultar un folio definitivo, consulta los cambios temporales únicamente 
	 * si el usuario es el dueño del bloqueo del folio.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultaFolioDefinitivo(EvnConsultaFolio evento) throws EventoException { 

		//SE VALIDA QUE LLEGUE EL USUARIO QUE ESTA INTENTANDO REALIZAR LA CONSULTA
		Usuario usuarioNegocio = evento.getUsuarioSIR();
		if(usuarioNegocio == null){
			throw new ConsultaFolioAdministracionException("No se encuentra el usuario para determinar el bloqueo de los folios.");
		}

		//SE VALIDA QUE LLEGUE LA MATRÍCULA A CONSULTAR
		String matricula = evento.getMatricula();
		if (matricula == null) {
			throw new ConsultaFolioAdministracionException("Se necesita saber la matrícula a consultar.");			
		}		
		
		//VARIABLES DE ENTRADA
		Circulo circulo = evento.getCirculo();
		org.auriga.core.modelo.transferObjects.Usuario user = evento.getUsuario();		

		//VARIBLES DE RETORNO 		
		Folio folio = null;
                List historialAreas = null;
		List foliosHijos = null;
		List foliosPadre = null;
		List foliosDerivadoHijos = null;
		List foliosDerivadoPadre = null;
		long numeroGravamenes = 0;
		List gravamenes = null;
		long numeroMedidasCautelares = 0;
		List medidasCautelares = null;
		long numeroFalsaTradicion = 0;
		List falsaTradicion = null;
		List ordenFalsaTradicion = new ArrayList();
		List anotacionesInvalidas = null;
		List ordenAnotacionesInvalidas = new ArrayList();		
		List salvedadesAnotaciones = null;
		List cancelaciones = null;
		Usuario usuarioBloqueo = null;
		Turno turnoTramite = null;
		Turno turnoDeuda = null;		
		List anotacionesPatrimonioFamiliar = null;
		long numanotacionesPatrimonioFamiliar = 0;
		List anotacionesAfectacionVivienda = null;
		long munanotacionesAfectacionVivienda = 0;
		List anotacionesAfectacionVivienda2 = null;
		long munanotacionesAfectacionVivienda2 = 0;
		List turnosFolioTramite = null;

		try {

				boolean usuarioTieneBloqueo = false;

				FolioPk id = new FolioPk();
				id.idMatricula = matricula;
				
				//SI EL FOLIO NO EXISTE SE VERIFICA SI ESTA EN LAS TABLAS SIR_MIG_REL_TURNO_FOLIO Ó
				//SIR_MIG_TRAMITE_TURNO_FOLIO.
				boolean existefolio = forseti.existeFolioIncluyendoTemporales(matricula);
				
				if(!existefolio){
					Folio folioTemp = new Folio();
					folioTemp.setIdMatricula(matricula);
					
					//SI EL FOLIO NO EXISTE SE VALIDA SI EXISTE EN  
					boolean isFolioSirMigTurnoFolioTramite = hermod.isFolioSirMigTurnoFolioTramite(folioTemp);
					if(isFolioSirMigTurnoFolioTramite){
						throw new ConsultaFolioAdministracionException("El folio se encuentra en trámite en el sistema FOLIO.");
					}
					
					//SI EL FOLIO NO EXISTE SE VALIDA SI EXISTE EN  
					boolean isFolioSirMigTurnoFolio = hermod.isFolioSirMigTurnoFolio(folioTemp);
					if(isFolioSirMigTurnoFolio){
						throw new ConsultaFolioAdministracionException("El folio aún se encuentra sin migrar a SIR.");
					}
				}
				
				//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
				usuarioBloqueo = forseti.getBloqueoFolio(id);
				
				
				//SE VERIFICA QUE EL USUARIO QUE QUIERE HACER LA CONSULTA ES EL DUEÑO DEL BLOQUEO, PARA CARGARLE TAMBIÉN 
				//LAS COSAS DEFINITIVAS, SINO ÚNICAMENTE SE CARGAN LAS COSAS TEMPORALES.
				if(usuarioBloqueo!=null && usuarioNegocio!=null && usuarioBloqueo.getUsername().equals(usuarioNegocio.getUsername())){
					usuarioTieneBloqueo = true;				
				}
				
				
				boolean puedeConsultarTurno = false;
				//SI SE QUIERE CONSULTAR UN TURNO DE OTRO CIRCULO 
				if (circulo != null && !matricula.startsWith(circulo.getIdCirculo())) {

					//SE DEBE VERIFICAR QUE TENGA EL ROL QUE PUEDE HACERLO.
					//SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
					List rolesValidos = hermod.getOPLookupCodes(COPLookupTypes.ROLES_CONSULTA_FOLIOS);

					//SE CONSULTAN LOS ROLES QUE PUEDEN CONSULTAR TURNOS DE CUALQUIER CIRCULO
					long idUsuario = fenrir.darIdUsuario(user.getUsuarioId());
					List roles = fenrir.darRolUsuario(idUsuario);
					List rolesString = new ArrayList();

					Iterator itRoles = roles.iterator();
					while (itRoles.hasNext()) {
						Rol rol = (Rol) itRoles.next();
						rolesString.add(rol.getRolId());
					}

					Iterator it = rolesValidos.iterator();
					while (it.hasNext()) {
						OPLookupCodes code = (OPLookupCodes) it.next();
						if (rolesString.contains(code.getValor())) {
							puedeConsultarTurno = true;
							break;
						}
					}

					if (!puedeConsultarTurno) {
						throw new ConsultaFolioAdministracionException("No puede consultar folios de otros circulos.");
					}

				} else {
					//SI EL TURNO QUE SE QUIERE CONSULTAR ES DEL MISMO CIRCULO, NO HAY NINGÚN PROBLEMA.
					puedeConsultarTurno = true;
				}


				//CONSULTA DEL FOLIO
				if (usuarioTieneBloqueo) {
					folio = forseti.getFolioByIDSinAnotaciones(id, usuarioNegocio);
				} else {
					folio = forseti.getFolioByIDSinAnotaciones(id);
				}
				
				if(folio == null){
					throw new ConsultaFolioAdministracionException("El folio que quiere consultar no existe.");					
				}
				
				//CONSULTA DE FOLIO DE MAYOR EXTENSIÓN Y NÚMERO DE ANOTACIONES DEL FOLIO
				boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
				long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());

				//CONSULTA DE LOS FOLIOS HIJOS
				if (usuarioTieneBloqueo) {
					foliosHijos = forseti.getFoliosHijosOrdenAnotacion(id, usuarioNegocio);
				} else {
					foliosHijos = forseti.getFoliosHijosOrdenAnotacion(id);
				}

				//CONSULTA DE LOS FOLIOS PADRE
				if (usuarioTieneBloqueo) {
					foliosPadre = forseti.getFoliosPadre(id, usuarioNegocio);
				} else {
					foliosPadre = forseti.getFoliosPadre(id);
				}
				
				//Se consulta el objeto FolioDerivado
				if (usuarioTieneBloqueo) {
					foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id, usuarioNegocio);
				} else {
					foliosDerivadoPadre = forseti.getFoliosDerivadoPadre(id);
				}
				
				if (usuarioTieneBloqueo) {
					foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(id, usuarioNegocio);
				} else {
					foliosDerivadoHijos = forseti.getFoliosDerivadoHijos(id);
				}
				
				//CONSULTA DE GRAVAMENES
				numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
				if (usuarioTieneBloqueo) {
					gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, usuarioNegocio, true);
				} else {
					gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes, true);
				}

				//CONSULTA DE MEDIDAS CAUTELARES
				numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				if (usuarioTieneBloqueo) {
					medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, usuarioNegocio, true);
				} else {
					medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares, true);
				}
				
				//CONSULTA ANOTACIONES DE FALSA TRADICION
				numeroFalsaTradicion = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
				if (usuarioTieneBloqueo) {
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, usuarioNegocio, true);
				} else {
					falsaTradicion = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);
				}
				if(falsaTradicion != null){
					Iterator it = falsaTradicion.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenFalsaTradicion.add(anotTemp.getOrden());	
					}
				}		
				
				//CONSULTA ANOTACIONES INVALIDAS
				if (usuarioTieneBloqueo) {
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id, usuarioNegocio);
				} else {
					anotacionesInvalidas = forseti.getAnotacionesInvalidas(id);
				}
				if(anotacionesInvalidas != null){
					Iterator it = anotacionesInvalidas.iterator();
					while(it.hasNext()){
						Anotacion anotTemp = (Anotacion)it.next();
						ordenAnotacionesInvalidas.add(anotTemp.getOrden());	
					}
				}	
				
				//	CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
				numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
				anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
				
				//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
				munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
				anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
				
				// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
				munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
				anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(id, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
				
				anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);
				
				//CONSULTA DE SALVEDADES
				if (usuarioTieneBloqueo) {
					salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id, usuarioNegocio);
				} else {
					salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(id);
				}
				
				//Compara las salvedades contra el modelo definitivo de salvedades y 
				//asigna el atributo temporal según si la salvedad es temporal
				if (salvedadesAnotaciones != null)
				{
					for (Iterator itSalvAnot = salvedadesAnotaciones.iterator(); itSalvAnot.hasNext();)
					{
						Anotacion anot = (Anotacion)itSalvAnot.next();
						List salvedades = anot.getSalvedades();
						AnotacionPk idAnot = new AnotacionPk();
						idAnot.idAnotacion =  anot.getIdAnotacion();
						idAnot.idMatricula = anot.getIdMatricula();
						List salveList2 = forseti.getSalvedadesAnotacion(idAnot);
						if (salvedades != null)
						{
							for (Iterator itSalvedades = salvedades.iterator(); itSalvedades.hasNext(); )
							{
								SalvedadAnotacion salvedad = (SalvedadAnotacion)itSalvedades.next();
								salvedad.setTemporal(true);
								if (salveList2 != null)
								{
									for (Iterator itSalveList2 = salveList2.iterator(); itSalveList2.hasNext(); )
									{
										SalvedadAnotacion salve2 = (SalvedadAnotacion)itSalveList2.next(); 
										if (salve2.getIdAnotacion().equals(salvedad.getIdAnotacion()) 
												&& salve2.getIdMatricula().equals(salvedad.getIdMatricula())
												&& salve2.getIdSalvedad().equals(salvedad.getIdSalvedad()))
										{
											salvedad.setTemporal(false);
										}
									}
								}
							}
						}
						
					}
				}

				//CONSULTA DE CANCELACIONES
				if (usuarioTieneBloqueo) {
					cancelaciones = forseti.getAnotacionesConCancelaciones(id, usuarioNegocio);
				} else {
					cancelaciones = forseti.getAnotacionesConCancelaciones(id);
				}

				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE EN TRAMITE UN FOLIO
				turnoTramite = forseti.getTurnoTramiteFolio(id.idMatricula);

				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE DEUDAS PENDIENTES, REFERENTES A UN FOLIO			
				turnoDeuda = forseti.getTurnoDeudaFolio(id);
				
				//OBTENER TURNOS EN TRAMITE PARA EL FOLIO EN EL SISTEMA FOLIO
				Folio folio2 = new Folio();
				folio2.setIdMatricula(matricula);
				turnosFolioTramite = hermod.getTurnosFolioTramite(folio2);

                                /*
                                * @author      : Julio Alcázar Rivas
                                * @change      : Consulta de informacion de traslado para ver detalles de folio
                                * @revision    : Se tiene en cuenta que los circulos pueden ser trasladados a circulos de Folio
                                * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                */
                                Traslado trasladoOrigen =  hermod.getFolioOrigenTraslado(matricula);
                                /**
                                * @Autor: Santiago Vásquez
                                * @Change: 1478.AJUSTE TRASLADO SIR - FOLIO
                                */
                                Traslado trasladoDestino =  hermod.getFolioDestinoTraslado(matricula);
                                Circulo circuloorigen = null;
                                Circulo circulodestino = null;
                                if(trasladoOrigen != null){
                                    CirculoPk circuloPk = null;
                                    if(!trasladoOrigen.getFolioDestino().substring(0, 3).equals("FOL")){
                                        circuloPk = new CirculoPk(trasladoOrigen.getFolioDestino().substring(0, 3));
                                    }else{
                                          /* @author      : Carlos Mario Torres Urina
                                           * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                           */
                                        if (trasladoDestino != null) {
                                            /**
                                            * @Autor: Santiago Vásquez
                                            * @Change: 1478.AJUSTE TRASLADO SIR - FOLIO
                                            */
                                            //String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                                            String idCirculo = trasladoOrigen.getFolioDestino().substring(trasladoOrigen.getFolioDestino().length() - 3, trasladoOrigen.getFolioDestino().length());
											circuloPk = new CirculoPk(idCirculo);
                                        } else {
                                            String folioDestino = trasladoOrigen.getFolioDestino();
                                            String idCirculo = folioDestino.substring(5);
                                            circuloPk = new CirculoPk(idCirculo);
                                        }
                                    }
                                    circulodestino = forseti.getCirculo(circuloPk);
                                }                                 
                                if(trasladoDestino != null){
                                  /* @author      : Carlos Mario Torres Urina
                                   * Caso Mantis  : 14985: Acta - Requerimiento 028_589_Formato_de_certificado_expedido_para_matrículas_trasladadas
                                   */
                                    String idCirculo = trasladoDestino.getFolioOrigen().getCirculo().substring(trasladoDestino.getFolioOrigen().getCirculo().length() - 3,trasladoDestino.getFolioOrigen().getCirculo().length());
                                    CirculoPk circuloPk = new CirculoPk(idCirculo);
                                    circuloorigen = forseti.getCirculo(circuloPk);
                                }
                                
                                String idMatricula = folio.getIdMatricula();
                                historialAreas = forseti.getHistorialArea(idMatricula);
				//return new EvnRespConsultaFolio(folio, null, null, null, null, null, null, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO);
				EvnRespConsultaFolio resp = new EvnRespConsultaFolio(historialAreas, folio, foliosPadre, foliosHijos, gravamenes, medidasCautelares, ordenFalsaTradicion, ordenAnotacionesInvalidas,  salvedadesAnotaciones, cancelaciones, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO, turnoDeuda, turnoTramite, usuarioBloqueo);
                                resp.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
				resp.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
				resp.setFoliosDerivadoHijo(foliosDerivadoHijos);
				resp.setFoliosDerivadoPadre(foliosDerivadoPadre);
				resp.setTurnosFolioTramite(turnosFolioTramite);
                                /*
                                * @author      : Julio Alcázar Rivas
                                * @change      : Set de informacion de traslado para ver detalles de folio
                                * Caso Mantis  : 0007676: Acta - Requerimiento No 247 - Traslado de Folios V2
                                */
                                resp.setTrasladoDestino(trasladoDestino);
                                resp.setTrasladoOrigen(trasladoOrigen);
                                resp.setCirculoDestino(circulodestino);
                                resp.setCirculoOrigen(circuloorigen);
				return resp;

		} catch (ConsultaFolioAdministracionException e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw e;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new ConsultaFolioAdministracionException(e.getMessage(), e);
		}
	}	
	
	
	
	/**
	 * Método que permite consultar un folio.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultaFolioBasico(EvnConsultaFolio evento) throws EventoException {

		String matricula = evento.getMatricula();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();
		
		//VARIBLES DE RETORNO 		
		Folio folio = null;
                List historialAreas = null;
		long numeroGravamenes = 0;
		List gravamenes = null;
		long numeroMedidasCautelares = 0;
		List medidasCautelares = null;
		Usuario usuarioBloqueo=null;
		Turno turnoTramite=null;
		Turno turnoDeuda=null;
		
		
		try {
			if (matricula != null) {

				FolioPk id = new FolioPk();
				id.idMatricula = matricula;
 
				//CONSULTA DEL FOLIO
			
				folio = forseti.getFolioByIDSinAnotaciones(id);
				
				if(folio==null){
					throw new EventoException("El folio con la matrícula "+matricula+" no existe en definitivo");
				}
				
				//CONSULTA DE FOLIO DE MAYOR EXTENSIÓN? Y NÚMERO DE ANOTACIONES DEL FOLIO
				boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
				long numeroAnotaciones = forseti.getCountAnotacionesFolio(folio.getIdMatricula());


				//CONSULTA DE GRAVAMENES
				numeroGravamenes = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN);
				gravamenes = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN, 0, (int) numeroGravamenes,true);
				
				
				//CONSULTA DE MEDIDAS CAUTELARES
				numeroMedidasCautelares = forseti.getCountAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
				medidasCautelares = forseti.getAnotacionesFolio(id, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR, 0, (int) numeroMedidasCautelares,true);
				

				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE EN TRAMITE UN FOLIO
				turnoTramite=forseti.getTurnoTramiteFolio(id.idMatricula);
			
				//OBTENER INFORMACIÓN DEL USUARIO QUE TIENE BLOQUEADO UN TURNO			
				usuarioBloqueo=forseti.getBloqueoFolio(id);
			
				//OBTENER INFORMACIÓN DEL TURNO QUE TIENE DEUDAS PENDIENTES, REFERENTES A UN FOLIO			
				turnoDeuda=forseti.getTurnoDeudaFolio(id);
                                 
                                String idMatricula = folio.getIdMatricula();
                                historialAreas = forseti.getHistorialArea(idMatricula);
				//return new EvnRespConsultaFolio(folio, null, null, null, null, null, null, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO);
                                
                                EvnRespConsultaFolio resp = new EvnRespConsultaFolio(historialAreas, folio, null, null, gravamenes, medidasCautelares, null, null, null, null, numeroAnotaciones, esMayorExtension, EvnRespConsultaFolio.CONSULTAR_FOLIO_REABRIR_OK, turnoDeuda, turnoTramite, usuarioBloqueo);
                                return resp;
			} else {
				throw new EventoException("Se necesita saber la matrícula a consultar");
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new EventoException("Error en la consulta de folio. ", e);
		}
	}
	
	/**
	 * Método que permite consultar un folio en Folio_Datos_tmp.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta consultaFolioBasicoTemporal(EvnConsultaFolio evento) throws EventoException {

		String matricula = evento.getMatricula();
		gov.sir.core.negocio.modelo.Usuario u = evento.getUsuarioSIR();
		
		//VARIBLES DE RETORNO 		
		FolioDatosTMP folio = null;
                List historialAreas = null;
		long numeroGravamenes = 0;
		List gravamenes = null;
		long numeroMedidasCautelares = 0;
		List medidasCautelares = null;
		Usuario usuarioBloqueo=null;
		Turno turnoTramite=null;
		Turno turnoDeuda=null;
		long numeroAnotaciones = 0;
		boolean esMayorExtension = true;
		

		
		
		try {
			if (matricula != null) {

				FolioDatosTMPPk id = new FolioDatosTMPPk();
				id.idMatricula = matricula;
 
				//CONSULTA DEL FOLIO
			
				folio = forseti.getFolioDatosTMP(id);
				
				/*if(folio==null){
					throw new EventoException("El folio con la matrícula "+matricula+" no existe en temporal");
				}*/
				String idMatricula = folio.getIdMatricula();
                                historialAreas = forseti.getHistorialArea(idMatricula);
                                EvnRespConsultaFolio resp = new EvnRespConsultaFolio(folio, EvnRespConsultaFolio.CONSULTAR_FOLIO_REABRIR_OK);
				resp.setHistorialAreas(historialAreas);
                                return resp;
			} else {
				throw new EventoException("Se necesita saber la matrícula a consultar");
			}
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANConsultaFolio.class,"Ha ocurrido una excepción al consultar datos del folio " + printer.toString());
			throw new EventoException("Error en la consulta de folio. ", e);
		}
	}

}
