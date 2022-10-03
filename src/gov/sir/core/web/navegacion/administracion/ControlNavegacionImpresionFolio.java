/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWImpresionFolio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWImpresionFolio</CODE>
 *
 * @author jmendez
 *
 */
public class ControlNavegacionImpresionFolio implements ControlNavegacion {

    /**
     * Constante que reenvia la navegació a preguntar cuáles la estación que se
     * va a usar para sacar el consecutivo de recibos.
     */
    public static final String DETERMINAR_ESTACION_REIMPRESION_RECIBOS = "DETERMINAR_ESTACION_REIMPRESION_RECIBOS";

    public static final String AGREGAR_DE_ARCHIVO_OK = "AGREGAR_DE_ARCHIVO_OK";

    public static final String PROGRAMAR_TAREA_OK = "PROGRAMAR_TAREA_OK";

    /**
     * Constructor por Default de <CODE>ControlNavegacionImpresionFolio</CODE>
     */
    public ControlNavegacionImpresionFolio() {
        super();
    }

    /**
     * Prepara el procesamiento de la navegación.
     *
     * @param request
     */
    public void doStart(HttpServletRequest request) {

    }

    /**
     * Método que procesa la siguiente acción de navegación dentro del flujo de
     * pantallas
     *
     * @param request
     * @return nombre de la acción siguiente
     * @throws ControlNavegacionException
     */
    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        String accion = request.getParameter(WebKeys.ACCION);

        HttpSession sesion = request.getSession();

        boolean isMultipart = FileUpload.isMultipartContent(request);

        if (!isMultipart) {
            if ((accion == null) || accion.equals("")) {
                throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
            }
            if (accion.equals(AWImpresionFolio.IMPRIMIR_FOLIO)) {
                return AWImpresionFolio.IMPRIMIR_FOLIO;
            } else if (accion.equals(AWImpresionFolio.TERMINA)) {
                return AWImpresionFolio.TERMINA;
            } else if (accion.equals(AWImpresionFolio.REIMPRIMIR_RECIBO) || accion.equals(AWImpresionFolio.REIMPRIMIR_RECIBOS)) {
                /*
			 * List estaciones = (List)request.getSession().getAttribute(AWImpresionFolio.LISTA_ESTACIONES);
			 *if(estaciones!=null){
			 *return DETERMINAR_ESTACION_REIMPRESION_RECIBOS;
			 *}
                 */
                return AWImpresionFolio.TERMINA;
            } else if (accion.equals(AWImpresionFolio.IMPRIMIR_CERTIFICADO)) {
                return AWImpresionFolio.IMPRIMIR_CERTIFICADO;
            } else if (accion.equals("BACK")){
                return AWImpresionFolio.IMPRIMIR_CERTIFICADO;
            } else if (accion.equals(AWImpresionFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA)) {
                return AWImpresionFolio.IMPRIMIR_CERTIFICADO_PERTENENCIA;
            } else if (accion.equals(AWImpresionFolio.REIMPRIMIR_CONSULTA)) {
                return AWImpresionFolio.REIMPRIMIR_CONSULTA;
            } else if (accion.equals(AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO)) {
                return AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO;
            } else if (accion.equals(AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA)) {
                return AWImpresionFolio.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA;
            } else if (accion.equals(AWImpresionFolio.OBTENER_ULTIMO_TURNO_IMPRESO) || accion.equals(AWImpresionFolio.ULTIMO_TURNO_IMPRESO_PROCESO) || accion.equals(AWImpresionFolio.REGRESAR_REIMPRIMIR_RECIBO)) {
                return AWImpresionFolio.OBTENER_ULTIMO_TURNO_IMPRESO;
            } else if (accion.equals(AWImpresionFolio.PROGRAMAR_TAREA)) {
                return PROGRAMAR_TAREA_OK;
            }
        } else {
            String ultimaVista = (String) request.getSession().getAttribute(SMARTKeys.VISTA_ACTUAL);
            if (ultimaVista.equals("admin.imprimirfolio.view")) {
                return AGREGAR_DE_ARCHIVO_OK;
            }
            return null;
        }

        return null;
    }

    /**
     * Finalización de la navegación
     *
     * @param request
     * @see
     * org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {

    }

}
