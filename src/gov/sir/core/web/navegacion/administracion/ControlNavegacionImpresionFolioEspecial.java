/*
 * Created on 25/10/2004
 *
 */
package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWImpresionFolioEspecial;
import gov.sir.core.web.acciones.certificado.AWCertificadoEspecial;
import static gov.sir.core.web.navegacion.certificado.ControlNavegacionCertificadoEspecial.AGREGAR_OK;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.SMARTKeys;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 * Control de Navegación para <CODE>AWImpresionFolioEspecial</CODE>
 *
 * @author jmendez
 *
 */
public class ControlNavegacionImpresionFolioEspecial implements ControlNavegacion {

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
    public ControlNavegacionImpresionFolioEspecial() {
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
            if (accion.equals(AWImpresionFolioEspecial.IMPRIMIR_FOLIO)) {
                return AWImpresionFolioEspecial.IMPRIMIR_FOLIO;
            } else if (accion.equals(AWImpresionFolioEspecial.TERMINA)) {
                return AWImpresionFolioEspecial.TERMINA;
            } else if (accion.equals(AWImpresionFolioEspecial.REIMPRIMIR_RECIBO) || accion.equals(AWImpresionFolioEspecial.REIMPRIMIR_RECIBOS)) {
                /*
			 * List estaciones = (List)request.getSession().getAttribute(AWImpresionFolioEspecial.LISTA_ESTACIONES);
			 *if(estaciones!=null){
			 *return DETERMINAR_ESTACION_REIMPRESION_RECIBOS;
			 *}
                 */
                return AWImpresionFolioEspecial.TERMINA;
            } else if (accion.equals(AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO)) {
                return AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO;
            } else if (accion.equals(AWCertificadoEspecial.CERTIFICADO_ESPECIAL)){
                return AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO;
            }  else if (accion.equals(AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO_PERTENENCIA)) {
                return AWImpresionFolioEspecial.IMPRIMIR_CERTIFICADO_PERTENENCIA;
            } else if (accion.equals(AWImpresionFolioEspecial.REIMPRIMIR_CONSULTA)) {
                return AWImpresionFolioEspecial.REIMPRIMIR_CONSULTA;
            } else if (accion.equals(AWImpresionFolioEspecial.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO)) {
                return AWImpresionFolioEspecial.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO;
            } else if (accion.equals(AWImpresionFolioEspecial.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA)) {
                return AWImpresionFolioEspecial.OBTENER_IMPRESORAS_CIRCULO_CERTIFICADO_PERTENENCIA;
            } else if (accion.equals(AWImpresionFolioEspecial.OBTENER_ULTIMO_TURNO_IMPRESO) || accion.equals(AWImpresionFolioEspecial.ULTIMO_TURNO_IMPRESO_PROCESO) || accion.equals(AWImpresionFolioEspecial.REGRESAR_REIMPRIMIR_RECIBO)) {
                return AWImpresionFolioEspecial.OBTENER_ULTIMO_TURNO_IMPRESO;
            } else if (accion.equals(AWImpresionFolioEspecial.PROGRAMAR_TAREA)) {
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
