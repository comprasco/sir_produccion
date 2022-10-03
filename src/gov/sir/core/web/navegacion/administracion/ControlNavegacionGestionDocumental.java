package gov.sir.core.web.navegacion.administracion;

import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.administracion.AWGestionDocumental;

import javax.servlet.http.HttpServletRequest;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

/**
 *Control de Navegaci�n para <CODE>AWGestionDocumental</CODE>
 * @author erobles
 */
public class ControlNavegacionGestionDocumental implements ControlNavegacion {

    /**
     *  Constructor por Default de <CODE>ControlNavegacionTrasladoTurno</CODE>
     */
    public ControlNavegacionGestionDocumental() {
            super();
    }

    /**
     *
     * Prepara el procesamiento de la navegaci�n.
     * @param request
     */
    public void doStart(HttpServletRequest request) {

    }

    /**
     * M�todo que procesa la siguiente acci�n de navegaci�n dentro del flujo de pantallas
     * @param request
     * @return nombre de la acci�n siguiente
     * @throws ControlNavegacionException
     */
    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);
        
        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException("La accion enviada por la accion web no es valida");
        }else if (accion.equals(AWGestionDocumental.SELECCIONA_CIRCULO)){
            return AWGestionDocumental.LISTA_TURNOS_GESTION_DOCUMENTAL;
        }else if (accion.equals(AWGestionDocumental.TURNO_REENCOLAR)) {
	    return AWGestionDocumental.TURNO_REENCOLAR;
        }else if (accion.equals(AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA)) {
	    return AWGestionDocumental.SELECCIONA_TURNOS_POR_FECHA;
        }else if (accion.equals(AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA)) {
	    return AWGestionDocumental.DEPURAR_TURNOS_POR_FECHA;
	}else if (accion.equals(AWGestionDocumental.TERMINA)) {
	    return AWGestionDocumental.TERMINA;
	}
        
        return null;
    }

    /**
     * Finalizaci�n de la navegaci�n
     *
     * @param request
     * @see org.auriga.smart.web.navegacion.ControlNavegacion#doEnd(javax.servlet.http.HttpServletRequest)
     */
    public void doEnd(HttpServletRequest arg0) {

    }

}
