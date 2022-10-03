package gov.sir.core.web.navegacion.comun;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.Pago;
import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.negocio.modelo.Solicitud;
import gov.sir.core.negocio.modelo.SolicitudCertificado;
import gov.sir.core.negocio.modelo.SolicitudConsulta;
import gov.sir.core.negocio.modelo.Turno;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.negocio.modelo.constantes.CTipoCertificado;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.comun.AWPago;
import static gov.sir.core.web.acciones.comun.AWPago.BUSCAR_GENERAL;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * @author eacosta
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ControlNavegacionPago implements ControlNavegacion {

    /**
     * Constante que indica que el proceso de solicitud fué exitoso
     */
    public static final String SOLICITUD_OK = "SOLICITUD_OK";

    /**
     * Constante que indica que el proceso de solicitud fué exitoso, pero no se
     * imprimió
     */
    public static final String CONFIRMACION_ERROR_IMPRESION = "CONFIRMACION_ERROR_IMPRESION";

    /**
     * Constante que indica que el proceso de solicitud no fué exitoso
     */
    public static final String SOLICITUD_FAILED = "SOLICITUD_FAILED";

    /**
     * Constante que indica que el proceso pago del turno de registro fue
     * exitoso y que desea continuarse con la radicación del turno.
     */
    public static final String PROCESAR_REGISTRO_CONTINUAR = "PROCESAR_REGISTRO_CONTINUAR";

    /**
     * Constante que indica que se quiere mostrar de nuevo la lista de
     * aplicaciones
     */
    public static final String RELISTAR_APLICACIONES_ = "RELISTAR_APLICACIONES_";

    /**
     * Constante que indica que se va a hacer la validacion del pago
     */
    public static final String VALIDAR_PAGO = "VALIDAR_PAGO";

    /**
     * Constante que indica que el proceso pago del turno de certificado fue
     * exitoso y que desea mostrar la confirmacion.
     */
    public static final String CONFIRMACION = "CONFIRMACION";

    /**
     * Constante que indica que se va a hacer el registro del pago
     */
    public static final String REGISTRAR_PAGO = "REGISTRAR_PAGO";

    /**
     * Constante que indica que se estan creando turnos de certificados
     * individuales y hay que regresar a la página inicial para crea uno nuevo
     */
    public static final String CREAR_NUEVO_TURNO_CERTIFICADOS = "CREAR_NUEVO_TURNO_CERTIFICADOS";

    public static final String CONSULTA_SIMPLE_INMEDIATA = "CONSULTA_SIMPLE_INMEDIATA";

    public static final String TIPO_CONSULTA_SIMPLE = "SIMPLE";

    public static final String TIPO_CONSULTA_CONSTANCIA = "CONSTANCIA";

    /**
     * Constante que indica que se cancelo la el proceso de liquidacion
     */
    public static final String CANCELADO = "CANCELADO";

    /**
     *
     */
    public void doStart(HttpServletRequest arg0) {
    }

    /**
     * Este método lo que hace es la verificacion de los diferentes objectos que
     * se encuentran el la sesion, y deacuerdo a esa verificacion manda una
     * respuesta para que sea procesada y asi poder tener una navegacion
     * acertada.
     */
    public String procesarNavegacion(HttpServletRequest request)
            throws ControlNavegacionException {
        String accion = request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                    "La accion enviada por la accion web no es valida");
        }

        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        String nombreProceso = proceso.getNombre();
        nombreProceso = nombreProceso.replaceAll(" ", "_");

        if (accion.equals(AWPago.PROCESAR)) {

            Turno turno = (Turno) request.getSession().getAttribute(WebKeys.TURNO);
            if (turno != null) {
                Solicitud solicitud = turno.getSolicitud();
                if (solicitud != null && solicitud instanceof SolicitudCertificado) {

                    Integer impreso = (Integer) request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
                    if (impreso != null && impreso.intValue() != 0) {
                        return CONFIRMACION_ERROR_IMPRESION;
                    }
                    /**
                     * En caso de que el certificado no sea inmediato se muestra
                     * el turno
                     */
                    if (((String) (request.getSession().getAttribute(WebKeys.CERTIFICADO_TIPO))).equals(CTipoCertificado.TIPO_INMEDIATO_ID)) {
                        request.getSession().removeAttribute(WebKeys.CERTIFICADO_TIPO);
                        request.getSession().removeAttribute(WebKeys.TURNO);
                        return CREAR_NUEVO_TURNO_CERTIFICADOS;
                    } else {
                        //request.getSession().removeAttribute(CTipoCertificado.TIPO_INMEDIATO_ID);
                        return CONFIRMACION;
                    }
                } else if (solicitud != null && solicitud instanceof SolicitudConsulta) {
                    SolicitudConsulta solCons = (SolicitudConsulta) solicitud;
                    if (solCons.getTipoConsulta().getNombre().equals(TIPO_CONSULTA_SIMPLE)
                            || solCons.getTipoConsulta().getNombre().equals(TIPO_CONSULTA_CONSTANCIA)) {
                        Fase fase = new Fase();
                        fase.setID(CFase.CON_CONSULTA_SIMPLE);
                        request.getSession().setAttribute(WebKeys.FASE, fase);
                        return CONSULTA_SIMPLE_INMEDIATA;
                    }

                }
            }

            if (request.getSession().getAttribute(WebKeys.TURNO) != null) {

                Integer impreso = (Integer) request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
                if (impreso != null && impreso.intValue() != 0) {
                    return CONFIRMACION_ERROR_IMPRESION;
                }

                return SOLICITUD_OK;

            }

            if (request.getSession().getAttribute(WebKeys.PAGO) != null) {

                Integer impreso = (Integer) request.getSession().getAttribute(WebKeys.ID_IMPRIMIBLE);
                if (impreso != null && impreso.intValue() != 0) {
                    return CONFIRMACION_ERROR_IMPRESION;
                }

                return SOLICITUD_OK;

            }

            return SOLICITUD_FAILED;
        } else if (accion.equals(AWPago.PROCESAR_REGISTRO_CONTINUAR)) {
            Boolean esCajeroResgistro = (Boolean) request.getSession().getAttribute(WebKeys.ES_CAJERO_REGISTRO);
            if (esCajeroResgistro != null && esCajeroResgistro.booleanValue()) {
                return PROCESAR_REGISTRO_CONTINUAR;
            } else {
                return SOLICITUD_OK;
            }
        } else if (accion.equals(AWPago.ADICIONAR_APLICACION)
                || accion.equals(AWPago.ELIMINAR_APLICACION)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.CARGAR_FORMAS_PAGO) 
                || accion.equals(AWPago.CARGAR_CAMPOS_CAPTURA_X_FORMA) 
                || accion.equals(AWPago.CUENTAS_X_CIRCULO_BANCO)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.VALIDAR)
                || accion.equals(AWPago.ELIMINAR_APLICACION_VALIDACION)
                || accion.equals(AWPago.ADICIONAR_APLICACION_VALIDACION)) {
            if (((Pago) request.getSession().getAttribute(WebKeys.PAGO)).getAplicacionPagos()
                    .isEmpty()) {
                return RELISTAR_APLICACIONES_ + proceso.getNombre();
            }

            return VALIDAR_PAGO;
        } else if (accion.equals(AWPago.BUSCAR_CONSIGNACION)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.BUSCAR_CHEQUE)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } /*
         * @autor         : HGOMEZ 
         * @mantis        : 12422 
         * @Requerimiento : 049_453 
         * @descripcion   : Se hacen los ajustes respectivos para que SIR controle el
         * flujo cuando se seleccionan las nuevas formas de pago.
         */ else if (accion.equals(AWPago.BUSCAR_PAGO_TARJETA_CREDITO)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.BUSCAR_PAGO_TARJETA_DEBITO)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.BUSCAR_PAGO_PSE)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if(accion.equals(AWPago.BUSCAR_GENERAL)){
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if (accion.equals(AWPago.REMOVER_INFO)) {
            return CANCELADO;
        } 

        return null;
    }

    /**
     *
     */
    public void doEnd(HttpServletRequest request) {
    }
}
