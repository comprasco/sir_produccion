package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWTurnoManualRegistro;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;
import gov.sir.core.eventos.registro.EvnRespLiquidacionRegistro;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jfrias
 */
public class ControlNavegacionTurnoManualRegistro implements ControlNavegacion {

    // edicion turno vinculado ----------------------------------------------------------
    public static final String REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK
            = EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK;

    public static final String REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK
            = EvnRespLiquidacionRegistro.REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK;
    // ----------------------------------------------------------------------------------

    /**
     * Constante para mostrar vista de carga exitosa
     */
    public static final String CARGAR_OK = "CARGAR_OK";

    /**
     * Constante para mostrar vista de asociado exitosa
     */
    public static final String ASOCIADO_OK = "ASOCIADO_OK";

    /**
     * Constante para mostrar vista de carga de documentos exitosa
     */
    public static final String CARGAR_DOCUMENTOS_OK = "CARGAR_DOCUMENTOS_OK";

    /**
     * Constante para mostrar vista de carga fallida
     */
    public static final String CARGAR_FAILED = "CARGAR_FAILED";

    /**
     * Constante que indica que el proceso de liquidación fué exitoso
     */
    public static final String LIQUIDACION_OK = "LIQUIDACION_OK";

    /**
     * Constante que indica que se estaba editando una liquidación
     */
    public static final String EDITAR_LIQUIDACION_OK = "EDITAR_LIQUIDACION_OK";

    /**
     * Constante que indica que el proceso de liquidación fué exitoso, y se
     * desea continuar con el pago
     */
    public static final String LIQUIDACION_CONTINUAR_OK = "LIQUIDACION_CONTINUAR_OK";

    /**
     * Constante que indica que el proceso de liquidación no fué exitoso
     */
    public static final String LIQUIDACION_FAILED = "LIQUIDACION_FAILED";

    /**
     * Constante que indica que el proceso de inserción de una matrícula a la
     * solicitud fue exitoso
     */
    public static final String AGREGAR_OK = "AGREGAR_OK";

    /**
     * Constante que indica que el proceso de eliminación de una matrícula de la
     * solicitud fue exitoso
     */
    public static final String ELIMINAR_OK = "ELIMINAR_OK";

    /**
     * Constante que indica que el proceso de eliminación de una matrícula no
     * fue exitoso
     */
    public static final String ELIMINAR_FAILED = "ELIMINAR_FAILED";

    /**
     * Constante que indica que el proceso de inserción de una matrícula a la
     * solicitud no fue exitoso
     */
    public static final String AGREGAR_FAILED = "AGREGAR_FAILED";

    /**
     * Constante para mostrar vista de preveservar existosa
     */
    public static final String PRESERVAR_OK = "PRESERVAR_OK";

    /**
     * Constante para mostrar vista turno.registro.liquidacion.view cuando se
     * recargó la vista
     */
    public static final String PRESERVAR_INFO_LIQUIDACION_OK = "PRESERVAR_INFO_LIQUIDACION_OK";

    /**
     * Constante para mostrar vista de liquidación existosa
     */
    public static final String LIQUIDAR_REGISTRO_OK = "LIQUIDAR_REGISTRO_OK";

    /**
     * Constante para mostrar vista de agregar acto existosa
     */
    public static final String AGREGAR_ACTO_OK = "AGREGAR_ACTO_OK";

    /**
     * Constante para mostrar vista de eliminar acto existosa
     */
    public static final String ELIMINAR_ACTO_OK = "ELIMINAR_ACTO_OK";

    /**
     * Constante para mostrar vista de antiguo sistema
     */
    public static final String VER_ANTIGUO_SISTEMA = "VER_ANTIGUO_SISTEMA";

    /**
     * Constante para mostrar vista de turno anterior válido
     */
    public static final String TURNO_ANTERIOR_VALIDADO = "TURNO_ANTERIOR_VALIDADO";

    /**
     * Constante para mostrar vista de eliminar turno anterior
     */
    public static final String ELIMINAR_TURNO_ANTERIOR = "ELIMINAR_TURNO_ANTERIOR";

    /**
     * Constante que indica que el proceso de solicitud fué exitoso
     */
    public static final String SOLICITUD_OK = "SOLICITUD_OK";

    /**
     *
     */
    public ControlNavegacionTurnoManualRegistro() {
        super();
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doStart(javax.servlet.http.HttpServletRequest)
     */
    public void doStart(HttpServletRequest arg0) {
    }

    /* (non-Javadoc)
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#procesarNavegacion(javax.servlet.http.HttpServletRequest)
     */
    public String procesarNavegacion(HttpServletRequest request)
            throws ControlNavegacionException {
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                    "La accion enviada por la accion web no es valida");
        }

        Boolean esEdicion = (Boolean) request.getSession().getAttribute(AWTurnoManualRegistro.ES_EDICION_LIQUIDACION);

        if (AWTurnoManualRegistro.REGISTRO_VINCULARTURNO_ADDITEM_ACTION.equals(accion)) {
            return REGISTRO_VINCULARTURNO_ADDITEM__EVENTRESP_OK;
        } else if (AWTurnoManualRegistro.REGISTRO_VINCULARTURNO_DELITEM_ACTION.equals(accion)) {
            return REGISTRO_VINCULARTURNO_DELITEM__EVENTRESP_OK;
        } else if (accion.equals(AWTurnoManualRegistro.LIQUIDAR_REGISTRO)) {
            if (request.getSession().getAttribute(WebKeys.TURNO) == null) {
                return LIQUIDAR_REGISTRO_OK;
            }

            return SOLICITUD_OK;
        } else if (accion.equals(AWTurnoManualRegistro.AGREGAR)) {
            return AGREGAR_OK;
        } else if (accion.equals(AWTurnoManualRegistro.ELIMINAR)) {
            return ELIMINAR_OK;
        } else if (accion.equals(AWTurnoManualRegistro.PRESERVAR_INFO)) {
            return PRESERVAR_OK;
        } else if (accion.equals(AWTurnoManualRegistro.PRESERVAR_INFO_LIQUIDAR)) {
            if (esEdicion != null && esEdicion.booleanValue()) {
                return AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION;
            }
            return PRESERVAR_INFO_LIQUIDACION_OK;
        } else if (accion.equals(AWTurnoManualRegistro.AGREGAR_ACTO)) {
            if (esEdicion != null && esEdicion.booleanValue()) {
                return AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION;
            }
            return AGREGAR_ACTO_OK;
        } else if (accion.equals(AWTurnoManualRegistro.ELIMINAR_ACTO)) {
            if (esEdicion != null && esEdicion.booleanValue()) {
                return AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION;
            }
            return ELIMINAR_ACTO_OK;
        } else if (accion.equals(AWTurnoManualRegistro.LIQUIDAR)) {
            return LIQUIDACION_OK;
        } else if (accion.equals(AWTurnoManualRegistro.LIQUIDAR_CONTINUAR)) {
            return LIQUIDACION_CONTINUAR_OK;
        } else if (accion.equals(AWTurnoManualRegistro.EDITAR_LIQUIDACION)) {
            return EDITAR_LIQUIDACION_OK;
        } else if (accion.equals(AWTurnoManualRegistro.CARGAR_DERECHOS)) {
            if (esEdicion != null && esEdicion.booleanValue()) {
                return AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION;
            }
            return CARGAR_OK;
        } else if (accion.equals(AWTurnoManualRegistro.CARGAR_DOCUMENTOS)) {
            return CARGAR_DOCUMENTOS_OK;
        } else if (accion.equals(AWTurnoManualRegistro.VER_ANTIGUO_SISTEMA)) {
            return VER_ANTIGUO_SISTEMA;
        } else if (accion.equals(AWTurnoManualRegistro.VALIDAR_TURNO_ANTERIOR)) {
            return TURNO_ANTERIOR_VALIDADO;
        } else if (accion.equals(AWTurnoManualRegistro.ELIMINAR_TURNO_ANTERIOR)) {
            return ELIMINAR_TURNO_ANTERIOR;
        } /*else if (
                accion.equals(
                		AWTurnoManualRegistro.ADICIONAR_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                		AWTurnoManualRegistro.ELIMINA_CERTIFICADO_ASOCIADO)) {
            return ASOCIADO_OK;
        } else if (accion.equals(
        				AWTurnoManualRegistro.NUEVO_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                		AWTurnoManualRegistro.AGREGAR_MATRICULA_CERTIFICADO_ASOCIADO) ||
                accion.equals(
                		AWTurnoManualRegistro.ELIMINAR_MATRICULA_CERTIFICADO_ASOCIADO) ||
			accion.equals(
					AWTurnoManualRegistro.AGREGAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA) ||
			accion.equals(
					AWTurnoManualRegistro.AGREGAR_CERTIFICADO_ASOCIADO_SEGREGACION) ||
			accion.equals(
					AWTurnoManualRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_DATOS_ANTIGUO_SISTEMA) ||
			accion.equals(
					AWTurnoManualRegistro.ELIMINAR_CERTIFICADO_ASOCIADO_SEGREGACION)
                    ) {
            //	return AWLiquidacionRegistro.NUEVO_CERTIFICADO_ASOCIADO;
            return ASOCIADO_OK;
        } */ else if (accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD)) {
            return AWTurnoManualRegistro.BUSCAR_SOLICITUD;
        } else if (accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION)) {
            return AWTurnoManualRegistro.BUSCAR_SOLICITUD_EDICION;
        } else if (accion.equals(AWTurnoManualRegistro.VALIDAR_SOLICITUD)) {
            return AWTurnoManualRegistro.VALIDAR_SOLICITUD;
        } else if (accion.equals(
                AWTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO)) {
            return AWTurnoManualRegistro.INCREMENTAR_SECUENCIAL_RECIBO;
        } else if (accion.equals(AWTurnoManualRegistro.CREAR_TURNO)) {
            return AWTurnoManualRegistro.CREAR_TURNO;
        } else if (accion.equals(AWTurnoManualRegistro.BUSCAR_SOLICITUD_PAGO)) {
            return AWTurnoManualRegistro.BUSCAR_SOLICITUD_PAGO;
        } else if (accion.equals(AWTurnoManualRegistro.TERMINA)) {
            return AWTurnoManualRegistro.TERMINA;
        } else {
            return null;
        }
    }

    /**
     * @see
     * org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {
    }
}
