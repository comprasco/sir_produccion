package gov.sir.core.negocio.acciones.registro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoException;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.negocio.acciones.SoporteAccionNegocio;
import org.auriga.smart.servicio.ServiceLocator;
import org.auriga.smart.servicio.ServiceLocatorException;
import org.auriga.util.ExceptionPrinter;

import gov.sir.core.eventos.registro.EvnRespDigitacionMasiva;
import gov.sir.core.eventos.registro.EvnDigitacionMasiva;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.acciones.excepciones.AnotacionTemporalRegistroException;
import gov.sir.core.negocio.acciones.excepciones.ServicioNoEncontradoException;
import gov.sir.core.negocio.acciones.excepciones.TomarFoliosException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionNegocioDigitacionMasivaException;
import gov.sir.core.negocio.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.negocio.modelo.*;
import gov.sir.core.negocio.modelo.constantes.*;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.acciones.excepciones.BloqueoFoliosHTException;
import gov.sir.forseti.ForsetiException;
import gov.sir.forseti.interfaz.ForsetiServiceInterface;
import gov.sir.hermod.interfaz.HermodServiceInterface;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase encargada de las opciones de digitación masiva.
  * @author ppabon
*/
public class ANDigitacionMasiva extends SoporteAccionNegocio {

	/**
	 * Instancia de Forseti
	 */
	private ForsetiServiceInterface forseti;

	/**
	 * Instancia de Hermod
	 */
	private HermodServiceInterface hermod;

	/**
	 * Instancia del service locator
	 */
	private ServiceLocator service = null;

	/**
	 *Constructor de la clase ANEnglobe.
	 *Es el encargado de invocar al Service Locator y pedirle una instancia
	 *de los servicio que necesita.
	 */
	public ANDigitacionMasiva() throws EventoException {
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
		EvnDigitacionMasiva evento = (EvnDigitacionMasiva) ev;

		if (evento.getTipoEvento().equals(EvnDigitacionMasiva.TOMAR_FOLIO_DIGITACION)) {
			return tomarFolios(evento);
		} else if (evento.getTipoEvento().equals(EvnDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA)) {
			//Realmente carga cualquier dato de la forma
			return consultarFolioDigitacion(evento);
		} else if (evento.getTipoEvento().equals(EvnDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA)) {
			//Es el mismo para todas, pero se envia parte del folio segun el parametro.
			return actualizarFolios(evento);
		}else if (evento.getTipoEvento().equals(EvnDigitacionMasiva.CONSTRUIR_COMPLEMENTACION)) {
			//permite generar una complementación a partir de las anotaciones de los folios
			return construirComplementacion(evento);
		} else if (evento.getTipoEvento().equals(EvnDigitacionMasiva.VER_DETALLES_FOLIO)) {
			//Es el mismo para todas, pero se envia parte del folio segun el parametro.
			return verDetallesFolio(evento);
		}else if(evento.getTipoEvento().equals(EvnDigitacionMasiva.AGREGAR_FOLIO_DIRECCION))
			return grabarDireccionTemporal(evento);
		else if(evento.getTipoEvento().equals(EvnDigitacionMasiva.ELIMINAR_FOLIO_DIRECCION)){
			return eliminarDireccionTemporal(evento);
		}else if(evento.getTipoEvento().equals(EvnDigitacionMasiva.ACTUALIZAR_FOLIO_COMPLEMENTACION)){
			return actualizarFolioComplementacion(evento);
		}

		return null;
	}

	private EventoRespuesta verDetallesFolio(EvnDigitacionMasiva evento) throws EventoException {
		Folio local_ParamFolio;
		gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;

		local_ParamFolio = evento.getFolio();
		local_ParamUsuarioSir = evento.getUsuarioSIR();
		EvnRespDigitacionMasiva resultado = null;

		Folio folio = null;
		FolioPk folioID;

		String local_Folio_IdMatricula = null;
		
 		Turno turno = evento.getTurno();
		
		long numeroAnotaciones = 0;

		local_Folio_IdMatricula = local_ParamFolio.getIdMatricula();
		try {

			folioID = new FolioPk();
			folioID.idMatricula = local_Folio_IdMatricula;
			
			gov.sir.core.negocio.modelo.Usuario usuario = evento.getUsuarioSIR();
			
			String criterio = CCriterio.TODAS_LAS_ANOTACIONES;		

			folio = forseti.getFolioByIDSinAnotaciones(folioID, null);
			
			Folio folioDef = forseti.getFolioByIDSinAnotaciones(folioID);
			
			if(folio.getEstado().getIdEstado().equals(CEstadoFolio.CERRADO)){
				throw new EventoException("El folio " + folio.getIdMatricula() + " se encuentra cerrado, se necesita la reapertura por parte del Coordinador Jurídico para poder ser calificado.");
			}
			
			boolean esMayorExtension = forseti.mayorExtensionFolio(folio.getIdMatricula());
			
			numeroAnotaciones = forseti.getCountAnotacionesFolio(folioID, criterio, null);
			
			List foliosHijos = forseti.getFoliosHijos(folioID,null);
			
			List foliosPadre = forseti.getFoliosPadre(folioID,null);
			
			long numeroGravamenes = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.GRAVAMEN);
			
			List gravamenes = forseti.getAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.GRAVAMEN,0, (int)numeroGravamenes,null,true);
			
			long numeroMedidasCautelares = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA,CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR);
			
			List medidasCautelares = forseti.getAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.MEDIDA_CAUTELAR,0, (int)numeroMedidasCautelares,null,true);
			
			List salvedadesAnotaciones = forseti.getAnotacionesConSalvedades(folioID, null);
			
			List cancelaciones = forseti.getAnotacionesConCancelaciones(folioID, null);
			
			long numeroFalsaTradicion = 0;
			List falsaTradicion = null;
			List ordenFalsaTradicion = new ArrayList();
			List anotacionesInvalidas = null;
			List ordenAnotacionesInvalidas = new ArrayList();
                        List historialAreas = forseti.getHistorialArea(folio.getIdMatricula());

			numeroFalsaTradicion = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION);
			
			falsaTradicion = forseti.getAnotacionesFolio(folioID, CCriterio.POR_GRUPO_NAT_JURIDICA, CGrupoNaturalezaJuridica.FALSA_TRADICION, 0, (int) numeroFalsaTradicion, null, true);

			if(falsaTradicion != null){
				Iterator it = falsaTradicion.iterator();
				while(it.hasNext()){
					Anotacion anotTemp = (Anotacion)it.next();
					ordenFalsaTradicion.add(anotTemp.getOrden());
				}
			}

			//CONSULTA ANOTACIONES INVALIDAS
			anotacionesInvalidas = forseti.getAnotacionesInvalidas(folioID, null);
			
			if(anotacionesInvalidas != null){
				Iterator it = anotacionesInvalidas.iterator();
				while(it.hasNext()){
					Anotacion anotTemp = (Anotacion)it.next();
					ordenAnotacionesInvalidas.add(anotTemp.getOrden());
				}
			}
			
			List anotacionesPatrimonioFamiliar = null;
			long numanotacionesPatrimonioFamiliar = 0;
			List anotacionesAfectacionVivienda = null;
			long munanotacionesAfectacionVivienda = 0;
			List anotacionesAfectacionVivienda2 = null;
			long munanotacionesAfectacionVivienda2 = 0;
			
			
//			CONSULTA ANOTACIONES PATRIMONIO FAMILIAR
			numanotacionesPatrimonioFamiliar = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA, null);
			anotacionesPatrimonioFamiliar = forseti.getAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.PATRIMONIO_DE_FAMILIA,0, (int)numanotacionesPatrimonioFamiliar,null,true);
			
			//	CONSULTA ANOTACIONES AFECTACION DE VIVIENDA
			munanotacionesAfectacionVivienda = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR, null);
			anotacionesAfectacionVivienda = forseti.getAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR,0, (int)munanotacionesAfectacionVivienda,null,true);
			
			// CONSULTA ANOTACIONES AFECTACION DE VIVIENDA 2
			munanotacionesAfectacionVivienda2 = forseti.getCountAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA,CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2, null);
			anotacionesAfectacionVivienda2 = forseti.getAnotacionesFolio(folioID, CCriterio.POR_NAT_JURIDICA, CNaturalezaJuridica.AFECTACION_A_VIVIENDA_FAMILIAR_2,0, (int)munanotacionesAfectacionVivienda2,null,true);
			
			anotacionesAfectacionVivienda.addAll(anotacionesAfectacionVivienda2);

			String linderoTemporal = "";
			List dirTemporales = null;
			if(folio!=null && folio.getLindero()!=null && !folio.getLindero().equals("")){
				if(folioDef!=null){
					if(folioDef.getLindero()!=null){
						if(turno!=null && turno.getIdFase()!=null && turno.getIdFase().equals(CFase.CAL_DIGITACION)){
							linderoTemporal = folio.getLindero();
						}else{
							linderoTemporal = folio.getLindero().substring(folioDef.getLindero().length(),folio.getLindero().length());
						}
						
					}else{
						linderoTemporal = folio.getLindero();
					}
					
					if(folioDef.getDirecciones()!=null && folio.getDirecciones()!=null){
						dirTemporales = new ArrayList();
						for(int i=folioDef.getDirecciones().size(); i<folio.getDirecciones().size(); i++){
							dirTemporales.add(new Boolean(true));
						}
					}
				}else{
					linderoTemporal = folio.getLindero();
					if(folio.getDirecciones()!=null){
						dirTemporales = new ArrayList();
						for(int i=0; i<folio.getDirecciones().size(); i++){
							dirTemporales.add(new Boolean(true));
						}
					}
				}
			}

			

			resultado = new EvnRespDigitacionMasiva(EvnRespDigitacionMasiva.VER_DETALLES_FOLIO);
                        resultado.setHistorialAreas(historialAreas);
			resultado.setFolio(folio);
			resultado.setFoliosPadre(foliosPadre);
			resultado.setFoliosHijos(foliosHijos);
			resultado.setGravamentes(gravamenes);
			resultado.setMedidasCautelares(medidasCautelares);
			resultado.setFalsaTradicion(ordenFalsaTradicion);
			resultado.setAnotacionesInvalidas(ordenAnotacionesInvalidas);
			resultado.setSalvedadesAnotaciones(salvedadesAnotaciones);
			resultado.setCancelaciones(cancelaciones);
			resultado.setNumeroAnotaciones(numeroAnotaciones); 
			resultado.setMayorExtensionFolio(esMayorExtension);
			resultado.setLinderoTemporal(linderoTemporal);
			
			resultado.setNumeroAnotaciones(numeroAnotaciones);
			resultado.setDirTemporales(dirTemporales);
			resultado.setAnotacionesPatrimonioFamiliar(anotacionesPatrimonioFamiliar);
			resultado.setAnotacionesAfectacionVivienda(anotacionesAfectacionVivienda);
			resultado.setFolioDef(folioDef);
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			ValidacionNegocioDigitacionMasivaException cfe = new ValidacionNegocioDigitacionMasivaException();
			cfe.addError(fe.getMessage());
			throw cfe;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANDigitacionMasiva.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		// wrap ------------------------------------
		

		// send-message ---------------------------------
		return resultado;
	}

	/**
	 * Método que permite bloquear los folios que hacen parte del turno al usuario que 
	 * esta trabajando con el turno.	
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta tomarFolios(EvnDigitacionMasiva evento) throws EventoException {

		EvnRespDigitacionMasiva evRespuesta = null;
		Turno turno = (Turno) evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		//LlaveBloqueo llaveBloqueo = null;
		/*Solicitud solicitud = (Solicitud) turno.getSolicitud();
		List solFolio = solicitud.getSolicitudFolios();
		List matriculas = new Vector();
		Iterator itSolFolio = solFolio.iterator();
		while (itSolFolio.hasNext()) {
			SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
			matriculas.add(sol.getFolio().getIdMatricula());
		}*/

		//llaveBloqueo = new LlaveBloqueo();

		try {
			TurnoPk turnoID = new TurnoPk();
			turnoID.idCirculo = turno.getIdCirculo();
			turnoID.idProceso = turno.getIdProceso();
			turnoID.idTurno = turno.getIdTurno();
			turnoID.anio = turno.getAnio();
			forseti.validarPrincipioPrioridadDigitacion(turnoID, usr);
			//llaveBloqueo = forseti.delegarBloqueoFolios(turnoID, usr);
		} catch (ForsetiException e) {
			if(e.getHashErrores() != null)
			{
				if(!e.getHashErrores().isEmpty())
				{
					throw new BloqueoFoliosHTException(e);
				}
			}
			
			throw new TomarFoliosException(e.getMessage(), e);
				
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANDigitacionMasiva.class,"Ha ocurrido una excepcion inesperada bloqueando los folios : " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		evRespuesta = new EvnRespDigitacionMasiva( EvnRespDigitacionMasiva.TOMAR_FOLIO/*,llaveBloqueo,  turno*/);
		evRespuesta.setTurno(turno);

		return evRespuesta;
	}
	
	
	/**
	 * processComplementacionCargar
	 * ANFolio.consultarFolioUsuario
	 * @param evento EvnDigitacionMasivaOptions
	 * @return EventoRespuesta
	 */
	private EventoRespuesta consultarFolioDigitacion(EvnDigitacionMasiva evento) throws EventoException {

		Folio local_ParamFolio;
		gov.sir.core.negocio.modelo.Usuario local_ParamUsuarioSir;

		local_ParamFolio = evento.getFolio();
		local_ParamUsuarioSir = evento.getUsuarioSIR();
		EvnRespDigitacionMasiva local_Result = null;

		Folio local_Folio = null;
		FolioPk local_FolioID;

		String local_Folio_IdMatricula = null;

		local_Folio_IdMatricula = local_ParamFolio.getIdMatricula();
                List historialAreas = null;
		try {

			local_FolioID = new FolioPk();
			local_FolioID.idMatricula = local_Folio_IdMatricula;

			local_Folio = forseti.getFolioByIDSinAnotaciones(local_FolioID, null);
                        
		} catch (ForsetiException fe) {
			List l;
			l = fe.getErrores();
			ValidacionNegocioDigitacionMasivaException cfe = new ValidacionNegocioDigitacionMasivaException();
			cfe.addError(fe.getMessage());
			throw cfe;
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANDigitacionMasiva.class,"Ha ocurrido una excepcion inesperada " + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}
                try{
                historialAreas = forseti.getHistorialArea(evento.getFolio().getIdMatricula());
                } catch (Throwable ex) {
                Logger.getLogger(ANDigitacionMasiva.class.getName()).log(Level.SEVERE, null, ex);
                }
		// wrap ------------------------------------
		local_Result = new EvnRespDigitacionMasiva(EvnRespDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA);
		local_Result.setFolio(local_Folio);
                local_Result.setHistorialAreas(historialAreas);

		// send-message ---------------------------------
		return local_Result;
	}

	/**
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta actualizarFolios(EvnDigitacionMasiva evento) throws EventoException {

		// unwrap ---------------------------------
		Turno turno = evento.getTurno();
		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();

		String tipoActualizacion = evento.getTipoActualizacion();
		List matriculas = evento.getConjuntoFolios();


		// -----------------------------------------
		List matriculasSinComplementacion = new ArrayList();
		List validaciones = new ArrayList();
		// process ---------------------------------

		try {

			Iterator itMatriculas = matriculas.iterator();
			Folio temp = null;
			Folio def = null;
                         /*
                        * @author : CTORRES
                        * @change : Se implemento validación para agregar salvedades solo para 
                        *           folios que no se crearon con el turno que se este trabajando.
                        * Caso Mantis : 11648
                        * No Requerimiento: Acta - Requerimiento No 029_453_Salvedades_Digitador_Masivo_Correcciones
                        */
                    long proceso = turno.getIdProceso();
                    if ((String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCION) || String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCIONES))) {
                        ValidacionParametrosException v = new ValidacionParametrosException();
                        
                        boolean sw = false;
                        for (Object pk : matriculas) {
                            FolioPk id = (FolioPk) pk;
                            Folio temp1 = forseti.getFolioByIDSinAnotaciones(id, null);
                            if (temp1.isDefinitivo() && !temp1.getRadicacion().equals(turno.getIdWorkflow())) {
                                if (evento.getFolio().getSalvedades() == null) {
                                    sw = true;
                                    v.addError("Debe ingresar una salvedad válida");
                                } else if (evento.getFolio().getSalvedades().isEmpty()) {
                                    sw = true;
                                    v.addError("Debe ingresar una salvedad válida");
                                } else 
                                {
                                    SalvedadFolio salvedad = (SalvedadFolio)evento.getFolio().getSalvedades().get(0);
                                    if(salvedad.getDescripcion().length()<30)
                                    {
                                        sw = true;
                                        v.addError("La salvedad debe tener por lo menos 30 carácteres.");
                                    }
                                }
                            }
                        }
                        if (sw) {
                            throw v;
                        }
                    }
			//SI SE ESTA ACTUALIZANDO LA COMPLEMENTACIÓN SE VERIFICA QUE EL FOLIO NO TENGA, SINO, NO LO HACE.
			//SI SE DESEA ACTUALIZAR ALGUNA OTRA COSA DEL FOLIO, NO SE HACE NINGUNA VALIDACIÓN.
			if (tipoActualizacion.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION)) {
				while (itMatriculas.hasNext()) {

					FolioPk id = (FolioPk) itMatriculas.next();
					temp = forseti.getFolioByIDSinAnotaciones(id, null/*usr*/);
					def = forseti.getFolioByIDSinAnotaciones(id);

					//SI EL FOLIO ES DEFINITIVO SE DEBE PREGUNTAR SI NO TIENE COMPLEMENTACIÓN PARA PODER GUARDAR LA
					//NUEVA COMPLEMENTACIÓN, SI EL FOLIO ES TEMPORAL, SI PERMITE GUARDAR LA NUEVA COMPLEMENTACIÓN.
					if (temp != null && temp.isDefinitivo()) {
						if ((temp.getComplementacion() == null || temp.getComplementacion().getComplementacion().trim().equals(""))) {
							matriculasSinComplementacion.add(id);
						} else {
							if (def.getComplementacion() != null && !def.getComplementacion().getComplementacion().trim().equals("")) {
								validaciones.add("El folio " + id.idMatricula + ", " + "ya tiene complementación y no ha sido actualizado.");
							} else {
								matriculasSinComplementacion.add(id);
							}
						}
					} else {
						matriculasSinComplementacion.add(id);
					}
				}

				//ACTUALIZA LOS FOLIOS QUE SON TEMPORALES O QUE NO TIENEN UNA COMPLEMENTACIÓN ASOCIADA.
				forseti.updateFolios(evento.getFolio(), matriculasSinComplementacion, usr, false);

				if (validaciones.size() > 0) {
					throw new ValidacionParametrosException(validaciones);
				}
			} else if (tipoActualizacion.equals(CDigitacionMasiva.OPCION_LINDERO)) {
				//Bug 3434. No se quiere que si el lindero es nulo o vacio se actualice
				//sino que al lindero actual se agregue lo que se escribe.
				 
				/*while (itMatriculas.hasNext()) {

					Folio.ID id = (Folio.ID) itMatriculas.next();
					temp = forseti.getFolioByIDSinAnotaciones(id, usr);
					def = forseti.getFolioByIDSinAnotaciones(id);

					//SI EL FOLIO ES DEFINITIVO SE DEBE PREGUNTAR SI NO TIENE LINDERO PARA PODER GUARDAR LA
					//NUEVO LINDERO, SI EL FOLIO ES TEMPORAL, SI PERMITE GUARDAR EL NUEVO LINDERO.
					if (temp != null && temp.isDefinitivo()) {
						if ((temp.getLindero() == null || temp.getLindero().trim().equals(""))) {
							matriculasSinComplementacion.add(id);
						} else {
							if (def.getLindero() != null && !def.getLindero().trim().equals("")) {
								validaciones.add("El folio " + id.idMatricula + ", " + "ya tiene lindero y no ha sido actualizado.");
							} else {
								matriculasSinComplementacion.add(id);
							}
						}
					} else {
						matriculasSinComplementacion.add(id);
					}
				}

				//ACTUALIZA LOS FOLIOS QUE SON TEMPORALES O QUE NO TIENEN LINDERO.
				forseti.updateFolios(evento.getFolio(), matriculasSinComplementacion, usr);

				if (validaciones.size() > 0) {
					throw new ValidacionParametrosException(validaciones);
				}
				*/                            
				forseti.updateFolios(evento.getFolio(), matriculas, usr, false);
			}  else if (tipoActualizacion.equals(CDigitacionMasiva.OPCION_DIRECCION)) {
				forseti.updateFolios(evento.getFolio(), matriculas, usr, false);
			}else if (tipoActualizacion.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL)) {
				OficioPk idOf = hermod.crearOficio(evento.getOficio());
			}

		} catch (ValidacionParametrosException e) {
			throw e;
		} catch (ForsetiException e) {
			throw new EventoException(e.getMessage(), e);
		} catch (Throwable e) {
			ExceptionPrinter printer = new ExceptionPrinter(e);
			Log.getInstance().error(ANDigitacionMasiva.class,"Ha ocurrido una excepcion inesperada guardando cambios de los folios" + printer.toString());
			throw new EventoException(e.getMessage(), e);
		}

		// wrap ------------------------------------
		EvnRespDigitacionMasiva local_Result;
		local_Result = new EvnRespDigitacionMasiva(EvnRespDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA);
		local_Result.setTurno(turno);
		local_Result.setValidaciones(validaciones);

		// send-message ---------------------------------
		return local_Result;
	}	
	
	
	/**
	 * Método que construye una complementación a partir de las anotaciones de folios.
	 * @param evento
	 * @return
	 * @throws EventoException
	 */
	private EventoRespuesta construirComplementacion(EvnDigitacionMasiva evento) throws EventoException {

		gov.sir.core.negocio.modelo.Usuario usr = evento.getUsuarioSIR();
		List rangoAnotaciones = evento.getRangoAnotaciones();
		ValidacionNegocioDigitacionMasivaException exception = new ValidacionNegocioDigitacionMasivaException();		
		
		if(rangoAnotaciones==null){
			rangoAnotaciones= new ArrayList();
		}

		Iterator itRangoAnotaciones = rangoAnotaciones.iterator();
		
		List anotaciones = null;
		List anotacionesSeleccionadas = new ArrayList();
		int ini = 0;
		int fin = 0;
		while (itRangoAnotaciones.hasNext()){
			RangoAnotacion rangoAnotacion = (RangoAnotacion)itRangoAnotaciones.next();
			ini = rangoAnotacion.getAnotacionInicio();
			fin =  (rangoAnotacion.getAnotacionFin() - rangoAnotacion.getAnotacionInicio()) + 1;
			if(ini >0){
				ini = ini - 1;
			}

			
			FolioPk fid = new FolioPk();
			fid.idMatricula = rangoAnotacion.getFolio().getIdMatricula();
			
			try {			
			
				anotaciones = forseti.getAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null, ini , fin, null, false);
				
				if(anotaciones == null || anotaciones.size()==0){
					anotaciones = forseti.getAnotacionesFolio(fid, CCriterio.TODAS_LAS_ANOTACIONES, null, ini , fin, usr , false);
				}
				
				//logger.debug("NUMERO ANOTACIONES FOLIO " + fid.idMatricula + ":" +(anotaciones!=null? ""+anotaciones.size():""));
				
				if(anotaciones.size()==0){
					exception.addError("Para el folio " +fid.idMatricula + " con el rango de anotaciones de la " + ini + " hasta la " + rangoAnotacion.getAnotacionFin() + " no se encontraron anotaciones válidas.");
				}else{
					anotacionesSeleccionadas.add(anotaciones);					
				}
				
			} catch (Throwable e) {
				exception.addError("Para el folio " +fid.idMatricula + " con el rango de anotaciones de la " + ini + " hasta la " + rangoAnotacion.getAnotacionFin() + " se genero el siguiente error." + e.getMessage());
			}
			
			
		}
		
		if(exception.getErrores().size()>0){
			throw exception; 
		}

		String complementacion = construirComplementacionFromAnotaciones(anotacionesSeleccionadas);

		EvnRespDigitacionMasiva rta;
		rta = new EvnRespDigitacionMasiva(EvnRespDigitacionMasiva.CONSTRUIR_COMPLEMENTACION);
		rta.setComplementacion(complementacion);

		return rta;
	}

	/**
	 * @param anotacionesSeleccionadas
	 * @return
	 */
	private String construirComplementacionFromAnotaciones (List anotacionesSeleccionadas){
		String complementacion = "";
		
		int i = 0;
		Iterator itSeleccionadas = anotacionesSeleccionadas.iterator();
		while (itSeleccionadas.hasNext()){
			List anotaciones = (List)itSeleccionadas.next();
			
			for (i = (anotaciones.size() -1); i >= 0 ; i--){
				Anotacion anotacion = (Anotacion)anotaciones.get(i);
				complementacion = complementacion + this.construirComplementacion(anotacion);	
			}
			
			complementacion = complementacion + "\n";
			
		}
		
		return complementacion;
	}

	/**
	 * @param anotacion
	 * @return
	 */
	private String construirComplementacion (Anotacion anotacion){
		String complementacion = "";
		
		try{
			
			if(anotacion !=null){
				//NUMERO DE ANOTACION
				complementacion = complementacion + anotacion.getOrden() + ". -";
				if(anotacion.getDocumento()!=null){
					Documento documento = anotacion.getDocumento();
					//TIPO DE DOCUMENTO Y NUMERO 
					complementacion = complementacion + documento.getTipoDocumento().getNombre() + " " + documento.getNumero() + " ";
					
					//FECHA DEL DOCUMENTO
					if(documento.getFecha()!=null){
						complementacion = complementacion + " del " + this.getFecha(documento.getFecha()) + " ";	
					}
					
					//OFICINA - OFICINA INTERNACIONAL O COMENTARIO

					if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getOficinaOrigen()!=null){
						complementacion = complementacion + (((anotacion.getDocumento().getOficinaOrigen().getNombre() != null)) ? anotacion.getDocumento().getOficinaOrigen().getNombre() : "")	+ " "	+ (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaOrigen() != null) && (anotacion.getDocumento().getOficinaOrigen().getNumero() != null)) ? anotacion.getDocumento().getOficinaOrigen().getNumero() : "")	+ " de "	+ (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaOrigen() != null) && (anotacion.getDocumento().getOficinaOrigen().getVereda() != null) && (anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre() != null)) ? anotacion.getDocumento().getOficinaOrigen().getVereda().getNombre() : "")	+ " ";	
					}else if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getOficinaInternacional()!=null){
						complementacion = complementacion + (((anotacion.getDocumento() != null) && (anotacion.getDocumento().getOficinaInternacional() != null) ) ? anotacion.getDocumento().getOficinaInternacional() : "") + " ";
					}else if(anotacion.getDocumento()!=null&&anotacion.getDocumento().getComentario()!=null){
						String comentario = anotacion.getDocumento().getComentario();
						String comentarioOficina = "&nbsp;";
						String comentarioVereda ="&nbsp;";			
						if(comentario.indexOf("-")!=-1){
							StringTokenizer token = new StringTokenizer(comentario, "-");
							comentarioVereda = token.nextToken();
							comentarioOficina = token.nextToken();
						}else{
							comentarioOficina = comentario;
						}				
						complementacion = complementacion + " " + (comentarioOficina)	+ " de "	+ (comentarioVereda)	+ " ";	
					}	
				}
					
				//REGISTRADA EN FECHA 
				if(anotacion.getFechaRadicacion()!=null){
					complementacion = complementacion + " registrada el " + this.getFecha(anotacion.getFechaRadicacion()) + " ";	
				}
				
				//REGISTRADA POR
				if(anotacion.getEspecificacion()!=null){
					complementacion = complementacion + " por "+ anotacion.getEspecificacion() +" ";	
				}else if (anotacion.getNaturalezaJuridica()!=null &&anotacion.getNaturalezaJuridica().getNombre()!=null){
					complementacion = complementacion + " por "+ anotacion.getNaturalezaJuridica().getNombre() +" ";
				}

				complementacion = complementacion + construirCiudadanosComplementacion(anotacion);
				
				complementacion = complementacion + " registrada en la matrícula " + anotacion.getIdMatricula() + " .-- " ;
				
			}
		
		}catch(Exception e){
			//logger.debug("ERROR CONSTRUYENDO TEXTO DE LA COMPLEMENTACION" + e.getMessage());
			Log.getInstance().error(ANDigitacionMasiva.class,e);
		}

				
		return complementacion;
	}
	
	/**
	 * @param anotacion
	 * @return
	 */
	private String construirCiudadanosComplementacion (Anotacion anotacion){
		String complementacion = "";
		
		try{
			
			if(anotacion !=null){

				if(anotacion.getAnotacionesCiudadanos()!=null){
					List ciudadanosTemp = anotacion.getAnotacionesCiudadanos();
					List ciudadanos =this.organizarCiudadanos(ciudadanosTemp);								
					
					Iterator it = ciudadanos.iterator();
					while(it.hasNext()){
						AnotacionCiudadano ac = (AnotacionCiudadano)it.next();
						complementacion = complementacion + ac.getRolPersona() + ": ";
						Ciudadano ciudadano = ac.getCiudadano();
						complementacion = complementacion + ciudadano.getNombreCompletoCiudadano()  + ", ";
					}
				}
				
			}
		
		}catch(Exception e){
			Log.getInstance().error(ANDigitacionMasiva.class,"ERROR CONSTRUYENDO TEXTO DE LA COMPLEMENTACION" + e.getMessage());
			Log.getInstance().error(ANDigitacionMasiva.class,e);
		}

				
		return complementacion;
	}
	
	/**
	 * @param ciudadanos
	 * @return
	 */
	private List organizarCiudadanos(List ciudadanos) {
		Iterator itCiudadanos = ciudadanos.iterator();
		List losA = new ArrayList();
		List losDe = new ArrayList();
		while(itCiudadanos.hasNext()){
			AnotacionCiudadano anotacionCiudadano = (AnotacionCiudadano) itCiudadanos.next();
			String rol = anotacionCiudadano.getRolPersona();
			if(rol.equals("DE")){
				losDe.add(anotacionCiudadano);
			} else if(rol.equals("A")){
				losA.add(anotacionCiudadano);
			}

		}
		losDe.addAll(losA);
		return losDe;
	}	
	
	
	/**
	 * @param fecha
	 * @return
	 */
	private String getFecha(Date fecha) {
		Calendar c = Calendar.getInstance();
		String fechaString = " ";

		if (fecha != null) {
			c.setTime(fecha);
			fechaString = c.get(Calendar.DAY_OF_MONTH) + "/" +
				(c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR);
		}

		return fechaString;
	}
	
	/** Graba la direccion al temporal de un folio 
	 * @param evento
	 * @return EvnRespuesta
	 */
	private EventoRespuesta grabarDireccionTemporal(EvnDigitacionMasiva evento) throws EventoException {
		Usuario usuarioNeg=evento.getUsuarioSIR();
		EvnRespFolio evnResp= null;
		Folio nuevo=new Folio();
		List dirTemporales = evento.getDirTemporales();
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		List direcciones = evento.getFolio().getDirecciones();
		Iterator it=direcciones.iterator();
		while(it.hasNext()){
			Direccion direccion=(Direccion)it.next();
			nuevo.addDireccione(direccion);
		}
		//SE ACTUALIZA EL FOLIO
		try {
			forseti.updateFolio(nuevo,usuarioNeg, null, false);
			FolioPk fid = new FolioPk();
			fid.idMatricula = nuevo.getIdMatricula();
                        /**
                         * @author      :   Henry Gómez Rocha y Fernando Padilla
                         * @change      :   Se llama al método forseti.getFolioByID(), para evitar que al momento de
                         *                  pasar del role Calificador al role Digitador no se verifique que
                         *                  usuario tiene el bloqueo del folio.
                         * Caso Mantis  :   0004967
                         */
			Folio folio = forseti.getFolioByID(fid, usuarioNeg, false);
			evnResp = new EvnRespFolio(folio,EvnRespFolio.AGREGAR_DIRECCION);
			if(dirTemporales == null)
				dirTemporales = new ArrayList();

			dirTemporales.add(new Boolean(true));
			evnResp.setDirTemporales(dirTemporales);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANDigitacionMasiva.class, "No se pudieron grabar las direcciones temporales:"+ep.toString());
			throw new EventoException("No se pudieron grabar las anotaciones temporales", e1);
		}
		return evnResp;
	}
	
	/** Graba la direccion al temporal de un folio 
	 * @param evento
	 * @return EvnRespuesta
	 */
	private EventoRespuesta eliminarDireccionTemporal(EvnDigitacionMasiva evento) throws EventoException {
		Usuario usuarioNeg=evento.getUsuarioSIR();
		Folio nuevo=new Folio();
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		List direcciones = evento.getFolio().getDirecciones();
		List dirTemporales = evento.getDirTemporales();
		Iterator it=direcciones.iterator();
		int i=0;
		int borrar = 0;
		while(it.hasNext()){
			Direccion direccion=(Direccion)it.next();
			nuevo.addDireccione(direccion);
			if(direccion.isToDelete())
				borrar = i;
			i++;
		}
		//SE ACTUALIZA EL FOLIO
		try {
			forseti.updateFolio(nuevo,usuarioNeg, null, false);
			dirTemporales.remove(borrar);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANDigitacionMasiva.class, "No se pudieron grabar las direcciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
		}
		EvnRespFolio evnResp = new EvnRespFolio(nuevo,EvnRespFolio.ELIMINAR_DIRECCION);
		evnResp.setDirTemporales(dirTemporales);
		
		return evnResp;
	}
	
	/** Graba la complementacion de un folio  
	 * @param evento
	 * @return EvnRespuesta
	 */
	private EventoRespuesta actualizarFolioComplementacion(EvnDigitacionMasiva evento) throws EventoException {
		Usuario usuarioNeg=evento.getUsuarioSIR();
		Folio nuevo=new Folio();
		nuevo.setIdMatricula(evento.getFolio().getIdMatricula());
		nuevo.setComplementacion(evento.getFolio().getComplementacion());
		try {
			forseti.updateFolio(nuevo,usuarioNeg, null, false);
		} catch (Throwable e1) {
			ExceptionPrinter ep=new ExceptionPrinter(e1);
			Log.getInstance().error(ANDigitacionMasiva.class, "No se pudieron grabar las direcciones temporales:"+ep.toString());
			throw new AnotacionTemporalRegistroException("No se pudieron grabar las anotaciones temporales", e1);
		}
		return new EvnRespFolio(nuevo,EvnRespFolio.ACTUALIZAR_FOLIO_COMPLEMENTACION);
	}


}
