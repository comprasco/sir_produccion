package gov.sir.core.web.acciones.registro;

/**
* @Autor: Edgar Lora
* @Mantis: 0013038
* @Requerimiento: 060_453
*/
import co.com.iridium.generalSIR.comun.GeneralSIRException;
import co.com.iridium.generalSIR.negocio.validaciones.ValidacionesSIR;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.eventos.Evento;
import org.auriga.smart.eventos.EventoRespuesta;
import org.auriga.smart.web.acciones.AccionWebException;
import org.auriga.smart.web.acciones.SoporteAccionWeb;

import gov.sir.core.eventos.comun.EvnPaginadorAnotaciones;
import gov.sir.core.eventos.comun.EvnRespPaginadorAnotaciones;
import gov.sir.core.eventos.registro.EvnCalificacion;
import gov.sir.core.eventos.registro.EvnDigitacionMasiva;
import gov.sir.core.eventos.registro.EvnRespCalificacion;
import gov.sir.core.eventos.registro.EvnRespDigitacionMasiva;
import gov.sir.core.eventos.registro.EvnRespFolio;
import gov.sir.core.negocio.modelo.Circulo;
import gov.sir.core.negocio.modelo.Complementacion;
import gov.sir.core.negocio.modelo.Direccion;
import gov.sir.core.negocio.modelo.Eje;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Folio;
import gov.sir.core.negocio.modelo.FolioPk;
import gov.sir.core.negocio.modelo.Oficio;
import gov.sir.core.negocio.modelo.RangoAnotacion;
import gov.sir.core.negocio.modelo.SalvedadFolio;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCorreccion;
import gov.sir.core.negocio.modelo.SolicitudFolio;
import gov.sir.core.negocio.modelo.SolicitudPermisoCorreccion;
import gov.sir.core.negocio.modelo.SolicitudRegistro;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.TurnoHistoria;
import gov.sir.core.negocio.modelo.Usuario;
import gov.sir.core.negocio.modelo.constantes.CComplementacion;
import gov.sir.core.negocio.modelo.constantes.CDigitacionMasiva;
import gov.sir.core.negocio.modelo.constantes.CDireccion;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CFolio;
import gov.sir.core.negocio.modelo.constantes.CProceso;
import gov.sir.core.negocio.modelo.constantes.CNaturalezaJuridica;
import gov.sir.core.negocio.modelo.constantes.CSalvedadFolio;
/**
 * @author: Cesar Ramirez
 * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
 * Se añade la clase con las constantes CTipoAnotacion
 **/
import gov.sir.core.negocio.modelo.constantes.CTipoAnotacion;
import gov.sir.core.negocio.modelo.util.Log;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPaginadorAnotaciones;
import gov.sir.core.web.acciones.correccion.AWModificarFolio;
import gov.sir.core.web.acciones.excepciones.AccionInvalidaException;
import gov.sir.core.web.acciones.excepciones.PaginadorCacheNoActualizadoException;
import gov.sir.core.web.acciones.excepciones.PaginadorPaginaNoEncontradaException;
import gov.sir.core.web.acciones.excepciones.ParametroInvalidoException;
import gov.sir.core.web.acciones.excepciones.ValidacionDigitacionMasivaException;
import gov.sir.core.web.acciones.excepciones.ValidacionParametrosException;
import gov.sir.core.web.helpers.comun.DatosRespuestaPaginador;
import gov.sir.core.web.helpers.comun.ElementoLista;
import gov.sir.core.web.helpers.comun.JvLocalUtils;
import gov.sir.core.web.helpers.comun.MostrarFolioHelper;
import gov.sir.core.web.helpers.comun.PaginadorAvanzado;
import gov.sir.core.web.helpers.correccion.region.model.EditFoliosRegionManager.PermisosCorreccionAspectModelConstants;

/**
* @author ppabon
* Acción Web para el manejo de todo lo relacionado a la fase de digitación masiva en en el proceso de calificación.
* a través del proceso de Englobe 
*/
public class AWDigitacionMasiva extends SoporteAccionWeb {
	
	//SIR-41(R1)	

	//ATRIBUTOS Y CONSTANTES
	/**
	* Acción solicitada y recibida dentro del request.
	*/
	private String accion;

	/** Lista de las matriculas que tiene el turno*/
	public static final String LISTA_MATRICULAS_TURNO = "LISTA_MATRICULAS_TURNO";

	//ACCIONES A EJECUTAR EN LA ACCIÓN WEB
	/**
	 * Constante que identifica que se desea BLOQUEAR los folios mientras se realiza el proceso de corrección
	 * Es usado en registro, dónde los folios no se encuentran bloqueados.
	 */
	public final static String TOMAR_FOLIO_DIGITACION = "TOMAR_FOLIO_DIGITACION";

	/** Constante para indicar que se desea realizar la consulta a un folio para cargar la información de un folio*/
	public static final String CONSULTAR_FOLIO_DIGITACION_MASIVA = "CONSULTAR_FOLIO_DIGITACION_MASIVA";
	
	/** Constante para indicar que se desea realizar el reload de la pagina*/
	public static final String DIGITACION_MASIVA_RELOAD = "DIGITACION_MASIVA_RELOAD";
	
	/** Constante para indicar que se desea realizar una actualización a los folios indicados*/
	public static final String EDITAR_FOLIO_DIGITACION_MASIVA = "EDITAR_FOLIO_DIGITACION_MASIVA";
	
	/** Constante para indicar que se desea entrar a la construcción de la complementación*/
	public static final String ENTRAR_CONSTRUCCION_COMPLEMENTACION = "ENTRAR_CONSTRUCCION_COMPLEMENTACION";	
	
	/** Constante para indicar que se desea seleccionar los folios para construir la complementación*/
	public static final String ELEGIR_FOLIOS_DIGITACION_MASIVA = "ELEGIR_FOLIOS_DIGITACION_MASIVA";

	/** Constante para indicar que se desea seleccionar las anotaciones de cada folio, para construir la complementación*/
	public static final String ELEGIR_ANOTACIONES_DIGITACION_MASIVA = "ELEGIR_ANOTACIONES_DIGITACION_MASIVA";

	/** Constante para indicar que se desea quitar un folio para que ya no forme parte de la construcción de la complementación*/
	public static final String QUITAR_FOLIO = "QUITAR_FOLIO";
	
	/** Constante para indicar que se desea salir de la construcción de la complementación*/
	public static final String SALIR_CONSTRUCCION_COMPLEMENTACION = "SALIR_CONSTRUCCION_COMPLEMENTACION";	

	/** Se regresa a la opción de correcciones*/
	public static final String BTN_BACK_ACTION = "BTN_BACK_ACTION";
	
	/** Se regresa a la opción de correcciones*/
	public static final String BTN_REGRESAR = "BTN_REGRESAR";

	public static final String VER_DETALLES_FOLIO = "VER_DETALLES_FOLIO";
	
	public static final String BTN_REGRESAR_DETALLES_FOLIO = "BTN_REGRESAR_DETALLES_FOLIO";
	
	
	//PQ
	public static final String ACTUALIZAR_FOLIO_CABIDA_LINDEROS="ACTUALIZAR_FOLIO_CABIDA_LINDEROS";
	public static final String ACTUALIZAR_FOLIO_DIRECCIONES="ACTUALIZAR_FOLIO_DIRECCIONES";
	public static final String AGREGAR_DIRECCION_CALIFICACION="AGREGAR_DIRECCION_CALIFICACION";
	public static final String CONSULTAR_ANOTACIONES_FOLIO="CONSULTAR_ANOTACIONES_FOLIO";
	public static final String AGREGAR_DIRECCION_DIGITACION="AGREGAR_DIRECCION_DIGITACION";
	public static final String ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION="ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION";
	public static final String ELIMINAR_DIRECCION_CALIFICACION="ELIMINAR_DIRECCION_CALIFICACION";
	public static final String ACTUALIZAR_COMPLEMENTACION="ACTUALIZAR_COMPLEMENTACION";
	public static final String REFRESCAR_PAGINADOR = "REFRESCAR_PAGINADOR";
	
	/**
	 * Crea una instancia de la Acción Web de Englobe
	 */
	public AWDigitacionMasiva() {
		super();
	}

	/**
	* Método que permite determinar la acción solicitada, y hacer el llamado
	* correspondiente, de acuerdo con la solicitud.
	*/
	public Evento perform(HttpServletRequest request) throws AccionWebException {

		accion = request.getParameter(WebKeys.ACCION);

		if ((accion == null) || (accion.trim().length() == 0)) {
			throw new AccionInvalidaException("Debe indicar una accion valida");
		} else if (accion.equals(AWDigitacionMasiva.TOMAR_FOLIO_DIGITACION)) {
			return tomarFoliosDigitacion(request);
		} else if (CONSULTAR_FOLIO_DIGITACION_MASIVA.equals(accion)) {
			return cargarInformacionFolio(request);
		} else if (DIGITACION_MASIVA_RELOAD.equals(accion)) {
			return reload(request);
		} else if (EDITAR_FOLIO_DIGITACION_MASIVA.equals(accion)) {
			return editarFolios(request);
		} else if (accion.equals(ENTRAR_CONSTRUCCION_COMPLEMENTACION)) {
			this.preservarInfoFormulario(request);
			return null;
		}  else if (accion.equals(ELEGIR_FOLIOS_DIGITACION_MASIVA)) {
			return elegirFolios(request);
		} else if (accion.equals(ELEGIR_ANOTACIONES_DIGITACION_MASIVA)) {
			return elegirAnotaciones(request);
		} else if (accion.equals(QUITAR_FOLIO)) {
			this.quitarFolios(request);
			return null;
		} else if (accion.equals(SALIR_CONSTRUCCION_COMPLEMENTACION)) {
			return salirConstruccionComplementacion(request);
		} else if (BTN_BACK_ACTION.equals(accion)) {
			return doProcess_BtnBack(request);
		} else if (BTN_REGRESAR.equals(accion)) {
			return regresar(request);
		} else if (VER_DETALLES_FOLIO.equals(accion))
		{
			return verDetallesFolio(request);
		} else if (BTN_REGRESAR_DETALLES_FOLIO.equals(accion))
		{
			return regresar(request);
		}else if(ACTUALIZAR_FOLIO_CABIDA_LINDEROS.equals(accion)){
			return actualizarFolioCabidaLinderos(request);
		}else if(AGREGAR_DIRECCION_DIGITACION.equals(accion)){
			return agregarFolioDireccion(request);
                } else if (accion.equals(ACTUALIZAR_FOLIO_DIRECCIONES)) {
                        return actualizarFolioDirecciones(request);
		}else if(ELIMINAR_DIRECCION_CALIFICACION.equals(accion)){
			return eliminarDireccion(request);
		}else if(ELIMINAR_DIRECCION_DEFINITIVA_CALIFICACION.equals(accion)){
			return doFolioEdit_DireccionDefinitiva_DelItem(request);
		}else if(ACTUALIZAR_COMPLEMENTACION.equals(accion)){
			return actualizarFolioComplementacion(request);
		}else if(CONSULTAR_ANOTACIONES_FOLIO.equals(accion)){
			return consultarAnotacionesFolio(request);
		}else if (accion.equals(REFRESCAR_PAGINADOR)) {
			return cambiarPagina(request);
		} 
		//ACCIÓN INVÁLIDA
		else {
			throw new AccionInvalidaException("La acción " + accion + " no es válida.");
		}
	}
	
        
        private Evento actualizarFolioDirecciones(HttpServletRequest request) {
        List t0_folio_direcciones = null; // direcciones existentes en db
        List t1_folio_direcciones = null; // direccione existentes en sesion
        List t2_folio_direcciones = null; // direcciones a enviar como delta

        HttpSession session = request.getSession();

        Folio t0_folio = (Folio) session.getAttribute(WebKeys.FOLIO);

        // modificar las direcciones
        delta_folio_direcciones:
        {

            // Conditional javaSrcBlock:
            // debe tener permiso para modificar direcciones
            // criterio para enviar direcciones a creacion: no tiene identificador
            // criterio para enviar direcciones a eliminacion: setToDelete : true
            // se debe tener en cuenta que se muestran 2 listas: una con los cambios definitivos y otra
            // con los temporales
            // para adicionar, solamente se recorre la lista de temporales
            // para eliminar, solamente se recorre la lista de definitivos
            // En sesion se cargan a t1 los valores de t0; se manipulan,
            // y luego se hace la actualizacion; se deben observar las variaciones
            // en este punto.
            // paso general:
            // para cada una de las direcciones en sesion:
            // observar si ya existia;
            // si tiene el flag toDelete = true, se coloca en la otra lista y se envia a eliminar
            // si no tiene id de direccion, se envia, para que se cree.
            // se hace con 2 ciclos para mayor claridad
            // A: recorrido por direcciones temporales
            // configurar-general
            t0_folio_direcciones = t0_folio.getDirecciones();
            t2_folio_direcciones = new java.util.ArrayList();

            if (null == t0_folio_direcciones) {
                t0_folio_direcciones = new java.util.ArrayList();
            }

            // configurar para esta comparacion
            t1_folio_direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

            if (null == t1_folio_direcciones) {
                t1_folio_direcciones = new java.util.ArrayList();
            }

            // forma de realizarlo:
            // 1. explorar nuevas adiciones
            // : las que estan en t1
            //TODO
            // Este codigo fue cambiado por el inmediatamente posterior.
            // Solo se cambio la forma de recorrer la lista.
            /*for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
													 iteratorT1.hasNext(); ) {
					Direccion t1_element = (Direccion) iteratorT1.next();
					//TODO
                    //String t1_element_id = t1_element.getIdDireccion();
					boolean existsInT0 = false;
					// for (java.util.Iterator iteratorT0 = t0_folio_direcciones.iterator();
					//                                     iteratorT0.hasNext(); ) {
					//    Direccion t0_element = (Direccion) iteratorT0.next();
					//    // tener en cuenta null's en id;
					//    String t0_element_id = t0_element.getIdDireccion();
					//
					//    if ((null != t0_element_id)
					//        && (null != t1_element_id)
					//        && (t0_element_id.equalsIgnoreCase(t1_element_id))) {
					//        existsInT0 = true;
					//        break;
					//    } // end if
					// } // end for

					if (!existsInT0) {
						// ver si tiene flag set to delete en true; si lo tiene, no es necesario agregarla
						// otra condicion es que no puede tener el id, porque hasta ahora, no se ha creado

						if (t1_element.isToDelete()) {
							continue;
						}
						if (null != t1_element.getIdDireccion()) {
							continue;
						}

						// se anade a la lista de cambios
						t2_folio_direcciones.add(t1_element);
						// se remueve de la otra lista ?
						// por el momento, no, para no afectar la iteracion; luego se podrian dejar en un arreglo

					} // end if

				} // end for
             */
            //Inicia nuevo codigo
            int numDirFolios = t1_folio_direcciones.size();
            Direccion t1_element;
            for (int itDF = 0; itDF < numDirFolios; itDF++) {
                t1_element = new Direccion();
                t1_element = (Direccion) t1_folio_direcciones.get(itDF);
                // ver si tiene flag set to delete en true; si lo tiene, no es necesario agregarla
                // otra condicion es que no puede tener el id, porque hasta ahora, no se ha creado
                if (t1_element.isToDelete()) {
                    continue;
                }
                if (null != t1_element.getIdDireccion()) {
                    continue;
                }
                // se anade a la lista de cambios
                t2_folio_direcciones.add(t1_element);
            }
            //Finaliza nuevo codigo

            // B: recorrido por definitivos
            // B.a: configurar para esta comparacion
            // configurar para esta comparacion
            // la lista modificada es la misma que se usa en t0
            t1_folio_direcciones = t0_folio_direcciones;

            if (null == t1_folio_direcciones) {
                t1_folio_direcciones = new java.util.ArrayList();
            }

            //TODO
            // Este codigo fue cambiado por el inmediatamente posterior.
            /*for (java.util.Iterator iteratorT1 = t1_folio_direcciones.iterator();
													 iteratorT1.hasNext(); ) {
					Direccion t1_element = (Direccion) iteratorT1.next();

					// si tiene flag setToDelete = true, se agrega en la lista de modificaciones
					if( null == t1_element ) {
						continue;
					}

					// debe tener id
					if( null == t1_element.getIdDireccion() ) {
						continue;
					}

					if( t1_element.isToDelete() ) {
						 t2_folio_direcciones.add( t1_element );
					}

				}*/
            //Inicia nuevo codigo
            numDirFolios = t1_folio_direcciones.size();
            for (int itDF = 0; itDF < numDirFolios; itDF++) {
                t1_element = new Direccion();
                t1_element = (Direccion) t1_folio_direcciones.get(itDF);

                // si tiene flag setToDelete = true, se agrega en la lista de modificaciones
                if (null == t1_element) {
                    continue;
                }

                // debe tener id
                if (null == t1_element.getIdDireccion()) {
                    continue;
                }

                if (t1_element.isToDelete()) {
                    t2_folio_direcciones.add(t1_element);
                }

            }
            //Finaliza nuevo codigo

            // 3. si no hay cambios, se deja nula la lista de cambios
            if ((null == t2_folio_direcciones)
                    || (t2_folio_direcciones.size() == 0)) {
                t2_folio_direcciones = null;
            }

        } // :delta_folio_direcciones

        session.removeAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

        // -----------------------------------------------------------------------------------------------
        // fijar los valores en sesion y despachar el evento
        Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
        org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
        String UID = request.getSession().getId();
        Usuario usuarioNeg = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
        String determinaInm = request.getParameter(CFolio.FOLIO_DETERMINACION_INMUEBLE);

        Folio folio = (Folio) request.getSession().getAttribute(WebKeys.FOLIO);

        if (determinaInm != null && !determinaInm.equals(WebKeys.SIN_SELECCIONAR)) {
            folio.setDeterminaInm(determinaInm);
        }
        request.getSession().setAttribute(CFolio.FOLIO_DETERMINACION_INMUEBLE, folio.getDeterminaInm());
        //crear e inicializar el evento
        EvnCalificacion evento = new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_DIRECCION);
        evento.setTurno(turno);
        evento.setUID(UID);
        evento.setUsuarioNeg(usuarioNeg);
        evento.setNuevasDirecciones(t2_folio_direcciones);
        evento.setFolio(folio);

        return evento;

        }
        
	private Evento verDetallesFolio(HttpServletRequest request) {
		
		HttpSession session = request.getSession();		
		this.preservarInfoFormulario(request);

		//SE CREAN LAS VARIABLES NECESARIAS
		org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
		param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
		param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		Circulo param_Circulo;
		param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);
		
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);

		//SE RECUPERAN LOS VALORES DEL FORMULARIO
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);

		if (null == idMatricula) {
			idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
		}

		Folio local_Folio = new Folio();
		local_Folio.setIdMatricula(idMatricula);
      
		//SE REMUEVE EL MENSAJE DE LA SESIÓN.
		session.removeAttribute(WebKeys.MENSAJE);

		EvnDigitacionMasiva local_Result;
		local_Result = new EvnDigitacionMasiva(param_UsuarioAuriga, EvnDigitacionMasiva.VER_DETALLES_FOLIO, turno,param_UsuarioSir);
		local_Result.setFolio(local_Folio);
		
		//pq
		request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_DIRECCION, new Boolean(true));
		request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_LINDEROS, new Boolean(true));
                request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_LINDEROS_DEFINIDOS, new Boolean(true));
		request.getSession().setAttribute(AWCalificacion.HABILITAR_EDICION_COMPLEMENTACION, new Boolean(true));
		request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, null);
		try {
			consultarAnotacionesFolio(request);
		} catch (AccionWebException e) {
			e.printStackTrace();
		}
		//Fin PQ
		
		return local_Result;

	}

	/**
	 * Método que permite bloquear los folios en la fase de digitación 
	 * @param request
	 * @return
	 */
	private EvnDigitacionMasiva tomarFoliosDigitacion(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
		request.getSession().removeAttribute(WebKeys.LISTA_DIFERENCIAS);

		EvnDigitacionMasiva evn = new EvnDigitacionMasiva(usuarioAuriga, EvnDigitacionMasiva.TOMAR_FOLIO_DIGITACION, turno, (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO));

		return evn;
	}

	/**
	 * Método que permite enviar la información del folio que quiera consulta
	 *
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	private Evento cargarInformacionFolio(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();		
		this.preservarInfoFormulario(request);

		//SE CREAN LAS VARIABLES NECESARIAS
		org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
		param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
		param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		Circulo param_Circulo;
		param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

		//SE RECUPERAN LOS VALORES DEL FORMULARIO
		String idMatricula = param_Circulo.getIdCirculo() + "-";
		idMatricula += request.getParameter(CFolio.ID_MATRICULA);

		if (null == idMatricula) {
			idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
		}

		Folio local_Folio = new Folio();
		local_Folio.setIdMatricula(idMatricula);

		//SE REMUEVE EL MENSAJE DE LA SESIÓN.
		session.removeAttribute(WebKeys.MENSAJE);

		EvnDigitacionMasiva local_Result;
		local_Result = new EvnDigitacionMasiva(param_UsuarioAuriga, EvnDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA, param_UsuarioSir);
		local_Result.setFolio(local_Folio);
		return local_Result;

	}
	
	/**
	 * Método que permite reload de la info
	 *
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	private Evento reload(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();		
		this.preservarInfoFormulario(request);

		//SE CREAN LAS VARIABLES NECESARIAS
		org.auriga.core.modelo.transferObjects.Usuario param_UsuarioAuriga;
		param_UsuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) session.getAttribute(SMARTKeys.USUARIO_EN_SESION);

		gov.sir.core.negocio.modelo.Usuario param_UsuarioSir;
		param_UsuarioSir = (gov.sir.core.negocio.modelo.Usuario) session.getAttribute(WebKeys.USUARIO);

		Circulo param_Circulo;
		param_Circulo = (Circulo) session.getAttribute(WebKeys.CIRCULO);

		//SE RECUPERAN LOS VALORES DEL FORMULARIO
		String idMatricula = param_Circulo.getIdCirculo() + "-";
		idMatricula += request.getParameter(CFolio.ID_MATRICULA);

		if (null == idMatricula) {
			idMatricula = (String) session.getAttribute(CFolio.ID_MATRICULA);
		}

		Folio local_Folio = new Folio();
		local_Folio.setIdMatricula(idMatricula);

		EvnDigitacionMasiva local_Result;
		local_Result = new EvnDigitacionMasiva(param_UsuarioAuriga, EvnDigitacionMasiva.DIGITACION_MASIVA_RELOAD, param_UsuarioSir);
		//local_Result.setFolio(local_Folio);
		return local_Result;

	}

	/**
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento editarFolios(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		this.preservarInfoFormulario(request);
		Direccion direccion = null;

		ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException();

		String parametro = request.getParameter(WebKeys.PARAMETRO);
		Folio folio = new Folio();
		Oficio oficio = null;
		List folios = new Vector();
		String desde = null;
		String hasta = null;
		String tipoActualizaccion = null;

		session.removeAttribute(WebKeys.MENSAJE);

		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		long proceso = turno.getIdProceso();

		String salvedad = request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO);
                 /**		
                   *  @create Carlos Torres
                   *  @change Se comenta las instruciones que validad salvedad para el procesos de correcciones
                   *  @mantis 11648: Acta - Requerimiento No 029_453_Salvedades_Digitador_Masivo_Correcciones
                   * 
                  if ((String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCION) || String.valueOf(proceso).equals(CProceso.PROCESO_CORRECCIONES))) {
		
				if ((salvedad == null) || salvedad.equals("")) {
						exception.addError("Debe ingresar una salvedad válida");
				} else {
  
						if (salvedad.length() < 30) {
						      exception.addError("La salvedad debe tener por lo menos 30 carácteres.");
						}
                                }
			}*/
		

		if (parametro.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION)) {
			tipoActualizaccion = CDigitacionMasiva.OPCION_COMPLEMENTACION;
			String complementacion = request.getParameter(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
			String idComplementacion = request.getParameter(CComplementacion.ID_COMPLEMENTACION);
			String tipoAccion = request.getParameter(CDigitacionMasiva.COMPLEMENTACION_ACCION);
			desde = request.getParameter(CDigitacionMasiva.COMPLEMENTACION_DESDE);
			hasta = request.getParameter(CDigitacionMasiva.COMPLEMENTACION_HASTA);

			if ((complementacion == null) || complementacion.equals("")) {
				exception.addError("Debe ingresar una complementación válida");
			}

			if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango inferior correcto");
			}

			if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango superior correcto");
			}

			if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
					exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
				}
			}

			if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
				if (idComplementacion == null) {
					exception.addError("Debe ingresar un identificador para la complementación válido");
				}
			}

			if (exception.getErrores().size() > 0) {
				throw exception;
			} else {
				Complementacion comp = new Complementacion();
				comp.setComplementacion(complementacion);

				if ((tipoAccion != null) && tipoAccion.equals(CFolio.ASOCIAR)) {
					comp.setIdComplementacion(idComplementacion);
				}

				folio.setComplementacion(comp);
			}
		}

		if (parametro.equals(CDigitacionMasiva.OPCION_LINDERO)) {
			tipoActualizaccion = CDigitacionMasiva.OPCION_LINDERO;
                        /**
                        * @Autor: Edgar Lora
                        * @Mantis: 0013038
                        * @Requerimiento: 060_453
                        */
			String lindero = " " + request.getParameter(CDigitacionMasiva.LINDERO_LINDERO);
			desde = request.getParameter(CDigitacionMasiva.LINDERO_DESDE);
			hasta = request.getParameter(CDigitacionMasiva.LINDERO_HASTA);

			if ((lindero == null) || lindero.equals("")) {
				exception.addError("Debe ingresar un lindero válido");
			}

			if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango inferior correcto");
			}

			if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango superior correcto");
			}

			if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
					exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
				}
			}
                        
                        /**
                        * @Autor: Edgar Lora
                        * @Mantis: 0013038
                        * @Requerimiento: 060_453
                        */
                        ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                        try {
                            String matricula = turno.getIdCirculo() + "-" + session.getAttribute(CDigitacionMasiva.LINDERO_MATRICULA);
                            if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
                                if(validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)){
                                    String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                    if(lindero.indexOf(articulo) != -1){
                                        int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                        if(lindero.length() - tamArticulo < 100){
                                            exception.addError("Debe añadir minimo 100 caracteres al campo de linderos.");
                                        }
                                    }else{
                                        exception.addError("El lindero debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                                    }
                                }
                            }
                        } catch (GeneralSIRException ex) {
                            exception.addError(ex.getMessage());
                        }

			if (exception.getErrores().size() > 0) {
				throw exception;
			} else {
				folio.setLindero(lindero);
			}
		}

		if (parametro.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL)) {
			tipoActualizaccion = CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL;
			String despacho = request.getParameter(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL);

			if ((despacho == null) || despacho.equals("")) {
				exception.addError("Debe ingresar un despacho judicial válido");
			}

			if (exception.getErrores().size() > 0) {
				throw exception;
			} else {
				oficio = new Oficio();
				oficio.setTextoOficio(despacho);
				oficio.setTurnoHistoria(obtenerTurnoHistoriaActual(turno));
				oficio.setFirmado(false);
			}
		}

		if (parametro.equals(CDigitacionMasiva.OPCION_DIRECCION)) {
			tipoActualizaccion = CDigitacionMasiva.OPCION_DIRECCION;
			List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);

			if (direcciones == null) {
				direcciones = new Vector();
			}

			String valorEje1 = request.getParameter(CFolio.FOLIO_EJE1);
			String valorValor1 = request.getParameter(CFolio.FOLIO_VALOR1);
			String valorEje2 = request.getParameter(CFolio.FOLIO_EJE2);
			String valorValor2 = request.getParameter(CFolio.FOLIO_VALOR2);

			desde = request.getParameter(CDigitacionMasiva.DIRECCION_DESDE);
			hasta = request.getParameter(CDigitacionMasiva.DIRECCION_HASTA);

			if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar el primer eje válido para la dirección");
			}

			if (valorValor1.length() <= 0) {
				if (valorEje1 != null && !valorEje1.equals(CDireccion.SIN_DIRECCION)) {
					exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
				}
			}

			if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
				valorEje2 = new String();
			} else {
				if (valorValor2.length() <= 0) {
					exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
				}
			}

			List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
			direccion = new Direccion();

			if (ejes != null) {
				Iterator itEje = ejes.iterator();

				while (itEje.hasNext()) {
					ElementoLista elementoEje = (ElementoLista) itEje.next();
					Eje eje;

					if (elementoEje.getId().equals(valorEje1)) {
						eje = new Eje();
						eje.setIdEje(elementoEje.getId());
						eje.setNombre(elementoEje.getValor());
						direccion.setEje(eje);
						direccion.setValorEje(valorValor1);
					}

					if (elementoEje.getId().equals(valorEje2)) {
						eje = new Eje();
						eje.setIdEje(elementoEje.getId());
						eje.setNombre(elementoEje.getValor());
						direccion.setEje1(eje);
					}

					if (valorValor2 != null) {
						direccion.setValorEje1(valorValor2);
					}
				}
			} else {
				exception.addError("La lista de los ejes no se encontro");
			}

			String complemento = request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR);
			String spec = null;
			if (direccion != null && direccion.getEje() != null && direccion.getEje().getNombre() != null) {
				spec = direccion.getEje().getNombre() + " " + valorValor1;
				if (direccion.getEje1() != null && direccion.getEje1().getNombre() != null) {
					spec += " " + direccion.getEje1().getNombre();
					if (valorValor2 != null) {
						spec += " " + valorValor2;
					}
				}
			}

			if (complemento != null && spec != null) {
				complemento = spec + " " + complemento;
			} else if (complemento == null && spec != null) {
				complemento = spec;
			}
			direccion.setEspecificacion(complemento != null ? complemento.toUpperCase() : null);

			if ((desde == null) || desde.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango inferior correcto");
			}

			if ((hasta == null) || hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				exception.addError("Debe seleccionar un rango superior correcto");
			}

			if (!desde.equals(WebKeys.SIN_SELECCIONAR) && !hasta.equals(WebKeys.SIN_SELECCIONAR)) {
				if ((new Integer(desde).intValue()) > (new Integer(hasta).intValue())) {
					exception.addError("Seleccione un rango válido. El rango inferior no debe ser estar por encima del rango superior");
				}
			}

			if (exception.getErrores().size() > 0) {
				throw exception;
			} else {
				folio.addDireccione(direccion);
			}
		}

		if (!parametro.equals(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL)) {
			/*
			if(salvedad!=null && !salvedad.equals("") && salvedad.length()>=50){
					SalvedadFolio salvedadFolio = new SalvedadFolio();
					salvedadFolio.setDescripcion(salvedad);
					folio.addSalvedade(salvedadFolio);
			}
			*/

			Solicitud solicitud = (Solicitud) turno.getSolicitud();
			if (solicitud == null) {
				solicitud = new Solicitud();
			}

			List solFolio = solicitud.getSolicitudFolios();
			if (solFolio == null) {
				solFolio = new Vector();
			}

			List temp = new ArrayList();
			temp.addAll(solFolio);

			Collections.sort(temp, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					SolicitudFolio solFolio1 = (SolicitudFolio) arg0;
					SolicitudFolio solFolio2 = (SolicitudFolio) arg1;
					return solFolio1.getIdMatricula().compareTo(solFolio2.getIdMatricula());
				}
			});

			int i = 0;
			int from = (new Integer(desde)).intValue();
			int to = (new Integer(hasta)).intValue();

			for (i = from; i <= to; i++) {
				SolicitudFolio sol = (SolicitudFolio) temp.get(i);
				FolioPk folioID = new FolioPk();
				folioID.idMatricula = sol.getFolio().getIdMatricula();
				folios.add(folioID);
			}
		}

		// build-message -----------------------------------------------------------------
		
		Fase fase = null;
		fase = (Fase)session.getAttribute(WebKeys.FASE); 
		
		if (fase != null && !fase.getID().equals(CFase.CAL_DIGITACION))
		{
			             /**		
                    *  @create Carlos Torres
                    *  @change Se agrega una condición para asegurar que la salvedad sea diferente de null 
                    *          y diferente de cadena vacía 
                    * */
                    if(salvedad!=null && !salvedad.equals("")){
			SalvedadFolio salvedadFolio = new SalvedadFolio();
			salvedadFolio.setDescripcion(salvedad);
			salvedadFolio.setNumRadicacion(turno.getIdWorkflow());
			folio.addSalvedade(salvedadFolio);
                    }

		}

		EvnDigitacionMasiva local_Result;
		local_Result = new EvnDigitacionMasiva(usuarioAuriga, EvnDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA, turno, usuarioSIR);
		local_Result.setFolio(folio);
		local_Result.setTipoActualizacion(tipoActualizaccion);
		local_Result.setOficio(oficio);
		local_Result.setConjuntoFolios(folios);

		return local_Result;

	}
	
	/**
	 * @param request
	 */
	private void preservarInfoFormulario(HttpServletRequest request){
		
		request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_ACCION , request.getParameter(CDigitacionMasiva.COMPLEMENTACION_ACCION));
		request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION , request.getParameter(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION));
		request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_DESDE , request.getParameter(CDigitacionMasiva.COMPLEMENTACION_DESDE));
		request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_HASTA , request.getParameter(CDigitacionMasiva.COMPLEMENTACION_HASTA));
		request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_MATRICULA , request.getParameter(CDigitacionMasiva.COMPLEMENTACION_MATRICULA));
		request.getSession().setAttribute(CComplementacion.ID_COMPLEMENTACION , request.getParameter(CComplementacion.ID_COMPLEMENTACION));		
		request.getSession().setAttribute(CDigitacionMasiva.LINDERO_ACCION , request.getParameter(CDigitacionMasiva.LINDERO_ACCION));
		request.getSession().setAttribute(CDigitacionMasiva.LINDERO_DESDE , request.getParameter(CDigitacionMasiva.LINDERO_DESDE));
		request.getSession().setAttribute(CDigitacionMasiva.LINDERO_HASTA , request.getParameter(CDigitacionMasiva.LINDERO_HASTA));
		request.getSession().setAttribute(CDigitacionMasiva.LINDERO_LINDERO , request.getParameter(CDigitacionMasiva.LINDERO_LINDERO));
		request.getSession().setAttribute(CDigitacionMasiva.LINDERO_MATRICULA , request.getParameter(CDigitacionMasiva.LINDERO_MATRICULA));
		request.getSession().setAttribute(CDigitacionMasiva.DIRECCION_ACCION , request.getParameter(CDigitacionMasiva.DIRECCION_ACCION));
		request.getSession().setAttribute(CDigitacionMasiva.DIRECCION_DESDE , request.getParameter(CDigitacionMasiva.DIRECCION_DESDE));
		request.getSession().setAttribute(CDigitacionMasiva.DIRECCION_HASTA , request.getParameter(CDigitacionMasiva.DIRECCION_HASTA));
		request.getSession().setAttribute(CDigitacionMasiva.DIRECCION_MATRICULA , request.getParameter(CDigitacionMasiva.DIRECCION_MATRICULA));
		request.getSession().setAttribute(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL , request.getParameter(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL));
		request.getSession().setAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO,request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO));
	}
	
	private void limpiarCamposFormularioDigitacion(HttpServletRequest request){
		request.getSession().removeAttribute(CDigitacionMasiva.COMPLEMENTACION_ACCION);
		request.getSession().removeAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
		request.getSession().removeAttribute(CDigitacionMasiva.COMPLEMENTACION_DESDE);
		request.getSession().removeAttribute(CDigitacionMasiva.COMPLEMENTACION_HASTA);
		request.getSession().removeAttribute(CDigitacionMasiva.COMPLEMENTACION_MATRICULA);
		request.getSession().removeAttribute(CComplementacion.ID_COMPLEMENTACION);		
		request.getSession().removeAttribute(CDigitacionMasiva.LINDERO_ACCION);
		request.getSession().removeAttribute(CDigitacionMasiva.LINDERO_DESDE);
		request.getSession().removeAttribute(CDigitacionMasiva.LINDERO_HASTA);
		request.getSession().removeAttribute(CDigitacionMasiva.LINDERO_LINDERO);
		request.getSession().removeAttribute(CDigitacionMasiva.LINDERO_MATRICULA);
		request.getSession().removeAttribute(CDigitacionMasiva.DIRECCION_ACCION);
		request.getSession().removeAttribute(CDigitacionMasiva.DIRECCION_DESDE);
		request.getSession().removeAttribute(CDigitacionMasiva.DIRECCION_HASTA);
		request.getSession().removeAttribute(CDigitacionMasiva.DIRECCION_MATRICULA);
		request.getSession().removeAttribute(CDigitacionMasiva.OPCION_DESPACHO_JUDICIAL);
		request.getSession().removeAttribute(CFolio.FOLIO_EJE1);
		request.getSession().removeAttribute(CFolio.FOLIO_EJE2);
		request.getSession().removeAttribute(CFolio.FOLIO_VALOR1);
		request.getSession().removeAttribute(CFolio.FOLIO_VALOR2);
		request.getSession().removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
	}
	
	/**
	 * @param request
	 */
	private void preservarInfoFormularioAnotaciones(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		
		List foliosOrigen = (List)session.getAttribute(WebKeys.LISTA_FOLIOS);
		if(foliosOrigen==null){
			foliosOrigen = new ArrayList();			
		}
		
		Folio folio = null;
		for(int i = 0; i < foliosOrigen.size() ; i++ ){
			folio = (Folio)foliosOrigen.get(i);
			
			if(folio!=null){

				String desde = request.getParameter(WebKeys.DESDE + folio.getIdMatricula());
				String hasta = request.getParameter(WebKeys.HASTA + folio.getIdMatricula());

				session.setAttribute(WebKeys.DESDE + folio.getIdMatricula(), desde);
				session.setAttribute(WebKeys.HASTA + folio.getIdMatricula(), hasta);
				
			}
		}
		
	}
	


	/**
	 * @param turno
	 * @return
	 */
	private TurnoHistoria obtenerTurnoHistoriaActual(Turno turno) {
		List tHistorias = turno.getHistorials();
		ListIterator iter = tHistorias.listIterator();

		String faseActual = turno.getIdFase();

		TurnoHistoria activo = null;
		while (iter.hasNext()) {
			TurnoHistoria tH = (TurnoHistoria) iter.next();
			if (tH.isActivo() && tH.getFase().equals(faseActual)) {
				activo = tH;
				break;
			}
		}
		return activo;
	}

	/**
	 * doProcess_BtnComplementacionCargar
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	private Evento doProcess_BtnBack(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
                /**
                * @Autor: Edgar Lora
                * @Mantis: 0013038
                * @Requerimiento: 060_453
                */
                Turno turno = (Turno)session.getAttribute( WebKeys.TURNO );
                List folios = turno.getSolicitud().getSolicitudFolios();
                Iterator iFolios = folios.iterator();
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException();
                while(iFolios.hasNext()){
                    SolicitudFolio sf = (SolicitudFolio)iFolios.next();
                    Folio folio = sf.getFolio();                    
                    try {
                        String matricula = folio.getIdMatricula();
                        String lindero = validacionesSIR.getLinderoDeMatricula(matricula);
                        if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
							/**
                             * @author: Cesar Ramirez
                             * @change: 1157.111.NO.INCLUIR.LINDEROS.PROPIEDAD.HORIZONTAL
                             * Se cambia el método de de validarLinderos por validarLinderosPrimeraAnotacionDerivada.
                             **/
                            if (validacionesSIR.validarLinderosPrimeraAnotacionDerivada(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS, CTipoAnotacion.DERIVADO)) {
                                String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                if(lindero.indexOf(articulo) != -1){
                                    int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                    if(lindero.length() - tamArticulo < 100){
                                        exception.addError("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                                    }
                                }else{
                                    exception.addError("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                                }
                            }
                        }
                    } catch (GeneralSIRException ex) {
                        exception.addError(ex.getMessage());
                    }
                }
                if (exception.getErrores().size() > 0) {
                        throw exception;
                }
		return null;

	}
	
	private Evento regresar(HttpServletRequest request) throws AccionWebException {

		HttpSession session = request.getSession();
		final String PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS = 
			"PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS";
		session.removeAttribute(PAGEITEM_OPCIONESDIGITACIONMASIVA_FROMPROCESS);
		session.removeAttribute("NOMBRE_RESULTADO_CALIFICACION");
		this.limpiarCamposFormularioDigitacion(request);
		return null;

	}
	
	//MÉTODOS PARA CONSTRUCCIÓN DE LA COMPLEMENTACIÓN A PARTIR DE LAS ANOTACIONES DE UN FOLIO
	/**
	 * Método que guarda los folios a partir de los cuáles se obtendrán las 
	 * anotaciones que construirá la complementación.
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	private Evento elegirFolios(HttpServletRequest request) throws AccionWebException {

		//SE CREAN LAS VARIABLES NECESARIAS
		ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException(); 
		
		String[] matriculasCopiar = request.getParameterValues(WebKeys.TITULO_CHECKBOX_ELIMINAR);
		if(matriculasCopiar == null || matriculasCopiar.length == 0){
			exception.addError("Debe seleccionar por lo menos un folio a partir del cuál se obtendrán las anotaciones que construirán la complementación");
		}

		if(exception.getErrores().size()>0){
			throw exception;
		}
		
		HttpSession session = request.getSession();
		Turno turno = (Turno)session.getAttribute(WebKeys.TURNO);
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();

		//SE LLENA LA LISTA DE FOLIOS A DÓNDE DESEA COPIARSE LA ANOTACIÓN
		List foliosACopiar = new ArrayList();
		if(matriculasCopiar !=null ){
			for (int i = 0; i < matriculasCopiar.length; i++) {
				Iterator it = solFolios.iterator();
				while(it.hasNext()){
					SolicitudFolio solFolio = (SolicitudFolio)it.next();
					if(solFolio.getIdMatricula().equals(matriculasCopiar[i])){
						Folio folio = new Folio();
						folio.setIdMatricula(solFolio.getIdMatricula());
						folio.setDefinitivo(solFolio.getFolio().isDefinitivo());
						folio.setNombreLote(solFolio.getFolio().getNombreLote());
						foliosACopiar.add(folio);
						break;
					}
				}
			}
		}
		
		session.setAttribute(WebKeys.LISTA_FOLIOS, foliosACopiar);
		
		return null;

	}	
	
	/**
	 * Método que guarda las anotaciones a partir de los cuáles se construirá la complementación.
	 * @param request HttpServletRequest
	 * @return Evento
	 */
	private Evento elegirAnotaciones(HttpServletRequest request) throws AccionWebException {

		//SE CREAN LAS VARIABLES NECESARIAS
		ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException(); 
		HttpSession session = request.getSession();
		List rangoAnotaciones = new ArrayList();
		
		List foliosOrigen = (List)session.getAttribute(WebKeys.LISTA_FOLIOS);
		if(foliosOrigen==null){
			foliosOrigen = new ArrayList();			
		}
		
		boolean folioOk = true;
		int anotacionInicial = 0;
		int anotacionFinal = 0;
		Folio folio = null;
		for(int i = 0; i < foliosOrigen.size() ; i++ ){

			folioOk = true;
			anotacionInicial = 0;
			anotacionFinal = 0;			
			
			folio = (Folio)foliosOrigen.get(i);
			Folio folioTemp = new Folio();
			if(folio!=null){
				folioTemp.setIdMatricula(folio.getIdMatricula());
				//folioTemp.setZonaRegistral(folio.getZonaRegistral());
				String desde = request.getParameter(WebKeys.DESDE + folio.getIdMatricula());
				String hasta = request.getParameter(WebKeys.HASTA + folio.getIdMatricula());
				
				//VALIDACIÓN ANOTACIÓN INICIAL
				if(desde == null || desde.equals("")){
					exception.addError("Para el folio " + folio.getIdMatricula() + " debe ingresar una anotación inicial válida.");
					folioOk = false;
				}else{
					try{
						anotacionInicial = new Integer(desde).intValue();
					}catch(Exception e){
						exception.addError("Para el folio " + folio.getIdMatricula() + " debe ingresar un número en la anotación inicial.");
						folioOk = false;	
					}
				}
				
				//VALIDACIÓN ANOTACIÓN FINAL
				if(hasta == null || hasta.equals("")){
					exception.addError("Para el folio " + folio.getIdMatricula() + " debe ingresar una anotación final válida.");
					folioOk = false;
				}else{
					try{
						anotacionFinal = new Integer(hasta).intValue();
					}catch(Exception e){
						exception.addError("Para el folio " + folio.getIdMatricula() + " debe ingresar un número en la anotación final.");
						folioOk = false;	
					}
				}
				
				//SI LOS NÚMEROS SON ENTEROS, PERO SON NEGATIVAS O LA ANOTACIÓN INICIAL ES SUPERIOR A LA ANOTACIÓN FINAL
				//SE GENERA LA CORRESPONDIENTE EXCEPCIÓN.
				if(anotacionFinal <= 0 || anotacionFinal <= 0 ){
					exception.addError("Para el folio " + folio.getIdMatricula() + " la anotación inicial y final son inválidas.");
					folioOk = false;
				}else if(anotacionInicial > anotacionFinal){
					exception.addError("Para el folio " + folio.getIdMatricula() + " la anotación inicial no puede ser mayor a la anotación final.");
					folioOk = false;
				}
				
				if(folioOk){
					RangoAnotacion ra = new RangoAnotacion();
					ra.setAnotacionInicio(anotacionInicial);
					ra.setAnotacionFin(anotacionFinal);
					ra.setFolio(folioTemp);
					rangoAnotaciones.add(ra);
				}
				
				session.setAttribute(WebKeys.DESDE + folio.getIdMatricula(), desde);
				session.setAttribute(WebKeys.HASTA + folio.getIdMatricula(), hasta);
				
			}
		}
		
		if(exception.getErrores().size()>0){
			throw exception;
		}

		Usuario usuarioSIR = (Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);

		EvnDigitacionMasiva evento = new EvnDigitacionMasiva(usuarioAuriga,EvnDigitacionMasiva.CONSTRUIR_COMPLEMENTACION,usuarioSIR);
		evento.setRangoAnotaciones(rangoAnotaciones);
		return  evento;

	}		
	
	/**
	 * Método que quita un folio para que no forme parte de la construcción de la complementación 
	 * @param request
	 * @return
	 */
	private void quitarFolios(HttpServletRequest request) {
		
		this.preservarInfoFormularioAnotaciones(request);
		List folios = (List) request.getSession().getAttribute(WebKeys.LISTA_FOLIOS);
		String matricula = request.getParameter(CFolio.ID_MATRICULA);
		
		Folio folio = null;
		Iterator it = folios.iterator();
		for(int i = 0; i< folios.size() ; i++){
			folio	= (Folio)folios.get(i);
			if(folio.getIdMatricula().equals(matricula)){
				i = folios.size() + 1; 
			}
		}
		
		if(folio != null){
			folios.remove(folio);
		}
		
		request.getSession().setAttribute(WebKeys.LISTA_FOLIOS ,folios);
		
	}	
	
	/**
	 * Método que borra de la sesión los atributos que estaban para la construcción de la complementación
	 * y retorna a la página de digitación masiva.  
	 * @param request
	 * @return
	 */
	private EvnDigitacionMasiva salirConstruccionComplementacion(HttpServletRequest request) {
		
		//SE BORRAN LOS ELEMENTOS DE LA SESIÓN YQ QUE NO SE NECESITAN
		List foliosOrigen = (List)request.getSession().getAttribute(WebKeys.LISTA_FOLIOS);
		if(foliosOrigen!=null){
			
			Iterator it = foliosOrigen.iterator();
			while(it.hasNext()){
				Folio folio = (Folio)it.next();
				request.getSession().removeAttribute(WebKeys.DESDE + folio.getIdMatricula());
				request.getSession().removeAttribute(WebKeys.HASTA + folio.getIdMatricula());
			}
			
			request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS);		
			
		}

		return null;
	}			
	
	

	/**
	 * @see org.auriga.smart.web.acciones.AccionWeb#doEnd(javax.servlet.http.HttpServletRequest, org.auriga.smart.eventos.EventoRespuesta)
	 */
	public void doEnd(HttpServletRequest request, EventoRespuesta evento) {

		if (evento instanceof EvnRespDigitacionMasiva) {
			EvnRespDigitacionMasiva respuesta = (EvnRespDigitacionMasiva) evento;

			if (respuesta.getTipoEvento().equals(EvnRespDigitacionMasiva.TOMAR_FOLIO)) {
				Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
				Solicitud solicitud = (Solicitud) turno.getSolicitud();
				List solFolio = solicitud.getSolicitudFolios();
				List matriculas = new ArrayList();
				Iterator itSolFolio = solFolio.iterator();
				while (itSolFolio.hasNext()) {
					SolicitudFolio sol = (SolicitudFolio) itSolFolio.next();
					matriculas.add(sol.getFolio().getIdMatricula());
				}
				request.getSession().setAttribute(AWDigitacionMasiva.LISTA_MATRICULAS_TURNO, matriculas);
				
				/**
				 * Si el turno es de registro, al calificador se le dan los permisos
				 * de complementación, linderos y direccion
				 */
				java.util.List modelPermisosList = null;

                if (turno.getSolicitud() instanceof SolicitudCorreccion){
                        SolicitudCorreccion solicitudCorreccion
                                = (SolicitudCorreccion) turno.getSolicitud();
                        modelPermisosList = solicitudCorreccion.getPermisos();
                }

                if (turno.getSolicitud() instanceof SolicitudRegistro){
                        SolicitudRegistro solicitudCorreccion
                                = (SolicitudRegistro) turno.getSolicitud();
                        modelPermisosList = solicitudCorreccion.getPermisos();
                }

                /*
                 * si el folio fue producto de segregación se debe permitir editar tipo de predio
                 * TFS 3575
                 */
                if (modelPermisosList != null)
                {
                	/*
                	 * A la colección inicial de permisos se le añade los permisos
                	 * FOLIO_TIPO_PREDIO_ID
                	 */
                	List permisosAdicionales = new ArrayList();
                	SolicitudPermisoCorreccion permisoCRAdicional = null;

                	permisoCRAdicional = new SolicitudPermisoCorreccion();
                	permisoCRAdicional.setIdPermiso(PermisosCorreccionAspectModelConstants.FOLIO_COMPLEMENTACION_ID);
                	permisosAdicionales.add(permisoCRAdicional);
                	
                	permisoCRAdicional = new SolicitudPermisoCorreccion();
                	permisoCRAdicional.setIdPermiso(PermisosCorreccionAspectModelConstants.FOLIO_CABIDAYLINDEROS_ID);
                	permisosAdicionales.add(permisoCRAdicional);
                	
                	permisoCRAdicional = new SolicitudPermisoCorreccion();
                	permisoCRAdicional.setIdPermiso(PermisosCorreccionAspectModelConstants.FOLIO_DIRECCION_ID);
                	permisosAdicionales.add(permisoCRAdicional);

                	for (Iterator permisosItera = modelPermisosList.iterator(); 
                	permisosItera.hasNext();)
                	{
                		SolicitudPermisoCorreccion permisoCorr1 = (SolicitudPermisoCorreccion)permisosItera.next();
                		for (Iterator permisosAdItera = permisosAdicionales.iterator(); 
                		permisosAdItera.hasNext();)
                		{
                			SolicitudPermisoCorreccion permisoCorr2 = (SolicitudPermisoCorreccion)permisosAdItera.next();
                			if (permisoCorr1.getIdPermiso() != null 
                					&& permisoCorr1.getIdPermiso().equals(permisoCorr2.getIdPermiso()))
                			{
                				permisosAdItera.remove();
                			}
                		}
                	}
                	permisosAdicionales.addAll(modelPermisosList);
                	modelPermisosList = permisosAdicionales;
                }
                
                request.getSession().setAttribute(gov.sir.core.web.acciones.correccion.AwCorrecciones_Constants.PARAM__MODEL_PERMISOS_LIST, modelPermisosList );
			}

			if (respuesta.getTipoEvento().equals(EvnRespDigitacionMasiva.CONSULTAR_FOLIO_DIGITACION_MASIVA)) {
				cargarInformacionFolio(request, respuesta);
			}
			if (respuesta.getTipoEvento().equals(EvnRespDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA)) {
				cargarResultadoActualizacion(request, respuesta);
			}	
			if (respuesta.getTipoEvento().equals(EvnRespDigitacionMasiva.CONSTRUIR_COMPLEMENTACION)) {
				
				String complementacionActual = (String)request.getSession().getAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
				if(complementacionActual==null){
					complementacionActual = "";
				}
				
				complementacionActual = complementacionActual + (respuesta!=null && respuesta.getComplementacion()!=null ?"\n\n" + respuesta.getComplementacion() : "" );
				
				request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION, complementacionActual);
				request.getSession().setAttribute(CDigitacionMasiva.COMPLEMENTACION_ACCION, CFolio.COPIAR);
				
				//SE BORRAN LOS ELEMENTOS DE LA SESIÓN YQ QUE NO SE NECESITAN
				List foliosOrigen = (List)request.getSession().getAttribute(WebKeys.LISTA_FOLIOS);
				Iterator it = foliosOrigen.iterator();
				while(it.hasNext()){
					Folio folio = (Folio)it.next();
					request.getSession().removeAttribute(WebKeys.DESDE + folio.getIdMatricula());
					request.getSession().removeAttribute(WebKeys.HASTA + folio.getIdMatricula());
				}
				request.getSession().removeAttribute(WebKeys.LISTA_FOLIOS);
			}
			
			if (respuesta.getTipoEvento().equals(EvnRespDigitacionMasiva.VER_DETALLES_FOLIO)) {
				Folio folio = respuesta.getFolio();
				request.getSession().removeAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
				request.getSession().setAttribute(WebKeys.FOLIO, folio);
                                request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS, respuesta.getFolio().getLinderosDef());
				request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES, new BigDecimal(respuesta.getNumeroAnotaciones()));
				request.getSession().setAttribute(WebKeys.HISTORIAL_AREAS,respuesta.getHistorialAreas());
				request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
                request.getSession().setAttribute(WebKeys.MAYOR_EXTENSION_FOLIO,new Boolean(respuesta.isMayorExtensionFolio()));
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_PADRE,respuesta.getFoliosPadre());
				request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_HIJO,respuesta.getFoliosHijos());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_GRAVAMEN,respuesta.getGravamenes());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_MEDIDAS_CAUTELARES,respuesta.getMedidasCautelares());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_FALSA_TRADICION,respuesta.getFalsaTradicion());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_INVALIDAS,respuesta.getAnotacionesInvalidas());
				request.getSession().setAttribute(WebKeys.LISTA_SALVEDADES_ANOTACIONES,respuesta.getSalvedadesAnotaciones());
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_CANCELACION,respuesta.getCancelaciones());
				request.getSession().setAttribute(WebKeys.TOTAL_ANOTACIONES,new BigDecimal(respuesta.getNumeroAnotaciones()));
				
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_PATRIMONIO_FAMILIAR, respuesta.getAnotacionesPatrimonioFamiliar());	
				request.getSession().setAttribute(WebKeys.LISTA_ANOTACIONES_AFECTACION_VIVIENDA, respuesta.getAnotacionesAfectacionVivienda());	
				request.getSession().setAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES, respuesta.getDirTemporales());

                                if(respuesta.getTurno()!=null){
					request.getSession().setAttribute(WebKeys.TURNO, respuesta.getTurno());
				}
				String complementacionTemp="",complementacionDef="";
				if(respuesta.getFolioDef()!=null && respuesta.getFolioDef().getComplementacion()!=null && respuesta.getFolioDef().getComplementacion().getComplementacion()!=null){
					if(folio.getComplementacion()!=null && folio.getComplementacion().getComplementacion()!=null){
						complementacionTemp=folio.getComplementacion().getComplementacion().substring(respuesta.getFolioDef().getComplementacion().getComplementacion().length(),folio.getComplementacion().getComplementacion().length());
						complementacionDef=folio.getComplementacion().getComplementacion();
					}
				}else{
					if(folio.getComplementacion()!=null && folio.getComplementacion().getComplementacion()!=null){
						complementacionTemp=folio.getComplementacion().getComplementacion();
						complementacionDef=complementacionTemp;
					}
				}
				request.getSession().setAttribute(CFolio.COMPLEMENTACION_ADICION, complementacionTemp);
				request.getSession().setAttribute(CFolio.COMPLEMENTACION,complementacionDef );
				String linderoDef;
				if(respuesta.getFolioDef()!=null && respuesta.getFolioDef().getLindero()!=null && folio.getLindero()!=null){
					String linderoTemp=folio.getLindero().substring(respuesta.getFolioDef().getLindero().length(),folio.getLindero().length());
					request.getSession().setAttribute(CFolio.LINDERO_ADICION,linderoTemp);
					linderoDef=folio.getLindero();
					request.getSession().setAttribute(CFolio.LINDERO,linderoDef);
				}else{
					if(folio.getLindero()!=null){
						request.getSession().setAttribute(CFolio.LINDERO_ADICION,folio.getLindero());
						request.getSession().setAttribute(CFolio.LINDERO,folio.getLindero());
					}
				}
                                
                                String linderosDefinidos;
				if(respuesta.getFolioDef()!=null && respuesta.getFolioDef().getLinderosDef()!=null && folio.getLinderosDef()!=null){
					linderosDefinidos=folio.getLinderosDef();
                                        request.getSession().setAttribute(CFolio.DEFINIR_LINDERO,linderosDefinidos);
					request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS,linderosDefinidos);
				}else{
					if(folio.getLinderosDef()!=null){
						request.getSession().setAttribute(CFolio.DEFINIR_LINDERO,folio.getLinderosDef());
						request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS,folio.getLinderosDef());
					}
				}

				if(respuesta.getFolioDef()!=null)
					request.getSession().setAttribute(CFolio.FOLIO_DEF,respuesta.getFolioDef());
			}
		}
		if(evento instanceof EvnRespPaginadorAnotaciones){
			EvnRespPaginadorAnotaciones respuesta = (EvnRespPaginadorAnotaciones) evento;
			if (respuesta.getTipoEvento().equals(EvnRespPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO)) {
				String ScantidadRegistros=Long.toString(respuesta.getTotalRegistros());
				DatosRespuestaPaginador RPag = new DatosRespuestaPaginador();
				RPag.setCantidadRegistros(respuesta.getTotalRegistros());
				RPag.setNumeroAnotacionesDefinitivas(respuesta.getNumeroAnotacionesDefinitivas());
				PaginadorAvanzado  pag= new PaginadorAvanzado(respuesta.getAnotacionesDefinitivas(), Integer.parseInt(ScantidadRegistros));
				try{
					int npagina= respuesta.getPaginaInicial();
					if(npagina!=0){
						RPag.setAnotacionesActual(pag.getPaginaDesdeCache(npagina));
					}else{
						RPag.setAnotacionesActual(pag.getPaginaDesdeCache(0));
					}
				}catch(PaginadorPaginaNoEncontradaException e){
					RPag.setAnotacionesActual(new Vector());
				}catch (PaginadorCacheNoActualizadoException e) {
					e.printStackTrace(System.out);
				}
				Boolean temp=(Boolean) request.getSession().getAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA);
				if(temp!=null){
					pag.setTipoConsulta(temp.booleanValue());
				}
				request.getSession().setAttribute(respuesta.getNombrePaginador(),pag);
				request.getSession().setAttribute(respuesta.getNombreResultado(),RPag);
				if (respuesta.getFoliosDerivadoPadre() != null) {
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_PADRE, respuesta.getFoliosDerivadoPadre());	
				}
				if (respuesta.getFoliosDerivadoHijo() != null) {
					request.getSession().setAttribute(WebKeys.LISTA_FOLIOS_DERIVADO_HIJO, respuesta.getFoliosDerivadoHijo());
				}
				request.getSession().removeAttribute(WebKeys.RECARGA);
			}
		}
		if(evento instanceof EvnRespFolio){
			EvnRespFolio respuesta = (EvnRespFolio)evento;
			if (respuesta.getTipoEvento().equals(EvnRespFolio.AGREGAR_DIRECCION)){
				if (respuesta.getFolio() != null)
				{
					request.getSession().removeAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
					List direcciones = new ArrayList();
					List direccions= respuesta.getFolio().getDirecciones();
					List dirTemporales = respuesta.getDirTemporales();
					int tam = respuesta.getFolio().getDirecciones().size();
					for(int i=0;i<tam;i++){
						if((tam-(i+1))<dirTemporales.size())
							direcciones.add(direccions.get(i));
					}
					request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
					request.getSession().setAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES, dirTemporales);
				}
			}
			if (respuesta.getTipoEvento().equals(EvnRespFolio.ELIMINAR_DIRECCION)){
				request.getSession().removeAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
				Integer numAplica = (Integer)request.getSession().getAttribute(WebKeys.POSICION);
				List direcciones = (List) request.getSession().getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
				direcciones.remove(numAplica.intValue());
				request.getSession().removeAttribute(WebKeys.POSICION);
				request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
				request.getSession().setAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES, respuesta.getDirTemporales());
			}
			if(respuesta.getTipoEvento().equals(EvnRespFolio.ACTUALIZAR_FOLIO_COMPLEMENTACION)){
				request.getSession().setAttribute(CFolio.COMPLEMENTACION_ADICION,request.getParameter(CFolio.COMPLEMENTACION_ADICION));
				if(respuesta.getFolio().getComplementacion()!=null && respuesta.getFolio().getComplementacion().getComplementacion()!=null)
					request.getSession().setAttribute(CFolio.COMPLEMENTACION,respuesta.getFolio().getComplementacion().getComplementacion());
				
			}
		}
		if(evento instanceof EvnRespCalificacion){
			EvnRespCalificacion respuesta=(EvnRespCalificacion)evento;
			if(respuesta.getTipoEvento().equals("FOLIO_TEMPORAL")){
				if(respuesta.getFolio()!=null)
					request.getSession().setAttribute(WebKeys.FOLIO,respuesta.getFolio());
                                       request.getSession().setAttribute(CFolio.LINDEROS_DEFINIDOS,respuesta.getFolio().getLinderosDef());
                                       request.getSession().setAttribute(CFolio.DEFINIR_LINDERO,respuesta.getFolio().getLinderosDef());
                                       request.getSession().setAttribute(WebKeys.HISTORIAL_AREAS, respuesta.getHistorialAreas());
					if(respuesta.getLinderoTemporal()!=null)
						request.getSession().setAttribute(CFolio.LINDERO_ADICION, respuesta.getLinderoTemporal());
					String lindero="";
					if(respuesta.getFolioDef()!=null && respuesta.getFolioDef().getLindero()!=null)
						lindero=respuesta.getFolioDef().getLindero();
					if( respuesta.getLinderoTemporal()!=null)
						lindero=lindero+" "+ respuesta.getLinderoTemporal();
					request.getSession().setAttribute(CFolio.LINDERO,lindero);
			}
		}

	}

	/**
	 * Permite cargar la información del folio consultado en la sesión para visualizar la información del mismo y
	 * luego a partir de esta información copiarla a otros folios.
	 * @param request
	 * @param evento
	 */
	private void cargarInformacionFolio(HttpServletRequest request, EvnRespDigitacionMasiva evento) {

		HttpSession session = request.getSession();

		Folio local_Folio = null;

		if ((local_Folio = evento.getFolio()) != null) {
			String parametro = request.getParameter(WebKeys.PARAMETRO);
                        
                        if(evento.getHistorialAreas() != null){
                            session.setAttribute(WebKeys.HISTORIAL_AREAS, evento.getHistorialAreas());
                        }
			if (parametro.equals(CDigitacionMasiva.OPCION_COMPLEMENTACION)) {
				String complementacionDigitada = (String) session.getAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
				if ((local_Folio.getComplementacion() != null) && (local_Folio.getComplementacion().getComplementacion() != null)) {
					if (complementacionDigitada != null) {
						session.setAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION, complementacionDigitada + local_Folio.getComplementacion().getComplementacion());
					} else {
						session.setAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION, local_Folio.getComplementacion().getComplementacion());	
					}
				} else {
					if (complementacionDigitada != null) {
						session.setAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION, complementacionDigitada);
					} else {
						session.removeAttribute(CDigitacionMasiva.COMPLEMENTACION_COMPLEMENTACION);
					}
				}

				if ((local_Folio.getComplementacion() != null) && (local_Folio.getComplementacion().getIdComplementacion() != null)) {
					session.setAttribute(CComplementacion.ID_COMPLEMENTACION, local_Folio.getComplementacion().getIdComplementacion());
				} else {
					session.removeAttribute(CComplementacion.ID_COMPLEMENTACION);
				}
			}

			if (parametro.equals(CDigitacionMasiva.OPCION_LINDERO)) {
				if (local_Folio.getLindero() != null) {
					session.setAttribute(CDigitacionMasiva.LINDERO_LINDERO, local_Folio.getLindero());
				} else {
					session.removeAttribute(CDigitacionMasiva.LINDERO_LINDERO);
				}
			}

			if (parametro.equals(CDigitacionMasiva.OPCION_DIRECCION)) {
				if ((local_Folio.getDirecciones() != null) && (local_Folio.getDirecciones().size() > 0)) {
					Direccion direccion = (Direccion) local_Folio.getDirecciones().get(local_Folio.getDirecciones().size() - 1);

					session.removeAttribute(CFolio.FOLIO_EJE1);
					session.removeAttribute(CFolio.FOLIO_EJE2);
					session.removeAttribute(CFolio.FOLIO_VALOR1);
					session.removeAttribute(CFolio.FOLIO_VALOR2);
					session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
					
					String eje_1 = "";
					String eje_2 = "";
					String valorEje = "";
					String valorEje1 = "";

					if ((direccion != null) && (direccion.getEje() != null)) {
						Eje eje = direccion.getEje();

						if (eje.getIdEje() != null) {
							eje_1 = eje.getNombre();
							session.setAttribute(CFolio.FOLIO_EJE1, eje.getIdEje());
						}
					}

					if ((direccion != null) && (direccion.getEje1() != null)) {
						Eje eje1 = direccion.getEje1();

						if (eje1.getIdEje() != null) {
							eje_2 = eje1.getNombre();
							session.setAttribute(CFolio.FOLIO_EJE2, eje1.getIdEje());
						}
					}

					if ((direccion != null) && (direccion.getValorEje() != null)) {
						valorEje = direccion.getValorEje().trim();
						session.setAttribute(CFolio.FOLIO_VALOR1, direccion.getValorEje());
					}

					if ((direccion != null) && (direccion.getValorEje1() != null)) {
						valorEje1 = direccion.getValorEje1().trim();
						session.setAttribute(CFolio.FOLIO_VALOR2, direccion.getValorEje1());
					}

					if ((direccion != null) && (direccion.getEspecificacion() != null)) {
						String spec = direccion.getEspecificacion();
						String spec2 = "";
						
						if (eje_1 != "")
							spec2 = eje_1;
						if (valorEje != "")
							spec2 += " " + valorEje;
						if (eje_2 != "")
							spec2 += " " + eje_2;
						if (valorEje1 != "") 
							spec2 += " " + valorEje1;
						
						spec2 = spec2.trim();
						if((spec.length() > spec2.length()) && spec.substring(0,spec2.length()).equals(spec2))
							spec=spec.substring(spec2.length()+1);
						//session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, direccion.getEspecificacion());
						session.setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, spec);
					}
				} else {
					session.removeAttribute(CFolio.FOLIO_EJE1);
					session.removeAttribute(CFolio.FOLIO_EJE2);
					session.removeAttribute(CFolio.FOLIO_VALOR1);
					session.removeAttribute(CFolio.FOLIO_VALOR2);
					session.removeAttribute(CFolio.FOLIO_COMPLEMENTO_DIR);
				} // if
			} // if
		} // if
		if (datosFoliosForaneos(request, evento)) {
			session.setAttribute(WebKeys.ALERTA_FOLIOS_FORANEOS, new Boolean(true));
		}

	}

	/**
	 * Determina si el folio consultado pertenece al turno, y si no es así muestra un mensaje.
	 * @param request
	 * @param matriculas
	 * @return
	 */
	private boolean datosFoliosForaneos(HttpServletRequest request, EvnRespDigitacionMasiva evento) {

		HttpSession session = request.getSession();

		List matriculas = new ArrayList();
		Turno turno = (Turno) session.getAttribute(WebKeys.TURNO);
		Solicitud solicitud = turno.getSolicitud();
		List solFolios = solicitud.getSolicitudFolios();

		Iterator it = solFolios.iterator();
		while (it.hasNext()) {
			SolicitudFolio solfolio = (SolicitudFolio) it.next();
			matriculas.add(solfolio.getIdMatricula());
		}

		if (evento == null || evento.getFolio() == null) {
			return false;
		}

		String matFolioTraido = evento.getFolio().getIdMatricula();
		Iterator itMatriculas = matriculas.iterator();
		if (matriculas.size() == 0) {
			return true;
		}

		boolean existe = false;
		while (itMatriculas.hasNext()) {
			String matricula = (String) itMatriculas.next();
			if (matricula.equals(matFolioTraido)) {
				existe = true;
			}
		}
		if (!existe) {
			return true;
		}

		return false;
	}

	/**
	 * Muestra el mensaje de operación satisfactoria cuando la copia de información a otros folios ha sido exitosa.
	 * @param request
	 * @param evento
	 */
	private void cargarResultadoActualizacion(HttpServletRequest request, EvnRespDigitacionMasiva evento) {

		HttpSession session = request.getSession();
		String parametro = request.getParameter(WebKeys.PARAMETRO);

		String local_TipoEvento;
		local_TipoEvento = evento.getTipoEvento();

		if (EvnRespDigitacionMasiva.EDITAR_FOLIO_DIGITACION_MASIVA.equals(local_TipoEvento)) {
			session.setAttribute(WebKeys.MENSAJE, "La operación ha sido exitosa");
		}

	}
	
	/**
	 * @param request
	 * @return
	 */
        
        /**
        * @Autor: Edgar Lora
        * @Mantis: 0013038
        * @Requerimiento: 060_453
        */
	private Evento actualizarFolioCabidaLinderos(HttpServletRequest request) throws AccionWebException {
		Turno turno= (Turno)request.getSession().getAttribute(WebKeys.TURNO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
			request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		String UID= request.getSession().getId();
		Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);

		Folio folio =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		String linderoNuevo = request.getParameter(CFolio.LINDERO_ADICION);
//pq
		String linderoEditado = request.getParameter(CFolio.LINDERO);
		if(linderoNuevo==null && linderoEditado!=null && !linderoEditado.equals("")){
			if(folio.getLindero()!=null && !folio.getLindero().equals(linderoEditado))
				folio.setLindero(linderoEditado);
		}

                /**
                 * @Autor: Edgar Lora
                 * @Mantis: 0013038
                 * @Requerimiento: 060_453
                 */
               
                AccionWebException exception = null;
                ValidacionesSIR validacionesSIR = new ValidacionesSIR();
                
                try {
                    String matricula = folio.getIdMatricula();
                    String lindero = linderoNuevo;
                    if(validacionesSIR.esFolioNuevo(turno.getIdWorkflow(), matricula)){
                            if (validacionesSIR.validarLinderos(matricula, CNaturalezaJuridica.NATURALEZA_PARA_VALIDAR_LINDEROS)) {
                                String articulo = ("Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012").toUpperCase();
                                if(lindero.indexOf(articulo) != -1){
                                    int tamArticulo = lindero.indexOf(articulo) + articulo.length();
                                    if(lindero.length() - tamArticulo < 100){
                                   //     exception = new AccionWebException("Debe añadir minimo 100 caracteres al campo de linderos de la matricula: " + matricula);
                                    }
                                }else{
                               //     exception = new AccionWebException("El lindero de la matricula " + matricula + " debe tener 100 caracteres a partir del texto \"Articulo 8 Parágrafo 1º. de la Ley 1579 de 2012\", Porfavor no lo borre.");
                                }
                            }
                    }
                } catch (GeneralSIRException ex) {
                    exception = new AccionWebException(ex.getMessage());
                }
                
                if (exception != null) {
                    throw exception;
                }
                
                
                        /**
                        * @Author Cristian David Garcia
                        * Sección de Edición de Areas correspondientes a los Linderos.
                        */
                        
                    String linderosDef = request.getParameter(CFolio.DEFINIR_LINDERO);
                    if (linderosDef != null || linderosDef.length() > 0){
                        folio.setLinderosDef(linderosDef);
                        request.getSession().setAttribute(CFolio.DEFINIR_LINDERO,linderosDef);
                    }
        
                    String coeficiente = request.getParameter(CFolio.FOLIO_COEFICIENTE);
                    if (coeficiente != null || coeficiente.length() > 0){
                        folio.setCoeficiente(coeficiente);
                    } else{
                        coeficiente = "0";
                        folio.setCoeficiente(coeficiente);
                    }

                    String privMetros = request.getParameter(CFolio.FOLIO_PRIVMETROS);

                    String privCentimetros = request.getParameter(CFolio.FOLIO_PRIVCENTIMETROS);

                    String consMetros = request.getParameter(CFolio.FOLIO_CONSMETROS);

                    String consCentimetros = request.getParameter(CFolio.FOLIO_CONSCENTIMETROS);

                    String hectareas = request.getParameter(CFolio.FOLIO_HECTAREAS);

                    String metros = request.getParameter(CFolio.FOLIO_METROS);

                    String centimetros = request.getParameter(CFolio.FOLIO_CENTIMETROS);
                    
                    
                    String hectareasT = "0";
                    String metrosT = "0";
                    String centimetrosT = "0";
                    int unidadmedidaexception = 3;
                    

                    if(hectareas == null || hectareas.length() <= 0){
                        unidadmedidaexception--;
                        hectareas = hectareasT;
                        folio.setHectareas(hectareasT);
                    }  else{
                        hectareasT = hectareas;
                    }

                    if(metros == null || metros.length() <= 0){
                        unidadmedidaexception--;
                        metros = metrosT;
                        folio.setMetros(metrosT);
                    } else{
                        metrosT = metros;
                    }

                    if(centimetros == null || centimetros.length() <= 0){
                        unidadmedidaexception--;
                        centimetros = centimetrosT;
                        folio.setCentimetros(centimetrosT);
                    } else{
                        centimetrosT = centimetros;
                    }

                if (unidadmedidaexception != 0){
                     if(!hectareas.equals("0")){
                         folio.setHectareas(hectareas);
                     }
                     if(!metros.equals("0")){
                         folio.setMetros(metros);
                     }
                     if(!centimetros.equals("0")){
                         folio.setCentimetros(centimetros);
                     }
                     
                     boolean datosPrivBien = true;
                        boolean privArea = false;

                        String metrosP = "0";
                        String centimetrosP = "0";

                        if(privMetros != null && !privMetros.isEmpty()){
                            privArea = true;
                            metrosP = privMetros;
                        } else{
                            privMetros = "0";
                        } 

                        if(privCentimetros != null && !privCentimetros.isEmpty()){
                            privArea = true; 
                            centimetrosP = privCentimetros;
                        } else{
                            privCentimetros = "0";
                        }

                        if (privArea){
                            unidadmedidaexception = 2;
                            String metrosC = "0";
                            String centimetrosC = "0";

                            if(consMetros == null || consMetros.length() <= 0){
                                unidadmedidaexception--;
                                consMetros = "0";
                            } else{
                                metrosC = consMetros;
                            } 

                            if(consCentimetros == null || consCentimetros.length() <= 0){
                                unidadmedidaexception--;
                                consCentimetros = "0";
                            } else{
                                centimetrosC = consCentimetros;
                            }

                            if (unidadmedidaexception == 0){
                                    exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                                    throw exception;
                            }

                            //Area Privada para Validar
                            int pMetros = Integer.parseInt(metrosP);
                            int pCentimetros = Integer.parseInt(centimetrosP);
                            //Area Construida para Validar
                            int cMetros = Integer.parseInt(metrosC);
                            int cCentimetros = Integer.parseInt(centimetrosC);
                            //Area del Terreno para Validar
                            int tHectareas = Integer.parseInt(hectareasT);
                            int tMetros = Integer.parseInt(metrosT);
                            int tCentimetros = Integer.parseInt(centimetrosT);

                            if(pMetros > cMetros){
                             datosPrivBien = false;
                            } else{
                                if(pMetros == cMetros){
                                    if(pCentimetros > cCentimetros){
                                    datosPrivBien = false;
                                    }
                                }
                            }

                            if(!datosPrivBien){
                            exception = new AccionWebException("El area privada no puede ser mayor al area construida");
                            throw exception;
                            } else{
                                folio.setPrivMetros(privMetros);
                                folio.setPrivCentimetros(privCentimetros);
                            }
                            boolean datosConsBien = true;

                            if(tHectareas <= 0){
                            if(cMetros > tMetros){
                             datosConsBien = false;
                            } else{
                                if(cMetros == tMetros){
                                    if(cCentimetros > tCentimetros){
                                    datosConsBien = false;
                                    }
                                }
                            }
                            }

                            folio.setConsMetros(consMetros);
                            folio.setConsCentimetros(consCentimetros);

                        } else{

                            boolean consArea = false;

                        String metrosC = "0";
                        String centimetrosC = "0";

                        if(consMetros != null && !consMetros.isEmpty()){
                            consArea = true;
                            metrosC = consMetros;
                        } else{
                            consMetros = "0";
                        }

                        if(consCentimetros != null && !consCentimetros.isEmpty()){
                            consArea = true; 
                            centimetrosC = consCentimetros;
                        } else{
                            consCentimetros = "0";
                        }

                            if(consArea){
                                //Area Construida para Validar
                            int cMetros = Integer.parseInt(metrosC);
                            int cCentimetros = Integer.parseInt(centimetrosC);
                            //Area Construida para Validar
                            int tHectareas = Integer.parseInt(hectareasT);
                            int tMetros = Integer.parseInt(metrosT);
                            int tCentimetros = Integer.parseInt(centimetrosT);

                             boolean datosConsBien = true;
                            if(tHectareas <= 0){
                            if(cMetros > tMetros){
                             datosConsBien = false;
                            } else{
                                if(cMetros == tMetros){
                                    if(cCentimetros > tCentimetros){
                                    datosConsBien = false;
                                    }
                                }
                            }}

                            if(!datosConsBien){
                                folio.setConsMetros(consMetros);
                                folio.setConsCentimetros(consCentimetros);
                            } else{
                                folio.setConsMetros(consMetros);
                                folio.setConsCentimetros(consCentimetros);
                            } 
                            }
                        }
                    
                     
                } else{
                    boolean privArea = false;

                    if(privMetros != null && !privMetros.isEmpty()){
                        privArea = true;
                    } 

                    if(privCentimetros != null && !privCentimetros.isEmpty()){
                        privArea = true; 
                    }
                    
                    if(privArea){
                        boolean consArea = false;
                        
                        if(consMetros != null && !consMetros.isEmpty()){
                        consArea = true;
                        } 

                        if(consCentimetros != null && !consCentimetros.isEmpty()){
                            consArea = true; 
                        }
                        
                        if(consArea){
                            exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
                        } else{
                            exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                        }
                    } else{
                         boolean consArea = false;
                        
                        if(consMetros != null && !consMetros.isEmpty()){
                        consArea = true;
                        } 

                        if(consCentimetros != null && !consCentimetros.isEmpty()){
                            consArea = true; 
                        }
                        
                        if(consArea){
                            exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
                        }
                    }
                }
                    
//                    String hectareasT = "0";
//                    String metrosT = "0";
//                    String centimetrosT = "0";
//                    int unidadmedidaexception = 3;
//                    
//
//                    if(hectareas == null || hectareas.length() <= 0){
//                        unidadmedidaexception--;
//                        folio.setHectareas(hectareasT);
//                    }  else{
//                        hectareasT = hectareas;
//                    }
//
//                    if(metros == null || metros.length() <= 0){
//                        unidadmedidaexception--;
//                        folio.setMetros(metrosT);
//                    } else{
//                        metrosT = metros;
//                    }
//
//                    if(centimetros == null || centimetros.length() <= 0){
//                        unidadmedidaexception--;
//                        folio.setCentimetros(centimetrosT);
//                    } else{
//                        centimetrosT = centimetros;
//                    }
//
//                if (unidadmedidaexception == 0){
//                     exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area del Terreno");
//                     throw exception;
//                }
//
//            // VALIDAR AREA PRIVADA
//                boolean datosPrivBien = true;
//                boolean privArea = false;
//
//                String metrosP = "0";
//                String centimetrosP = "0";
//
//                if(privMetros != null && !privMetros.isEmpty()){
//                    privArea = true;
//                    metrosP = privMetros;
//                } 
//
//                if(privCentimetros != null && !privCentimetros.isEmpty()){
//                    privArea = true; 
//                    centimetrosP = privCentimetros;
//                }
//
//                if (privArea){
//                    unidadmedidaexception = 2;
//                    String metrosC = "0";
//                    String centimetrosC = "0";
//
//                    if(consMetros == null || consMetros.length() <= 0){
//                        unidadmedidaexception--;
//                    } else{
//                        metrosC = consMetros;
//                    } 
//
//                    if(consCentimetros == null || consCentimetros.length() <= 0){
//                        unidadmedidaexception--;
//                    } else{
//                        centimetrosC = consCentimetros;
//                    }
//
//                    if (unidadmedidaexception == 0){
//                            exception = new AccionWebException("Debe digitar por lo menos una unidad de medida válida para el Area Construida");
//                            throw exception;
//                    }
//
//                    //Area Privada para Validar
//                    int pMetros = Integer.parseInt(metrosP);
//                    int pCentimetros = Integer.parseInt(centimetrosP);
//                    //Area Construida para Validar
//                    int cMetros = Integer.parseInt(metrosC);
//                    int cCentimetros = Integer.parseInt(centimetrosC);
//                    //Area del Terreno para Validar
//                    int tHectareas = Integer.parseInt(hectareasT);
//                    int tMetros = Integer.parseInt(metrosT);
//                    int tCentimetros = Integer.parseInt(centimetrosT);
//
//                    if(pMetros > cMetros){
//                     datosPrivBien = false;
//                    } else{
//                        if(pMetros == cMetros){
//                            if(pCentimetros > cCentimetros){
//                            datosPrivBien = false;
//                            }
//                        }
//                    }
//
//                    if(!datosPrivBien){
//                    exception = new AccionWebException("El area privada no puede ser mayor al area construida");
//                    throw exception;
//                    }
//                    boolean datosConsBien = true;
//
//                    if(tHectareas <= 0){
//                    if(cMetros > tMetros){
//                     datosConsBien = false;
//                    } else{
//                        if(cMetros == tMetros){
//                            if(cCentimetros > tCentimetros){
//                            datosConsBien = false;
//                            }
//                        }
//                    }
//                    }
//
//                    if(!datosConsBien){
//                    exception = new AccionWebException("El area construida no puede ser mayor al area del terreno");
//                    throw exception;
//                    }
//
//                } else{
//
//                    boolean consArea = false;
//
//                String metrosC = "0";
//                String centimetrosC = "0";
//
//                if(consMetros != null && !consMetros.isEmpty()){
//                    consArea = true;
//                    metrosC = consMetros;
//                } 
//
//                if(consCentimetros != null && !consCentimetros.isEmpty()){
//                    consArea = true; 
//                    centimetrosC = consCentimetros;
//                }
//
//                    if(consArea){
//                        //Area Construida para Validar
//                    int cMetros = Integer.parseInt(metrosC);
//                    int cCentimetros = Integer.parseInt(centimetrosC);
//                    //Area Construida para Validar
//                    int tHectareas = Integer.parseInt(hectareasT);
//                    int tMetros = Integer.parseInt(metrosT);
//                    int tCentimetros = Integer.parseInt(centimetrosT);
//
//                    boolean datosConsBien = true;
//                    if(tHectareas <= 0){
//                    if(cMetros > tMetros){
//                     datosConsBien = false;
//                    } else{
//                        if(cMetros == tMetros){
//                            if(cCentimetros > tCentimetros){
//                            datosConsBien = false;
//                            }
//                        }
//                    }}
//
//                    if(!datosConsBien){
//                    exception = new AccionWebException("El area construida no puede ser mayor al area del terreno");
//                    throw exception;
//                    } 
//                    }
//                }

//Fin pq		
		// SE LE PASA LA PARTE ADICIONAL QUE EL CALIFICADOR LE AGREGO AL LINDERO. LA INFORMACIÓN ES SIN LO 
		//EL LINDERO TENIA ORIGINALMENTE, ÚNICAMENTE LO NUEVO. LUEGO ESTE LINDERO SE CONCATENA CON EL QUE TENIA EL FOLIO
		EvnCalificacion evento= new EvnCalificacion(usuarioAuriga, EvnCalificacion.ACTUALIZAR_FOLIO_CABIDA_LINDEROS);
		evento.setTurno(turno);
		evento.setUID(UID);
		evento.setUsuarioNeg(usuarioNeg);
		evento.setNuevoLindero(linderoNuevo);
		evento.setFolio(folio);

		return evento;
	}

	/**
	 * @param request
	 * @return
	 */
	private Evento agregarFolioDireccion(HttpServletRequest request) throws AccionWebException {
		HttpSession session = request.getSession();
		preservarInfoBasicaFolio(request);
		Folio folioAnt =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folio =  new Folio();
		folio.setIdMatricula(folioAnt.getIdMatricula());
		List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
		List dirTemporales = (List)session.getAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
		if(dirTemporales==null){
			dirTemporales = new ArrayList();
		}
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
		request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		if (direcciones == null) {
			direcciones = new Vector();
		}
		ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException();
		String valorEje1 = request.getParameter(AWModificarFolio.FOLIO_EJE1);
		if ((valorEje1.length() <= 0) || valorEje1.equals(WebKeys.SIN_SELECCIONAR)) {
			exception.addError("Debe seleccionar el primer eje válido para la dirección");
		}
		String valorValor1 = request.getParameter(AWModificarFolio.FOLIO_VALOR1);
		if (valorValor1.length() <= 0) {
			exception.addError("Debe ingresar valor válido para el primer eje de la dirección");
		}
		String valorEje2 = request.getParameter(AWModificarFolio.FOLIO_EJE2);
		String valorValor2 = request.getParameter(AWModificarFolio.FOLIO_VALOR2);
		if ((valorEje2.length() <= 0) || valorEje2.equals(WebKeys.SIN_SELECCIONAR)) {
			valorEje2 = new String();
		} else {
			if (valorValor2.length() <= 0) {
				exception.addError("Debe ingresar valor válido para el segundo eje  de la dirección");
			}
		}
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		List ejes = (List) session.getServletContext().getAttribute(WebKeys.LISTA_EJES_DIRECCION);
		Direccion direccion = new Direccion();
		if (ejes != null) {
			Iterator itEje = ejes.iterator();
			while (itEje.hasNext()) {
				ElementoLista elementoEje = (ElementoLista) itEje.next();
				Eje eje;
				if (elementoEje.getId().equals(valorEje1)) {
					eje = new Eje();
					eje.setIdEje(elementoEje.getId());
					eje.setNombre(elementoEje.getValor());
					direccion.setEje(eje);
					direccion.setValorEje(valorValor1);
				}
				if (elementoEje.getId().equals(valorEje2)) {
					eje = new Eje();
					eje.setIdEje(elementoEje.getId());
					eje.setNombre(elementoEje.getValor());
					direccion.setEje1(eje);
				}
				if (valorValor2 != null) {
					direccion.setValorEje1(valorValor2);
				}
			}
		} else {
			exception.addError("La lista de los ejes no se encontro");
		}
		String complemento = request.getParameter(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);		
		String spec = null;
		if(direccion.getEje().getNombre() != null){
			spec = direccion.getEje().getNombre() + " " + valorValor1;
			if(direccion.getEje1()!=null && direccion.getEje1().getNombre() != null){
				spec += " " + direccion.getEje1().getNombre();  
				if (valorValor2 != null) {
					spec += " " + valorValor2;
				}
			}
		}		   
		if(complemento != null && spec != null){
			complemento = spec + " " + complemento;
		} else if(complemento == null && spec != null){
			complemento = spec;			
		}		
		direccion.setEspecificacion(complemento!=null?complemento.toUpperCase():null);
		direcciones.add(direccion);//se esta duplicando
		List direccionesTmp = new ArrayList();
		direccionesTmp.add(direccion);
		if (exception.getErrores().size() > 0) {
			throw exception;
		} else {
			folio.setDirecciones(direccionesTmp);
		}
		eliminarInfoBasicaDireccion(request);
		session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
		EvnDigitacionMasiva evento=new EvnDigitacionMasiva(usuarioAuriga,EvnDigitacionMasiva.AGREGAR_FOLIO_DIRECCION);
		evento.setUsuarioSIR(usuarioSIR);
		evento.setFolio(folio);
		evento.setDirTemporales(dirTemporales);
		return evento;
	}
	
	
	/**
	 * @param request
	 */
	private void preservarInfoBasicaFolio(HttpServletRequest request) {
		if (request.getParameter(CFolio.ID_MATRICULA) != null) {
					request.getSession().setAttribute(CFolio.ID_MATRICULA, request.getParameter(CFolio.ID_MATRICULA));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_ID_DEPTO));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_DEPTO, request.getParameter(CFolio.FOLIO_LOCACION_NOM_DEPTO));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_ID_MUNIC));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_MUNIC, request.getParameter(CFolio.FOLIO_LOCACION_NOM_MUNIC));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_ID_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_ID_VEREDA));
		}
		if (request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LOCACION_NOM_VEREDA, request.getParameter(CFolio.FOLIO_LOCACION_NOM_VEREDA));
		}
		if (request.getParameter(CFolio.FOLIO_ESTADO_FOLIO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_ESTADO_FOLIO, request.getParameter(CFolio.FOLIO_ESTADO_FOLIO));
		}
		if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
		}
		if (request.getParameter(CFolio.FOLIO_COMPLEMENTACION) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTACION, request.getParameter(CFolio.FOLIO_COMPLEMENTACION));
		}
		if (request.getParameter(CFolio.FOLIO_LINDERO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_LINDERO, request.getParameter(CFolio.FOLIO_LINDERO));
		}
		if (request.getParameter(CFolio.FOLIO_TIPO_PREDIO) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_TIPO_PREDIO, request.getParameter(CFolio.FOLIO_TIPO_PREDIO));
		}
		if (request.getParameter(CFolio.FOLIO_COD_CATASTRAL) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_COD_CATASTRAL, request.getParameter(CFolio.FOLIO_COD_CATASTRAL));
		}
		if (request.getParameter(CFolio.FOLIO_EJE1) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_EJE1, request.getParameter(CFolio.FOLIO_EJE1));
		}
		if (request.getParameter(CFolio.FOLIO_EJE2) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_EJE2, request.getParameter(CFolio.FOLIO_EJE2));
		}
		if (request.getParameter(CFolio.FOLIO_VALOR1) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_VALOR1, request.getParameter(CFolio.FOLIO_VALOR1));
		}
		if (request.getParameter(CFolio.FOLIO_VALOR2) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_VALOR2, request.getParameter(CFolio.FOLIO_VALOR2));
		}
		if (request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR) != null) {
			request.getSession().setAttribute(CFolio.FOLIO_COMPLEMENTO_DIR, request.getParameter(CFolio.FOLIO_COMPLEMENTO_DIR));
		}
		if (request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO) != null) {
			request.getSession().setAttribute(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO, request.getParameter(CSalvedadFolio.DESCRIPCION_SALVEDAD_FOLIO));
		}
	}
		
	/**
	 *  Elimina de la sesión la información de una dirección.
	 */
	private void eliminarInfoBasicaDireccion(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(AWModificarFolio.FOLIO_EJE1);
		session.removeAttribute(AWModificarFolio.FOLIO_VALOR1);
		session.removeAttribute(AWModificarFolio.FOLIO_EJE2);
		session.removeAttribute(AWModificarFolio.FOLIO_VALOR2);
		session.removeAttribute(AWModificarFolio.FOLIO_COMPLEMENTO_DIR);
	}
	
    protected Evento doFolioEdit_DireccionDefinitiva_DelItem( HttpServletRequest request)throws AccionWebException {
    	ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException();
    	HttpSession session = request.getSession();
        preservarInfoBasicaFolio(request);
        Folio t0_folio = (Folio)session.getAttribute(WebKeys.FOLIO);
        List direcciones = t0_folio.getDirecciones();
        if( null == direcciones ) {
            direcciones = new Vector();
        }
        Direccion element=null;
        try {
        	int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
        	element = (Direccion)direcciones.get( aplicacionNumero );
            if( null != element ) {
                   element.setToDelete( true );
             }
        }catch (NumberFormatException e) {
                throw new ParametroInvalidoException("El número del documento a eliminar es inválido");
        }catch (ArrayIndexOutOfBoundsException e) {
                if (direcciones.size() == 0) {
                        exception.addError("La lista es vacía");
                }
                else {
                        exception.addError("El índice del documento a eliminar está fuera del rango");
                }
        }
        if (exception.getErrores().size() > 0) {
			throw exception;
		}
        Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
			request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Folio folioAnt =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folio =  new Folio();
		folio.setIdMatricula(folioAnt.getIdMatricula());
		List direccionesTmp = new ArrayList();
		direccionesTmp.add(element);
		folio.setDirecciones(direccionesTmp);
		EvnDigitacionMasiva evento= new EvnDigitacionMasiva(usuarioAuriga, EvnDigitacionMasiva.ELIMINAR_FOLIO_DIRECCION);
		evento.setUsuarioSIR(usuarioSIR);
		evento.setFolio(folio);
		return evento;
    }
    
	/**
	 * Permite eliminar una dirección de un folio determinado.
	 * @param request
	 * @return
	 * @throws AccionWebException
	 */
	private Evento eliminarDireccion(HttpServletRequest request) throws AccionWebException {
		ValidacionDigitacionMasivaException exception = new ValidacionDigitacionMasivaException();
		List dirTemporales = (List)request.getSession().getAttribute(AWCalificacion.LISTA_DIRECCIONES_TEMPORALES);
		preservarInfoBasicaFolio(request);
		HttpSession session = request.getSession();
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
							request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Folio folioAnt =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folio =  new Folio();
		folio.setIdMatricula(folioAnt.getIdMatricula());
		List direcciones = (List) session.getAttribute(WebKeys.LISTA_DIRECCION_ANOTACION);
		if (direcciones == null) {
			direcciones = new Vector();
		}
		Direccion direccion =null;
		try {
			int aplicacionNumero = Integer.parseInt(request.getParameter(WebKeys.POSICION));
			session.setAttribute(WebKeys.POSICION, new Integer(aplicacionNumero));
			direccion = (Direccion)direcciones.get(aplicacionNumero);
			direccion.setToDelete(true);
			request.getSession().setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION,direcciones);
		} catch (NumberFormatException e) {
			exception.addError("El número del documento a eliminar es inválido");
		} catch (ArrayIndexOutOfBoundsException e) {
			if (direcciones.size() == 0) {
				throw new ParametroInvalidoException("La lista es vacía");
			} else {
				exception.addError("El índice del documento a eliminar está fuera del rango");
			}
		}
		if (exception.getErrores().size() > 0) {
			throw exception;
		}
		List direccionesTmp = new ArrayList();
		direccionesTmp.add(direccion);
		folio.setDirecciones(direccionesTmp);
		EvnDigitacionMasiva evento= new EvnDigitacionMasiva(usuarioAuriga, EvnDigitacionMasiva.ELIMINAR_FOLIO_DIRECCION);
		evento.setUsuarioSIR(usuarioSIR);
		evento.setFolio(folio);
		evento.setDirTemporales(dirTemporales);
		session.setAttribute(WebKeys.LISTA_DIRECCION_ANOTACION, direcciones);
		return evento;
	}
	
	/**
	 * @param request
	 * @return
	 */
	private Evento actualizarFolioComplementacion(HttpServletRequest request) {
		org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = 	(org.auriga.core.modelo.transferObjects.Usuario)
			request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
		Folio folio =(Folio)request.getSession().getAttribute(WebKeys.FOLIO);
		Folio folioDef=(Folio)request.getSession().getAttribute(CFolio.FOLIO_DEF);
		String complementacionDef="";
		if(folioDef!=null && folioDef.getComplementacion()!=null && folioDef.getComplementacion().getComplementacion()!=null)
			complementacionDef=folioDef.getComplementacion().getComplementacion();
		String complementacion=request.getParameter(CFolio.COMPLEMENTACION_ADICION);
		if(complementacion!=null && folio!=null){
			if(folio.getComplementacion()==null){
				Complementacion comp = new Complementacion();
                comp.setComplementacion(complementacion);
                folio.setComplementacion(comp);
			}else{
				folio.getComplementacion().setComplementacion(complementacionDef+" "+complementacion);
			}
		}
		EvnDigitacionMasiva evento= new EvnDigitacionMasiva(usuarioAuriga, EvnDigitacionMasiva.ACTUALIZAR_FOLIO_COMPLEMENTACION);
		Usuario usuarioSIR = (gov.sir.core.negocio.modelo.Usuario) request.getSession().getAttribute(WebKeys.USUARIO);
		evento.setFolio(folio);
		evento.setUsuarioSIR(usuarioSIR);
		return evento;
	}
	
	
	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento consultarAnotacionesFolio(HttpServletRequest request) throws AccionWebException {
		boolean datosValidos = true;
		boolean consulta=false;
		boolean consultarAnotacionesDefinitivas = false;
		boolean anotacionDelta = false;
		//se verifica si se debe consultar únicamente las definitivas
		String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);
		if(anotacionesDefinitivas!=null && anotacionesDefinitivas.equals("TRUE")){
			consultarAnotacionesDefinitivas = true;
		}
		request.getSession().setAttribute("folioPosibleTemporal","true");
		//inicio de parametros para la consulta
		String idMatricula = request.getParameter(CFolio.ID_MATRICULA);
		String idZonaRegistral = request.getParameter(CFolio.ID_ZONA_REGISTRAL);
		String inicio =	request.getParameter(WebKeys.INICIO);
		String cantidad =	request.getParameter(WebKeys.CANTIDAD);
		String tPaginaInicial = request.getParameter(AWPaginadorAnotaciones.PAGINA_INICIAL);
		String nombrePaginador = request.getParameter(WebKeys.NOMBRE_PAGINADOR);
		request.getSession().setAttribute(WebKeys.NOMBRE_PAGINADOR, nombrePaginador);
		String nombreResultado = request.getParameter(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		request.getSession().setAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR, nombreResultado);
		boolean consultarAnotacionesFolioTemporal = false;
		final String ANOTACIONES_FOLIO_TEMPORAL = "ANOTACIONES_FOLIO_TEMPORAL";
		String consultarAnotacionesFolioTemporalStr = request.getParameter(ANOTACIONES_FOLIO_TEMPORAL);
		if(consultarAnotacionesFolioTemporalStr!=null && consultarAnotacionesFolioTemporalStr.equals("TRUE")){
			consultarAnotacionesFolioTemporal = true;
		}
		Boolean tempdelta =(Boolean) request.getSession().getAttribute("ANOTACION_DELTA");
		if(tempdelta!=null){
			anotacionDelta=tempdelta.booleanValue();
		}
		int paginaInicial=0;
		if(tPaginaInicial!=null && !tPaginaInicial.equals("")){
			paginaInicial=Integer.parseInt(tPaginaInicial);
		}
		Boolean temp=(Boolean) request.getSession().getAttribute(AWPaginadorAnotaciones.TIPO_CONSULTA);
		if(temp!=null){
			consulta=temp.booleanValue();
		}
		if (idMatricula == null || idZonaRegistral== null) {
			datosValidos = false;
		}
		if (inicio == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(inicio);
			}catch(Exception e){
				datosValidos = false;
			}
		}
		if (cantidad == null) {
			datosValidos = false;
		}else{
			try{
				Integer d = new Integer(cantidad);
			}catch(Exception e){
				datosValidos = false;
			}
		}
		if(datosValidos==true){
			FolioPk folioID = new FolioPk();
			folioID.idMatricula= idMatricula;
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);

			EvnPaginadorAnotaciones e=new EvnPaginadorAnotaciones(usuarioAuriga, folioID, new Integer(inicio).intValue(), new Integer(cantidad).intValue(), EvnPaginadorAnotaciones.CONSULTAR_ANOTACIONES_FOLIO, usuarioNeg, consultarAnotacionesDefinitivas);
			if(consulta){
				e.setConsulta(consulta);
			}
			if (anotacionDelta){
				e.setGetAnotacionesConDeltadas(true);
			}
			e.setNombrePaginador(nombrePaginador);
			e.setNombreResultado(nombreResultado);
			e.setConsultarAnotacionesFolioTemporal(consultarAnotacionesFolioTemporal);
			if(paginaInicial>0){
				e.setPaginaInicial(paginaInicial);
			}
			return e;
		}else{
			return null;
		}
	}
	
	/**
	 * Método que permite llamar a los métodos de la acción de neegocio para consultar
	 * las anotaciones de un folio
	 * @param request
	 * @return
	 */
	private Evento cambiarPagina(HttpServletRequest request) throws AccionWebException {
		HttpSession session= request.getSession();
		String nombrePaginador = (String)request.getSession().getAttribute(WebKeys.NOMBRE_PAGINADOR);
		String nombreResultado = (String)request.getSession().getAttribute(WebKeys.NOMBRE_RESULTADOS_PAGINADOR);
		boolean consultarAnotacionesDefinitivas = false;
		String anotacionesDefinitivas = request.getParameter(AWPaginadorAnotaciones.ANOTACIONES_DEFINITIVAS);
		if(anotacionesDefinitivas!=null && anotacionesDefinitivas.equals("TRUE")){
			consultarAnotacionesDefinitivas = true;
		}
		PaginadorAvanzado pag = (PaginadorAvanzado) request.getSession().getAttribute(nombrePaginador);
		if(pag==null){
		  Log.getInstance().debug(AWDigitacionMasiva.class, "\n NO EXISTE UN PAGINADOR ACTIVO. \n");
		}
		String nombrePaginaActual = (String)request.getSession().getAttribute(AWPaginadorAnotaciones.NOMBRE_NUM_PAGINA_ACTUAL);
		String numPagina= (String)request.getParameter(nombrePaginaActual);
		request.getSession().setAttribute(AWPaginadorAnotaciones.NUM_PAGINA_ACTUAL, numPagina);
		//obtener folio
		Folio folio = (Folio) session.getAttribute(AWPaginadorAnotaciones.FOLIO_HELPER);
		List aDefinitivas=null;
		int npagina=0;
		if(numPagina!=null && numPagina!=""){
			npagina= Integer.parseInt(numPagina);
		}
		pag.setNumeroPagina(npagina);
		try{
			aDefinitivas= pag.getPaginaDesdeCache(npagina);
		}catch(PaginadorPaginaNoEncontradaException e){
			boolean consulta=false;
			consulta=pag.getTipoConsulta();
			int inicio;
			inicio = ((npagina/10)* pag.getTamanoCacheResultados()) ;
			int cantidad= 100;
			FolioPk folioID = new FolioPk();
			folioID.idMatricula= folio.getIdMatricula();
			org.auriga.core.modelo.transferObjects.Usuario usuarioAuriga = (org.auriga.core.modelo.transferObjects.Usuario) request.getSession().getAttribute(SMARTKeys.USUARIO_EN_SESION);
			Usuario usuarioNeg=(Usuario)request.getSession().getAttribute(WebKeys.USUARIO);
			EvnPaginadorAnotaciones ev=new EvnPaginadorAnotaciones(usuarioAuriga, folioID, inicio, cantidad, EvnPaginadorAnotaciones.CARGAR_PAGINA, usuarioNeg,consultarAnotacionesDefinitivas);
			if(consulta){
				ev.setConsulta(consulta);
			}
			ev.setNombrePaginador(nombrePaginador);
			ev.setNombreResultado(nombreResultado);
			return ev;
		}catch (PaginadorCacheNoActualizadoException e) {
			e.printStackTrace(System.out);
		}
			DatosRespuestaPaginador RPag = (DatosRespuestaPaginador) request.getSession().getAttribute(nombreResultado);
			RPag.setAnotacionesActual(aDefinitivas);
			doProcess_PaginadorMultiselect_SaveState( request );
		return null;
	}
	
	  private void	  doProcess_PaginadorMultiselect_SaveState( HttpServletRequest request ) {
	          HttpSession session;
	          session = request.getSession();
	          final String PREFIX = "PAGINADOR:";
	          final String CHECKBOXCONTROLLER_MULTISELECTENABLED = "CHECKBOXCONTROLLER_MULTISELECTENABLED";
	          final String CHECKBOXCONTROLLER_TARGETFORMFIELDID  = "CHECKBOXCONTROLLER_TARGETFORMFIELDID";
	          Boolean checkBoxController_MultiSelectEnabled;
	          checkBoxController_MultiSelectEnabled = null;
	          if( ( null == ( checkBoxController_MultiSelectEnabled = (Boolean)session.getAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED ) ) )
	          ) {
	              return;
	          }
	          String checkboxController_TargetFormFieldId;
	          checkboxController_TargetFormFieldId = null;
	          if( ( null == ( checkboxController_TargetFormFieldId = (String)session.getAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID ) ) ) ) {
	             return;
	          }
	          final String LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID   = checkboxController_TargetFormFieldId;
	          String idsAnotacionesCsv = request.getParameter( LOCAL_CHECKBOXCONTROLLER_CONTROLEDFIELD_TARGET_FORMFIELDID );
	         String[] idsAnotaciones = JvLocalUtils.csvStringToStringArray( idsAnotacionesCsv, JvLocalUtils.DEFAULT_SEPARATOR, true );
	         session.setAttribute( MostrarFolioHelper.LISTA_ANOTACIONES_MULT_CANCELACION, idsAnotaciones );
	         session.removeAttribute( PREFIX + CHECKBOXCONTROLLER_MULTISELECTENABLED );
	         session.removeAttribute( PREFIX + CHECKBOXCONTROLLER_TARGETFORMFIELDID  );
	  }

	
}
