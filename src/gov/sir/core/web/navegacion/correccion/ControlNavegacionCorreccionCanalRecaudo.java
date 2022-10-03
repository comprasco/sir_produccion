/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sir.core.web.navegacion.correccion;

import gov.sir.core.negocio.modelo.Proceso;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.correccion.AWCorreccionCanalRecaudo;
import javax.servlet.http.HttpServletRequest;
import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 *
 * @author developer5
 */
public class ControlNavegacionCorreccionCanalRecaudo implements ControlNavegacion {

    public static final String MODIFICAR_FORMA_PAGO= "MODIFICAR_FORMA_PAGO";
    public static final String RELISTAR_APLICACIONES_ = "RELISTAR_APLICACIONES_";
    
    public ControlNavegacionCorreccionCanalRecaudo() {
        super();
    }

    public void doStart(HttpServletRequest hsr) {
    }

    @Override
    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);
        
        Proceso proceso = (Proceso) request.getSession().getAttribute(WebKeys.PROCESO);
        String nombreProceso = proceso.getNombre();
        nombreProceso = nombreProceso.replaceAll(" ", "_");
        
        if (accion.equals(AWCorreccionCanalRecaudo.MODIFICAR_FORMA_PAGO)) {
            return MODIFICAR_FORMA_PAGO;
        } else if (accion.equals(AWCorreccionCanalRecaudo.CARGAR_FORMAS_PAGO)
                || accion.equals(AWCorreccionCanalRecaudo.CARGAR_CAMPOS_CAPTURA_X_FORMA)
                || accion.equals(AWCorreccionCanalRecaudo.CUENTAS_X_CIRCULO_BANCO)) {
            return RELISTAR_APLICACIONES_ + nombreProceso;
        } else if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
        }
        
        return null;
    }

    public void doEnd(HttpServletRequest hsr) {

    }

}
