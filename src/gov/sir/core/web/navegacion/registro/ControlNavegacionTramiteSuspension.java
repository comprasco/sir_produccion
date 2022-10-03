/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.navegacion.registro;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWTramiteSuspension;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileUpload;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 *
 * @author developer5
 */
public class ControlNavegacionTramiteSuspension implements ControlNavegacion {

    public ControlNavegacionTramiteSuspension() {
        super();
    }

    public void doStart(HttpServletRequest request) {
    }

    public String procesarNavegacion(HttpServletRequest request)
            throws ControlNavegacionException {

        HttpSession session = request.getSession();
        boolean isMultipart = FileUpload.isMultipartContent(request);
        String accion = "";

        if (isMultipart) {
            String accionMultipart = (String) session.getAttribute(WebKeys.ACCION);
            if (accionMultipart != null) {
                if (accionMultipart.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_PREV)) {
                    return AWTramiteSuspension.AGREGAR_RESPUESTA_PREV;
                } else if (accionMultipart.equals(AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP)) {
                    return AWTramiteSuspension.AGREGAR_RESPUESTA_TEMP;
                } else if (accionMultipart.equals(AWTramiteSuspension.AVANZAR)) {
                    return AWTramiteSuspension.AVANZAR;
                } else if (accionMultipart.equals(AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP)) {
                    return AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_TEMP;
                } else if (accionMultipart.equals(AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_PREV)) {
                    return AWTramiteSuspension.CANCELAR_TRAMITE_SUSP_PREV;
                }
            } else {
                return null;
            }

        } else {
            accion = request.getParameter(WebKeys.ACCION);
        }

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
        }

        return null;
    }

    public void doEnd(HttpServletRequest request) {

    }

}
