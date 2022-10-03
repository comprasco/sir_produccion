package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.eventos.correccion.EvnRespCorreccion;
import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.LlaveBloqueo;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWCorreccion;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Clase que define la navegaci�n para la Acci�n Web, AWCorreccion.
 * @author ppabon
 */
public class ControlNavegacionCorreccion implements ControlNavegacion {

        /**
         * Identifica que se envio correctamente el folio con
         * las correcciones temporales al revisor
         * */
    public static final String ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK = EvnRespCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK;
    public static final String ENVIARCORRECCIONSIMPLEAREVISIONANALISIS__EVENTRESP_OK = EvnRespCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK;

    // revision-aprobar
    public static final String CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK
        = EvnRespCorreccion.CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK;
    // bug 4992
    public static final String CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART__EVENTRESP_OK
        = "CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART__EVENTRESP_OK";

    // bug 3551
    public static final String PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA__EVENTRESP_OK
     = "PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA__EVENTRESP_OK";
    //<nombre>correccion.digitacion.masiva.view</nombre>

    // bug 3536
    public static final String PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK
        = "PAGEEVENT_CORRECCIONREVISARAPROBAR_DESHACERCAMBIOS_EVENTRESPOK";




	/** Constante que indica que NO se bloquearon correctamente los folios antes de empezar el proceso de correcci�n*/
	public static final String COR_CORRECCION = "COR_CORRECCION";

	/** Constante que indica que se bloquearon correctamente los folios antes de empezar el proceso de correcci�n*/
	public static final String TOMAR_FOLIO_CORRECCION_OK = "TOMAR_FOLIO_CORRECCION_OK";

	/** Constante que indica que NO se bloquearon correctamente los folios antes de empezar el proceso de especializaci�n en correcciones*/
	public static final String COR_USUARIO_ESPECIALIZADO = "COR_USUARIO_ESPECIALIZADO";

	/** Constante que indica que se bloquearon correctamente los folios antes de empezar el proceso de especializaci�n en correcciones*/
	public static final String TOMAR_FOLIO_ESPECIALIZADO_OK = "TOMAR_FOLIO_ESPECIALIZADO_OK";

	/** Constante que indica que NO se bloquearon correctamente los folios antes de empezar el proceso de digitaci�n en correcciones*/
	public static final String COR_DIGITACION_MASIVA = "COR_DIGITACION_MASIVA";

	/** Constante que indica que se bloquearon correctamente los folios antes de empezar el proceso de digitaci�n en correcciones*/
	public static final String TOMAR_FOLIO_DIGITACION_OK = "TOMAR_FOLIO_DIGITACION_OK";

    /** Constante que indica que se bloquearon correctamente los folios antes de empezar el proceso de digitaci�n en correcciones*/
    public static final String TOMAR_FOLIO_REVISION_OK= "TOMAR_FOLIO_REVISION_OK";

	/** Constante que indica que se aprob� satisfactoriamente una solicitud de correcci�n*/
	public static final String APROBAR_OK = "APROBAR_OK";

	/** Constante que identifica que se desea forzar la aprobaci�n de la correcci�n*/
	public final static String FORZAR_APROBACION = "FORZAR_APROBACION";

	/** Constante que identifica que se desea preguntar si se manda a mayor valor o se aprueba el caso definitivamente. */
	public final static String PREGUNTAR_APROBACION = "PREGUNTAR_APROBACION";

	/** Constante que identifica que se desea visualizar los cambios que se realizaron en la correcci�n. */
	public final static String CAMBIOS_PROPUESTOS = "CAMBIOS_PROPUESTOS";

	/** Constante que indica que no se aprob� satisfactoriamente una solicitud de correcci�n*/
	public static final String APROBAR_FAILED = "APROBAR_FAILED";

	/** Constante que indica que se neg� satisfactoriamente una solicitud de correcci�n*/
	public static final String NEGAR_OK = "NEGAR_OK";

	/** Constante que indica que no se neg� satisfactoriamente una solicitud de correcci�n*/
	public static final String NEGAR_FAILED = "NEGAR_FAILED";

	/** Constante que indica que se deleg� una correcci�n satisfactoriamente a algui�n diferente*/
	public static final String DELEGAR_CASO_OK = "DELEGAR_CASO_OK";

	/** Constante que indica que no se deleg� una correcci�n satisfactoriamente a algui�n diferente*/
	public static final String DELEGAR_CASO_FAILED = "DELEGAR_CASO_FAILED";

	/** Constante que indica que se devuelvi� satisfactoriamente el caso de correcci�n a qui�n la ten�a originalmente*/
	public static final String DEVOLVER_CASO_OK = "DEVOLVER_CASO_OK";
        public static final String VISUALIZAR_PDF="VISUALIZAR_PDF";

	/** Constante que indica que no se devuelvi� satisfactoriamente el caso de correcci�n a qui�n la ten�a originalmente*/
	public static final String DEVOLVER_CASO_FAILED = "DEVOLVER_CASO_FAILED";

	/** Constante que indica que se redireccion� satisfactoriamente el caso, para que se trabaje por mayor valor o por especializado*/
	public static final String REDIRECCIONAR_CASO_OK = "REDIRECCIONAR_CASO_OK";

	/** Constante que indica que no se redireccion� satisfactoriamente el caso, para que se trabaje por mayor valor o por especializado*/
	public static final String REDIRECCIONAR_CASO_FAILED = "REDIRECCIONAR_CASO_FAILED";

	/** Constante que indica que se edit� satisfactoriamente un folio*/
	public static final String EDITAR_FOLIO_OK = "EDITAR_FOLIO_OK";

	/** Constante que indica que no se edit� satisfactoriamente un folio*/
	public static final String EDITAR_FOLIO_FAILED = "EDITAR_FOLIO_FAILED";

	/** Constante que indica que no se necesita cargar la pantalla de adici�n de actos, porque se agreg� una nueva anotacion*/
	public static final String CARGAR_MAYOR_VALOR = "CARGAR_MAYOR_VALOR";

	/** Constante que indica que se desea Imprimir un certificado*/
	public static final String IMPRIMIR = "IMPRIMIR";
             /*
        * @author : CTORRES
        * @change : Se agrega la constante TOMAR_TURNO_TESTAMENTO,EDITAR_TESTAMENTO 
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
        /*Contante que indica que se desea tomar el turno de testamento asociado al turno*/
        public static final String TOMAR_TURNO_TESTAMENTO = "TOMAR_TURNO_TESTAMENTO";
        /*Constante que indica que se desea tomar el turno de testamento asociado al turno*/
        public static final String EDITAR_TESTAMENTO = "EDITAR_TESTAMENTO";

	/**
	 * M�todo de inicio
	 */
	public void doStart(HttpServletRequest arg0) {
	}

	/**
	 *        Este m�todo lo que hace es la verificaci�n de los diferentes objectos que se encuentran en la sesion,
	 *        y de acuerdo a esa verificaci�n manda una respuesta para que sea procesada y asi poder tener una
	 *        navegaci�n acertada.
	 */
	public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
		String accion = (String) request.getParameter(WebKeys.ACCION);

		if ((accion == null) || accion.equals("")) {
			throw new ControlNavegacionException("La acci�n enviada por la acci�n web no es v�lida");
		}
                // ------ corr-simple:enviar a revision aprobacion
                if( AWCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION_ACTION.equals( accion ) ) {
                    return ENVIARCORRECCIONSIMPLEAREVISIONAPROBACION__EVENTRESP_OK;
                }
                // ------ corr-simple:devolver a revision analisis
                else if( AWCorreccion.ENVIARCORRECCIONSIMPLEAREVISIONANALISIS_ACTION.equals( accion ) ) {
                    return ENVIARCORRECCIONSIMPLEAREVISIONANALISIS__EVENTRESP_OK;
                }
                // ------ corr-simple:revision-aprobacion-printform
                else if( AWCorreccion.CORR_REVISIONAPROBACION__PRINTFORM.equals( accion ) ) {
                   return CORR_REVISIONAPROBACION__PRINTFORM_EVENTRESP_OK;
                }
                // bug 4992 (digitacion-masiva) -----
                else if( AWCorreccion.CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART_ACTION.equals( accion ) ) {
                   return CORRECCIONSIMPLEMAIN_PRINTFORM_STEP0_BTNSTART__EVENTRESP_OK;
                }
                // bug 3551 (digitacion-masiva) -----
                else if( AWCorreccion.PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA_ACTION.equals( accion ) ){
                    return PAGE_REGION__STARTOPCIONESDIGITACIONMASIVA__EVENTRESP_OK;
                }
                // bug 3536 () -----
                else if( AWCorreccion.PAGE_CORRECCIONREVISARAPROBAR_BTNDESHACERCAMBIOS_ACTION.equals( accion ) ){
                    return NEGAR_OK;
                }else if (accion.equals(AWCorreccion.VISUALIZAR_PDF)) {
					return VISUALIZAR_PDF;
                }
                //


                else if (accion.equals(AWCorreccion.APROBAR)) {
			String aprobar = (String) request.getSession().getAttribute(AWCorreccion.APROBAR);
			if (aprobar != null && aprobar.equals(EvnRespCorreccion.REDIRECCIONAR_CASO)) {
				request.getSession().removeAttribute(AWCorreccion.APROBAR);
				return CARGAR_MAYOR_VALOR;
			} else if (aprobar != null && aprobar.equals(EvnRespCorreccion.PREGUNTAR_APROBACION)) {
				request.getSession().removeAttribute(AWCorreccion.APROBAR);
				return PREGUNTAR_APROBACION;
			} else {
				request.getSession().removeAttribute(AWCorreccion.APROBAR);
				return APROBAR_OK;
			}
		} else if (accion.equals(AWCorreccion.FORZAR_APROBACION)) {
			return APROBAR_OK;
		} else if (accion.equals(AWCorreccion.CARGAR_CAMBIOS_PROPUESTOS)) {
			return CAMBIOS_PROPUESTOS;
		} else if (accion.equals(AWCorreccion.NEGAR)) {
			return NEGAR_OK;
		} else if (accion.equals(AWCorreccion.APROBAR_ESPECIALIZADO)) {
			return APROBAR_OK;
		} else if (accion.equals(AWCorreccion.NEGAR_ESPECIALIZADO)) {
			return NEGAR_OK;
		} else if (accion.equals(AWCorreccion.APROBAR_DIGITACION)) {
			return APROBAR_OK;
		} else if (accion.equals(AWCorreccion.NEGAR_DIGITACION)) {
			return NEGAR_OK;
		} else if (accion.equals(AWCorreccion.APROBAR_MICROFILMACION)) {
			return APROBAR_OK;
		} else if (accion.equals(AWCorreccion.NEGAR_MICROFILMACION)) {
			return NEGAR_OK;
		} else if (accion.equals(AWCorreccion.DELEGAR_CASO)) {
			return DELEGAR_CASO_OK;
		} else if (accion.equals(AWCorreccion.TOMAR_FOLIO_CORRECCION)) {
			LlaveBloqueo llaveBloqueo = (LlaveBloqueo) request.getSession().getAttribute(WebKeys.FOLIOS_BLOQUEADOS);
            Fase fase=(Fase)request.getSession().getAttribute(WebKeys.FASE);
            if (fase.getID().equals(CFase.COR_REVISION_ANALISIS)){
                return TOMAR_FOLIO_REVISION_OK;
            }else{
                return TOMAR_FOLIO_CORRECCION_OK;
            }

		} else if (accion.equals(AWCorreccion.TOMAR_FOLIO_DIGITACION)) {
			LlaveBloqueo llaveBloqueo = (LlaveBloqueo) request.getSession().getAttribute(WebKeys.FOLIOS_BLOQUEADOS);
			return TOMAR_FOLIO_DIGITACION_OK;
		} else if (accion.equals(AWCorreccion.TOMAR_FOLIO_ESPECIALIZADO)) {
			LlaveBloqueo llaveBloqueo = (LlaveBloqueo) request.getSession().getAttribute(WebKeys.FOLIOS_BLOQUEADOS);
			return TOMAR_FOLIO_ESPECIALIZADO_OK;
		} else if (accion.equals(AWCorreccion.DEVOLVER_CASO)) {
			return DEVOLVER_CASO_OK;
		} else if (accion.equals(AWCorreccion.PREGUNTAR_APROBACION)) {
			return PREGUNTAR_APROBACION;
		} else if (accion.equals(AWCorreccion.REDIRECCIONAR_CASO)) {
			return REDIRECCIONAR_CASO_OK;
		} else if (accion.equals(AWCorreccion.NOTIFICAR_CIUDADANO_EXITO) || accion.equals(AWCorreccion.NOTIFICAR_CIUDADANO_FRACASO)) {
			return APROBAR_OK;
		} else if (accion.equals(AWCorreccion.IMPRIMIR) || accion.equals(AWCorreccion.IMPRIMIR_INDIVIDUAL)) {
			return IMPRIMIR;
		} else if (accion.equals(AWCorreccion.PAGO_MAYOR_VALOR)) {
            return APROBAR_OK;
        } else if (accion.equals(AWCorreccion.AVANZAR_IMPRIMIR)) {
			return APROBAR_OK;
		}  else if (accion.equals(AWCorreccion.TERMINAR_EJECUCION_ACTUACION_ADMINISTRATIVA)) {
			return APROBAR_OK;
		}  
                     /*
        * @author : CTORRES
        * @change : Se agrega condicion para la acciondes de correccion de testamento 
        * Caso Mantis : 12291
        * No Requerimiento: Acta - Requerimiento No 045_453_Correccion_o_Formulario_Inscripcion_Testamentos
        */
                else if (accion.equals(AWCorreccion.TOMAR_TURNO_TESTAMENTO)) {
			return TOMAR_FOLIO_CORRECCION_OK;
		}else {
			return null;
		}
	}

	/**
	 * M�todo para terminar el control de navegaci�n.
	 */
	public void doEnd(HttpServletRequest request) {
	}
}
