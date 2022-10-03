/*
 * Created on 27-sep-2004
*/
package gov.sir.core.web.navegacion.registro;

import gov.sir.core.negocio.modelo.Fase;
import gov.sir.core.negocio.modelo.constantes.CFase;
import gov.sir.core.web.WebKeys;
import gov.sir.core.web.acciones.registro.AWConfrontacion;

import org.auriga.smart.web.navegacion.ControlNavegacion;
import org.auriga.smart.web.navegacion.ControlNavegacionException;

import javax.servlet.http.HttpServletRequest;


/**
 * @author dlopez
*/
public class ControlNavegacionConfrontacion implements ControlNavegacion {
	
	public static final String ASOCIAR_OK = "ASOCIAR_OK";
	public static final String CORRECTIVA_ASOCIAR_OK = "CORRECTIVA_ASOCIAR_OK";
	public static final String ASOCIAR_FAILED = "ASOCIAR_FAILED";		
	public static final String CONFIRMAR_OK= "CONFIRMAR_OK";
	public static final String ELIMINAR_OK= "ELIMINAR_OK";
	public static final String CORRECTIVA_ELIMINAR_OK= "CORRECTIVA_ELIMINAR_OK";
	public static final String VER_RADICACION ="VER_RADICACION";
	public static final String GURDAR_CAMBIOS_CONFRONTACION = "GURDAR_CAMBIOS_CONFRONTACION";
	
    public ControlNavegacionConfrontacion() {
        super();
    }

    public void doStart(HttpServletRequest request) {
    }

    public String procesarNavegacion(HttpServletRequest request) throws ControlNavegacionException {
        String accion = (String) request.getParameter(WebKeys.ACCION);

        if ((accion == null) || accion.equals("")) {
            throw new ControlNavegacionException(
                "La accion enviada por la accion web no es valida");
        }
        if (accion.equals(AWConfrontacion.ASOCIAR_UNA_MATRICULA)) {        	
        	Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
        	if(fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				return CORRECTIVA_ASOCIAR_OK;
        	}
			return ASOCIAR_OK;
        } else if (accion.equals(AWConfrontacion.CONFIRMAR)) {
            return CONFIRMAR_OK;    
        } else if (accion.equals(GURDAR_CAMBIOS_CONFRONTACION)){
        	return ASOCIAR_OK;
        } else if (accion.equals(AWConfrontacion.ELIMINAR)) {
			Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
			if(fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				return CORRECTIVA_ELIMINAR_OK;
			}
			return ELIMINAR_OK;			
		} else if (accion.equals(AWConfrontacion.ASOCIAR_UN_RANGO)){
			Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
			if(fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				return CORRECTIVA_ASOCIAR_OK;
			}
			return ASOCIAR_OK;
        } else if (accion.equals(AWConfrontacion.VER_RADICACION)){        	 
			return VER_RADICACION;
	    } else if (accion.equals(AWConfrontacion.OCULTAR_DATOS_ANTIGUO_SISTEMA)){ 
			Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
			if(fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				return CORRECTIVA_ASOCIAR_OK;
			}
			return ASOCIAR_OK;
		} else if (accion.equals(AWConfrontacion.ACTUALIZAR_DATOS_ANTIGUO_SISTEMA)){ 
			Fase fase = (Fase)request.getSession().getAttribute(WebKeys.FASE);
			if(fase.getID().equals(CFase.CAL_CONFRONTACION_CORRECTIVA)){
				return CORRECTIVA_ASOCIAR_OK;
			}
			return ASOCIAR_OK;
		} else {
            return null;
        }
    }

    /**
     *
     */
    public void doEnd(HttpServletRequest request) {
    }
}
